import java.util.*;

public class SortingAlgorithms {
	public static void main(String[] args) {
		//Makes integer arrays of various sizes and sorts them using various sorting algorithms

		int[] a1 = generate(10);
		int[] a2 = generate(1000);
		int[] a3 = generate(100000);
		int[] a4 = generate(1000000);
		int[] a5 = generate(10000000);

		sort(a1);
		sort(a2);
		sort(a3);
		sort(a4);
		//sort(a5);
	}
	public static void sort(int[] e) {
		//Sorts an array with all four algorithms and prints the currentTimeMillis() to show how long they took
		String result = e.length + ";" + System.currentTimeMillis() + ";";

		if (e.length == 10) printArray(e);
		int[] e_is = e.clone(), e_ms = e.clone(), e_hs = e.clone(), e_qs = e.clone();
		insertionSort(e_is);
		result += System.currentTimeMillis() + ";";

		mergeSort(e_ms);
		result += System.currentTimeMillis() + ";";

		heapSort(e_hs);
		result += System.currentTimeMillis() + ";";

		quickSort(e_qs);
		result += System.currentTimeMillis() + ";";

		System.out.println(result);
		if (e.length == 10) {
			printArray(e_is);
			printArray(e_ms);
			printArray(e_hs);
			printArray(e_qs);
		}
	}

	public static void printArray(int[] e) {
		//Prints the contents of an array
		for (int i = 0; i< e.length; i++) {
			System.out.print(e[i] + " ");
		}
		System.out.println();
	}
	public static void printArray(double[] e) {
		//Prints the contents of an array
		for (int i = 0; i< e.length; i++) {
			System.out.print(e[i] + " ");
		}
		System.out.println();
	}

	public static int[] generate(int size) {
		//Generates a random integer array with the appropriate size of elements and the specified size
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			result[i] = (int)(Math.random() * 89999) + 10000;
		}
		return result;
	}
	public static double[] generateDoubles(int size) {
		double[] result = new double[size];
		for (int i = 0; i < size; i++) {
			result[i] = Math.random();
		}
		return result;
	}

	//Insertion sort: n^2 running time
	public static void insertionSort(int[] e) {
		for (int i = 1; i < e.length; i++) {
			int key = e[i];
			int j = i - 1;

			//Move elements until the key is in the right spot
			while (j >= 0 && e[j] > key) {
				e[j + 1] = e[j];
				j--;
			}

			//Insert the key
			e[j + 1] = key;
		}
	}

	//Merge sort: nlgn running time
	public static void mergeSort(int[] e) {
		//Initial call
		mergeSort(e, 0, e.length);
	}
	public static void mergeSort(int[] e, int r, int q) {
		//r is start, q is 1 past the finish
		if (r + 1 == q) return;
		//Split into two smaller arrays
		mergeSort(e, r, (q + r) / 2);
		mergeSort(e, (q + r) / 2, q);
		//Combine
		merge(e, r, (q + r) / 2, q);
	}
	public static void merge(int[] e, int p, int q, int r) {
		//p is start of first, q is start of second, r is start of third

		//Create the two parts of the arrays and fill the L and R arrays
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
		int size = e.length;
		//Pull largest element to back of array
		for (int i = e.length - 1; i > 0; i--) {
			int temp = e[0];
			e[0] = e[i];
			e[i] = temp;
			size--;
			//Fix heap problems that may have occurred
			maxHeapify(e, 0, size);
		}
	}
	public static void buildMaxHeap(int[] e, int size) {
		//Converts a heap into a max-heap
		for (int i = parent(size - 1); i >= 0; i--) {
			maxHeapify(e, i, size);
		}
	}
	public static void maxHeapify(int e[], int p, int size) {
		//Fixes a single bad node, if nessecary: p is the node in question
		int largest = p;
		int l = left(p);
		int r = right(p);
		if (l < size) if (e[l] > e[p]) largest = l;
		if (r < size) if (e[r] > e[largest]) largest = r;
		if (largest != p) {
			//Fix the node and make the recursive call
			int temp = e[p];
			e[p] = e[largest];
			e[largest] = temp;
			maxHeapify(e, largest, size);
		}
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
		//Initial call
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
				//Move the element
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

	//Linear sorting algorithms
	public static void countingSort(int[] e, int k, int d) {
		int[] B = new int[e.length];
		int[] C = new int[k + 1];

		for (int i = 0; i < e.length; i++) {
			C[convertElement(e[i], d)]++;
		}
		for (int i = 1; i <= k; i++) {
			C[i] += C[i - 1];
		}
		for (int i = e.length - 1; i >= 0; i--) {
			B[C[convertElement(e[i], d)] - 1] = e[i];
			C[convertElement(e[i], d)]--;
		}

		for (int i = 0; i < e.length; i++) {
			e[i] = B[i];
		}
	}
	public static int convertElement(int e, int d) {
		//When sorting by a particular digit, this function returns the specified digit from an element
		return (int)((e * 1.0) / Math.pow(10, d - 1)) % 10;
	}
	public static void radixSort(int[] e, int d) {
		for (int i = 1; i <= d; i++) {
			countingSort(e, 9, i);
		}
	}

	public static void bucketSort(double[] e) {
		@SuppressWarnings("unchecked")
		ArrayList<Double>[] buckets = new ArrayList[e.length];
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new ArrayList<Double>(2);
		}
		for (int i = 0; i < e.length; i++) {
			buckets[(int)(e.length * e[i])].add(e[i]);
		}
		for (int i = 0; i < buckets.length; i++) {
			if (!buckets[i].isEmpty()) Collections.sort(buckets[i]);
		}
		int current = 0;
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[i].size(); j++) {
				e[current] = buckets[i].get(j);
				current++;
			}
		}
	}
}
