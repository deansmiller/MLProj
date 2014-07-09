package pcaknn;

import gesturerecognition.NNSkinFilter;
import gesturerecognition.SkinLocalizer;

import java.awt.image.BufferedImage;

import nn.Network;
import utils.ImageUtils;


public class Main {

	public static void main(String[] args) {
		
		try{
//			BufferedImage test = ImageUtils.getBufferedImage("/home/deansmiller/Pictures/Webcam/tester2.jpg");
//			test = ImageUtils.scaleImage(test, 24, 24);
//			Network skinNN = Network.createNetworkFromFile("/home/deansmiller/Dropbox/Jarvis/src/real_skin_nn.data");
//			NNSkinFilter skin = new NNSkinFilter(skinNN, 0.9999, 24, 24);
//			SkinLocalizer loc = new SkinLocalizer(24, 24);
//			test = loc.localize(test);
//			test = skin.filterSkinPixels(test);
//			ImageUtils.saveImage(test, "/home/deansmiller/Pictures/test.jpg");
//			Data input = ImageUtils.toData(test);
//			DataFolderReader folderReader = new DataFolderReader("/home/deansmiller/Pictures/Jarvis Test Cases/J/filtered2");
//			KNN knn = new KNN(folderReader, 8, 6);
//			knn.calculateNearest(input);
			

			
		} catch(Exception e){
			System.out.println(e);
		}
		
	}

}
