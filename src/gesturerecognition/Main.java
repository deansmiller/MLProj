package gesturerecognition;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import nn.Network;
import utils.ImageUtils;



public class Main {

	public static void main(String[] args) {
		try {
			BufferedImage image = ImageUtils.getBufferedImage("/home/deansmiller/Pictures/3-fingers.jpg");
			
			List<Network> networks = new ArrayList<Network>();
			networks.add(Network.createNetworkFromFile("resources/skin_classifier1.nn"));
			NNSkinFilter skinFilter = new NNSkinFilter(networks, 0.9);
			BufferedImage filteredImage = skinFilter.filterSkinPixels(image);
			
			FingerCounter counter = new FingerCounter();
			List<DropLine> points = counter.countFingers(filteredImage);
			Graphics g = image.getGraphics();
			g.setColor(Color.RED);
			for(DropLine point : points){
				g.fillOval(point.x.intValue(), point.y.intValue(), 10, 10);
			}
//			Font f = new Font("Dialog", Font.PLAIN, 20);
//			g.setFont(f);
//			
//			g.drawString("Fingers: " + points.size(), 5, 40);
//			System.out.println("Fingers: " + points.size());
			g.dispose();
			ImageUtils.saveImage(image, "/home/deansmiller/Pictures/output.jpg");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
