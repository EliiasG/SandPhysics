package physicsgame;

import physicsgame.tilebehaviours.FireBehaviour;
import physicsgame.tilebehaviours.SandBehaviour;
import physicsgame.tilebehaviours.TileBehaviour;
import physicsgame.tilebehaviours.WaterBehaviour;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class World
{
    private final Tile[][] tiles;
    private final int width, height;
    private final TileBehaviour[] tileBehaviours =
    {
        new SandBehaviour(this, Tile.SAND, Tile.MOVING_SAND, 4),
        new SandBehaviour(this, Tile.DUST, Tile.MOVING_DUST, 1),
        new WaterBehaviour(this),
        new FireBehaviour(this)
    };

    public World(int width, int height)
    {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
    }

    public void setTile(int x, int y, Tile state)
    {
        if(state == Tile.EMPTY)
        {
            tiles[x][y] = null;
            return;
        }
        tiles[x][y] = state;
    }

    public Tile getTile(int x, int y)
    {
        Tile tile = tiles[x][y];
        if(tile == null) return Tile.EMPTY;
        return tile;
    }

    public void update()
    {
        Date beforeDate = new Date();
        for(TileBehaviour tileBehaviour: tileBehaviours)
        {
            tileBehaviour.update();
        }
        Date afterDate = new Date();
        System.out.println("Update time: " + (afterDate.getTime() - beforeDate.getTime()) + " ms");
    }

    public void placeCircle(int x, int y, int radius, Tile tile)
    {
        for(int currentX = 0; currentX < width; currentX++)
        {
            for(int currentY = 0; currentY < height; currentY++)
            {
                if(isPointInCircle(x, y, radius, currentX, currentY))
                {
                    setTile(currentX, currentY, tile);
                }
            }
        }
    }

    private boolean isPointInCircle(int circleX, int circleY, int radius, int pointX, int pointY)
    {
        int x = circleX - pointX;
        int y = circleY - pointY;
        //if(Math.abs(x) > radius || Math.abs(y) > radius) return false; //square root optimisation
        return (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(radius, 2));
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
