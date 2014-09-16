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
    private ArrayList<Region> regions = new ArrayList();
    private int counter = 0;

    public ConnectedComponentBlobDetector(){

    }

    public BufferedImage detect(BufferedImage image){
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int w = 1; w < image.getWidth() - 1; w++){
            for(int h = 1; h < image.getHeight() - 1; h++){

                checkPixelNeighbours(image, w, h);
            }
        }
        long count = regions.stream().filter(region -> region.x == 0 && region.y == 0 && region.width == 0 && region.height == 0).count();
        System.out.println(count);
        return output;
    }

    private void checkPixelNeighbours(BufferedImage image, int x, int y){
        Region region = new Region();
        if(!(image.getRGB(x + 1, y) == Color.WHITE.getRGB() && image.getRGB(x, y - 1) == Color.WHITE.getRGB())){
            counter++;

        } else if(image.getRGB(x + 1, y) == Color.WHITE.getRGB() && image.getRGB(x, y - 1) == Color.WHITE.getRGB()){
            region.add(x, y - 1);
            region.add(x + 1, y);
        } else {

            if(image.getRGB(x + 1, y) == Color.WHITE.getRGB()){
                region.add(x + 1, y);
            }
            if(image.getRGB(x, y - 1) == Color.WHITE.getRGB()){
                region.add(x, y - 1);
            }
        }
       regions.add(region);


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
