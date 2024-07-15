package com.sudedaka.spacegame.screens.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sudedaka.spacegame.GameConstants;
import com.sudedaka.spacegame.Main;



import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class GameScreen implements Screen {

    //MENÜ
    private Main game;
    private Stage stage;
    private Skin mySkin;

    //Ekran
    private Camera camera;
    private Viewport viewport;

    //Grafikler
    private float backgroundHeight;
    private SpriteBatch batch;

    //Sesler
    private Sound explosionSound;
    private Sound laserPlayerSound;
    private Sound laserEnemySound;
    private Sound loseSound;

    private TextureAtlas textureAtlas;
    private Texture explosionTexture;

    private TextureRegion[] backgrounds;
    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion, enemyShipTextureRegion, enemyShieldTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion;

    //Zamanlama
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 3f; //3 saniyede bir düşman gemi oluşturulucak.
    private float enemySpawnTimer = 0;  //3 saniye ulaşınca zamanlayıcı kendisini sıfırlaması için.

    //Ekran parametreleri
    private final float World_Width = 72;
    private final float World_Height = 128;
    private final float Touch_Movement_Threshold = 5f; //dokunma hareket eşiği
    //Oyun objeleri
    private PlayerShip playerShip;
    private LinkedList<EnemyShip> enemyShipList;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;
    private LinkedList<Explosion> explosionList;

    private int score = 0;
    private int highScore;
    public static int SCORE;
    //FONT
    BitmapFont font;
    float verticalMargin, leftX, rightX, centreX, row1Y, row2Y, sectionWidth;


    public GameScreen(final Main game) {
        this.game = game;
        stage = new Stage(game.screenPort);
        mySkin = new Skin(Gdx.files.internal(GameConstants.skin));
        Gdx.input.setInputProcessor(stage);

        //SESLER
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosionsound.wav"));
        laserPlayerSound = Gdx.audio.newSound(Gdx.files.internal("sfx_laser1.ogg"));
        laserEnemySound = Gdx.audio.newSound(Gdx.files.internal("sfx_laser2.ogg"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("loseSound.wav"));

        camera = new OrthographicCamera();
        viewport = new StretchViewport(World_Width, World_Height, camera);

        textureAtlas = new TextureAtlas("Images.atlas");

        highScore = HighScoreHandler.getHighScore();

        //ARKA PLAN
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundHeight = World_Height * 2;
        backgroundMaxScrollingSpeed = (float) (World_Height) / 4;

        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_blue");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true);
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");

        explosionTexture = new Texture("explosion.png");

        //Oyun nesnesi ayarı
        playerShip = new PlayerShip(World_Width / 2, World_Height / 4, 10, 10, 48, 6, 0.4f, 4, 45, 0.5f, playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);
        enemyShipList = new LinkedList<>();

        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        explosionList = new LinkedList<>();

        batch = new SpriteBatch();

        mySkin.setScale(GameConstants.density);
        Button pauseBtn = new TextButton("Pause", mySkin);
        pauseBtn.setSize(GameConstants.col_width * 2, GameConstants.row_height / 2);
        pauseBtn.setPosition(GameConstants.screenWidth / 6 * 4, GameConstants.screenWidth / 8 - pauseBtn.getHeight());

        pauseBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoPauseScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
        stage.addActor(pauseBtn);

        prepare();
    }


    public void prepare() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 80;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
        font = fontGenerator.generateFont(fontParameter);

        font.getData().setScale(0.1f);
        verticalMargin = font.getCapHeight() / 2;
        leftX = verticalMargin;
        rightX = World_Width * 2 / 3 - leftX;
        centreX = World_Width / 3;
        row1Y = World_Height - verticalMargin;
        row2Y = row1Y - verticalMargin - font.getCapHeight();
        sectionWidth = World_Width / 3;
    }


    @Override
    public void render(float deltaTime) { //Oyun devam ettiği sürece devamlı çagrılan metot

        batch.begin();

        renderBackground(deltaTime);
        detectInput(deltaTime);
        playerShip.update(deltaTime);
        spawnEnemyShips(deltaTime);

        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) // tarayacak döngü kurulur.
        {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemy(enemyShip, deltaTime);
            enemyShip.update(deltaTime);
            enemyShip.draw(batch);
        }

        playerShip.draw(batch); //oyuncu gemisi

        renderLasers(deltaTime);
        detectCollisions(); //lazerler ve gemiler arasındaki çarpışmaların tespiti
        updateAndRenderExplosions(deltaTime);
        updateAndRenderFont();
        updateHighScore(deltaTime);

        batch.end();
        stage.act();
        stage.draw();

    }

    private void updateAndRenderFont() {
        //üst sıra:İsimler
        font.draw(batch, "Score", leftX, row1Y, sectionWidth, Align.left, false);
        font.draw(batch, "Shield", centreX, row1Y, sectionWidth, Align.center, false);
        font.draw(batch, "Lives", rightX, row1Y, sectionWidth, Align.right, false);
        //ikinci sıra: Değerleri
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), leftX, row2Y, sectionWidth, Align.right, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), centreX, row2Y, sectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), rightX, row2Y, sectionWidth, Align.right, false);
    }

    private void spawnEnemyShips(float deltaTime) {
        enemySpawnTimer += deltaTime;
        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyShipList.add(new EnemyShip(Main.random.nextFloat() * (World_Width - 10) + 5, World_Height - 5, 10, 10, 48, 3, 0.3f, 5, 50, 0.8f, enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
    }


    private void detectInput(float deltaTime) {
        //KLAVYE
        //geminin hareket edebileceği maksimum mesafeyi belirle
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = World_Width - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = (float) World_Height / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) //Eğer gemi sağa çekilmişse ve maksimum limit 0dan büyükse
        {
            playerShip.translate(Math.min(playerShip.movementSpeed * deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playerShip.translate(0f, Math.min(playerShip.movementSpeed * deltaTime, upLimit));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playerShip.translate(0f, Math.max(-playerShip.movementSpeed * deltaTime, downLimit));
        }
        //MOUSE-EL
        if (Gdx.input.isTouched()) {
            //dokunulan ekranın konumunu
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //world positiona çevir
            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);
            //x ve y farkı ayarla
            Vector2 playerShipCentre = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width / 2,
                    playerShip.boundingBox.y + playerShip.boundingBox.height / 2);
            float touchDistance = touchPoint.dst(playerShipCentre);
            if (touchDistance > Touch_Movement_Threshold) //dokunma uzaklığı > dokunma hareketi eşiğinden
            {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                //geminin maksimum hızı ayarı
                float xMove = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                playerShip.translate(xMove, yMove);
            }
        }
    }

    private void moveEnemy(EnemyShip enemyShip, float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float) World_Height / 2 - enemyShip.boundingBox.y;
        rightLimit = World_Width - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = World_Height - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        enemyShip.translate(xMove, yMove);
    }


    private void detectCollisions() {
        //  her bir oyuncu lazerinin düşman gemisine çarpmasının kontrolü
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext())
            {
                EnemyShip enemyShip = enemyShipListIterator.next();
                if (enemyShip.intersects(laser.boundingBox)) { //oyuncu lazeri, düşman gemiyi vurduğunda ortadan kaybolucak
                    if (enemyShip.hitAndCheckDestroyed(laser))
                    {
                        enemyShipListIterator.remove();
                        explosionList.add(new Explosion(explosionTexture, new Rectangle(enemyShip.boundingBox), 0.7f));
                        SCORE = score += 100;
                        explosionSound.play();
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }

        //her bir düşman lazerinin oyuncu gemisine çarpmasını kontrolü
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersects(laser.boundingBox)) { //oyuncu lazeri, düşman gemiyi vurduğunda ortadan kaybolucak
                if (playerShip.hitAndCheckDestroyed(laser)) {
                    explosionList.add(new Explosion(explosionTexture, new Rectangle(playerShip.boundingBox), 1.6f));
                    playerShip.lives--;

                    if (playerShip.lives == 0) {
                        loseSound.play();
                        game.gotoGameOver();
                    }
                }
                laserListIterator.remove();
            }
        }
    }

    private void updateAndRenderExplosions(float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) //patlama bittiyse ekrandan kalk
            {
                explosionListIterator.remove();

            } else //bitmediyse ekrana çiz
            {
                explosion.draw(batch);
            }
        }
    }

    private void renderLasers(float deltaTime) {
        //oyuncu lazerleri
        if (playerShip.canFireLaser()) //Eğer oyuncu gemisi ateşleyebiliyorsa
        {
            Laser[] lasers = playerShip.fireLasers();
            playerLaserList.addAll(Arrays.asList(lasers));
            laserPlayerSound.play();
        }

        //düşman lazerleri
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            {
                if (enemyShip.canFireLaser()) {
                    Laser[] lasers = enemyShip.fireLasers();
                    enemyLaserList.addAll(Arrays.asList(lasers));
                    laserEnemySound.play();
                }
            }
        }
        //eski lazerleri sil
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime; //oyuncunun gemisi olduğu için lazer yukarı doğru gidektir bu yüzden + yapıyorum ve geçen zamanı çarpıyorum.
            if (laser.boundingBox.y > World_Height)//eski lazerler ekranın dışına çıkmışsa kaldır
            {
                iterator.remove();
            }
        }

        iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y + laser.boundingBox.height < 0) {
                iterator.remove();
            }
        }
    }

    private void renderBackground(float deltaTime)
    {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) //arkaplan sayısı arttıkça en uzak katmanı ekrana gelene kadar yaz
        {
            //kaydırırken ekranın sonunun gelip gelmediğinin kontrolü
            if (backgroundOffsets[layer] > World_Height) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], World_Width, World_Height);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + World_Height, World_Width, World_Height);
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    public void updateHighScore(float deltaTime) {
        if (score > highScore) {
            highScore = score;
            HighScoreHandler.setHighScore(highScore);
        }
    }

    @Override
    public void dispose() {

        stage.dispose();
        laserEnemySound.dispose();
        loseSound.dispose();
        explosionSound.dispose();
        laserPlayerSound.dispose();
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



}
