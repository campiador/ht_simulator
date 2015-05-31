package circuitInformation;

import java.util.ArrayList;
import java.util.Random;

import dataStructures.Heap;


public class Algorithms {
	
	
	
	public static ArrayList<int[]> mero(Circuit circuit, int[] rareIndices, int[] rareValues, int minLimit, ArrayList<int[]> randomVectors){
//		PriorityQueue<CircuitVector>  e = new PriorityQueue<CircuitVector>();
		ArrayList<int[]> reducedVectors = new ArrayList<int[]>();
		int[] numberOfExcitements = new int[circuit.getWireList().size()];
//		Arrays.fill(numberOfExcitements, 0);
		boolean finished = true;
		
		res.add(new int[10]);
		
		for (int i = 0; i < rareIndices.length; i++) {
			circuit.getWireList().get(rareIndices[i]).setSatisfier(rareValues[i]);
		}
		
		
		for (int i = 0; i < rareIndices.length; i++) {
			numberOfExcitements[rareIndices[i]]=0;
		}
		
		Heap<CircuitVector> vectorHeap = new Heap<CircuitVector>(true, 1);
		
		
		for (int i = 0; i < randomVectors.size(); i++) {
			CircuitVector vector = new CircuitVector(randomVectors.get(i));
			vector.setSatisfiedIndices(circuit.testVector(generateTest(vector.getVectorValues(), 100),rareIndices));
			vectorHeap.insert(vector);
//			System.out.println(i);
//			e.add(vector);
		}
		System.out.println("Vectors sorted by their C_R. Computing the reduced vector list...");

		
		while (!vectorHeap.isEmpty()) {
//			CircuitVector vector = e.poll();
			CircuitVector vector = vectorHeap.deleteMP();
			CircuitVector tmpVector = vector;
			
			
			for (int i = 0; i < vector.size(); i++) {
				tmpVector.getVectorValues()[i] = 1 - tmpVector.getVectorValues()[i];
				ArrayList<int[]> test =generateTest(tmpVector.getVectorValues(), 50);		
				tmpVector.setSatisfiedIndices(circuit.testVector(test ,	rareIndices));
				if (tmpVector.getNumberOfSatisfiedRares() > vector.getNumberOfSatisfiedRares()){
					vector = tmpVector;
				}else{
					tmpVector.getVectorValues()[i]=1-tmpVector.getVectorValues()[i];
				}
			}
			

			if (vector.getNumberOfSatisfiedRares() > 0) {
				
				reducedVectors.add(vector.getVectorValues());
				
				
				for (int i = 0; i < vector.getNumberOfSatisfiedRares(); i++) {
					numberOfExcitements[vector.getSatisfiedIndices().get(i)]++;
					if (numberOfExcitements[vector.getSatisfiedIndices().get(i)] < minLimit){
						finished=false;
						break;
					}
				}
				if(finished)
					return reducedVectors;
				else
					finished=true;
			}

		}
		return null;
	}
	
	


	static ArrayList<int[]> res = new ArrayList<int[]>();
	static int[] tmp;

	private static void makeTestVectorReady(int size) {
		tmp = new int[size];
		for (int j = 0; j < size; j++) {
			tmp[j]=-1;
		}
		for (int i = 0; i < 30; i++) {
			res.add(tmp);
		}
	}
	
	private static ArrayList<int[]> generateTest(int[] vector, int size) {
		res = new ArrayList<int[]>();
		int n = vector.length;
		res.add(vector);
		makeTestVectorReady(n);
		return res;
	}
	
	public static ArrayList<int[]> generate(int n) {
		Random rand = new Random();
		ArrayList<int[]> res = new ArrayList<int[]>();
		for(int i=0; i<10000; i++){
			int[] randomVector = new int[n];
			for(int j=0; j<n; j++){
				randomVector[j]=rand.nextInt(2);
			}
			res.add(randomVector);
		}
		return res;
	}
}
