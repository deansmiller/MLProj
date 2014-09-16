package objectdetection;

import utils.ImageUtils;
import webcam.Video;


public class Main {

	public static void main(String[] args) {
		try {
            //new Video();

            ConnectedComponentBlobDetector detector = new ConnectedComponentBlobDetector();
            detector.detect(ImageUtils.getBufferedImage("/home/deansmiller/Pictures/hand.jpg"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
