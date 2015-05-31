package circuitInformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TrojanDetector {

	public static boolean detect(Circuit healthyCircuit, Circuit trojanCircuit, ArrayList<int[]> testVectors) {
		try {
			ArrayList<int[]> healthyOutput = healthyCircuit.run(testVectors, testVectors.size());
			ArrayList<int[]> trojanOutput = trojanCircuit.run(testVectors, testVectors.size());
			
			for (int i = 0; i < healthyOutput.size(); i++) {
				if(!Arrays.equals(healthyOutput.get(i), trojanOutput.get(i)))
					return false;
			}
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {

		long startTime   = System.currentTimeMillis();

		
			Circuit cir1 = TextHelper.buildCircuit(new File("c1908a.v"));
			Circuit cir2 = TextHelper.buildCircuit(new File("c1908a_trojan.v"));
//			ArrayList<int[]> testVectors = Algorithms.generate(cir1.getInputWireList().size());
			
			ArrayList<int[]> randomVectors=Algorithms.generate(cir1.getInputWireList().size());
			ArrayList<Integer> rareIndiceList=new ArrayList<Integer>();
			ArrayList<Integer> rareValueList=new ArrayList<Integer>();
			ArrayList<Wire> wireList=cir1.getWireList();

			int size=wireList.size();
			boolean[] selected = new boolean[size];
			Arrays.fill(selected, false);
			Random rand = new Random();
			System.out.println("Finding rare nodes...");
			System.out.println("Rare nodes found: ");
			int[] rareIndices = new int[rareIndiceList.size()];
			int[] rareValues = new int[rareValueList.size()];
			
			for (int i = 0; i < cir1.getWireList().size()/3; i++) {
				int j=rand.nextInt(size);
				if(!selected[j]){
					selected[j]=true;
					rareIndiceList.add(j);
					int rareValue = rand.nextInt(2);
					rareValueList.add(rareValue);
					System.out.println(wireList.get(j).getName() + " with associated rare value: " + rareValue);
				}
				

			}
			rareIndices = new int[rareIndiceList.size()];
			rareValues = new int[rareValueList.size()];
			for (int k = 0; k < rareIndices.length; k++) {
				rareIndices[k] = rareIndiceList.get(k);
				rareValues[k] = rareValueList.get(k);
			}
			ArrayList<int[]> reducedVectors = new ArrayList<int[]>();
			reducedVectors = Algorithms.mero(cir1, rareIndices, rareValues, 100, randomVectors);
			System.out.println("Random vectors reduced to size " + reducedVectors.size());
			
			boolean same = new TrojanDetector().detect(cir1, cir2, reducedVectors);
			if(same)
				System.out.println("Trojan not detected!");
			else
				System.out.println("Trojan detected!");

			
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Total time consumed: "+totalTime+"ms");
			

	}
	
	
	
	
}
