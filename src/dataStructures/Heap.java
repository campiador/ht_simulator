package dataStructures;
import java.util.Arrays;

public class Heap<E extends Comparable<E>>{
//implements Iterable<E>

	private Comparable[] list;

	private boolean isMax = true;
	private int heapSize = 0;
	private int capacity = 0;

	public Heap(final boolean isMax, int capacity) {
		this.isMax = isMax;
		this.capacity = capacity;
		list = new Comparable[capacity];
	}

	public void insert(E e) {
		heapSize = heapSize + 1;
//		Comparable[] tmp = list;
		list = Arrays.copyOf(list, heapSize);
		list[heapSize - 1] = e;
		int insertedIndex = heapSize-1; // index of the inserted element
		if(isMax){
			while(insertedIndex>0 && list[insertedIndex].compareTo(list[parent(insertedIndex)])>0){
				E tmp = (E) list[insertedIndex];
				list[insertedIndex] = list[parent(insertedIndex)];
				list[parent(insertedIndex)] = tmp;
				insertedIndex=parent(insertedIndex);
			}
		}else{
			while(insertedIndex>0 && list[insertedIndex].compareTo(list[parent(insertedIndex)])<0){
				E tmp = (E) list[insertedIndex];
				list[insertedIndex] = list[parent(insertedIndex)];
				list[parent(insertedIndex)] = tmp;
				insertedIndex=parent(insertedIndex);
			}
		}
	}

	private E delete() {
		if (heapSize == 0)
			return null;
		E removedObject = (E) list[heapSize - 1];
		heapSize = heapSize - 1;
		list = Arrays.copyOf(list, heapSize);
		return removedObject;
	}

	private E delete(int i) {
		if (i >= heapSize || i < 0)
			return null;
		if (heapSize == 1)
			return delete();
		Comparable tmp = list[i];
		list[i] = list[heapSize - 1];
		list[heapSize - 1] = tmp;
		E removedObject = delete();
		heapify(i);
		return removedObject;
	}

	public E deleteMP() {
		if (heapSize == 0)
			return null;
		return delete(0);
	}

	public void delete(Comparable value) {
		for (int i = 0; i < heapSize; i++) {
			if (list[i].equals(value)) {
				delete(i);
			}
		}
	}

	public E getMP() {
		if (heapSize == 0)
			return null;
		return (E) list[0];
	}
	
	public E get(int i){
		return (E) list[i];
	}
	
	private void heapify(int i) {
		if (isMax) {
			int left = this.leftChild(i);
			int right = this.rightChild(i);
			int largest;
			if (left >= heapSize)
				return;
			if (list[left].compareTo(list[i]) > 0) {
				largest = left;
			} else {
				largest = i;
			}
			if (right < heapSize && list[right].compareTo(list[largest]) > 0) {
				largest = right;
			}
			if (largest != i) {
				E tmp = (E) list[i];
				list[i] = list[largest];
				list[largest] = tmp;
				heapify(largest);
			}
		} else {
			int left = this.leftChild(i);
			int right = this.rightChild(i);
			int smallest;
			if (right > heapSize)
				return;
			if (list[left].compareTo(list[i]) < 0) {
				smallest = left;
			} else {
				smallest = i;
			}
			if (right < heapSize && list[right].compareTo(list[smallest]) < 0) {
				smallest = right;
			}
			if (smallest != i) {
				E tmp = (E) list[i];
				list[i] = list[smallest];
				list[smallest] = tmp;
				heapify(smallest);
			}

		}

	}

	public Object[] heapsort() {
		Object[] sortedArray = new Object[heapSize];
		int cnt = 0;
		while (heapSize > 0) {
			if(isMax)
				sortedArray[heapSize - 1] = this.deleteMP();
			else
				sortedArray[cnt++] = this.deleteMP();
		}
		return sortedArray;
	}

	private int rightChild(int i) {
		return 2 * i + 2;
	}

	private int leftChild(int i) {
		return 2 * i + 1;
	}
	
	private int parent(int i) {
		return (i-1)/2;
	}


	protected void buildHeapify() {

		for (int i = heapSize / 2 + 3; i >= 0; i--) {
			heapify(i);
		}
	}

	public boolean isEmpty() {
		return heapSize==0;
	}
	
	public int getSize() {
		return list.length;
	}

//	 public static void main(String[] args) {
//	 Integer[] a = {1,3,7,4,2,6,5};
//	 Heap<Integer> yify = new Heap<Integer>(false, 7);
//	 for (int i = 0; i < a.length; i++) {
//	 yify.insert(a[i]);
//	 }
//	 System.out.println((Integer)yify.getMP());
//	 Object[] sortedyify = yify.heapsort();
////	 for(Object o: sortedyify)
////	 System.out.println((Integer)o);
//	 }

//	@Override
//	public Iterator<E> iterator() {
//		Iterator<E> iterator = new Iterator<E>() {
//
//			@Override
//			public boolean hasNext() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public E next() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			
//		};
//		return null;
//	}

}
