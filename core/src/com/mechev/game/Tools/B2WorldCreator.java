package com.mechev.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mechev.game.MarioBros;
import com.mechev.game.Sprites.Brick;
import com.mechev.game.Sprites.Coin;

/**
 * Created by void on 19.06.17.
 */

public class B2WorldCreator
{
public B2WorldCreator(World world, TiledMap map)
{
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;

//Създаване на тялото на земята и фикстурите.
    for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) //Това get(2) е номерацията на слоя който използвахме в Tiled.(Броенето става като се отвори Tiled и се гледат от долу на горе започващо от 0.)
    {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioBros.PPM,(rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

        body = world.createBody(bdef); // Имплементиране на тялото във бокс2д светът.

        shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM,rect.getHeight() / 2 / MarioBros.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }


//Създаване на тялото на тръбите и фикстурите.
    for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) //Това get(3) е номерацията на слоя който използвахме в Tiled.
    {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioBros.PPM,(rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

        body = world.createBody(bdef); // Имплементиране на тялото във бокс2д светът.

        shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM,rect.getHeight() / 2 / MarioBros.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
//Създаване на тялото на тухлите и фикстурите.
    for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) //Това get(5) е номерацията на слоя който използвахме в Tiled.
    {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
      new Brick(world,map,rect);
    }
//Създаване на тялото на монетите и фикстурите.
    for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) //Това get(4) е номерацията на слоя който използвахме в Tiled.
    {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        new Coin(world,map,rect);
    }
}
}

