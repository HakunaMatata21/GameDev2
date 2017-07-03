package com.mechev.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mechev.game.MarioBros;
import com.mechev.game.Screens.PlayScreen;

/**
 * Created by void on 14.06.17.
 */

public class Mario extends Sprite
{
    public enum State {FALLING,JUMPING,STANDING,RUNNING}; //Това ще са състоянията на спрайт-а на марио
    public State currentState;
    public State previousState;
public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private boolean runningRight; //За да знаем посоката на марио.
    private float stateTimer; //Таймер за спрайтовете на състоянията.

    public Mario(World world, PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0; //Рестартиране на таймера.
        runningRight = true; //Посоката е дясно по-подразбиране.

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1;i<4;i++)
        {
frames.add(new TextureRegion(getTexture(),i * 16,0,16,16)); //16,0,16,16 - при взимане от спрайт-листа ще умножи от първия кадър който ни е нужен(в случая ни трябват 1,2,3(първият кадър демек 0 е как марио стой прав)16 е за Х оста защото кадърите са разделени на 16х16,0 е за У оста,другите 16,16 са размерите.)
        marioRun = new Animation<TextureRegion>(0.1f,frames);//Първата стойност е продължителността на всеки кадър.
            frames.clear();
        }

        for(int i = 4;i < 6;i++)
        {
        frames.add(new TextureRegion(getTexture(),i * 16,0,16,16));
            marioJump = new Animation<TextureRegion>(0.1f,frames);
            frames.clear();
        }


        defineMario();
        marioStand = new TextureRegion(getTexture(),1,11,16,16); //Нумерациите са координатите на спрайт-а като започва от 1,11 и е голям 16х16.
        setBounds(0,0,16 / MarioBros.PPM,16 / MarioBros.PPM); // За да знае колко голям спрайт да рендер-не(делим и 2те стойности на PPM за да може спрайт-а да е скалиран)
        setRegion(marioStand); // Самото пускане на спрайт-а

    }
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() / 2);
    }
public void defineMario()
 {
     BodyDef bdef = new BodyDef(); //Инициализиране на тялото.
     bdef.position.set(32 / MarioBros.PPM,32 / MarioBros.PPM); //Къде да бъде създаден Марио.
     bdef.type = BodyDef.BodyType.DynamicBody; //Какъв тип да бъде - Динамичен.
     b2body = world.createBody(bdef); //Самото създаване на тялото.

     FixtureDef fdef = new FixtureDef(); //Инициализиране на фикстурата.
     CircleShape shape = new CircleShape(); //Инициализиране на формата кръг.
     shape.setRadius(6 / MarioBros.PPM); //Радиус на кръга.
     fdef.shape = shape;
     b2body.createFixture(fdef); //Казваме на тялото да използва фикстурата която създадохме.


 }

}

