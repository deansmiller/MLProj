package gesturerecognition;

public class DropLine {
	
	public Double y; // length of line
	public Double x; // x axis position of line
	
	public DropLine(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "(" + x + ":" + y + ")";
	}
	
}