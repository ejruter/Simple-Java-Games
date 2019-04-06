import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tennis extends Applet implements Runnable, KeyListener {

    final int WIDTH  = 700, HEIGHT = 500;
    Thread thread;
    HumanPaddle p1;
    Ball b1;
    AIPaddle p2;
    boolean gamestarted;
    Graphics gfx;
    Image img;

    public void init() {
        this.resize(WIDTH, HEIGHT);
        gamestarted = false;
        this.addKeyListener(this);
        p1 = new HumanPaddle(1);
        b1 = new Ball();
        p2 = new AIPaddle(2,b1);
        img = createImage(WIDTH,HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();

    }

    public void paint (Graphics g) {
        gfx.setColor(Color.green);
        gfx.fillRect(0,0,WIDTH,HEIGHT);
        if (b1.getX() < -10 || b1.getX() > 710) {
            gfx.setColor(Color.red);
            gfx.drawString("Game Over", 350,250);
        }
        else {
            p1.draw(gfx);
            b1.draw(gfx);
            p2.draw(gfx);
        }
        if(!gamestarted) {
            gfx.setColor(Color.white);
            gfx.drawString("Impossible Pong", 320,100);
            gfx.drawString("Press Enter to Begin", 310,130);
        }
        g.drawImage(img,0,0,(this));
    }

    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void run() {
        for(;;) {
            if(gamestarted) {
                p1.move();
                p2.move();
                b1.move();
                b1.checkPaddleCollision(p1, p2);
            }



            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
p1.setUpAccel(true);
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
p1.setDownAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            gamestarted = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
p1.setUpAccel(false);
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
p1.setDownAccel(false);
        }

    }
}
