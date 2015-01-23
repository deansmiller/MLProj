package pcaknn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote {

	private List<Result> results;
	private String[] labels;
//	public String
	
	public Vote(List<Result> results, String[] labels){
		this.results = results;
		this.labels = labels;

	}
	
//	public void count(){
//		int aCount = 0;
//		int bCount = 0;
//		Map<String, Integer> voteMap = new HashMap<String, Integer>();
//		String label = "";
//		for(Result result : results){
//			label = result.getLabel().substring(0, result.getLabel().lastIndexOf("/"));
//			if(label.contains(sampleLabelA)) voteMap.put(label, aCount++);
//			else if(label.contains(sampleLabelB)) voteMap.put(label, bCount++);
//		}
//		int max = Math.max(aCount, bCount);
//		System.out.println(voteMap);		
//	}
}
