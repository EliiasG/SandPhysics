package physicsgame;

import processing.core.PApplet;
import processing.core.PGraphics;

public class WorldRenderer
{
    World world;
    int lastMillis = 0;

    public WorldRenderer(World world)
    {
        this.world = world;
    }

    private int getTileColor(Tile tile, PApplet applet)
    {
        return switch (tile)
        {
            case SAND, MOVING_SAND -> applet.color(250, 210, 135);
            case WATER, MOVING_WATER -> applet.color(17, 46, 140);
            case DUST, MOVING_DUST -> applet.color(230);
            case FIRE -> applet.color(235, 172, 26);
            case GREEN_STUFF -> applet.color(0, 255, 0);
            //case MOVING_WATER -> applet.color(200, 56, 180);
            case WALL -> applet.color(120);
            default -> -1;
        };
    }

    public PGraphics render(PApplet applet, Tile selectedTile)
    {
        PGraphics graphics = applet.createGraphics(world.getWidth(), world.getHeight());

        graphics.beginDraw();
        graphics.loadPixels();

        int[] pixels = graphics.pixels;
        for(int i = 0; i < pixels.length; i++)
        {
            int color = getTileColor(world.getTile(i % world.getWidth(), i / world.getWidth()), applet);
            if(color == -1) continue;
            pixels[i] = color;
        }
        //System.out.println("Sand count: " + count);
        lastMillis = applet.millis();
        graphics.updatePixels();

        graphics.strokeWeight(4);

        graphics.fill(getTileColor(selectedTile, applet));

        graphics.rect(2, 2, 32, 32);

        graphics.endDraw();


        return graphics;
    }
}
