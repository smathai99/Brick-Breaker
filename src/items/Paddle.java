package items;

import java.awt.*;

public class Paddle {

    public int height = 50, width = 150;
    public volatile int x, y = 475;

    public void draw(Graphics2D g) {

        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
    }
}
