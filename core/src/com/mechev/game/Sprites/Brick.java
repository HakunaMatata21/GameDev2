package com.mechev.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import org.w3c.dom.css.Rect;

/**
 * Created by void on 19.06.17.
 */

public class Brick extends InteractiveTileObject
{
public Brick(World world, TiledMap map, Rectangle bounds)
{
    super(world,map,bounds);
}
}
