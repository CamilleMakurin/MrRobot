package v2;

import org.apache.commons.lang3.time.StopWatch;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TEstTEstTTEEStScrennshotTest {

    public static void main(String[] args) throws Exception {

        StopWatch stopWatch = StopWatch.create();

        stopWatch.start();
        Thread.sleep(1000);
        System.out.println("getTime()" + stopWatch.getTime());
        stopWatch.suspend();
        Thread.sleep(10000);
        System.out.println("getTime()" + stopWatch.getTime());
        stopWatch.resume();
        System.out.println("getTime()" + stopWatch.getTime());
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println("getTime()" + stopWatch.getTime());
        System.out.println("getMessage()" + stopWatch.getMessage());
        System.out.println("getNanoTime()" + stopWatch.getNanoTime());


    }

    private static void makeScreenshot() throws AWTException {
        Robot r = new Robot();
        Rectangle rect = new Rectangle(100, 100, 200, 200);
        BufferedImage img = new Robot().createScreenCapture(rect);
    }
}
