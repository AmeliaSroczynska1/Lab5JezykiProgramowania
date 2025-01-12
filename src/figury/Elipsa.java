package figury;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Elipsa extends Figura {

    public Elipsa(Graphics2D buf, int del, int w, int h) {
        super(buf, del, w, h);
        // Tworzenie elipsy o losowych wymiarach
        int width = 20 + rand.nextInt(30);  // Szerokość od 20 do 50
        int height = 10 + rand.nextInt(20); // Wysokość od 10 do 30
        area = new Area(new Ellipse2D.Double(0, 0, width, height));
        aft = new AffineTransform(); // Inicjalizacja transformacji
    }
}
