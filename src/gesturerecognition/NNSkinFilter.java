package gesturerecognition;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import nn.Network;

public class NNSkinFilter {
	
	private List<Network> nns;
	private final int RED = 0, GREEN = 1, BLUE = 2, RGB = 3;
	private double threshold;
	private Color tmpColor;
	private double[] input = new double[RGB];

	
	public NNSkinFilter(List<Network> nns, double threshold) throws Exception {
		this.nns = nns;
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
				boolean isSkin = true;
				for(Network n : nns){
					n.applyInput(input);
					if(n.getOutput()[0] > threshold) isSkin = true;
				}
				
				if(isSkin){
					output.setRGB(w, h, Color.WHITE.getRGB());
				} else output.setRGB(w, h, Color.BLACK.getRGB());

			}
		}
		
		return output;
	}

}
