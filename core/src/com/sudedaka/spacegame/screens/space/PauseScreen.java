package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.sudedaka.spacegame.GameConstants;
import com.sudedaka.spacegame.Main;

public class PauseScreen implements Screen {

    final Main game;
    private Skin mySkin;
    private Stage stage;
    private SpriteBatch batch;
    Texture background;


    public PauseScreen(final Main game) {

        this.game = game;
        game.myAssetManager.queueAddSkin();
        game.myAssetManager.manager.finishLoading();
        mySkin = game.myAssetManager.manager.get(GameConstants.skin);

        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Texture("backgrr.png");

        Label gameTitle = new Label("PAUSED", mySkin, "big");
        gameTitle.setFontScale(GameConstants.density);
        gameTitle.setSize(GameConstants.col_width * 2, GameConstants.row_height * 2);
        gameTitle.setPosition(GameConstants.centerX - gameTitle.getWidth() / 2, GameConstants.centerY + GameConstants.row_height);
        gameTitle.setAlignment(Align.center);


        mySkin.setScale(GameConstants.density);
        Button restartBtn = new TextButton("Restart",mySkin);
        restartBtn.setSize(GameConstants.col_width*5,GameConstants.row_height);
        restartBtn.setPosition(GameConstants.centerX - restartBtn.getWidth()/2,GameConstants.centerY);
        restartBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoGameScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        Button menuBtn = new TextButton("Menu",mySkin);
        menuBtn.setSize(GameConstants.col_width*5,GameConstants.row_height);
        menuBtn.setPosition(GameConstants.centerX -menuBtn.getWidth()/2,restartBtn.getY() - GameConstants.row_height -15);
        menuBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoMenuScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        Button exitBtn = new TextButton("Exit",mySkin);
        exitBtn.setSize(GameConstants.col_width*5,GameConstants.row_height);
        exitBtn.setPosition(GameConstants.centerX -exitBtn.getWidth()/2,menuBtn.getY() - GameConstants.row_height -15);
        exitBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });



        stage.addActor(gameTitle);
        stage.addActor(restartBtn);
        stage.addActor(menuBtn);
        stage.addActor(exitBtn);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {

        batch.begin();
        batch.draw(background,0,0, GameConstants.screenWidth,GameConstants.screenHeight);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mySkin.dispose();
        stage.dispose();
    }
}
