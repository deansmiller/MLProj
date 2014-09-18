package playground;

import handdetection.NNSkinFilter;
import neuralnetwork.Network;
import neuralnetwork.Pattern;
import neuralnetwork.PatternReader;
import utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: deansmiller
 * Date: 16/09/14
 * Time: 16:59
 */
public class Main {

    public static void main(String[] args) {
        try {
            PatternReader reader = new PatternReader("resources/real_skin_data.csv");
            List<Pattern> patterns = reader.read();
            ArrayList<Pattern> trainingSet = new ArrayList<>();
            trainingSet.addAll(patterns);
            Collections.shuffle(trainingSet);
            Network network = new Network(3, 14, 1);
            network.setParameters(0.001, 0.001);
            network.setPatterns(trainingSet);
            network.train();
            Collections.shuffle(trainingSet);
            network.testRun(trainingSet);
            network.persistNetwork("resources/skin.network");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
