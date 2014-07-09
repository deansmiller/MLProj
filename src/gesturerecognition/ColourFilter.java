package gesturerecognition;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColourFilter {
	
	private float[] hsb;
	private double threshold;
	private int BLACK = Color.BLACK.getRGB();
	private int WHITE = Color.WHITE.getRGB();
	
	public ColourFilter(Color colour, double threshold){
		hsb = toHSB(colour);
		this.threshold = threshold;
	}
	
	public BufferedImage filter(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage filtered = new BufferedImage(width, height, image.getType());
		Color colour;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				colour = new Color(image.getRGB(i, j));
				double red = colour.getRed();
				double green = colour.getGreen();
				double blue = colour.getBlue();
				double ratio1 = (red - green) / (red + green);
				double ratio2 = blue / (red + green);
				if(0.0 <= ratio1 && ratio1 <= 0.5 && ratio2 <= 0.5){
					filtered.setRGB(i, j, WHITE);
				} else filtered.setRGB(i, j, BLACK);
			}
		}
		return filtered;
	}
	
	private float[] toHSB(Color colour){
		return Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
	}
	
	private float[] toHSB(int pixel){
		return Color.RGBtoHSB((pixel>>16)&0xff, (pixel>>8)&0xff, pixel&0xff, null);
	}
	
	private double euclideanDistance(float[] pixelHSB){
		double sum = 0;
		for(int i = 0; i < hsb.length; i++){
			sum += Math.pow(hsb[i] - pixelHSB[i], 2);
		}
		
		return Math.sqrt(sum);
	}

}
