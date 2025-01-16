package figury;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

class StaticCircle extends Figura {

    public StaticCircle(Graphics2D buf, int del, int w, int h) {
        super(buf, del, w, h);
        // Tworzenie statycznego koła o losowych wymiarach i pozycji
        int diameter = 20;
        int x = rand.nextInt(w - diameter);
        int y = rand.nextInt(h - diameter);
        area = new Area(new Ellipse2D.Double(x, y, diameter, diameter));
        shape = area;
		clr = Color.red;
    }

    @Override
    public void run() {
        // Nic nie robi, bo koło jest statyczne
    }
}
