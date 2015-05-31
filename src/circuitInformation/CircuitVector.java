package circuitInformation;

import java.util.ArrayList;

public class CircuitVector implements Comparable<CircuitVector>{
	

	private int[] vectorValues;
	private int numberOfSatisfiedRares;
	private ArrayList<Integer> satisfiedIndices;
	





	public CircuitVector(int[] vectorValues) {
		super();
		this.vectorValues = vectorValues;
	}

	 
	
	public ArrayList<Integer> getSatisfiedIndices() {
		return satisfiedIndices;
	}
	
	
	
	public void setSatisfiedIndices(ArrayList<Integer> satisfiedIndices) {
		this.satisfiedIndices = satisfiedIndices;
		setNumberOfSatisfiedRares(this.satisfiedIndices.size());
	}
	
	public int getNumberOfSatisfiedRares() {
		return numberOfSatisfiedRares;
	}

	
	public void setNumberOfSatisfiedRares(int numberOfSatisfiedRares) {
		this.numberOfSatisfiedRares = numberOfSatisfiedRares;
	}

	
	public int[] getVectorValues() {
		return vectorValues;
	}


	public int size(){
		return vectorValues.length;
	}

	public void setVectorValues(int[] vectorValues) {
		this.vectorValues = vectorValues;
	}




	@Override
	public int compareTo(CircuitVector o) {
		if(this.numberOfSatisfiedRares>o.numberOfSatisfiedRares)
			return 1;
		if(this.numberOfSatisfiedRares<o.numberOfSatisfiedRares)
			return -1;
		return 0;
	}

}
