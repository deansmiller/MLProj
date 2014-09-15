package nn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {

	public static void main(String[] args) {

		try {		

			PatternReader train = new PatternReader("resources/real_skin_data.csv"); 
			ArrayList<Pattern> trainingSet = new ArrayList<Pattern>();
			trainingSet.addAll(train.read());
			Collections.shuffle(trainingSet);
			
			List<Pattern> set1 = new ArrayList();
			List<Pattern> set2 = new ArrayList();
			int half = trainingSet.size() / 2;
			int i = 0;
			for(Pattern pattern : trainingSet){
				if(i <= half) set1.add(pattern);
				else set2.add(pattern);
				i++;
			}
			
			if(set1.size() + set2.size() != trainingSet.size()) throw new Exception("Set sizes do not equal total amount of training patterns");
			
			Network n = new Network(3, 12, 1);
			n.setParameters(0.001, 0.001);
			n.setPatterns(set2);
			n.train();
			Collections.shuffle(set1);
			n.testRun(set1);
			n.persistNetwork("resources/skin_classifier2.nn");
//			
//
//			BufferedImage b = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/sample faces/test5.jpg");	
//			Network face = Network.createNetworkFromFile("/home/deansmiller/Dropbox/Jarvis/src/test.data");
//			face.applyInput(ImageUtils.toMatrix(b));
//			System.out.println(face.getOutput()[0]);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
