package eigen;

import utils.ArrayUtils;
import Jama.Matrix;



public class Main {

	public static void main(String[] args) {
		
		try {
			
			EigenImageProcessor eImages = new EigenImageProcessor("/home/deansmiller/Pictures/faces/");
			Matrix E = eImages.createMatrix();
			ArrayUtils.dimensions(E);
			eImages.calculateMeanImage(E);
			eImages.subtractMean();
			Matrix C = eImages.computeCovMatrix();
			eImages.computeSVD(C);
			eImages.computeEigenVectors(5);
			System.out.println("Completed");
			
	
//			ImageFolderProcessor imageProcessor = new ImageFolderProcessor("/home/deansmiller/Dropbox/testfaces/");
//			imageProcessor.process(new ImageOperation(){
//
//				@Override
//				public BufferedImage execute(File imageFile) {
//					try {
//						BufferedImage image = ImageUtils.getBufferedImage(imageFile);
//						image = ImageUtils.scaleImage(image, 50, 50);
//						image = ImageUtils.greyScale(image);
//						ImageUtils.saveImage(image, "/home/deansmiller/Dropbox/testfaces/" + imageFile.getName());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					return null;
//				}
//				
//			});
			


			
			
//			ImageUtils.toData(b).print();
//			ImageUtils.saveImage(b, "/home/deansmiller/Desktop/facesMeanG.jpg");
//			EigenImage e = new EigenImage(new File("/home/deansmiller/Pictures/faces/3a.jpg"));
			
			


//			eImages.detect("/home/deansmiller/Documents/sample/subject03.sleepy");
//			eImages.detect("/home/deansmiller/Documents/sample/subject01.glasses");
//			eImages.detect("/home/deansmiller/Documents/sample/subject02.leftlight");
//			eImages.detect("/home/deansmiller/Dropbox/sample faces/test1.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/sample faces/test13.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/sample faces/test5.jpg");	
//			eImages.detect("/home/deansmiller/Dropbox/sample faces/test13.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/testfaces/1b.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/testfaces/15b.jpg");
//			System.out.println("Non faces");
//			eImages.detect("/home/deansmiller/Dropbox/Photos/Sample Album/test11.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/Photos/Sample Album/test8.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/Photos/Sample Album/brum.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/Photos/Sample Album/Costa Rican Frog.jpg");
//			eImages.detect("/home/deansmiller/Dropbox/Photos/Sample Album/Boston City Flow.jpg");
			
//			BufferedImage b = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/Photos/Sample Album/Boston City Flow.jpg");			
//			Network face = Network.createNetworkFromFile("/home/deansmiller/Dropbox/Jarvis/src/test.data");
//			face.applyInput(ImageUtils.toMatrix(b));
//			System.out.println(face.getOutput()[0]);
			
			
//			System.out.println();
//			eImages.detect("/home/deansmiller/Pictures/test8.jpg");
//			eImages.detect("/home/deansmiller/Pictures/test4.jpg");
//			eImages.detect("/home/deansmiller/Pictures/test3.jpg");

			
//			Network skinNN = Network.createNetworkFromFile(("/home/deansmiller/Dropbox/Jarvis/src/SKIN.data"));
//			BufferedImage original = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/sample faces/group3.jpg");
//			int scaledWidth = (int) (original.getWidth());
//			int scaledHeight = (int) (original.getHeight());
//			System.out.println("Filtering skin pixels..");
//			NNSkinFilter skin = new NNSkinFilter(skinNN, 0.8, scaledWidth, scaledHeight);
//			BufferedImage image = skin.filterSkinPixels(original);		
//			BlobDetector locale = new BlobDetector(scaledWidth, scaledHeight);
//			locale.setParameters(5, 22);
//			System.out.println("Localizing..");
//			Set<Region> regions = locale.localize(image);
//			System.out.println("Creating image..");
//			BufferedImage transformed = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
//		    Graphics2D g2 = original.createGraphics();
//			for(int w = 0; w < scaledWidth; w++){
//				for(int h = 0; h < scaledHeight; h++){
//					transformed.setRGB(w, h, image.getRGB(w, h));
//				}
//			}
//		    g2.setColor(Color.GREEN);
//			Iterator<Region> iterator = regions.iterator();
//			ArrayList<BufferedImage> candidates = new ArrayList<BufferedImage>();
//			ArrayList<Region> candidateRegions = new ArrayList<Region>();
//			System.out.println("Drawing regions..");
//			while(iterator.hasNext()) {
//				Region r = iterator.next();
//				g2.draw(r);
//				if(r.y + r.height < scaledHeight){
//					candidates.add(original.getSubimage(r.x, r.y, r.width, r.height));
//					candidateRegions.add(r);
//				}
//			}
			
//			int x = 0;
//			for(BufferedImage i : candidates){
//				i = ImageUtils.scaleGreyImage(i, 50, 50);
//				ImageUtils.saveImage(i, "/home/deansmiller/Pictures/test" + x + ".jpg");
//				if(eImages.detect("/home/deansmiller/Pictures/test" + x + ".jpg")){
//					
//				}
//				x++;
//				g2.draw(candidateRegions.get(x));
//			}
			
			
//			g2.dispose();
//			System.out.println("Saving image..");
//			ImageUtils.saveImage(original, "/home/deansmiller/Desktop/group3_.jpg");
//		    System.out.println("Completed..");
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
