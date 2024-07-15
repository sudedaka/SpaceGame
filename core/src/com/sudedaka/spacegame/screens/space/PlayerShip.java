package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {

    int lives=0;
    public PlayerShip(float xCentre, float yCentre,
                      float width, float height,
                      float movementSpeed, int shield,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, shieldTextureRegion, laserTextureRegion);
        lives=3;

    }
    @Override
    public com.sudedaka.spacegame.screens.space.Laser[] fireLasers() {

        com.sudedaka.spacegame.screens.space.Laser[] laser = new com.sudedaka.spacegame.screens.space.Laser[2];
        laser[0] = new com.sudedaka.spacegame.screens.space.Laser(boundingBox.x+boundingBox.width*0.07f,boundingBox.y+boundingBox.height*0.45f,laserWidth,laserHeight,laserMovementSpeed,laserTextureRegion);
        laser[1] = new com.sudedaka.spacegame.screens.space.Laser(boundingBox.x+boundingBox.width*0.93f,boundingBox.y+boundingBox.height*0.45f,laserWidth,laserHeight,laserMovementSpeed,laserTextureRegion);

        timeSinceLastShot =0;

        return laser;
    }

}
