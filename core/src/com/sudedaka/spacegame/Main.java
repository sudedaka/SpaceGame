package com.sudedaka.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sudedaka.spacegame.managers.MyAssetManager;
import com.sudedaka.spacegame.screens.MenuScreen;
import com.sudedaka.spacegame.screens.space.GameOver;
import com.sudedaka.spacegame.screens.space.GameScreen;
import com.sudedaka.spacegame.screens.space.HelpScreen;
import com.sudedaka.spacegame.screens.space.PauseScreen;
import com.sudedaka.spacegame.screens.space.SplashScreen;


import java.util.Random;

public class Main extends Game {
    //	com.sudedaka.spacegame.screens.space.GameScreen gameScreen;
    public static Random random = new Random();

    private SpriteBatch batch;
    public Viewport screenPort;
    public MyAssetManager myAssetManager = new MyAssetManager();

    @Override
    public void create() {

        batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        screenPort = new ScreenViewport();
        this.setScreen(new SplashScreen(this));
    }

    public void gotoGameScreen(){
        GameScreen gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
    public void gotoPauseScreen(){
        PauseScreen pauseScreen = new PauseScreen(this);
        setScreen(pauseScreen);
    }

    public void gotoGameOver(){
        GameOver gameOver = new GameOver(this);
        setScreen(gameOver);
    }
    public void gotoMenuScreen(){
        MenuScreen menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }
    public void gotoHelpScreen(){
        HelpScreen helpScreen = new HelpScreen(this);
        setScreen(helpScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {


    }

}
