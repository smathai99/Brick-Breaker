import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public abstract class Game extends javax.swing.JFrame implements MouseListener, MouseWheelListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    /* difference between time of update and world step time */
    float localTime = 0f;
    /** Creates new form Game */
    public Game() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setSize(800, 600);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public static void startWindow(Game game) {
        /* Create and display the form */
        new Thread() {

            {
                setDaemon(true);
                start();
            }

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (Throwable t) {
                    }
                }
            }
        };
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                game.setVisible(true);
                game.start(1 / 60f, 5);
            }
        });
    }

    /**
     * Starts the game loop in a new Thread.
     * @param fixedTimeStep
     * @param maxSubSteps maximum steps that should be processed to catch up with real time.
     */
    public final void start(final float fixedTimeStep, final int maxSubSteps) {
        this.createBufferStrategy(2);
        init();
        new Thread() {

            {
                setDaemon(true);
            }

            @Override
            public void run() {
                long start = System.nanoTime();
                while (true) {
                    long now = System.nanoTime();
                    float elapsed = (now - start) / 1000000000f;
                    start = now;
                    internalUpdateWithFixedTimeStep(elapsed, maxSubSteps, fixedTimeStep);
                    internalUpdateGraphicsInterpolated();
                    if (1000000000 * fixedTimeStep - (System.nanoTime() - start) > 1000000) {
                        try {
                            Thread.sleep(0, 999999);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * Updates game state if possible and sets localTime for interpolation.
     * @param elapsedSeconds
     * @param maxSubSteps
     * @param fixedTimeStep
     */
    private void internalUpdateWithFixedTimeStep(float elapsedSeconds, int maxSubSteps, float fixedTimeStep) {
        int numSubSteps = 0;
        if (maxSubSteps != 0) {
            // fixed timestep with interpolation
            localTime += elapsedSeconds;
            if (localTime >= fixedTimeStep) {
                numSubSteps = (int) (localTime / fixedTimeStep);
                localTime -= numSubSteps * fixedTimeStep;
            }
        }
        if (numSubSteps != 0) {
            // clamp the number of substeps, to prevent simulation grinding spiralling down to a halt
            int clampedSubSteps = (numSubSteps > maxSubSteps) ? maxSubSteps : numSubSteps;
            for (int i = 0; i < clampedSubSteps; i++) {
                update(fixedTimeStep);
            }
        }
    }

    /**
     * Calls render with Graphics2D context and takes care of double buffering.
     */
    private void internalUpdateGraphicsInterpolated() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics2D g = null;
        try {
            g = (Graphics2D) bf.getDrawGraphics();
            render(g, localTime);
        } finally {
            g.dispose();
        }
        // Shows the contents of the backbuffer on the screen.
        bf.show();
        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        //Drawing is done which looks very jerky
        Toolkit.getDefaultToolkit().sync();
    }

    protected void init(){}

    protected abstract void render(Graphics2D g, float interpolationTime);

    protected abstract void update(float elapsedTime);
}