package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

class Laser {
    float movementSpeed; //saniye başına geçecek zamanı
    Rectangle boundingBox;

    TextureRegion textureRegion;

    public Laser(float xCentre, float yBottom, float width, float height,float movementSpeed, TextureRegion textureRegion)
    {
        this.boundingBox = new Rectangle(xCentre - width/2, yBottom,width,height);
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch){
        batch.draw(textureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }
}
