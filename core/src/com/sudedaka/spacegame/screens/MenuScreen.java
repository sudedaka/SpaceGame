package com.sudedaka.spacegame.screens;


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
import com.sudedaka.spacegame.screens.space.HighScoreHandler;


public class MenuScreen implements Screen {

    final Main game;
    private Skin mySkin;
    private Stage stage;
    private SpriteBatch batch;
    Texture background;

    public MenuScreen(final Main game){

        this.game = game;
        batch = new SpriteBatch();
        game.myAssetManager.queueAddSkin();
        game.myAssetManager.manager.finishLoading();
        mySkin = game.myAssetManager.manager.get(GameConstants.skin);


        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);

        String path = "mainbackground.png";
        background = new Texture(path);


        Label gameTitle = new Label("GAME MENU",mySkin,"big");
        gameTitle.setFontScale(GameConstants.density);
        gameTitle.setSize(GameConstants.col_width*2,GameConstants.row_height*2);
        gameTitle.setPosition(GameConstants.centerX - gameTitle.getWidth()/2,GameConstants.centerY + GameConstants.row_height);
        gameTitle.setAlignment(Align.center);


        mySkin.setScale(GameConstants.density);

        Button spaceBtn = new TextButton("PLAY",mySkin);
        spaceBtn.setSize(GameConstants.col_width*5,GameConstants.row_height);
        spaceBtn.setPosition(GameConstants.centerX - spaceBtn.getWidth()/2,GameConstants.centerY);
        spaceBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoGameScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoGameScreen();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        Button helpBtn = new TextButton("HELP",mySkin);
        helpBtn.setSize(GameConstants.col_width*5,GameConstants.row_height);
        helpBtn.setPosition(GameConstants.centerX - helpBtn.getWidth()/2,spaceBtn.getY() - GameConstants.row_height -15);
        helpBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoHelpScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        stage.addActor(gameTitle);
        stage.addActor(spaceBtn);
        stage.addActor(helpBtn);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        batch.begin();
        batch.draw(background,0,0,GameConstants.screenWidth,GameConstants.screenHeight);
        HighScoreHandler.load();
        batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height)

    {
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
        mySkin.dispose();
        stage.dispose();

    }

}
