package eigen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import utils.ImageUtils;
import Jama.Matrix;

public class EigenImage {

	private BufferedImage image;
	private int width, height;
	Matrix matrix;
	String name;
	
	
	public EigenImage(File file) throws IOException{
		name = file.getName();
		image = ImageUtils.getBufferedImage(file);
		width = image.getWidth();
		height = image.getHeight();
		convertToMatrix();
		
	}
	
	
	private void convertToMatrix(){
		double[][] tmp = new double[width][height];
		int pixel, r, g, b;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//pixel = image.getRGB(i, y);
	            r = new Color(image.getRGB(i, j)).getRed();
	            g = new Color(image.getRGB(i, j)).getGreen();
	            b = new Color(image.getRGB(i, j)).getBlue();
				tmp[i][j] = ((r + g + b) / 3); 
				//tmp[i][j] = image.getRGB(i, j);
			}
		}
		matrix = new Matrix(tmp);
	}
	
	public double[] getVector(){;
		return matrix.getColumnPackedCopy();
	}
	
	public String toString(){
		return name;
	}
	
	

}
