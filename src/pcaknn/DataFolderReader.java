package pcaknn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import utils.ImageUtils;


public class DataFolderReader implements Callable<ArrayList<Data>>{
	
	private ArrayList<Data> data;
	private List<File> files;
	private Data input;
	private int width = 24, height = 24;
	
	public DataFolderReader(String directory){
		files = readFolder(new File(directory));
		data = new ArrayList<Data>();
	}
	
	
	public ArrayList<Data> read()  {
		System.out.println("Creating dataset..");
		for(File file : files){
			try {
				//System.out.println("Processing: " + file.getPath());
				if(file.isDirectory()){
					data.addAll(createData(readFolder(file)));
				} else {
					data.add(readImage(file));
				}				
			} catch(IOException e){
				System.out.println(e);
			}
		}
		return data;
	}
	
	public void setScale(int w, int h){
		width = w;
		height = h;
	}
	
	
	private Data readImage(File file) throws IOException {
		//input = ImageUtils.toData(ImageUtils.scaleGreyImage(ImageUtils.greyScale(ImageUtils.getBufferedImage(file)), 24, 24));
		input.setLabel(file.getPath());
		return input;	
	}
	
	
	private ArrayList<Data> createData(List<File> files) throws IOException {
		ArrayList<Data> tmp = new ArrayList<Data>();
		for(File file : files){
			tmp.add(readImage(file));
		}	
		return tmp;
	}
	
	public List<File> readFolder(File file){
		List<File> files = Arrays.asList(file.listFiles());
		List<File> tmp = new ArrayList<File>();
		for(File f : files){
			if(f.isDirectory()){
				tmp.addAll(readFolder(f));
			} else tmp.add(f);
		}
		files = tmp;
		return files;
	}
	
	
	public void print(){
		for(File file : files){
			System.out.println(file);
		}		
	}


	@Override
	public ArrayList<Data> call() throws Exception {
		// TODO Auto-generated method stub
		return read();
	}

}
