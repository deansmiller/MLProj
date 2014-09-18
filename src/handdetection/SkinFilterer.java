package handdetection;

import com.github.sarxos.webcam.WebcamImageTransformer;
import neuralnetwork.Network;
import objectdetection.BlobDetector;
import objectdetection.Region;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * User: deansmiller
 * Date: 17/09/14
 * Time: 16:54
 *
 * Used to transform webcam video images by filtering skin pixels using a neural network
 * and .....
 */


public class SkinFilterer implements WebcamImageTransformer {

    private Set<Region> regions;
    private Iterator<Region> iterator;
    private BlobDetector blobDetector;
    private NNSkinFilter nnSkinFilter;


    public SkinFilterer(int width, int height){
        blobDetector = new BlobDetector(width, height);
        try {
            nnSkinFilter = new NNSkinFilter(Network.createNetworkFromFile("resources/skin.network"), 0.7);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public BufferedImage transform(BufferedImage image) {
        BufferedImage original = ImageUtils.copyImage(image);
        try {
            image = nnSkinFilter.filterSkinPixels(image);
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
}
