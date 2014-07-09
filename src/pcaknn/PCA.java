package pcaknn;

import Jama.Matrix;

/*
 * 
 * Principal Component Analysis class used for dimension reduction
 * 
 */

public class PCA {
	
	private Data X, Y, covarianceMatrix, meanAdjustedData;
	private double[] pXTmp, pYTmp;
	private Matrix eigenVectors, featureVector;
	
	public PCA(Data X, Data Y){
		this.X = X;
		this.Y = Y;
	}
	
	public PCA(Data X){
		this.X = X;
		this.Y = null;
	}
	
	public PCA(){}
	
	public void setData(Data X){
		this.X = X;
	}
	
	private double calculateMean(double[] data){
		double sum = 0;
		for(int i = 0; i < data.length; i++){
			sum += data[i];
		}
		return sum / (data.length);
	}
	
	private double calculateCovariance(double[] x, double[] y){
		double cov = 0;
		for(int i = 0; i < x.length; i++){
			cov += x[i] * y[i];
		}
		return cov / (x.length - 1);
	}
	
	
	public Data calculateCovarianceMatrix(){
		if(Y != null){
			double meanX = calculateMean(X.getMatrix().getRowPackedCopy());
			double meanY = calculateMean(Y.getMatrix().getRowPackedCopy());
			pXTmp = X.getMatrix().getRowPackedCopy();
			pYTmp = Y.getMatrix().getRowPackedCopy();
			for(int i = 0; i < pXTmp.length; i++){
				pXTmp[i] = pXTmp[i] - meanX;
				pYTmp[i] = pYTmp[i] - meanY;
			}
			double[][] ads = new double[pXTmp.length][pXTmp.length];
			ads[0] = pXTmp;
			ads[1] = pYTmp;
			meanAdjustedData = new Data(ads, ads.length, ads.length);
			double[][] cov = new double[2][2];
			cov[0][0] = calculateCovariance(pXTmp, pXTmp);
			cov[0][1] = calculateCovariance(pXTmp, pYTmp);
			cov[1][0] = calculateCovariance(pYTmp, pXTmp);
			cov[1][1] = calculateCovariance(pYTmp, pYTmp);
			covarianceMatrix = new Data(cov, cov.length, cov.length);
			return covarianceMatrix;
		} else {
			double meanX = calculateMean(X.getMatrix().getRowPackedCopy());
			pXTmp = X.getMatrix().getRowPackedCopy();
			for(int i = 0; i < pXTmp.length; i++){
				pXTmp[i] = pXTmp[i] - meanX;
			}
			double[][] ads = new double[1][pXTmp.length];
			ads[0] = pXTmp;
			meanAdjustedData = Data.dataFromArray(pXTmp, 1, pXTmp.length);
			double[][] cov = new double[1][1];
			cov[0][0] = calculateCovariance(pXTmp, pXTmp);
			covarianceMatrix = new Data(cov, cov.length, cov.length);
			return covarianceMatrix;			
		}
	}
	
	
	public Matrix deriveFeatureVector(){
		calculateCovarianceMatrix();
		eigenVectors = covarianceMatrix.getMatrix().eig().getV().transpose();
//		Data d = new Data(eigenVectors);
//		d.print();
		featureVector = eigenVectors.times(meanAdjustedData.getMatrix());
		//AdjustedDataSet.print();
		
//		System.out.println(eigenVectors.getRowDimension() + "," + eigenVectors.getColumnDimension());
//		System.out.println(AdjustedDataSet.getMatrix().getRowDimension() + "," + AdjustedDataSet.getMatrix().getColumnDimension());
//		
//		for(int i = 0; i < featureVector.getRowDimension(); i++){
//			for(int j = 0; j < featureVector.getColumnDimension(); j++){
//				System.out.print(featureVector.get(i, j) + ",");
//			}
//			System.out.println();
//		}
		return featureVector;
	}
}
