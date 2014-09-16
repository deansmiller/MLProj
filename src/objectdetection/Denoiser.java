package objectdetection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * User: deansmiller
 * Date: 15/09/14
 * Time: 10:50
 */
public class Denoiser {

    private BufferedImage image;
    private ArrayList<Pixel> pixels;


    public Denoiser(BufferedImage image){
        this.image = image;
        pixels = new ArrayList<>();
    }

//    public BufferedImage denoise(){
//        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
//
//        for(int w = 0; w < image.getWidth(); w++){
//            for(int h = 0; h < image.getHeight(); h++){
//                test(new Pixel(w, h));
//            }
//        }
//
//
//
//        return output;
//    }
//
//    private boolean test(Pixel pixel){
//        // get neighbouring pixels
//        int x = pixel.x, y = pixel.y;
//        if(image.getRGB(x, y) == Color.WHITE.getRGB()){
//            test(new Pixel(x - 1, y)); // one left
//            test(new Pixel(x + 1, y)); // one right
//            test(new Pixel(x, y + 1)); // one down
//            test(new Pixel(x, y - 1)); // one above
//            return true;
//        }
//
//        return false;
//
//    }

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
