package webcam;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import neuralnetwork.Network;
import pcaknn.Data;
import pcaknn.PCA;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamPanel;

public class VideoPanel extends WebcamPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private double[] input;
	private PCA pca = new PCA();
	private Data data;
	private Network network;
	
	public VideoPanel(Webcam webcam, boolean start) {
		super(webcam, start);
	}

	public VideoPanel(Webcam arg0, Dimension arg1, boolean arg2) {
		super(arg0, arg1, arg2);
	}

	public VideoPanel(Webcam webcam) {
		super(webcam);
	}
	
	public void webcamImageObtained(WebcamEvent we) {
//		image = ImageUtils.scaleImage(we.getImage(), 24, 24);
//		data = ImageUtils.toData(image);
//		pca.setData(data);
//		input = pca.deriveFeatureVector().getRowPackedCopy();
//		try {
//			network.applyInput(input);
//			if(network.getOutput()[0] > 0.98) System.out.println("STOP GESTURE DETECTED!!");
//
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	
	}
	
}
