package effectiveJava;

import java.awt.*;
import java.util.Objects;

public class MainEJ {
    public static void main(String[] args) {

        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);

        System.out.println("Point: "+p.equals(cp));
        System.out.println("ColorPoint: "+cp.equals(p));


    }
}
