package com.sudedaka.spacegame.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sudedaka.spacegame.GameConstants;

public class MyAssetManager {
    public final AssetManager manager = new AssetManager();

    public void queueAddSkin(){
        SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter(GameConstants.skinAtlas);
        manager.load(GameConstants.skin, Skin.class,parameter);
    }
}
