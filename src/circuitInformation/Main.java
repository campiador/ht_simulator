package circuitInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Random;





public class Main {
	
	
	static ArrayList<Integer> rareIndiceList=new ArrayList<Integer>();
	static ArrayList<Integer> rareValueList=new ArrayList<Integer>();

	
	public static void main(String[] args) {
		
		
		long startTime   = System.currentTimeMillis();

		Circuit circuit = TextHelper.buildCircuit(new File("c432.v"));
		findRareNodeIndices(circuit);
		ArrayList<Wire> wireList=circuit.getWireList();
//		ArrayList<Integer> rareIndiceList=new ArrayList<Integer>();
//		ArrayList<Integer> rareValueList=new ArrayList<Integer>();
		ArrayList<int[]> randomVectors=Algorithms.generate(circuit.getInputWireList().size());
		int size=wireList.size();
		boolean[] selected = new boolean[size];
		Arrays.fill(selected, false);
		Random rand = new Random();
//		System.out.println("finding rare nodes...");
//		System.out.println("rare nodes found: ");
		int[] rareIndices = new int[rareIndiceList.size()];
		int[] rareValues = new int[rareValueList.size()];
		
//		for (int i = 0; i < circuit.getWireList().size()/3; i++) {
//			int j=rand.nextInt(size);
//			if(!selected[j]){
//				selected[j]=true;
//				rareIndiceList.add(j);
//				int rareValue = rand.nextInt(2);
//				rareValueList.add(rareValue);
////				System.out.println(wireList.get(j).getName() + " with associated value: " + rareValue);
//			}
			

//		}
		
		rareIndices = new int[rareIndiceList.size()];
		rareValues = new int[rareValueList.size()];
		for (int k = 0; k < rareIndices.length; k++) {
			rareIndices[k] = rareIndiceList.get(k);
			rareValues[k] = rareValueList.get(k);
		}
		ArrayList<int[]> reducedVectors = new ArrayList<int[]>();
		reducedVectors = Algorithms.mero(circuit, rareIndices, rareValues, 50, randomVectors);
		System.out.println("Reduced vector size to " + reducedVectors.size());
		
		Circuit trojanCircuit = TextHelper.buildCircuit(new File("c432_trojan.v"));
		
		boolean same = TrojanDetector.detect(circuit, trojanCircuit, reducedVectors);
		
		System.out.println(same ? "Trojan not detected." : "Trojan detected.");
		
		
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time consumed: "+totalTime + "ms");
		
		
	}
	
	public static void findRareNodeIndices(Circuit circuit){
		java.util.Scanner input=null;
		try {
			input = new java.util.Scanner(new File("c432_saif.saif"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int linecount= 0;
		ArrayList<RareNode> itemList = new ArrayList<RareNode>();

		RareNode tempNet = null;
		int shouldbreak = 0;
		while (input.hasNextLine()) {
			linecount++;
			String line;
			
			line = input.nextLine();
			line = line.trim();
			
			if (linecount >= 15) {
				switch ((linecount - 15) % 4) {
				case 0:
					if ( line.equals(")") ) {
						shouldbreak = 2;
						break;
					}
					tempNet = new RareNode(line.replace("(", ""));
					break;
				case 1:
					
			        String[] ara = line.split("\\s+");
			        
			        String zero;
			        String one;
			        
			        for (int i = 0; i < ara.length; i++) {
			        	ara[i] = ara[i].replace("(", "");
			        	ara[i] = ara[i].replace(")", "");
					}
			        
			        zero = ara[1];
			        one = ara[3];
			        
			        tempNet.setZeroTime(zero);
					tempNet.setOneTime(one);
					
					break;
					
				case 2:
					String[] arb = line.split("\\s+");
					String transit;
					for (int i = 0; i < arb.length; i++) {
						arb[i] = arb[i].replace("(", "");
						arb[i] = arb[i].replace(")", "");
					}
					
					transit = arb[1];
					tempNet.setTransitionCount(transit);
					
					break;
				case 3:
					if (!tempNet.getTransitionCount().equals("0")) {
						itemList.add(tempNet);
					}
					break;

				default:
					break;
				}
				
			}
			
			if (shouldbreak == 2) {
				break;
			}
			
		}
		
		input.close();
		
		RareNode rareNode = new RareNode("rare");
		rareNode.setmItems(itemList);
		rareNode.sortByZeroTimeAsc();
		ArrayList<RareNode> rareZeros = rareNode.getmItems();
		rareNode.sortByOneTimeAsc();
		ArrayList<RareNode> rareOnes = rareNode.getmItems();
		ArrayList<RareNode> rares = new ArrayList<RareNode>();
//		for (int i = 0; i < rareZeros.size()/6; i++) {
//			rares.add(rareZeros.get(i));
//			rares.add(rareOnes.get(i));
//		}
		
//		ArrayList<Integer> rareIndices = new ArrayList<Integer>();
//		ArrayList<Integer> rareValues = new ArrayList<Integer>();
		
		ArrayList<Wire> wireList = circuit.getWireList();

		for (int i = 0; i < rareZeros.size()/6; i++) {
//			rares.add(rareZeros.get(i));
			for (int j = 0; j < wireList.size(); j++) {
				if (rareZeros.get(i).getName().equals(wireList.get(j).getName())) {
					rareIndiceList.add(j);
					rareValueList.add(0);
				}
			}
		}
		for (int i = 0; i < rareOnes.size()/6; i++) {
				for (int j = 0; j < wireList.size(); j++) {
					if (rareOnes.get(i).getName().equals(wireList.get(j).getName())) {
					rareIndiceList.add(j);
					rareValueList.add(1);
				}
			}
		}			
		
		
		
		
	}
}


