package gesturerecognition;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import utils.ImageUtils;
import nn.Network;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;

public class Video extends JFrame implements Runnable {

	private static final long serialVersionUID = -585739158170333370L;
	private static final int INTERVAL = 100;
	private Webcam webcam = Webcam.getDefault();
	private final int WIDTH = 320;
	private final int HEIGHT = 240, TIME = 5;
	private int i = 0;

	
	private class ImageFilterer implements WebcamImageTransformer {
		private NNSkinFilter filter;
		private FingerCounter counter;
		private SkinLocalizer boxer;

		public ImageFilterer(NNSkinFilter filter) {
			this.filter = filter;
			counter = new FingerCounter(5, 1.20);
		}

		@Override
		public BufferedImage transform(BufferedImage image) {
			try {
				image = ImageUtils.filterSkin(image);
				//image = counter.count(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
	}


	public Video() throws Exception {
		Thread updater = new Thread(this, "updater-thread");
		updater.setDaemon(true);
		updater.start();
		setTitle("Jarvis");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		webcam.setViewSize(new Dimension(WIDTH, HEIGHT));
		
		ArrayList<Network> networks = new ArrayList<Network>();
		networks.add(Network.createNetworkFromFile("resources/skin_classifier2.nn"));
		webcam.setImageTransformer(new ImageFilterer(new NNSkinFilter(networks, 0.99)));
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
