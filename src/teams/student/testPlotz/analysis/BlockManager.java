package teams.student.testPlotz.analysis;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import engine.states.Game;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;

import java.util.ArrayList;

public class BlockManager {
    int leftEdge;
    int rightEdge;
    int topEdge;
    int bottomEdge;

    int totalWidth;
    int totalHeight;

    public Block[][] blocks;

    public int cols;
    public int rows;

    private Player ally;
    private Player opponent;


    int timer;
    public BlockManager( Player p)
    {
        ally = p;
        opponent = p.getOpponent();


        leftEdge = Game.getMapLeftEdge();
        rightEdge = Game.getMapRightEdge();
        topEdge = Game.getMapTopEdge();
        bottomEdge = Game.getMapBottomEdge();

        totalWidth = Math.abs(rightEdge -leftEdge);
        totalHeight = Math.abs(topEdge - bottomEdge);

        cols = (int) (totalWidth/Block.SIZE);
        rows = (int) (totalHeight/Block.SIZE);

        blocks = new Block[rows][cols];

        for (int r =0; r< blocks.length; r++)
        {
            for (int c = 0; c< blocks[0].length; c++)
            {
                blocks[r][c] = new Block(r,c);
            }
        }
        for (int r =0; r< blocks.length; r++)
        {
            for (int c = 0; c< blocks[0].length; c++)
            {

               blocks[r][c].setAdjBlocks(getAdjacentBlocks(r,c));
            }
        }

    }

    public ArrayList<Block> getAdjacentBlocks(int r, int c)
    {
        ArrayList<Block> adjBlx = new ArrayList<>();
        for (int i = -1; i<2; i++)
        {
            for (int j = -1; j<2; j++)
            {
                if ((r+i>=0 &&r+i<blocks.length) && (c+j>=0 && c+j<blocks[0].length) &&!(i==0 &&j==0))
                {
                    adjBlx.add(blocks[r+i][c+j]);
                }
            }
        }

        return adjBlx;


    }



    public void draw(Graphics g)
    {
        for (int r =0; r< blocks.length; r++)
        {
            for (int c = 0; c< blocks[0].length; c++)
            {
                blocks[r][c].draw(g);
            }
        }
    }

    public void update()
    {

        if (timer%90 == 0){
            timer = 0;
            for (int r= 0; r< blocks.length; r++)
            {
                for (int c= 0; c< blocks[0].length; c++)
                {
                    blocks[r][c].update();
                }
            }

            ArrayList<Unit> allEnemy = opponent.getMyUnits();

            for (Unit e: allEnemy)
            {
                if (!e.hasComponent(Drillbeam.class)&& !e.hasComponent(Collector.class))
                {
                    int c = (int)((e.getX()-leftEdge)/Block.SIZE);
                    int r = (int)((e.getY()-topEdge)/Block.SIZE);
                    blocks[r][c].addEnemyCount();

                    if (inBounds(r,c))
                    {
                        blocks[r][c].addAllyCount();
                    }

                }
            }

            ArrayList<Unit> allAlly = ally.getMyUnits();

            for (Unit e: allAlly)
            {
                if (!e.hasComponent(Drillbeam.class)&& !e.hasComponent(Collector.class))
                {
                    int c = (int)((e.getX()-leftEdge)/Block.SIZE);
                    int r = (int)((e.getY()-topEdge)/Block.SIZE);
                    if (inBounds(r,c))
                    {
                        blocks[r][c].addAllyCount();
                    }


                }
            }

            for(Block[] blx : blocks)
            {
                for (Block b: blx)
                {
                    b.setColor();
                }
            }
        }
        timer ++;


    }

    public boolean inBounds(int r, int c)
    {
        return (r>0 && r<blocks.length && c>0 && c<blocks[0].length);
    }


}
