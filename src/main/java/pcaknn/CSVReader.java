package pcaknn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CSVReader {

	private String[][] data;
	private int columns, rows;
	protected BufferedReader bReader;
	
	public CSVReader(String filename, int rows, int columns){
		this.columns = columns;
		this.rows = rows;
		data = new String[rows][columns];
		setDataFile(filename);
	}
	
	
	public void setDataFile(String filename){
		try {
			bReader = new BufferedReader(new FileReader(filename));
		} catch(FileNotFoundException e){
			throw new Error(e);
		}
	}
	
	
	public Data read() throws IOException{
		String currentLine;
		int index = 0;
		while((currentLine = bReader.readLine()) != null){
			data[index] = currentLine.split(",");
			index++;
		}
		
		bReader.close();
		return new Data(data, rows, columns);

	}

	
	
	
	
}
