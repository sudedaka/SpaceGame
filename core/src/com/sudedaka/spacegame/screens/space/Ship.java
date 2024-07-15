package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    float movementSpeed;
    int shield;



    //pozisyon ve konumları
    float xPosition,yPosition;
    float width,height;
    Rectangle boundingBox;

    //lazer bilgileri
    float laserWidth,laserHeight;
    float laserMovementSpeed; //lazerin oynama süresi
    float timeBetweenShots; //iki geminin lazerlerin vurma süresi
    float timeSinceLastShot=0; // en son lazer ne zaman ateşlendi

    //grafikler
    TextureRegion shipTextureRegion,shieldTextureRegion,laserTextureRegion;

    public Ship(float xCentre, float yCentre, float width, float height,float movementSpeed,int shield,
                float laserWidth,float laserHeight,float laserMovementSpeed, float timeBetweenShots,
                TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,TextureRegion laserTextureRegion)
    {
        this.shield = shield;
        this.movementSpeed = movementSpeed;
        this.boundingBox = new Rectangle(xCentre - width/2, yCentre - height/2,width,height);
        this.laserWidth =laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;



    }

    public void update(float deltaTime)  //en son lazer ne zaman ateşlendi kısmı
    {
        timeSinceLastShot +=deltaTime;
    }
    public boolean canFireLaser ()//gemiye lazer ateşleyebilecek durumda olup olmadığını sorduğu kısım
    {
        //iki geminin birbirini vurma süresinin yeterince zaman geçip geçmediğini karşılaştırdım. Yani gemiye vurucak sonra sıradaki lazeri atıcak
        boolean result = (timeSinceLastShot - timeBetweenShots >=0 );
        return  result;
    }

    public abstract com.sudedaka.spacegame.screens.space.Laser[] fireLasers();

    public void translate(float xChange, float yChange)
    {
        boundingBox.setPosition(boundingBox.x + xChange ,boundingBox.y+yChange);
    }
    public void draw(Batch batch)
    {
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if(shield>0) //kalkanı yoksa geminin kalkan çiz
        {
            batch.draw(shieldTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        }
    }

    public boolean intersects(Rectangle otherRectangle)
    {
        return boundingBox.overlaps(otherRectangle);
    }
    public boolean hitAndCheckDestroyed(com.sudedaka.spacegame.screens.space.Laser laser)
    {
        if(shield>0)
        {
            shield--;

            return false;
        }
        return  true;
    }
}