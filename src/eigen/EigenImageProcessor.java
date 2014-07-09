package eigen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ojalgo.matrix.BasicMatrix.Factory;
import org.ojalgo.matrix.PrimitiveMatrix;

import utils.ArrayUtils;
import utils.ImageUtils;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import Jama.SingularValueDecomposition;


public class EigenImageProcessor {
	

	private ArrayList<EigenImage> eigenImages;
	private Matrix meanMatrix, originalMatrix, differenceImagesMatrix, eigenVectorMatrix, weightsMatrix, kVectorMatrix;
	private int threshold;
	
	public EigenImageProcessor(String directory) throws IOException{
		File dir = new File(directory);
		List<File> files = Arrays.asList(dir.listFiles());
		eigenImages = new ArrayList<EigenImage>();
		for(File file : files){
			eigenImages.add(new EigenImage(file));
		}
	}
	
	public Matrix createMatrix() throws Exception{
		Matrix matrix;
		int rows = eigenImages.get(0).getVector().length;
		int cols = eigenImages.size();
		double[][] tmp = new double[cols][rows];
		double[] vector;
		for(int col = 0; col < cols; col++){
			vector = eigenImages.get(col).getVector();
			for(int row = 0; row < rows; row++){
				tmp[col][row] = vector[row];
				
			}
		}
		
		matrix = new Matrix(tmp);
		matrix = matrix.transpose();
		originalMatrix = matrix;
		
//		vector = ArrayUtils.getColumn(originalMatrix, 0);
//		Matrix M = ArrayUtils.toMatrix(vector, rows, 1);
//		ImageUtils.saveImage(this.drawEigenImage(matrix), "/home/deansmiller/Desktop/b.jpg");
//		ImageUtils.toData(ImageUtils.getBufferedImage("/home/deansmiller/Documents/test1/2b.jpg")).print();
//		double[] T;
//		for(int i = 0; i < cols; i++){
//			T = ArrayUtils.getColumn(originalMatrix, i);
//			ImageUtils.saveImage(this.drawEigenImage(ArrayUtils.toMatrix(T, rows, 1)), "/home/deansmiller/Desktop/b" + i + ".jpg");
//		}
		return matrix;
	}
	
	
	public void calculateMeanImage(Matrix matrix) throws Exception {
		//calculate average
		int rows = matrix.getRowDimension();
		int cols = matrix.getColumnDimension();

		double[] column = ArrayUtils.getColumn(matrix, 0);
		for(int i = 1; i < cols; i++){
			column = ArrayUtils.plus(column, ArrayUtils.getColumn(matrix, i));
		}
		
		for(int i = 0; i < rows; i++){
			column[i] = (int) column[i] /  cols;
		}
		
		meanMatrix = ArrayUtils.toMatrix(column, rows, 1);
		ImageUtils.saveImage(this.drawEigenImage(meanMatrix), "/home/deansmiller/Desktop/MEAN.jpg");
	}
	
	
	
	public void subtractMean() throws Exception{ // 
		System.out.println("Subtracting mean..");
		int cols = originalMatrix.getColumnDimension();
		int rows = originalMatrix.getRowDimension();
		double[] T;
		double[][] tmp = new double[cols][rows];
		for(int i = 0; i < cols; i++){
			T = ArrayUtils.subtract(ArrayUtils.getColumn(originalMatrix, i), ArrayUtils.mean(ArrayUtils.getColumn(originalMatrix, i)));
			tmp[i] = T;
			//ImageUtils.saveImage(this.drawEigenImage(ArrayUtils.toMatrix(ArrayUtils.getColumn(originalMatrix, i), originalMatrix.getRowDimension(), 1)), "/home/deansmiller/Desktop/" + i + ".jpg");
		}
		
		//ImageUtils.saveImage(this.drawEigenImage(ArrayUtils.toMatrix(ArrayUtils.getColumn(originalMatrix, 0), originalMatrix.getRowDimension(), 1)), "/home/deansmiller/Desktop/before.jpg");

		differenceImagesMatrix = new Matrix(tmp).transpose();
		ArrayUtils.dimensions(differenceImagesMatrix);

	}
	
	public Matrix computeCovMatrix(){
		Matrix cov = differenceImagesMatrix.transpose().times(differenceImagesMatrix);
		ArrayUtils.dimensions(cov);
		return cov;
	}
	
