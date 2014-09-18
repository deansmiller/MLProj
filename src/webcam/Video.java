package webcam;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JFrame;

import objectdetection.BlobDetector;
import objectdetection.ColourFilter;
import objectdetection.Region;
import utils.ImageUtils;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;

public class Video extends JFrame implements Runnable {

	private static final long serialVersionUID = -585739158170333370L;
	private static final int INTERVAL = 100;
	private Webcam webcam = Webcam.getDefault();
	private final int WIDTH = 320;
	private final int HEIGHT = 240;


	public Video(WebcamImageTransformer imageFilterer) throws Exception {
		Thread updater = new Thread(this, "updater-thread");
		updater.setDaemon(true);
		updater.start();
		setTitle("ML Proj");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		webcam.setViewSize(new Dimension(WIDTH, HEIGHT));
		webcam.setImageTransformer(imageFilterer);
		webcam.open();
		VideoPanel panel = new VideoPanel(webcam);
		add(panel);
		pack();
		setVisible(true);
	}


	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(INTERVAL * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
