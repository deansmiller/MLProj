package handdetection;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 *  Assumptions: The image is of a hand and the hand is Color.WHITE
 *  and the background is Color.BLACK
 *  This class works by drawing lines SCAN_WIDTH apart vertically until contact with a white pixel
 *  It then looks at neighbouring lines (drop lines) and removes the ones that are longest
 *  Leaving the shortest lines which must be finger tips.
 */


public class FingerCounter {
	
	private int SCAN_WIDTH = 20;
	private double THRESHOLD = 1.05; // 5%
	private ArrayList<Integer> drops;
	private ArrayList<DropLine> lines;
	private Double averageDrop = 0.0;
	private int MARKER_SIZE = 20;
	
	public FingerCounter(int scanWidth, double threshold){
		SCAN_WIDTH = scanWidth;
		THRESHOLD = threshold;
		drops = new ArrayList<>();
		lines = new ArrayList<>();		
	}

    public FingerCounter(int scanWidth){
        SCAN_WIDTH = scanWidth;
        drops = new ArrayList<>();
        lines = new ArrayList<>();
    }
	
	public FingerCounter(){
		drops = new ArrayList<>();
		lines = new ArrayList<>();		
	}
	
	public void setThreshold(double t){
		THRESHOLD = 1 + t;
	}
	
	public ArrayList<DropLine> countFingers(BufferedImage image){
		drops.clear();
		lines.clear();
		int width = image.getWidth();
		int height = image.getHeight();
		Graphics g = image.getGraphics();
		g.setColor(Color.RED);
		for(int i = 0; i < width; i += SCAN_WIDTH){
			for(int j = 0; j < height; j++){
				int pixel = image.getRGB(i, j);
				if(pixel == Color.white.getRGB()){
					if(i > 0){
						drops.add(j);
						lines.add(new DropLine(i, j));
						break;
					}
				}
			}
		}
		
		if(drops.size() > 0){
			double[] x = new double[drops.size()];
			double[] y = new double[drops.size()];
			
			for(int i = 0; i < drops.size(); i++){
				x[i] = i;
				y[i] = drops.get(i);
			}

			// thumb stuff
//			DropLine leftist = maxDropLine(lines);
//			System.out.println(leftist);
//			g.fillOval(leftist.x.intValue(), leftist.y.intValue(), MARKER_SIZE, MARKER_SIZE);
			
			DropLine tip = null;
			ArrayList<DropLine> fingerTips = countFingers(lines);
			for(int i = 0; i < fingerTips.size(); i++){
				tip = fingerTips.get(i);
				g.fillOval(tip.x.intValue(), tip.y.intValue(), MARKER_SIZE, MARKER_SIZE);
			}
			return fingerTips;
		} 
		return null;
		
	}
	
	
	public BufferedImage count(BufferedImage image){
		drops.clear();
		lines.clear();
		int width = image.getWidth();
		int height = image.getHeight();
		Graphics g = image.getGraphics();
		g.setColor(Color.RED);
		for(int i = 0; i < width; i += SCAN_WIDTH){
			for(int j = 0; j < height; j++){
				int pixel = image.getRGB(i, j);
				if(pixel == Color.white.getRGB()){
					if(i > 0){
						drops.add(j);
						lines.add(new DropLine(i, j));
						break;
					}
				}
			}
		}
		
		if(drops.size() > 0){
			double[] x = new double[drops.size()];
			double[] y = new double[drops.size()];
			
			for(int i = 0; i < drops.size(); i++){
				x[i] = i;
				y[i] = drops.get(i);
			}

			// thumb stuff
//			DropLine leftist = maxDropLine(lines);
//			System.out.println(leftist);
//			g.fillOval(leftist.x.intValue(), leftist.y.intValue(), MARKER_SIZE, MARKER_SIZE);
			
			DropLine tip = null;
			ArrayList<DropLine> fingerTips = countFingers(lines);
			for(int i = 0; i < fingerTips.size(); i++){
				tip = fingerTips.get(i);
				g.fillOval(tip.x.intValue(), tip.y.intValue(), MARKER_SIZE, MARKER_SIZE);
			}
			
			System.out.println(fingerTips);
			
			// show off
			Font f = new Font("Dialog", Font.PLAIN, 20);
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Fingers: " + fingerTips.size(), 5, 40);
			System.out.println("Fingers: " + fingerTips.size());
		} 
		g.dispose();
		return image;
	}
	
	private ArrayList<DropLine> countFingers(ArrayList<DropLine> lines){
		ArrayList<DropLine> fingerTips = new ArrayList<DropLine>();
		// calculate average drop
		for(int i = 0; i < lines.size(); i++){
			averageDrop += lines.get(i).y;
		}
		averageDrop /= lines.size();
		
		// lowest drops, must be finger tips
		for(int i = 0; i < lines.size(); i++){
			double mag = lines.get(i).y;
			if(mag < averageDrop) fingerTips.add(lines.get(i));
		}

		fingerTips = removeExtraDropLines(fingerTips);
		return fingerTips;
	}
	
	public ArrayList<DropLine> removeExtraDropLines(ArrayList<DropLine> fingerTips){	
		DropLine lastDrop = null;
		DropLine nextDrop = null, thisDrop = null;
	
		
		for(int i = 1; i < fingerTips.size(); i++){
			lastDrop = fingerTips.get(i - 1);
			thisDrop = fingerTips.get(i);
			if(i < fingerTips.size() - 1)
				nextDrop = fingerTips.get(i + 1);
			
			if(thisDrop.x - SCAN_WIDTH == lastDrop.x && thisDrop.y > lastDrop.y){
				fingerTips.remove(thisDrop);
			} else if(thisDrop.x - SCAN_WIDTH == lastDrop.x && thisDrop.y < lastDrop.y){
				fingerTips.remove(lastDrop);
			}
			
			if(nextDrop != null){

				if(thisDrop.x + SCAN_WIDTH == nextDrop.x && thisDrop.y > nextDrop.y){
					fingerTips.remove(thisDrop);
				} else if(thisDrop.x + SCAN_WIDTH == nextDrop.x && thisDrop.y < nextDrop.y){
					fingerTips.remove(nextDrop);
				}
			}
		}
		return fingerTips;
	}
	
	private DropLine maxDropLine(ArrayList<DropLine> dropLines){
		DropLine max = null;
		max = dropLines.get(0);
		for(int i = 1; i < dropLines.size(); i++){
			if(dropLines.get(i).x < max.x) max = dropLines.get(i);
		}
		System.out.println(max);
		return max;
	}
	
}
