package physicsgame.tilebehaviours;

import physicsgame.Tile;
import physicsgame.World;

public class SandBehaviour implements TileBehaviour
{
    private final World world;

    private final int stiffness;
    private final Tile mainTile;
    private final Tile updateTile;

    public SandBehaviour(World world, Tile mainTile, Tile updateTile, int stiffness)
    {
        this.world = world;
        this.mainTile = mainTile;
        this.updateTile = updateTile;
        this.stiffness = stiffness;
    }

    private void updateGravity(int x, int y)
    {
        if(y + 1 >= world.getHeight()) return;
        Tile tileToReplace = world.getTile(x, y + 1);
        if(tileToReplace == mainTile) return;
        if(tileToReplace == Tile.WATER)
        {
            world.setTile(x, y, Tile.MOVING_WATER);
            world.setTile(x, y + 1, updateTile);
        }
        else if(tileToReplace == Tile.EMPTY)
        {
            world.setTile(x, y, Tile.UPDATED_EMPTY);
            world.setTile(x, y + 1, updateTile);
        }
    }

    private void updateFlow(int x, int y)
    {
        if(y <= stiffness - 1) return;
        for (int i = 0; i < stiffness; i++)
        {
            if(world.getTile(x, y - 1 - i) != mainTile)
            {
                return;
            }
        }
        if(x == 0 || x == world.getWidth() - 1) return;
        for(int i: new int[] {-1, 1})
        {
            Tile tileToReplace = world.getTile(x + i, y);
            if(tileToReplace != Tile.EMPTY && tileToReplace != Tile.WATER) continue;
            world.setTile(x + i, y, updateTile);
            if(tileToReplace == Tile.WATER)
            {
                world.setTile(x, y, Tile.MOVING_WATER);
            }
            else
            {
                world.setTile(x, y, Tile.UPDATED_EMPTY);
            }
            break;
        }
    }

    @Override
    public void update()
    {
        for(int y = world.getHeight() - 1; y > -1 ; y--)
        {
            for(int x = 0; x < world.getWidth(); x++)
            {
                Tile tile = world.getTile(x, y);
                if(tile != mainTile) continue;
                updateGravity(x, y);
            }
            for(int x = 0; x < world.getWidth(); x++)
            {
                Tile tile = world.getTile(x, y);
                if(tile != mainTile) continue;
                updateFlow(x, y);
            }
            for(int x = 0; x < world.getWidth(); x++)
            {
                if(y == 0) continue;
                for(int i: new int[] {0, 1})
                {
                    Tile tile = world.getTile(x, y - i);
                    if(tile == updateTile) world.setTile(x, y - i, mainTile);
                    else if(tile == Tile.UPDATED_EMPTY) world.setTile(x, y - i, Tile.EMPTY);
                    else if(tile == Tile.MOVING_WATER) world.setTile(x, y - i, Tile.WATER);
                }
            }
        }
    }
}
