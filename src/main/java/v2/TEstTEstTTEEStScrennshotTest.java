package v2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class TEstTEstTTEEStScrennshotTest {

    public static void main(String[] args) throws Exception {

        Robot r = new Robot();
        r.mouseMove(651,25);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        //  Rectangle rect = new Rectangle(100, 100, 200, 200);
      //  BufferedImage img = new Robot().createScreenCapture(rect);


    }
}