	public void computeSVD(Matrix cov){
		System.out.println("Computing SVD..");
		SingularValueDecomposition svd = cov.svd();
        System.out.println("Computed SVD..");
		eigenVectorMatrix = svd.getU();
		
	}
	

	
	
	public void computeEigenVectors(int K) throws Exception{

	}
	
	public boolean detect(String filepath) throws Exception{
		EigenImage image = new EigenImage(new File(filepath));
//		Matrix m = image
		boolean detected = true;
		double[] differenceVector = ArrayUtils.subtract(image.matrix.getColumnPackedCopy(), meanMatrix.getColumnPackedCopy());
		Matrix differenceMatrix = ArrayUtils.toMatrix(differenceVector, differenceVector.length, 1); //mean adjusted image
		ArrayUtils.dimensions(kVectorMatrix);
		Matrix inputImageWeights = kVectorMatrix.times(differenceMatrix);
		Matrix projection = kVectorMatrix.times(inputImageWeights);
		Matrix det = differenceMatrix.minus(projection);
		double distance = calculateEuclideanDistance(det.getColumnPackedCopy(), meanMatrix.getColumnPackedCopy()).doubleValue() / 1000000000;
		if(distance >= threshold){
			System.out.println("No face detected: " + filepath + " distance: " + distance);
			detected = false;
		} else System.out.println("Face detected: " + filepath + " distance: " + distance);
		return detected;
		
	}
	
	public void classify(String filepath) throws Exception{
		System.out.println("Classifying " + filepath);
		EigenImage image = new EigenImage(new File(filepath));
		//project image into facespace
		double[] differenceVector = ArrayUtils.subtract(image.matrix.getColumnPackedCopy(), meanMatrix.getColumnPackedCopy());
//		PrintUtils.print(image.matrix.getColumnPackedCopy());
//		PrintUtils.print(meanMatrix.getColumnPackedCopy());
		Matrix differenceMatrix = ArrayUtils.toMatrix(differenceVector, differenceVector.length, 1);
		Matrix W = kVectorMatrix.times(differenceMatrix);
		for(int i = 0; i < weightsMatrix.getColumnDimension(); i++){
			double[] input = ArrayUtils.getColumn(W, 0);
			double[] t = ArrayUtils.getColumn(weightsMatrix, i);
			double distance = calculateEuclideanDistance(input, t).doubleValue();
			System.out.println(distance);
		}
	}
	
	private BigDecimal calculateEuclideanDistance(double[] a, double[] b) throws Exception{
		double distance = 0;
		if(a.length != b.length) throw new Exception(a.length +  " != " + b.length);
		for(int i = 0; i < a.length; i++){
			distance += Math.pow(a[i] - b[i], 2);
		}

		return BigDecimal.valueOf(Math.sqrt(distance));
	}
	
	private BigDecimal calculateMahalanobisDistance(double[] a, double[] b) throws Exception{
		double distance = 0;
		if(a.length != b.length) throw new Exception(a.length +  " != " + b.length);
		for(int i = 0; i < a.length; i++){
			distance += Math.pow(a[i] - b[i], 2);
		}

		return BigDecimal.valueOf(Math.sqrt(distance));
	}
	
	private BufferedImage drawEigenImage(Matrix eigen){
		int imageSize = (int)Math.sqrt(eigen.getRowDimension());
		double[] eigenVector = eigen.getColumnPackedCopy();
		BufferedImage eigenImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		int count = 0;
		for(int i = 0; i < imageSize; i++){
			for(int j = 0; j < imageSize; j++){
				eigenImage.setRGB(j, i, (int) eigenVector[count]);
				//System.out.print(eigenVector[count] + ",");
				count++;
			}
			//System.out.println();
		}
		System.out.println("Eigenimage rendered...");
		return ImageUtils.greyScale(eigenImage);
	}
	
	private BufferedImage drawEigenImage(double[] vector, int imageSize){
		System.out.println("Rendering vector");
		BufferedImage eigenImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_BYTE_GRAY);
		int count = 0;
		int x = 0;
		for(int i = 0; i < vector.length; i+=imageSize){
			for(int j = 0; j < imageSize; j++){
				eigenImage.setRGB(j, x, (int) vector[count]);
				//System.out.print(vector[count] +  ",");
				count++;
			}
			x++;
			//System.out.println();
		}
		return eigenImage;
	}

	public void setDetectionThreshold(int i) {
		threshold = i;
		
	}
	
	
	
	

}
