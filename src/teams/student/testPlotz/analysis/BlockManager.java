package teams.student.testPlotz.analysis;

import engine.states.Game;
import org.newdawn.slick.Graphics;

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

    public BlockManager()
    {
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
        for (int r= 0; r< blocks.length; r++)
        {
            for (int c= 0; c< blocks[0].length; c++)
            {
                blocks[r][c].update();
            }
        }
    }


}
