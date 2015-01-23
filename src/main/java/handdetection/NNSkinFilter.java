package handdetection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import neuralnetwork.Network;

public class NNSkinFilter {
	
	private Network nn;
	private final int RED = 0, GREEN = 1, BLUE = 2, RGB = 3;
	private double threshold;
	private Color tmpColor;
	private double[] input = new double[RGB];

	
	public NNSkinFilter(Network nn, double threshold) throws Exception {
		this.nn = nn;
		this.threshold = threshold;
	}
	
	
	public BufferedImage filterSkinPixels(BufferedImage image) throws Exception {
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

		for(int w = 0; w < output.getWidth(); w++){
			for(int h = 0; h < output.getHeight(); h++){
				tmpColor = new Color(image.getRGB(w, h));
				input[RED] = tmpColor.getRed();
				input[GREEN] = tmpColor.getGreen();
				input[BLUE] = tmpColor.getBlue();
                nn.applyInput(input);
				if(nn.getOutput()[0] > threshold){
					output.setRGB(w, h, Color.WHITE.getRGB());
				} else output.setRGB(w, h, Color.BLACK.getRGB());

			}
		}
		
		return output;
	}

}
