package teams.student.testPlotz.analysis;

import engine.states.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import ui.display.Fonts;

import java.util.ArrayList;

public class Block {

    private int id;
    private static int ID;

    private int enemy;
    private int ally;

    private float x;
    private float y;

    private int r;
    private int c;

    private Color myColor;

    public static float SIZE = 800;

    public ArrayList<Block> adjBlocks;

    public float getX() { return x;}
    public float getY() { return y;}

    public float getMidX() { return (2*x+SIZE)/2;}
    public float getMidY() { return (2*y+SIZE)/2;}

    public float getId() { return id;}

    public int getEnemy() { return enemy;}
    public int getAlly() { return ally;}

    public void addEnemyCount(){enemy++;}
    public void addAllyCount(){ally++;}

    public Block(int r, int c)
    {
        this.r = r;
        this.c = c;

        x = Game.getMapLeftEdge()+ c* SIZE;
        y = Game.getMapTopEdge() + r* SIZE;

        id = ID;
        ID ++;
        adjBlocks = new ArrayList<>();
    }

    public void draw(Graphics g)
    {
        g.setLineWidth(3);
        g.setColor(myColor);
        g.fillRect(x,y,SIZE,SIZE);
        g.setColor(Color.white);
        g.drawRect(x,y,SIZE, SIZE);
        g.setFont(Fonts.mediumFont);
        g.drawString("enemy: "+enemy +"| ally: "+ally, getMidX(), getMidY());
        if (adjBlocks!= null)
        {
            int i =1;
            for (Block b: adjBlocks)
            {

                g.drawString(b.toString(), getMidX(), getMidY() + 19 *i);
                i++;
            }
        }

    }

    public String toString()
    {
        return "r: "+r+"c: "+c;
    }

    public void setAdjBlocks(ArrayList<Block> blockList)
    {
        adjBlocks = blockList;
    }

    public boolean isWithin(Point p)
    {
        if (p.getX() > x && p.getY()>y && p.getX()<x+SIZE && p.getY()<y+SIZE)
        {
            return true;
        }
        return false;
    }

    public void update()
    {
        enemy = 0;
        ally =0;

    }

    public void setColor()
    {
        if (enemy + ally>0)
        {
            myColor = new Color((float)(enemy)/(enemy+ally), (float)(ally)/(ally+enemy),0, (float)(enemy+ally)/40);
        }
        else
        {
            myColor = new Color(0,0,0,.2f);
        }
    }


}
