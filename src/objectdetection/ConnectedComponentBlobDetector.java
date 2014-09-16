package objectdetection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * User: deansmiller
 * Date: 16/09/14
 * Time: 16:30
 */
public class ConnectedComponentBlobDetector {

    private ArrayList labels = new ArrayList();

    public ConnectedComponentBlobDetector(){

    }

    public BufferedImage detect(BufferedImage image){
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int total = image.getHeight() * image.getWidth();
        System.out.println("Total: " + total);
        int count = 1;
        double perc;
        for(int w = 1; w < image.getWidth() - 1; w++){
            for(int h = 1; h < image.getHeight() - 1; h++){
                perc = (total / count++) * 100;
                System.out.println(perc);
                checkPixelNeighbours(image, w, h);
            }
        }
        System.out.println(labels);
        return output;
    }

    private void checkPixelNeighbours(BufferedImage image, int x, int y){
        if(image.getRGB(x - 1, y) == Color.WHITE.getRGB()){
            checkPixelNeighbours(image, x - 1, y);
        }
        if(image.getRGB(x + 1, y) == Color.WHITE.getRGB()){
            checkPixelNeighbours(image, x - 1, y);
        }
        if(image.getRGB(x, y - 1) == Color.WHITE.getRGB()){
            checkPixelNeighbours(image, x - 1, y);
        }
        if(image.getRGB(x, y + 1) == Color.WHITE.getRGB()){
            checkPixelNeighbours(image, x - 1, y);
        }

        labels.add(labels.size() + 1);
    }


    private class Pixel {
        public int x, y;
        public String label;
        public Pixel(int x, int y, String label){
            this.x = x;
            this.y = y;
            this.label = label;
        }
    }
}
