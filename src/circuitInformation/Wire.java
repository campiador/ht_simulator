package circuitInformation;


public class Wire {
	
	static int WIRE_INPUT=0;
	static int WIRE_OUTPUT=1;
	static int WIRE_INTERNAL=2;
	private int currentBit= -1;
	private int nextBit = -1;
	private int type=-1;
	private String name="";
	private int satisfier=-1;
	private boolean satisfied;

	
	public int getSatisfier() {
		return satisfier;
	}
	public void setSatisfier(int satisfier) {
		this.satisfier = satisfier;
	}
	public boolean isSatisfied() {
		return satisfied;
	}
	public void setSatisfied(boolean satisfied) {
		this.satisfied = satisfied;
	}
	
	public Wire(int type) {
		this.type=type;
	}
	public Wire(String name, int type) {
		this.type=type;
		this.name=name;
	}
	
//	public Wire() {
//	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public int getCurrentBit() {
		return currentBit;
	}
//	public boolean getCurrentBitAsBoolean() {
//		return currentBit==1;
//	}

	public void setCurrentBit(int currentBit) {
			this.currentBit= currentBit;
			satisfied= (currentBit==satisfier);
			
	}
	public void setCurrentBit(boolean currentBit) {
		if(currentBit==true)
			this.currentBit= 1;
		else
			this.currentBit= 0;
}
	public void setNextBit(boolean nextBit) {
		if(nextBit==true)
			this.nextBit = 1;
		else
			this.nextBit=0;
	}
	
	public int getNextBit() {
		return nextBit;
	}
	public void setNextBit(int nextBit) {
		this.nextBit = nextBit;
	}
	public boolean getNextBitAsBoolean() {
		return nextBit==1;
	}
	

}
