import items.Ball;
import items.Brick;
import items.Paddle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class MyGame extends Game {

    Ball ball;
    Paddle paddle;
    Vector<Brick> bricks;

    public static void main(String args[]) {
        MyGame game = new MyGame();
        startWindow(game);
    }



    @Override
    protected void init() {
        int radius = 20;
        ball = new Ball(getWidth() / 2, 2.5f * radius + 80, 200, 200, radius, new Color(0x339DB9));
        paddle = new Paddle();

        bricks = new Vector<>();

        int space = 10;
        int spaceEnds = 50;
        for (int y = 100; y < getHeight() /1.75; y += Brick.HEIGHT + space) {
            for (int x = spaceEnds; x < getWidth() - (Brick.WIDTH + spaceEnds); x += Brick.WIDTH + space) {
                bricks.add(new Brick(x, y));
            }
        }
    }

    /**
     * render the game  (override/replace)
     *
     * @param g
     * @param interpolationTime time of the rendering within a fixed timestep (in seconds)
     */
    @Override
    protected void render(Graphics2D g, float interpolationTime) {
        g.clearRect(0, 0, getWidth(), getHeight());
        ball.draw(g);
        paddle.draw(g);
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).draw(g);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        paddle.x = e.getX() - paddle.width/2;
    }

    /**
     * update game. elapsedTime is fixed.  (override/replace)
     *
     * @param elapsedTime
     */
    @Override
    protected void update(float elapsedTime) {

        ball.x += ball.vX * elapsedTime;
        ball.y += ball.vY * elapsedTime;

        if (ball.x > getWidth() - ball.radius || ball.x < ball.radius) {
            ball.vX *= -1;
        }

        if (ball.y > getHeight() - ball.radius || ball.y < ball.radius) {
            ball.vY *= -1;
        }
        if (paddle.x <= ball.x && ball.x <= paddle.x + paddle.width&& ball.y > paddle.y - ball.radius) {
            ball.vY *= -1;
        }
    }
}
