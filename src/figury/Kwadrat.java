package figury;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Kwadrat extends Figura {

    public Kwadrat(Graphics2D buf, int del, int w, int h) {
        super(buf, del, w, h);
        // Tworzenie kwadratu o losowych wymiarach
        int size = 20 + rand.nextInt(30); // Losowy rozmiar od 20 do 50
        area = new Area(new Rectangle2D.Double(0, 0, size, size));
        aft = new AffineTransform(); // Inicjalizacja transformacji
    }
}
