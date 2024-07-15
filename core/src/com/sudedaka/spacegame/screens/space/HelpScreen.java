package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sudedaka.spacegame.GameConstants;
import com.sudedaka.spacegame.Main;

public class HelpScreen implements Screen {

    Main game;
    private final float World_Width = 72;
    private final float World_Height = 128;
    private SpriteBatch batch;
    Texture background;
    private Stage stage;
    private Skin mySkin;

    public HelpScreen(final Main game)
    {
        this.game = game;
        batch = new SpriteBatch();
        game.myAssetManager.queueAddSkin();
        game.myAssetManager.manager.finishLoading();
        mySkin = game.myAssetManager.manager.get(GameConstants.skin);

        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);
        background = new Texture("helppng.png");

        mySkin.setScale(GameConstants.density);
        Button okBtn = new TextButton("OK",mySkin);
        okBtn.setSize(GameConstants.col_width * 2, GameConstants.row_height / 2);
        okBtn.setPosition(GameConstants.screenWidth / 6 * 4, GameConstants.screenWidth / 8 - okBtn.getHeight());
        okBtn.addListener(new InputListener(){
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

        stage.addActor(okBtn);
    }




    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background,0,0, GameConstants.screenWidth,GameConstants.screenHeight);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width,height);
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

    }

    @Override
    public void show() {

    }
}
