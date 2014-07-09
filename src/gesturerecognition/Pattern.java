package gesturerecognition;

public class Pattern extends nn.Pattern{

	public Pattern(double[] input, double[] output) {
		super(input, output);
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		String tmp = "";
		for(int i = 0; i < super.input.length; i++) tmp += super.input[i] + ",";
		return tmp;
	}

}
