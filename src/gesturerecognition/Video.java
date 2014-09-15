package gesturerecognition;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JFrame;
import utils.ImageUtils;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;

public class Video extends JFrame implements Runnable {

	private static final long serialVersionUID = -585739158170333370L;
	private static final int INTERVAL = 100;
	private Webcam webcam = Webcam.getDefault();
	private final int WIDTH = 320;
	private final int HEIGHT = 240;
	
	private class ImageFilterer implements WebcamImageTransformer {
		private BlobDetector boxer;
        private ColourFilter colourFilter;
        private Set<Region> regions;
        private Iterator<Region> iterator;

		public ImageFilterer() {
            boxer = new BlobDetector(WIDTH, HEIGHT);
            colourFilter = new ColourFilter(Color.BLUE, 0.7);
		}

		@Override
		public BufferedImage transform(BufferedImage image) {
            BufferedImage original = ImageUtils.copyImage(image);
			try {

                image = colourFilter.filter(image);
                regions = boxer.localize(image);
                Graphics2D g2 = original.createGraphics();
                g2.setColor(Color.GREEN);
                iterator = regions.iterator();
                while(iterator.hasNext()) g2.draw(iterator.next());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return original;
		}
	}


	public Video() throws Exception {
		Thread updater = new Thread(this, "updater-thread");
		updater.setDaemon(true);
		updater.start();
		setTitle("Colour Detection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		webcam.setViewSize(new Dimension(WIDTH, HEIGHT));
		webcam.setImageTransformer(new ImageFilterer());
		webcam.open();
		VideoPanel panel = new VideoPanel(webcam);
		add(panel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		new Video();
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
