package objectdetection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * User: deansmiller
 * Date: 16/09/14
 * Time: 16:30
 */
public class ConnectedComponentBlobDetector {

    private List<Pixel> edgePixels;
    private List<Pixel> test;
    private int counter = 0;

    public ConnectedComponentBlobDetector(){
        edgePixels = new ArrayList<>();
        test = new ArrayList<>();
    }

    public BufferedImage detect(BufferedImage image){
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Color colourPixel;
        for(int w = 1; w < image.getWidth() - 1; w++){
            for(int h = 1; h < image.getHeight() - 1; h++){
                colourPixel = new Color(image.getRGB(w, h));
                Pixel p = new Pixel(w, h);
                if(colourPixel.getRGB() == Color.WHITE.getRGB()){
                    getEdgePixels(p, image);
                } else {
                    blackPixel(p, image);
                }
            }
        }

//        for(int w = 1; w < image.getWidth() - 1; w++){
//            for(int h = 1; h < image.getHeight() - 1; h++){
//                colourPixel = new Color(image.getRGB(w, h));
//                if(colourPixel.getRGB() == Color.WHITE.getRGB()){
//                    Pixel p = new Pixel(w, h);
//
//                }
//            }
//        }

        for(Pixel p : edgePixels){
            output.setRGB(p.x, p.y, Color.GREEN.getRGB());
        }
;
        return output;
    }

    private void blackPixel(Pixel pixel, BufferedImage image){
        if(image.getRGB(pixel.x + 1, pixel.y) == Color.WHITE.getRGB() && edgePixels.contains(new Pixel(pixel.x + 1, pixel.y))){
            pixel.label = "" + counter;
        } else if(image.getRGB(pixel.x - 1, pixel.y) == Color.WHITE.getRGB() && edgePixels.contains(new Pixel(pixel.x - 1, pixel.y))){
            pixel.label = "" + counter;
        } else if(image.getRGB(pixel.x, pixel.y + 1) == Color.WHITE.getRGB() && edgePixels.contains(new Pixel(pixel.x, pixel.y + 2))){
            pixel.label = "" + counter;
        } else if(image.getRGB(pixel.x, pixel.y - 1) == Color.WHITE.getRGB() && edgePixels.contains(new Pixel(pixel.x, pixel.y - 1))){
            pixel.label = "" + counter;
        } else counter++;
    }

    private void getEdgePixels(Pixel pixel, BufferedImage image){
        int count = 0;
        if(image.getRGB(pixel.x + 1, pixel.y) != Color.WHITE.getRGB()){
            edgePixels.add(pixel);
            count++;
        } else if(image.getRGB(pixel.x - 1, pixel.y) != Color.WHITE.getRGB()){
            edgePixels.add(pixel);
            count++;
        } else if(image.getRGB(pixel.x, pixel.y + 1) != Color.WHITE.getRGB()){
            edgePixels.add(pixel);
            count++;
        } else if(image.getRGB(pixel.x, pixel.y - 1) != Color.WHITE.getRGB()){
            edgePixels.add(pixel);
            count++;
        }


    }






    private class Pixel {
        public int x, y;
        public String label;
        public Pixel(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
