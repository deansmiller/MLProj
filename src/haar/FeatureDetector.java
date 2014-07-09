package haar;

import gesturerecognition.Region;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import utils.ImageUtils;

public class FeatureDetector {
	
	private int windowSize = 5;
	private ArrayList<Region> windows = new ArrayList<Region>();
	private int threshold;
	
	public FeatureDetector(){
		threshold = (int) (Math.pow(windowSize, 2) * 0.25);
	}
	
	public void setWindowSize(int size){
		this.windowSize = size;
		threshold = (int) (Math.pow(windowSize, 2) * 0.25);
	}
	
	
	public ArrayList<Region>  detect(BufferedImage image) throws IOException{
		int width = image.getWidth();
		int height = image.getHeight();
		//ArrayList<Region> thresholdedRegions = new ArrayList<Region>();
		ArrayList<Region> markers = new ArrayList<Region>();
		Region window;
		Graphics g = image.getGraphics();
		g.setColor(Color.red);
		for(int w = 0; w < width - windowSize; w++){
			for(int h = 0; h < height - windowSize; h++){
				window = new Region(w, h, windowSize, windowSize);
				if(determineWhiteCount(image, window)){ 
					Region left = new Region(window.x - windowSize, window.y, windowSize, windowSize);
					Region right = new Region(window.x + (windowSize * 2), window.y, windowSize, windowSize);
					if(!determineWhiteCount(image, left)) {
						markers.add(window);
					}					
					
				}
			}
		}
	
//		for(int i = 0; i < thresholdedRegions.size(); i++){
//			window = thresholdedRegions.get(i);
//			Region left = new Region(window.x - windowSize, window.y, windowSize, windowSize);
//			Region right = new Region(window.x + (windowSize * 2), window.y, windowSize, windowSize);
//			if(!thresholdedRegions.contains(left) && !thresholdedRegions.contains(right)) {
//				//g.drawRect(window.x, window.y, 10, 10);
//				markers.add(new Region(window.x, window.y, window.width, window.height));
//			}
//			
//		}
		
		Region holder = markers.get(0);
		ArrayList<Region> n = new ArrayList<Region>();
		for(int i = 0; i < markers.size(); i++){
			window = markers.get(i);
			for(int j = 0; j < markers.size(); j++){
				if(window.intersects(markers.get(j)) && window != markers.get(j) && !window.intersects(holder)){
					n.add(window);
					g.drawRect(window.x, window.y, 5, 5);
					holder = (Region) window.clone();
				}
			}
		}
		
		
		ArrayList<Region> t = new ArrayList<Region>();
		for(int i = 0; i < n.size(); i++){
			window = n.get(i);
			if(!testRegionExistence(10, window, n)){
				//g.drawRect(window.x, window.y, 2, 2);
				t.add(window);
			}
			
		}		

		ImageUtils.saveImage(image, "/home/deansmiller/Desktop/test.jpg");
		return t;
	}
	
	
	// search regions arraylist for any regions that are within a specifed distance to the provided region
	private boolean testRegionExistence(int distance, Region region, ArrayList<Region> regions){
		boolean exists = false;
		for(Region r : regions){
			if(region != r && r.y + (distance) == region.y){ //below
				exists = true;
				break;
			} else if(region != r && r.y - (distance*2) == region.y){ //above
				exists = true;
				break;				
			}
				//else if(region != r && r.x - (distance) == region.x){ // left
//				exists = true;
//				break;				
//			} else if(region != r && r.x + (distance*2) == region.x){ // left
//				exists = true;
//				break;				
//			}
		}
		
		return exists;
	}
	
	
	private boolean determineWhiteCount(BufferedImage image, Region window){
		int whiteCount = 0;
		for(int w = window.x; w < window.width + window.x; w++){
			for(int h = window.y; h < window.height + window.y; h++){
				if(image.getRGB(w, h) == Color.WHITE.getRGB()) whiteCount++;
			}
		}
		if(whiteCount > threshold) return true;
		return false;
	}
	

}
