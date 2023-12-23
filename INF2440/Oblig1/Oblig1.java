import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Oblig1 {
	public static void main(String[] args) {
		for (int i = 0; i<9; i++) {
			//System.out.println("----------------------------------");
			new Sorter(Integer.parseInt(args[0]));
		}
		//System.out.println("----------------------------------");
	}
}

class Sorter {
	int n;
	int cores;
	int[] arraySort, sekvensiell, parallell;
	CyclicBarrier cb;
	Lock lock = new ReentrantLock();

	Sorter(int n) {
		this.n = n;
		setup();
		start();
	}

	public void setup() {
		cores = Runtime.getRuntime().availableProcessors();
		cb = new CyclicBarrier(cores+1);
		arraySort = new int[n];
		Random r = new Random();

		for (int i = 0; i<n; i++) {
			arraySort[i] = r.nextInt(999999);
		}

		sekvensiell = arraySort.clone();
		parallell = arraySort.clone();
	}

	public void start() {
		arraySortRun();
		sekvensiellRun();
		parallellRun();
	}

	public void arraySortRun() {
		long time =  System.nanoTime();
		Arrays.sort(arraySort);
		//System.out.println("Array.sort(): " + ((System.nanoTime() - time)/1000000.0) + "ms");
	}

	public void sekvensiellRun() {
		int temp;
		long time =  System.nanoTime();
		sekvensiell = insertSort(sekvensiell, 0, 40);
		for (int i = 40; i<n; i++) {
			if (sekvensiell[i] > sekvensiell[39]) {
				temp = sekvensiell[39];
				sekvensiell[39] = sekvensiell[i];
				sekvensiell[i] = temp;
				sekvensiell = insertSort(sekvensiell, 0, 40);
			}
		}
		//System.out.println("Sekvensiell:  " + ((System.nanoTime() - time)/1000000.0) + "ms");
	}

	public void parallellRun() {
		long time =  System.nanoTime();
		Threads[] threads = new Threads[cores];
		parallell = insertSort(parallell, 0, 40);
		for (int i = 0; i<cores; i++) {
			(threads[i] = new Threads(i)).start();
		}

		try {
			cb.await();
			for (int i = 0; i<cores; i++) {
				if (threads[i].nr != 0) {
					merge(threads[i].start);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		//System.out.println("Parallell:    " + ((System.nanoTime() - time)/1000000.0) + "");
		System.out.println(((System.nanoTime() - time)/1000000.0));
	}

	public  int[] insertSort(int[] a, int v, int h) {
		int i, t;
		for (int k = v; k<h; k++) {
			t = a[k+1];
			i = k;
			while (i>=v && a[i]<t) {
				a[i+1] = a[i];
				i--;
			}
			a[i+1] = t;
		}
		return a;
	}

	public void merge(int start) {
		for(int i = start+39; i >= start; i--) {			
			if(parallell[i] > parallell[39]) {
				int tmp = parallell[i];
				parallell[i] = parallell[39];
				parallell[39] = tmp;
				int j = 39;
				while(j > 0 && parallell[j] > parallell[j-1]) {
					tmp = parallell[j];
					parallell[j] = parallell[j-1];
					parallell[j-1] = tmp;
					j--;
				}
			}
		}
	}

	class Threads extends Thread {
		int nr;
		int start;
		int end;
		int temp;

		Threads(int nr) {
			this.nr = nr;
			if (nr == 0) {
				start = 0;
			} else {
				start = Math.round(((float)nr/cores)*n);
			}
			if (nr == cores-1) {
				end = n;
			} else {
				end = Math.round(n*((float)(nr+1)/cores));	
			}
		}

		public void run() {

			parallell = insertSort(parallell, start, start+39);
			for (int i = start+39; i<end; i++) {
				if (parallell[i] > parallell[start+39]) {
					temp = parallell[start+39];
					parallell[start+39] = parallell[i];
					parallell[i] = temp;
					parallell = insertSort(parallell, start, start+39);
				}
			}
			try {
				cb.await();
			} catch (Exception e) {
				return;
			}
		}
	}
}


































