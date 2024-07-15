package com.sudedaka.spacegame;

import com.badlogic.gdx.Gdx;

public class GameConstants {
    public static final String skin = "skin/glassy-ui.json";
    public static final String skinAtlas = "skin/glassy-ui.atlas";
    public static final int screenWidth = Gdx.graphics.getWidth();
    public static final int screenHeight = Gdx.graphics.getHeight();
    public static final int centerX = screenWidth/2;
    public static final int centerY = screenHeight/2;
    public static final int col_width = screenWidth/8;
    public static final int row_height = screenHeight/8;
    public static final float density = Gdx.graphics.getDensity();
}
