package items;

import java.awt.*;

public class Ball extends Rectangle{

    public float x, y, vX, vY, radius;

    public Color color;

    public void draw(Graphics2D g) {

        g.setColor(color);
        g.fillOval((int) (x - radius), (int) (y - radius), (int) radius *2 , (int) radius *2);

    }

    public Ball(float x, float y, float vX, float vY, float radius, Color color) {

        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
        this.radius = radius;
        this.color = color;

    }
}