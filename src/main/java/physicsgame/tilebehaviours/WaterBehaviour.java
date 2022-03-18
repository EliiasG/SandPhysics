package physicsgame.tilebehaviours;

import physicsgame.Tile;
import physicsgame.World;

public class WaterBehaviour implements TileBehaviour
{
    private final World world;

    private final int flowSpeed = 2;

    public WaterBehaviour(World world)
    {
        this.world = world;
    }

    private void updateGravity(int x, int y)
    {
        if(y + 1 >= world.getHeight()) return;
        Tile tileToReplace = world.getTile(x, y + 1);
        if(tileToReplace != Tile.EMPTY && tileToReplace != Tile.UPDATED_EMPTY) return;
        world.setTile(x, y + 1, Tile.MOVING_WATER);
        world.setTile(x, y, Tile.UPDATED_EMPTY);
    }

    private void updateFlow(int x, int y)
    {
        if(y >= world.getHeight() - 2) return;
        //if(!(world.getTile(x, y + 1) == Tile.WATER)) return;
        for (int i = -flowSpeed; i < flowSpeed + 1; i++)
        {
            if(x+i <= -1 || x+i >= world.getWidth()) continue;
            if(world.getTile(x + i, y) != Tile.EMPTY) continue;
            world.setTile(x + i, y, Tile.MOVING_WATER);
            world.setTile(x, y, Tile.UPDATED_EMPTY);
            updateGravity(x + i, y);
            return;
        }
    }

    @Override
    public void update()
    {
        for(int y = world.getHeight() - 1; y > -1 ; y--)
        {
            for (int x = 0; x < world.getWidth(); x++)
            {
                Tile tile = world.getTile(x, y);
                if(tile != Tile.WATER) continue;
                updateGravity(x, y);
            }
            for(int x = 0; x < world.getWidth(); x++)
            {
                if(y == 0) continue;
                for(int i: new int[] {0, 1})
                {
                    Tile tile = world.getTile(x, y - i);
                    if(tile == Tile.MOVING_WATER) world.setTile(x, y - i, Tile.WATER);
                }
            }
        }
        for(int y = 0; y < world.getHeight() ; y++)
        {
            for(int x = 0; x < world.getWidth(); x++)
            {
                Tile tile = world.getTile(x, y);
                if(tile != Tile.WATER) continue;
                updateFlow(x, y);
            }
        }
    }
}
