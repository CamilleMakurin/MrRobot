package v2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ForTestsMAin {

    public static void main(String[] args) throws Exception {
        makeScreenshot();

    }

    private static void makeScreenshot() throws AWTException {
        Robot r = new Robot();
        Rectangle rect = new Rectangle(100, 100, 200, 200);
        BufferedImage img = new Robot().createScreenCapture(rect);
    }
}
