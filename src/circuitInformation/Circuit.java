package circuitInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class Circuit {

	private String name;
	private ArrayList<Wire> wireList = new ArrayList<Wire>();
	private ArrayList<Wire> inputWireList = new ArrayList<Wire>();
	private ArrayList<Wire> outputWireList = new ArrayList<Wire>();
	private ArrayList<Wire> internalWireList = new ArrayList<Wire>();
	private ArrayList<Gate> gateList = new ArrayList<Gate>();
	private ArrayList<Wire> rareWires;

	public Circuit(String name, ArrayList<Wire> inputWireList,
			ArrayList<Wire> outputWireList, ArrayList<Wire> internalWireList,
			ArrayList<Gate> gateList) {
		this.name = name;
		this.inputWireList = inputWireList;
		this.outputWireList = outputWireList;
		this.internalWireList = internalWireList;
		this.wireList.addAll(inputWireList);
		this.wireList.addAll(internalWireList);
		this.wireList.addAll(outputWireList);
		this.gateList = gateList;
	}

	public Circuit(String name) {
		this.name = name;
	}

	public Circuit(String name, Wire[] inputWires, Wire[] outputWires,
			Wire[] internalWires, Gate[] gates) {

		this.name = name;
		this.outputWireList = new ArrayList<Wire>(Arrays.asList(outputWires));
		this.inputWireList = new ArrayList<Wire>(Arrays.asList(inputWires));
		this.internalWireList = new ArrayList<Wire>(
				Arrays.asList(internalWires));
		this.gateList = new ArrayList<Gate>(Arrays.asList(gates));
		for (Wire wire : inputWireList)
			wireList.add(wire);
		for (Wire wire : internalWireList)
			wireList.add(wire);
		for (Wire wire : outputWireList)
			wireList.add(wire);
		

	}

	
	public void addWire(Wire wire, int wireType) {
		if (wireType == Wire.WIRE_INPUT) {
			inputWireList.add(wire);
		} else if (wireType == Wire.WIRE_OUTPUT) {
			outputWireList.add(wire);
		} else if (wireType == Wire.WIRE_INTERNAL) {
			internalWireList.add(wire);
		}
	}

	public ArrayList<int[]> run(ArrayList<int[]> inputs, int clocks) {
//		String MessageLog="";
		
		ArrayList<int[]> outputListOfVectors = new ArrayList<int[]>();

		
		int clock=0;
		ListIterator<int[]> inputIterator = inputs.listIterator();
		int[] inputVector=inputIterator.next();
//		System.out.println("clock " + clock + ": ------------------------------------");
		for (int i=0; i<inputVector.length; i++) {
			wireList.get(i).setCurrentBit(inputVector[i]);
//			System.out.println(wireList.get(i).getName() + "->" + wireList.get(i).getCurrentBit());
		}
//		for (int i = inputVector.length; i < wireList.size(); i++) {
//			System.out.println(wireList.get(i).getName() + "->" + wireList.get(i).getCurrentBit());
//
//		}
		
			for (clock=1; inputIterator.hasNext()&& clock<clocks; clock++) {
				inputVector = inputIterator.next();
				for (int i = 0; i < inputVector.length; i++) {
					wireList.get(i).setNextBit(inputVector[i]);							
				}
				for (Gate gate : gateList) {
					gate.execute(this);
				}
//				System.out.println("clock " + clock + ": ------------------------------------");
//				MessageLog +="clock " + clock + "-------------------------------------\n";
				for (Wire wire : wireList) {
					wire.setCurrentBit(wire.getNextBit());
//					System.out.println( wire.getName() + " -> " + wire.getCurrentBit());
//					MessageLog += "the Value of " + wire.getName() + " was changed to " + wire.getCurrentBit();
				}
				int[] outputVector = new int[outputWireList.size()];
				int constant = wireList.size() - outputVector.length;
				for (int i = 0; i < outputVector.length; i++) {
					outputVector[i] = wireList.get(i+constant).getCurrentBit();
				}
				outputListOfVectors.add(outputVector);
			}
			return outputListOfVectors;
		}

	public ArrayList<Integer> testVector(ArrayList<int[]> inputs, int[] rareIndices) {
		
		for (Wire wire : wireList) {
			wire.setCurrentBit(-1);
			wire.setNextBit(-1);
		}
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		ListIterator<int[]> inputIterator = inputs.listIterator();
		int[] inputVector = inputIterator.next();
		for (int i = 0; i < inputVector.length; i++) {
			wireList.get(i).setCurrentBit(inputVector[i]);
		}

		for (int clock = 1; inputIterator.hasNext(); clock++) {
			inputVector = inputIterator.next();
			for (int i = 0; i < inputVector.length; i++) {
				wireList.get(i).setNextBit(inputVector[i]);
			}
			for (Gate gate : gateList) {
				gate.execute(this);
			}
			
			
			/*Here comes the tricky part*/
			
			for(int i=0; i<rareIndices.length; i++){
				if(wireList.get(rareIndices[i]).isSatisfied()){
					res.add(rareIndices[i]);
				}
			}
			
			for (Wire wire : wireList) {
				wire.setCurrentBit(wire.getNextBit());
			}
		}
		return res;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Wire> getWireList() {
		return wireList;
	}

	public ArrayList<Wire> getInputWireList() {
		return inputWireList;
	}

	public ArrayList<Wire> getOutputWireList() {
		return outputWireList;
	}

	public ArrayList<Gate> getGateList() {
		return gateList;
	}

	public ArrayList<Wire> getInternalWire() {
		return internalWireList;
	}

	public ArrayList<Wire> getRareWires() {
		return rareWires;
	}

	public void setRareWires(ArrayList<Wire> rareWires) {
		this.rareWires = rareWires;
	}

}
