package circuitInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextHelper {
	
	static String[] wiresList;
	static String[] gatesList;
	public static Wire[] wires;
	
	
	static String keyWords = "module|input|output|wire|endmodule";

	public static Circuit buildCircuit(File inputFile) {

		Scanner sc = null;

		try {
			sc = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
		}

		String description = "";

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.trim().startsWith("//") || line.matches("\\s*"))
				continue;
			description = description + line + "\n";
		}

		description = description.trim();
		String[] splittedDes = description.split(keyWords);
		Wire[] inOutWires = splitWireList(splittedDes[1].substring(
				splittedDes[1].indexOf("(") + 1, splittedDes[1].indexOf(")")),
				-1);
		Wire[] inputWires = splitWireList(splittedDes[2],
				Wire.WIRE_INPUT);
		
		

		for (Wire wire : inputWires) {
			wire.setCurrentBit(1);
		}

		Wire[] outputWires = splitWireList(splittedDes[3],
				Wire.WIRE_OUTPUT);
		Wire[] internalWires = splitWireList(
				splittedDes[4].split(";")[0], Wire.WIRE_INTERNAL);
		setTextHelperReady(inOutWires, internalWires);
		Gate[] gates = splitGateList(splittedDes[4].split(";", 2)[1]);
		Circuit circuit = new Circuit("Healthy Circuit", inputWires,
				outputWires, internalWires, gates);
		sc.close();
		return circuit;
	}
	
	
	
	private static void setTextHelperReady(Wire[] inOutWires,
			Wire[] internalWires) {
		wires = new Wire[inOutWires.length + internalWires.length];

		for (int i = 0; i < inOutWires.length; i++) {
			wires[i] = inOutWires[i];
		}

		for (int i = 0; i < internalWires.length; i++) {
			wires[i + inOutWires.length] = internalWires[i];
		}
	}


	public static Wire[] splitWireList(String string, int type) {
//		System.out.println(string);
		string = string.split("\\s*;\\s*")[0].trim();
		String[] elements = string.split("\\s*,\\s*");
		Wire[] wires = new Wire[elements.length];
		for (int i = 0; i < elements.length; i++) {
			wires[i] = new Wire(elements[i].trim(),type);
		}
		return wires;
	}
	public static Gate[] splitGateList(String string) {
		string = string.trim();
		String[] gateList = string.split("\\s*;\\s*");
		Gate[] gates = new Gate[gateList.length];
		for (int i = 0; i < gateList.length; i++) {
			String line = gateList[i].trim();
			String[] splittedLine = line.split("\\s+|\\s*\\(\\s*", 3);
			String operator = splittedLine[0].trim();
			String name = splittedLine[1].trim();
			String[] params = splittedLine[2].trim().substring(splittedLine[2].indexOf("(")+1, splittedLine[2].indexOf(")")).split("\\s*,\\s*");
			gates[i] = new Gate(getTypeByID(operator), name, getWireByID(params[0]), getInputWiresByID(params));
		}
		return gates;
	}

	private static int getTypeByID(String operator) {
		operator=operator.trim();
		if(operator.equals("and"))
			return Gate.GATE_AND;
		if(operator.equals("or"))
			return Gate.GATE_OR;
		if(operator.equals("nand"))
			return Gate.GATE_NAND;
		if(operator.equals("nor"))
			return Gate.GATE_NOR;
		if(operator.equals("xor"))
			return Gate.GATE_XOR;
		if(operator.equals("not"))
			return Gate.GATE_NOT;
		return -1;
		
	}
	private static int getWireByID(String wireName) {
		wireName = wireName.trim();
		for (int i=0; i<wires.length; i++) {
			if (wires[i].getName().equals(wireName)) {
				return i;
			}
		}
		return -1;
	}


	private static int[] getInputWiresByID(String[] params) {
		int[] res = new int[params.length-1];
		for(int i=1 ; i<params.length ; i++)
			res[i-1] = getWireByID(params[i]);
		return res;
	}
	
}
