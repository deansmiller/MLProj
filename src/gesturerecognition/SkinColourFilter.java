package gesturerecognition;

import utils.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: deansmiller
 * Date: 15/09/14
 * Time: 16:21
 */
public class SkinColourFilter {

    private int BLACK = Color.BLACK.getRGB();
    private int WHITE = Color.WHITE.getRGB();
    private float[][] imageMatrix;

    public SkinColourFilter(){

    }

    private void convertToHSB(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        Color colour;
        imageMatrix = new float[width][];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                colour = new Color(image.getRGB(i, j));
                imageMatrix[i] = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
            }
        }
    }

    public BufferedImage filter(BufferedImage image){
        convertToHSB(image);
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filtered = new BufferedImage(width, height, image.getType());
        int H = 0;
        float h = 0.0f;
        float[] white = {0.0f, 0.0f, 1.0f};
        float[] black = {0.0f, 0.0f, 0.0f};
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                h = imageMatrix[i][H];
                if(h < 0.18 || h > 0.175)
                   imageMatrix[i] = white;
                else imageMatrix[i] = black;
            }
        }

        int rgb = 0;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                rgb = Color.HSBtoRGB(imageMatrix[i][0], imageMatrix[i][1], imageMatrix[i][2]);
                Color c = new Color(rgb);
                System.out.println(c);
                filtered.setRGB(i, j, rgb);
            }
        }
        return filtered;
    }

}
