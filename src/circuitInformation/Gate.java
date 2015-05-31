package circuitInformation;

public class Gate {
	static int GATE_AND = 0;
	static int GATE_OR = 1;
	static int GATE_NAND = 2;
	static int GATE_NOR = 3;
	static int GATE_XOR = 4;
	static int GATE_NOT = 5;

	private int type = -1;
	private String name="";
	private int[] inputIndex;
	private int outputIndex;
	public Gate(int type, String name, int outputIndex, int... inputIndex) {
		super();
		this.type = type;
		this.name = name;
		this.inputIndex = inputIndex;
		this.outputIndex = outputIndex;
	}


	public Gate(int type, String name) {
		super();
		this.type = type;
		this.name = name;
	}


	public Gate(int type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public int[] getInputWires() {
		return inputIndex;
	}

	public void setInputWires(int[] inputWires) {
		this.inputIndex = inputWires;
	}


	public int getOutputWire() {
		return outputIndex;
	}


	public void setOutputWire(int outputWire) {
		this.outputIndex = outputWire;
	}
	
	public void execute(Circuit circuit) {
		int[] inputVector = new int[inputIndex.length];
		int i=0;
		for(int index: inputIndex){
			if(index==-1)
			inputVector[i]=circuit.getWireList()
					.get(index).getCurrentBit();
			i++;
		}
		circuit.getWireList().get(outputIndex).setNextBit(execute(inputVector));
	}
		
		
	private int execute(int[] inputVector){

		boolean nonDeterminism=false;

		switch (type) {
		case 0: // AND
			for (int i = 0; i < inputVector.length; i++) {
				if(inputVector[i]==0){
					return 0;
				}
				if(inputVector[i]==-1){
//					System.out.println(inputVector[i]);
					nonDeterminism=true;
				}
			}
			if(nonDeterminism)
				return -1;
			return 1;


		case 1: // OR
			for (int i = 0; i < inputVector.length; i++) {
				if(inputVector[i]==1){
					return 1;
				}
				if(inputVector[i]==-1)
					nonDeterminism=true;
			}
			if(nonDeterminism)
				return -1;
			return 0;


		case 2: // NAND
			for (int i = 0; i < inputVector.length; i++) {
				if(inputVector[i]==1){
					return 0;
				}
				if(inputVector[i]==-1)
					nonDeterminism=true;
			}
			if(nonDeterminism)
				return -1;
			return 1;

		case 3: // NOR
			for (int i = 0; i < inputVector.length; i++) {
				if(inputVector[i]==1){
					return 0;
				}
				if(inputVector[i]==-1)
					nonDeterminism=true;
			}
			if(nonDeterminism)
				return -1;
			return 1;


		case 4: // XOR
			boolean numberOfOnesIsOdd = false;
			for (int i = 0; i < inputVector.length; i++) {
				if (inputVector[i]==1) {
					numberOfOnesIsOdd = !numberOfOnesIsOdd;
				}
				if(inputVector[i]==-1)
					return -1;
			}
			if(numberOfOnesIsOdd)
				return 1;
			return 0;


		case 5: // NOT
			if(inputVector[0]==-1)
				return -1;
			return (1-inputVector[0]);

		default:
			break;
		}
		return 111;
	}

}
