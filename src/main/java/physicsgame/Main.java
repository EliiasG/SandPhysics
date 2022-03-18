package physicsgame;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.MouseEvent;

public class Main extends PApplet
{
    World world;
    WorldRenderer worldRenderer;
    int currentTileIndex = 0;
    Tile[] tiles = new Tile[]
    {
        Tile.SAND,
        Tile.WATER,
        Tile.WALL,
        Tile.DUST,
        Tile.FIRE,
        Tile.GREEN_STUFF
    };

    public static void main(String[] args)
    {
        PApplet.main("physicsgame.Main");
    }

    @Override
    public void settings()
    {
        size(1250, 1000);
        smooth(0);
    }

    @Override
    public void setup()
    {
        world = new World(width / 4, height / 4);
        worldRenderer = new WorldRenderer(world);
        frameRate(60);
    }

    @Override
    public void mouseWheel(MouseEvent event)
    {
        currentTileIndex = (currentTileIndex + tiles.length + event.getCount()) % tiles.length;
        System.out.println(currentTileIndex);
    }

    @Override
    public void draw()
    {
        background(39, 120, 171);
        for (int i = 0; i < 3; i++)
        {
            world.update();
        }
        PImage worldImage = worldRenderer.render(this, tiles[currentTileIndex]);
        image(worldImage, 0, 0, width, height);
        if(mousePressed)
        {
            Tile tile = tiles[currentTileIndex];
            if(mouseButton == RIGHT) tile = Tile.EMPTY;
            world.placeCircle((int)((mouseX / (float)width)*world.getWidth()), (int)((mouseY / (float)height)*world.getHeight()), 8, tile);
        }
    }
}
