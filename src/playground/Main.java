package playground;

import handdetection.DropLine;
import handdetection.FingerCounter;
import handdetection.NNSkinFilter;
import neuralnetwork.Network;
import objectdetection.ConnectedComponentBlobDetector;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * User: deansmiller
 * Date: 16/09/14
 * Time: 16:59
 */
public class Main {

    public static void main(String[] args) {
        try {
            String filename = "1.jpg";
            BufferedImage image = ImageUtils.getBufferedImage("resources/test_images/" + filename);
            NNSkinFilter nnSkinFilter = new NNSkinFilter(Network.createNetworkFromFile("resources/skin.network"), 0.9);
            BufferedImage filteredImage = nnSkinFilter.filterSkinPixels(image);
            FingerCounter fingerCounter = new FingerCounter();
            ArrayList<DropLine> fingerTips = fingerCounter.countFingers(filteredImage);

            DropLine tip = null;
            Graphics g = image.getGraphics();
            g.setColor(Color.RED);
            for(int i = 0; i < fingerTips.size(); i++){
                tip = fingerTips.get(i);
                g.fillOval(tip.x.intValue(), tip.y.intValue(), 20, 20);
            }
            // show off
            Font f = new Font("Dialog", Font.PLAIN, 20);
            g.setFont(f);
            g.drawString("Fingers: " + fingerTips.size(), 5, 40);
            System.out.println("Fingers: " + fingerTips.size());
            ImageUtils.saveImage(image, "resources/output_images/output_" + filename);
            System.out.println("Completed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
