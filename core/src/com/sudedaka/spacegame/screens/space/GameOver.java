package com.sudedaka.spacegame.screens.space;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

import java.util.Locale;


public class GameOver implements Screen {

    Main game;
    private Skin mySkin;
    private Stage stage;
    private SpriteBatch batch;
    Texture background;
    BitmapFont font;
    private final float World_Width = 72;
    private final float World_Height = 128;
    float verticalMargin, leftX, row1Y, row2Y, sectionWidth,centreX;

    public GameOver(final Main game) {

        this.game = game;
        game.myAssetManager.queueAddSkin();
        game.myAssetManager.manager.finishLoading();
        mySkin = game.myAssetManager.manager.get(GameConstants.skin);

        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Texture("space2.jpg");

        Label gameTitle = new Label("GAME OVER", mySkin, "big");
        gameTitle.setFontScale(GameConstants.density);
        gameTitle.setSize(GameConstants.col_width * 2, GameConstants.row_height * 2);
        gameTitle.setPosition(GameConstants.centerX - gameTitle.getWidth() / 2, GameConstants.centerY + GameConstants.row_height);
        gameTitle.setAlignment(Align.center);


        mySkin.setScale(GameConstants.density);

        Button againBtn = new TextButton("Again",mySkin);
        againBtn.setSize(GameConstants.col_width*5,GameConstants.row_height);
        againBtn.setPosition(GameConstants.centerX - againBtn.getWidth()/2,GameConstants.centerY);;
        againBtn.addListener(new InputListener(){
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
        menuBtn.setPosition(GameConstants.centerX -againBtn.getWidth()/2,againBtn.getY() - GameConstants.row_height -15);
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

        prepare();

        stage.addActor(gameTitle);
        stage.addActor(menuBtn);
        stage.addActor(againBtn);

    }

    public void prepare() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size =320;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(0, 0, 0, 1);
        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale( .2f,.2f);

        //  font.getData().setScale(0.1f);

        verticalMargin = font.getCapHeight() / 2;
        row1Y = (World_Height - verticalMargin)*1.5f;
        row2Y = row1Y - verticalMargin - font.getCapHeight();
        sectionWidth = World_Width / 3;
        leftX = verticalMargin*19;
        centreX = World_Width / 2;

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        batch.draw(background,0,0,GameConstants.screenWidth, GameConstants.screenHeight);

        font.draw(batch, "High Score", leftX, row1Y, sectionWidth, Align.center,false);
        font.draw(batch, String.format(Locale.getDefault(), "%06d", HighScoreHandler.getHighScore()), leftX, row2Y, sectionWidth, Align.center,false);

        font.draw(batch,"Score",centreX*6, row1Y, sectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%06d", GameScreen.SCORE), centreX*6, row2Y, sectionWidth, Align.center, false);


        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width,height);
    }

    @Override
    public void show() {
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
