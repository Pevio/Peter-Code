
public class SortingAlgorithms {

	public static void main(String[] args) {
		//Makes integer arrays of various sizes and sorts them using various sorting algorithms
		int[] a1 = generate(10);
		//int[] a2 = generate(1000);
		//int[] a3 = generate(100000);
		//int[] a4 = generate(1000000);
		//int[] a5 = generate(10000000);
		
		sort(a1);
		//sort(a2);
		//sort(a3);
		//sort(a4);
		//sort(a5);
	}
	public static void sort(int[] e) {
		//Sorts an array with all four algorithms and prints the currentTimeMillis() to show how long they took
		String result = e.length + ";" + System.currentTimeMillis() + ";";
		
		if (e.length == 10) printArray(e);
		int[] newE = e.clone();
		insertionSort(newE);
		result += System.currentTimeMillis() + ";";
		
		mergeSort(e.clone());
		result += System.currentTimeMillis() + ";";
		
		heapSort(e.clone());
		result += System.currentTimeMillis() + ";";
		
		quickSort(e.clone());
		result += System.currentTimeMillis();
		
		System.out.println(result);
		if (e.length == 10) printArray(newE);
	}
	
	public static void printArray(int[] e) {
		//Prints the contents of an array
		for (int i = 0; i< e.length; i++) {
			System.out.print(e[i] + " ");
		}
		System.out.println();
	}
	public static int[] generate(int size) {
		//Generates a random array
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			result[i] = (int)(Math.random() * 89999) + 10000;
		}
		return result;
	}
	
	//Insertion sort: n^2 running time
	public static void insertionSort(int[] e) {
		for (int i = 1; i < e.length; i++) {
			for (int j = 0; j < i; j++) {
				if (e[i] < e[j]) {
					//Insert
					int temp = e[i];
					for (int k = i; k > j; k--) {
						e[k] = e[k - 1];
					}
					e[j] = temp;
				}
			}
		}
	}
	
	//Merge sort: nlgn running time
	public static void mergeSort(int[] e) {
		mergeSort(e, 0, e.length);
	}
	public static void mergeSort(int[] e, int r, int q) {
		//r is start, q is 1 past the finish
		if (r + 1 == q) return;
		mergeSort(e, r, (q + r) / 2);
		mergeSort(e, (q + r) / 2, q);
		merge(e, r, (q + r) / 2, q);
	}
	public static void merge(int[] e, int p, int q, int r) {
		//p is start of first, q is start of second, r is start of third
		
		//Create the two parts of the arrays
		int n1 = q - p;
		int n2 = r - q;
		int[] L = new int[n1 + 1];
		int[] R = new int[n2 + 1];
		
		for (int i = 0; i < n1; i++) {
			L[i] = e[p + i];
		}
		L[n1] = Integer.MAX_VALUE;
		for (int i = 0; i < n2; i++) {
			R[i] = e[q + i];
		}
		R[n2] = Integer.MAX_VALUE;
		
		//Merge them into e[]
		int i = 0;
		int j = 0;
		for (int k = p; k < r; k++) {
			if (L[i] < R[j]) {
				e[k] = L[i];
				i++;
			} else {
				e[k] = R[j];
				j++;
			}
		}
	}
	
	//Heap Sort: nlgn runnning time
	public static void heapSort(int[] e) {
		buildMaxHeap(e, e.length);
		//Pull largest element to back of array
		for (int i = 0; i < e.length; i++) {
			extractMax(e, e.length - i);
		}
	}
	public static void buildMaxHeap(int[] e, int size) {
		//Converts a heap into a max-heap
		for (int i = 0; i >= 0; i--) {
			maxHeapify(e, parent(e.length - 1), size);
		}
	}
	public static void maxHeapify(int e[], int p, int size) {
		//Fixes a single bad node
		int bigger = 0;
		if (left(p) >= size) return;
		if (right(p) == size) bigger = left(p);
		bigger = e[left(p)] > e[right(p)] ? left(p) : right(p);
		if (e[bigger] > e[p]) {
			int temp = e[p];
			e[p] = e[bigger];
			e[bigger] = temp;
			maxHeapify(e, bigger, size);
		}
	}
	public static void extractMax(int[] e, int size) {
		//Moves the largest element of a max-heap to the back of the array and max-heapifys it
		int temp = e[0];
		e[0] = e[size - 1];
		e[size - 1] = temp;
		maxHeapify(e, 0, size - 1);
	}
	//Heap sort helper methods
	public static int parent(int p) {
		return p / 2;
	}
	public static int left(int p) {
		return 2 * p;
	}
	public static int right(int p) {
		return 2 * p + 1;
	}
	
	//QuickSort: n^2 worst case; nlgn average case running time
	public static void quickSort(int[] e) {
		quickSort(e, 0, e.length);
	}
	public static void quickSort(int[] e, int p, int r) {
		//p is start of the part of the array we need to sort; r is one past the last element
		if (p >= r) return;
		int q = partition(e, p, r);		//q is the new position of the pivot element, after partition() is called
		quickSort(e, p, q);
		quickSort(e, q + 1, r);
	}
	public static int partition(int[] e, int p, int r) {
		//Partitions the array into two subarrays, one all greater and one all less than the pivot element
		int x = e[r - 1];	//pivot
		int i = p - 1;
		for (int j = p; j < r - 1; j++) {
			if (e[j] <= x) {
				i++;
				int temp = e[i];
				e[i] = e[j];
				e[j] = temp;
			}
		}
		
		//Move the pivot element then return its position
		i++;
		int temp = e[i];
		e[i] = e[r - 1];
		e[r - 1] = temp;
		return i;
	}
}
