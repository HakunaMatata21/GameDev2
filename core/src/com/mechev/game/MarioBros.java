package com.mechev.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mechev.game.Screens.PlayScreen;

public class MarioBros extends Game
{
	public static final int V_WIDTH = 400; //virtualna shirochina za igrata
	public static final int V_HEIGHT = 208; //virtualna visochina za igrata
public static final float PPM = 100; //Pixels per meter.
	public SpriteBatch batch;
	public Texture img;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render ()
	{
	super.render();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		img.dispose();
	}
}
