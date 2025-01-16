package figury;

import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerButton extends JButton implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    private int x, y;
    private int step = 5;

    public PlayerButton(String text) {
        super(text);
        x = 0;
        y = 0;
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            x -= step;
        } else if (key == KeyEvent.VK_RIGHT) {
            x += step;
        } else if (key == KeyEvent.VK_UP) {
            y -= step;
        } else if (key == KeyEvent.VK_DOWN) {
            y += step;
        }
        setLocation(x, y);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}