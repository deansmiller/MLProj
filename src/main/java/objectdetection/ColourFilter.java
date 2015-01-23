package objectdetection;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColourFilter {
	
	private float[] selectedHsb;
	private double threshold;
	private int BLACK = Color.BLACK.getRGB();
	private int WHITE = Color.WHITE.getRGB();
	
	public ColourFilter(Color colour, double threshold){
		selectedHsb = toHSB(colour);
		this.threshold = threshold;
	}

    public void setThreshold(double t){
        threshold = t;
    }
	
	public BufferedImage filter(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage filtered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Color colour;
        float[] hsb;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				colour = new Color(image.getRGB(i, j));
                hsb = toHSB(colour);
                //System.out.println(hsb[0] + "," + "," + hsb[1] + "," + hsb[2]);
                if(euclideanDistance(hsb) < threshold){

					filtered.setRGB(i, j, WHITE);
				} else filtered.setRGB(i, j, BLACK);
			}
		}
		return filtered;
	}
	
	private float[] toHSB(Color colour){
		return Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
	}

	
	private double euclideanDistance(float[] pixelHSB){
		double sum = 0;
		for(int i = 0; i < selectedHsb.length; i++){
			sum += Math.pow(selectedHsb[i] - pixelHSB[i], 2);
		}
		
		return Math.sqrt(sum);
	}

}
