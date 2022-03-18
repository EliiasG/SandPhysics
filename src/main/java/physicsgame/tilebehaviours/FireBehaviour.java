package physicsgame.tilebehaviours;

import physicsgame.Tile;
import physicsgame.World;

public class FireBehaviour implements TileBehaviour
{
    private final World world;

    public FireBehaviour(World world)
    {
        this.world = world;
    }

    private void updateSpread(int x, int y)
    {
        if((int)(Math.random() * 20) == 0) world.setTile(x, y, Tile.EMPTY);

        if(x != 0 && x != world.getWidth() - 1)
        {
            for (int i : new int[]{-1, 1})
            {
                if (world.getTile(x + i, y) == Tile.EMPTY) continue;
                world.setTile(x + i, y, Tile.UPDATED_FIRE);
            }
        }
        if(y != 0 && y != world.getHeight() - 1)
        {
            for (int i : new int[]{-1, 1})
            {
                if (world.getTile(x, y + i) == Tile.EMPTY) continue;
                world.setTile(x, y + i, Tile.UPDATED_FIRE);
            }
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
                if(tile != Tile.FIRE) continue;
                updateSpread(x, y);
            }
        }
        for(int y = world.getHeight() - 1; y > -1 ; y--)
        {
            for (int x = 0; x < world.getWidth(); x++)
            {
                if(world.getTile(x, y) == Tile.UPDATED_FIRE) world.setTile(x, y, Tile.FIRE);
            }
        }
    }
}
