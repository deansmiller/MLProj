package gesturerecognition;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import pcaknn.Data;
import pcaknn.ImageReader;

public class ColourPixelsImageReader extends ImageReader {
	
	private Color[][] colours;
	
	public ColourPixelsImageReader() {
		super();
	}

	public ColourPixelsImageReader(String path) throws IOException {
		super(path);
	}
	
	protected void scaleImage(){
		rImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = rImage.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(oImage, 0, 0, scaledWidth, scaledHeight, null);
	    g2.dispose();
	}
	
	public Data read(){
		scaleImage();
		data = new double[scaledWidth][scaledHeight];
		for(int h = 0; h < scaledWidth; h++){
			for(int w = 0; w < scaledHeight; w++){
				data[h][w] = rImage.getRGB(h, w);
			}
		}
		return new Data(data, scaledWidth, scaledHeight);
	}
	
	public Color[][] readColours(){
		scaleImage();
		colours = new Color[scaledWidth][scaledHeight];
		for(int h = 0; h < scaledWidth; h++){
			for(int w = 0; w < scaledHeight; w++){
				colours[w][h] = new Color(rImage.getRGB(h, w));
			}
		}
		return colours;
	}
	
	public ArrayList<Color> toColourArray(){
		Data tmp = read();
		double[] d = tmp.getMatrix().getRowPackedCopy();
		ArrayList<Color> colours = new ArrayList<Color>();
		for(int w = 0; w < d.length; w++){
			colours.add(new Color((int) d[w]));
		}
		return colours;
	}
	
	
}
