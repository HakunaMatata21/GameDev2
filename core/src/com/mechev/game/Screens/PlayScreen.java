package com.mechev.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mechev.game.MarioBros;
import com.mechev.game.Scenes.Hud;
import com.mechev.game.Sprites.Mario;
import com.mechev.game.Tools.B2WorldCreator;

/**
 * Created by void on 05.06.17.
 */

public class PlayScreen implements Screen
{
    private MarioBros game;
    private TextureAtlas atlas; //За импортването на големия файл от TexturePacker.

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
//Box2d Променливи.
    private World world;
    private Box2DDebugRenderer b2dr; //Представлява графично изобразяване  на телата във  box2d света.
private Mario player; //Mario Class Object


    public PlayScreen(MarioBros game)
    {
atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;

        gamecam = new OrthographicCamera(); //Създаване на камера която да следи марио в камера-светът.
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gamecam); //Създаване на FitViewport който да държи една и съща пропорция на екрана въпреки размера на екрана.

        hud = new Hud(game.batch);//за показване на точките/кръвта/нивоти и т.н

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);// За да центрираме камерата в центъра на екрана.

        world = new World(new Vector2(0, -10), true); //инициализиране на светът - първите 2 стойности са за гравитацията,буулеан-а е дали искаме да приспим обектите който не правят нищо в момента.(бокс2д постоянно прави изчисления и ако един обект е заспал,просто не го включва в изчисленията докато не му зададем команда.)
        player = new Mario(world,this); // Инициализиране на марио.
        b2dr = new Box2DDebugRenderer();
        b2dr.SHAPE_STATIC.set(1, 0, 0, 1); //Прави очертанията на box2d червени.
        new B2WorldCreator(world,map); //За да се изпълни класът със създаването на Box2D светът.
    }
    @Override
    public void show()
    {

    }

    public TextureAtlas getAtlas()
    {
     return atlas;
    }

    public void handleInput(float dt) //Ще проверява постоянно за натиснато копче или нещо подобно.
    {
if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) //При натискане на копчето.
        player.b2body.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true); //Прилагане на сила-първите 2 са Х и У а getworldcenter е къде точно искаме да приложим тази сила.в случаят го слагаме да е в центъра за да може всичко да е нормално,3тият параметър е дали тази сила ще събуди обекта.

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) //За да проверим дали копчето се задържа,освен това проверяваме дали марио се движи по бързо от 2.
   player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true); //Прилагане на сила(0.1 хоризонтално,0 вертикално,централно)

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) //За да проверим дали копчето се задържа,освен това проверяваме дали марио се движи по бързо от 2.
        player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true); //Прилагане на сила(0.1 хоризонтално,0 вертикално,централно)
    }

    public void update(float dt) //ще update-ва всичко.
    {
        handleInput(dt); //Първо ще прослушва за контролиране от играча.
        world.step(1/60f,6,2); //TimeStep - Колко често да прави изчисления в случая е 60 пъти в секунда. (Другите 2 са за това колко прецизно да бъде изчислението при сблъсък на 2 тела например,но пък отнема повече време.)
        player.update(dt);

        gamecam.position.x = player.b2body.getPosition().x; //Позицията на камерата да зависи от позицията на марио,интересува ни движението му само по оста Х.
        gamecam.update(); //Обновяване с актуални координати нашата гейм камера.
        renderer.setView(gamecam); //Рендеръ да рисува само това което камерата вижда.
    }

    @Override
    public void render(float delta)
    {
        update(delta);

        //Изчисти екрана с черно.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //рендър за гейм картата.
        renderer.render(); //Рендер за игралната карта.

        //рендер за box2d дебъг линиите.
        b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //Кара батч-а да рисува сега това което вижда камерата.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw(); //Рисуване на сцената.
    }

    @Override
    public void resize(int width, int height)
    {
gamePort.update(width, height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        world.dispose();
    b2dr.dispose();
        hud.dispose();
    }
}
