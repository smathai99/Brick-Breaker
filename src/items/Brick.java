package items;

import java.awt.*;

public class Brick extends Rectangle{

    public static final int HEIGHT = 25, WIDTH = 75;

    public void draw(Graphics2D g) {

        g.setColor(Color.darkGray);
        g.fillRect(x, y, width, height);
    }

    public Brick (int brickX, int brickY) {
        super(brickX, brickY, WIDTH, HEIGHT);
    }


}
