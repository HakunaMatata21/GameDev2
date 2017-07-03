package com.mechev.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mechev.game.MarioBros;

/**
 * Created by void on 08.06.17.
 */

public class Hud implements Disposable
{
public Stage stage;
    private Viewport viewport; //shte se izpolzva nov viewport specialno za hud-a za da moje da se sinhronizira s ostanaliqt svqt.
    private int worldTimer;
    private float timeCount;
    private int score;

    Label countdownLabel;
    Label  scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;

    public Hud(SpriteBatch sb)
    {
    worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,sb); //izpolzva syshtiqt SpriteBatch.

        Table table = new Table();
        table.top(); // da se pozicionira gore.
        table.setFillParent(true); //tova oznachava che sega table-a e na cqlata scena.(izpylva ekrana na igrata)

        countdownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // 3 stoi za broq cifri,d stoi za integer.
        scoreLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE)); //tuk ne e nujen integer a samo da izpisva TIME.
        levelLabel = new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel =new Label("WORLD",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel =new Label("MARIO",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //sled kato label-ite sa gotovi e vreme da gi slojim na masata a imenno v lenta nai-gore na ekrana.
        table.add(marioLabel).expandX().padTop(10); //expandX syzdava edna lenta i ako tova e edinstveniqt label shte zaeme cqlata lenta,no ako sa poveche te izpolzvat edna i syshta lenta zaemaiki mqsto poravno toest ako sa 3 sqko edno shte zaema 1/3 ot lentata.
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row(); //vsichko pod tozi red shte byde edin red po dulu ot label-ite gore.
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table); //dobavqme masata kym scenata.
    }

    @Override
    public void dispose()
    {
    stage.dispose();
    }
}
