package pcaknn;

public class Result implements Comparable {
	
	private Data data;
	private double distance;
	
	public Result(Data data, double distance){
		this.data = data;
		this.distance = distance;
	}
	
	public String toString(){
		return data.getLabel() + ": " + distance;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public String getLabel(){
		return data.getLabel();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (Double.doubleToLongBits(distance) != Double
				.doubleToLongBits(other.distance))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		Result r = (Result) o;
		if(distance > r.getDistance()){
			return 1;
		} else if(distance < r.getDistance()){
			return -1;
		}
		return 0;
	}
	
}