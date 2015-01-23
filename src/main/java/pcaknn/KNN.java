package pcaknn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNN {
	
	private int k, threshold;
	private ArrayList<Data> dictionary;
	private DataFolderReader folderReader;
	private PCA pca;
	public boolean running = false, muted = false;
	private String dirA, dirB;
	
	
	public KNN(DataFolderReader reader, int k, int threshold) throws IOException {
		folderReader = reader;
//		ExecutorService executor = Executors.newFixedThreadPool(10);
//		Future<ArrayList<Data>> futureList = executor.submit(folderReader);
		dictionary = folderReader.read();
		this.k = k;
		this.threshold = threshold;
	}
	
	public void setVoteParams(String dirA, String dirB){
		this.dirA = dirA;
		this.dirB = dirB;
	}
	
	
	private ArrayList<Data> processDataSet(){
		ArrayList<Data> processedData = new ArrayList<Data>();
		Data tmp;
		for(Data data : dictionary){
			pca = new PCA(data);
			tmp = new Data(pca.deriveFeatureVector());
			tmp.setLabel(data.getLabel());
			processedData.add(tmp);
		}
		return processedData;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Result> calculateNearest(Data input) throws Exception{
		//System.out.println("Calculating nearest neighbours...");
		running = true;
		ArrayList<Data> dataSet = processDataSet();
		pca = new PCA(input);
		float sum;
		List<Result> results = new ArrayList<Result>();
		
		double[] tmpInput = pca.deriveFeatureVector().getRowPackedCopy();
		double[] tmpData;
		for(Data data : dataSet){
			sum = 0;
			tmpData = data.getMatrix().getRowPackedCopy();
			for(int i = 0; i < tmpInput.length; i++){
				sum += Math.pow((tmpInput[0] - tmpData[0]), 2);
			}
			results.add(new Result(data, Math.sqrt(sum)));
		}
		
		if(results.size() > 0){
			Collections.sort(results);
			results = results.subList(1, k + 1);
			
			if(dirA != null){
				int aCount = 0;
				int bCount = 0;
				Map<String, Integer> voteMap = new HashMap<String, Integer>();
				String label = "";
				for(Result result : results){
					label = result.getLabel().substring(0, result.getLabel().lastIndexOf("/"));
					if(label.contains(dirA)) {
						aCount++;
						voteMap.put(label, aCount);
					}
					else if(label.contains(dirB)){
						bCount++;
						voteMap.put(label, bCount);
					}
				}
				int max = Math.max(aCount, bCount);
				
				if(max >= threshold){
					//System.out.println(voteMap + " " +  max);
					for(String key : voteMap.keySet()){
						if(voteMap.get(key) == max) {
							//System.out.println(key + " --> " + max);
							if(key.contains("Stop Hand")){
								final Runtime rt = Runtime.getRuntime();
								if(muted){
									rt.exec("amixer sset Master 25");
									muted = false;
									System.out.println("Sound on");
								} else {
									rt.exec("amixer sset Master 0");
									muted = true;
									System.out.println("Sound off");
								}
							}
						}
					}
				}
			}
			
//			for(Result result : results){
//				System.out.println(result);
//			}
		} else System.out.println("No results!");
		running = false;
		return results;
	}
	
	
//	public Result getResult(List<Result> result){
//		for(Result res : results){
//			System.out.println(result);
//		}		
//	}
	
}
