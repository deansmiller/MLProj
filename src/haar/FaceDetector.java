package haar;

import gesturerecognition.Region;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import utils.ImageUtils;

public class FaceDetector {
	
	private double threshold;
	private Map<Double, Region> map;
	
	public FaceDetector(double threshold){
		this.threshold = threshold;
		map = new TreeMap<Double, Region>();
	}
	
	
	public void detect(BufferedImage image, int rows) throws IOException{
		int width = image.getWidth();
		int height = image.getHeight();
        System.out.println(height);
		int windowHeight = (int) ((int) height * 0.2);
		System.out.println(windowHeight);
        for(int i = 0; i < height; i += windowHeight){
            Region window = new Region(0, i, width, windowHeight);
            //System.out.println(window);
            map.put(processFeatureWindow(image, window), window);
            //System.out.println();
        }
        Region[] regions = new Region[3];
        for(int i = 0; i < rows; i++){
        	Region r = (Region) map.values().toArray()[i];
        	horizontalScan(image, r);
        }

//        Graphics g = image.getGraphics();
//        g.setColor(Color.red);
//        g.drawRect(r.x, r.y, r.width, r.height);
//        System.out.println(r);
//        System.out.println(map);
//        ImageUtils.saveImage(image, "/home/deansmiller/Desktop/test.jpg");

	}
	
	private void horizontalScan(BufferedImage image, Region region){
		int width = image.getWidth();
		int height = image.getHeight();
		int windowWidth = (int) ((int) height * 0.2);
	}
	
	private double processFeatureWindow(BufferedImage image, Region window) throws IOException{	
		double topSum = 0, bottomSum = 0, gray;
		int r, g, b;
		for(int i = 0; i < window.width; i++){
			for(int j = window.y; j < (int) window.height + window.y; j++){
				int rgb = image.getRGB(i, j);
				r = (rgb >> 16) & 0xFF;
				g = (rgb >> 8) & 0xFF;
				b = (rgb & 0xFF);
				gray = (r + g + b) / 3;
				topSum += gray;
                //System.out.print(gray + ", ");
			}
            //System.out.println();
		}
		//System.out.println(topSum);

//		for(int i = 0; i < window.width; i++){
//			for(int j = (int)window.height / 2; j < window.height; j++){
//	            r = new Color(image.getRGB(i, j)).getRed();
//	            g = new Color(image.getRGB(i, j)).getGreen();
//	            b = new Color(image.getRGB(i, j)).getBlue();
//				bottomSum += (r + g + b) / 3;
//			}
//		}
//		
		return topSum; //(bottomSum - topSum) / 1000;
		
	}
	

}
