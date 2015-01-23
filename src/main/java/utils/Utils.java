package utils;

public class Utils {

	
	public static void forLoop(Operation op, int count){
		for(int i = 0; i < count; i++){
			op.execute(i);
		}
	}
}
