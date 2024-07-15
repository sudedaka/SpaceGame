package com.sudedaka.spacegame.screens.space;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sudedaka.spacegame.Main;

class EnemyShip extends Ship {

    Vector2 directionVector;
    float timeSinceLastDirectionChange =0; //son konumdan geçen zaman
    float directionChangeFrequency = 0.75f; //düşman gemisinin hareket sıklığı

    public EnemyShip(float xCentre, float yCentre,
                     float width, float height, float movementSpeed,
                     int shield, float laserWidth, float laserHeight,
                     float laserMovementSpeed, float timeBetweenShots,
                     TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, shieldTextureRegion, laserTextureRegion);

        directionVector = new Vector2(0,-1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomDirectionVector() //Rastgele yön değişimi
    {
        double bearing = Main.random.nextDouble()* 6.283185; //0 v 1 arasında sayı vericek. //PI*2 de olabilirdi.
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if(timeSinceLastDirectionChange >directionChangeFrequency) //son konum 0.75ten büyükse yer değiştirme zamanıdır
        {
            randomDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency; // tekrarı önlemek için
        }
    }

    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.18f, boundingBox.y - laserHeight, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox.width * 0.82f, boundingBox.y - laserHeight, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0;
        return laser;
    }

    @Override
    public void draw(Batch batch)
    {
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if(shield >0)
        {
            batch.draw(shieldTextureRegion,boundingBox.x,boundingBox.y-boundingBox.height*0.2f,boundingBox.width,boundingBox.height);
        }
    }

}
