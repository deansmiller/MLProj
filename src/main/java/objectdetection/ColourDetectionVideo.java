package objectdetection;

import com.github.sarxos.webcam.WebcamImageTransformer;
import utils.ImageUtils;
import webcam.Video;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Set;

/**
 * User: deansmiller
 * Date: 17/09/14
 * Time: 16:40
 */
public class ColourDetectionVideo {

    public static void main(String... args){

        try {

            int WIDTH = 320;
            int HEIGHT = 240;

            new Video(new WebcamImageTransformer() {
                private Set<Region> regions;
                private Iterator<Region> iterator;
                private BlobDetector blobDetector = new BlobDetector(WIDTH, HEIGHT);
                private ColourFilter colourFilter = new ColourFilter(Color.BLUE, 0.7);

                @Override
                public BufferedImage transform(BufferedImage image) {
                    BufferedImage original = ImageUtils.copyImage(image);
                    try {
                        image = colourFilter.filter(image);
                        regions = blobDetector.localize(image);
                        Graphics2D g2 = original.createGraphics();
                        g2.setColor(Color.GREEN);
                        iterator = regions.iterator();
                        while(iterator.hasNext()) {
                            Region region = iterator.next();
                            g2.draw(region);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return original;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
