package haar;

import java.awt.image.BufferedImage;
import java.io.IOException;

import utils.ImageUtils;

public class Main {

	public static void main(String[] args) {
		
		
		FaceDetector detector = new FaceDetector(40);
		
		try {
			//BufferedImage face = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/testfaces/87b.jpg");
			BufferedImage face = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/sample faces/test13.jpg");
			//detector.detect(face);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		

	}

}
