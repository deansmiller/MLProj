package neuralnetwork;

import java.io.IOException;

import pcaknn.ImageReader;

//Transforms images into comma sep value strings for input to NN
public class SampleReader {
	
	private ImageReader reader;
	private String directory;
	
	
	public SampleReader(String directory, ImageReader imageReader) throws IOException{
		reader = imageReader;
		this.directory = directory;
		reader.setPath(directory);
	}
	
	

}
