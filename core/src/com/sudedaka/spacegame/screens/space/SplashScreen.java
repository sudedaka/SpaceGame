package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.sudedaka.spacegame.Main;
import com.sudedaka.spacegame.screens.MenuScreen;

public class SplashScreen implements Screen {

    final Main game;
    private Texture splashTexture;
    private Image splashImage;
    private  Stage splashStage;
    private float WIDTH,HEIGHT;
    private   Sound splashSound;

    private StretchViewport viewport;
    private OrthographicCamera camera;
    private SpriteBatch batch;



    public SplashScreen(final Main game) {
        this.game = game;
        WIDTH = 1280;
        HEIGHT = 720;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH,HEIGHT,camera);
        viewport.apply();
        camera.position.set(WIDTH/2,HEIGHT/2,0);
        camera.update();

        splashTexture = new Texture(Gdx.files.internal("splash.png"));
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        splashImage = new Image(splashTexture);
        splashImage.setSize(WIDTH,HEIGHT);


        splashStage = new Stage(new StretchViewport(WIDTH,HEIGHT, new AndroidCamera(WIDTH,HEIGHT)));
        splashStage.addActor(splashImage);

        splashImage .addAction(Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(1.25F),Actions.delay(1F), Actions.fadeOut(0.75F)));
        splashSound = Gdx.audio.newSound(Gdx.files.internal("splashsound.wav"));
        splashSound.play();
        splashSound.loop(1);


    }



    private void handleInput() {
        if (Gdx.input.justTouched()) {
            game.setScreen(new MenuScreen(game));
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        handleInput();

        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        splashStage.act();
        splashStage.draw();




    }

    @Override
    public void resize(int width, int height) {
        splashStage.getViewport().update(width,height,true);

        viewport.update(width,height);
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
    public void dispose()
    {
        splashSound.dispose();
    }
}
