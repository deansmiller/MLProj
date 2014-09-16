package playground;

import objectdetection.ConnectedComponentBlobDetector;
import utils.ImageUtils;
import webcam.Video;

/**
 * User: deansmiller
 * Date: 16/09/14
 * Time: 16:59
 */
public class Main {

    public static void main(String[] args) {
        try {
//            new Video();

            ConnectedComponentBlobDetector detector = new ConnectedComponentBlobDetector();
            detector.detect(ImageUtils.getBufferedImage("/home/deansmiller/Pictures/hand.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
