package circuitInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class TrojanInserter {
	


	
	public static String insertTrojan(String filePath, int numberOfTriggers) {
		String keyWords = "module|input|output|wire|endmodule";
		Scanner sc=null;
		try {
			sc=new Scanner(new File(filePath));
		} catch (Exception e) {e.printStackTrace();}
		String description = "";
		while (sc.hasNextLine()){
			String line = sc.nextLine();
			if (line.trim().startsWith("//") || line.matches("\\s*"))
				continue;
			description= description + line + "\n";
		}
		description = description.trim();
		String[] splittedDes = description.split(keyWords);
		String[] internals = splittedDes[4].split(";", 2)[0].split("\\s*,\\s*");
		if(numberOfTriggers==0){
			System.out.println(description);
			return null;
		}
		if(numberOfTriggers>internals.length)
			numberOfTriggers=Math.min(numberOfTriggers-1, 32);
		internals[0] = internals[0].trim();
		int[] triggerIndex =  getRandomIntegerList(numberOfTriggers,internals);
		String victimWire = internals[triggerIndex[0]];
		System.out.println(victimWire + " was selected as the victim wire");
		String triggerWire = "N_TRIGGER";
		String payLoadWire = "N_P";
//		wires whose bits are to be and-ed and set as N_TRIGGER bit:
		String[] tempWires = new String[numberOfTriggers];
		for (int i = 1; i < numberOfTriggers+1; i++) {
			tempWires[i-1] = internals[triggerIndex[i]];
		}
		
		System.out.println(Arrays.toString(tempWires) + " were selected as lead-to-trigger wires");
		
		
		String tempWireList="";
		for (int i = 0; i < tempWires.length-1; i++) {
			tempWireList+=tempWires[i] + ", ";
		}
		tempWireList+=tempWires[tempWires.length-1];
		String XORgate = "and N_T_XOR (N_TRIGGER, "+tempWireList + ");";
		String trojanGate = "xor ("+ payLoadWire + ", " + triggerWire + "," + victimWire +");";
		String internalList = splittedDes[4].split("\\s*;\\s*", 2)[0];
		String gateList = splittedDes[4].split("\\s*;\\s*",2)[1];
		internalList = internalList + ",\n" + payLoadWire + ", " + triggerWire +";" + " \n";
		gateList = gateList.replaceAll(",\\s*" + victimWire + "\\s*,", ", " + payLoadWire + ",");
		gateList = gateList.replaceAll(",\\s*" + victimWire + "\\s*\\)", ", " + payLoadWire + ")");
		gateList = gateList + "\n" + XORgate +"\n"+ trojanGate +  "	\n";

		description = "module".concat(splittedDes[1]).concat("input").concat(splittedDes[2]).concat("output").concat(splittedDes[3]).concat("wire").concat(internalList + gateList).concat("\nendmodule");
		System.out.println(description);
		String path="";
		try {
			path=filePath.replace(".v", "_trojan.v");
			PrintWriter pw = new PrintWriter(path);
			System.out.println("New File Created!");
			pw.println(description);
			pw.close();
			return path;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	private static int[] getRandomIntegerList(int numberOfTriggers,
			String[] internals) {
		Set<Integer> triggerIndex = new LinkedHashSet<Integer>();
		Random rand = new Random();
		while(triggerIndex.size()<numberOfTriggers+1){
			triggerIndex.add(rand.nextInt(internals.length));
		}
		int i=0;
		int[] res = new int[triggerIndex.size()];
		for (Integer integer : triggerIndex) {
			res[i++] = integer;
		}
		return res;
	}


}
