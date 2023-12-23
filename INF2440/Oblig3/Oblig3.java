import java.util.*;
import java.util.concurrent.*;

class Oblig3 {
	public static void main(String [] args) {
		if (args.length != 1) {
			System.out.println("bruk: >java Oblig3 <n>");
		} else {
			int n = Integer.parseInt(args[0]);

			System.out.println("Sekvensiellt:");
			new MultiRadix().doIt(n);

			System.out.println("Parallelt:");
			new MultiRadixParallel().doIt(n);
		}
	}
}

class MultiRadixParallel {
	final int NUM_BIT = 7;
	int cores, n;
	int[] a, b, t, bit;
	int[][] allCount;
	int globalMax = Integer.MIN_VALUE;
	CyclicBarrier cb, sync;

	MultiRadixParallel() {
		cores = Runtime.getRuntime().availableProcessors();
		cb = new CyclicBarrier(cores + 1);
		sync = new CyclicBarrier(cores);
		allCount = new int[cores][];
	}
	void doIt (int len) {
		a = new int[len];
		b = new int[len];
		Random r = new Random(123);
		for (int i = 0; i < len;i++) {
			a[i] = r.nextInt(len);
		}

		long tt = System.nanoTime();
		radixPara();
		double tid = (System.nanoTime() -tt)/1000000.0;
		System.out.println("Sorterte "+n+" tall: " + tid + " millisek.");
		
		testSort(a);
	}

	void radixPara() {
		n = a.length;

		for(int index = 0; index < cores; index++) {
			new Worker(index).start();
		}

		try {
			cb.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void testSort(int[] a){
		for (int i = 0; i< a.length-1;i++) {
			if (a[i] > a[i+1]){
				System.out.println("SorteringsFEIL på plass: "+i +" a["+i+"]:"+a[i]+" > a["+(i+1)+"]:"+a[i+1]);
				return;
			}
		}
	}

	synchronized void setGlobalMax(int value) {
		if (globalMax < value) {
			globalMax = value;
		}
	}

	class Worker extends Thread {
		int num;
		int localMax = Integer.MIN_VALUE;

		Worker(int num) {
			this.num = num;
		}

		public void run() {
			try {
				findMax();		// a) finn max verdi
				sync.await();	// max done

				radixMulti();
				cb.await();		// done
			} catch(Exception e) {
				System.out.println("Tråd nr: " + num);
				e.printStackTrace();
				System.exit(0);
			}
		}

		void findMax() {
			for(int i = num*n/cores; i < (num+1)*n/cores; i++) {
				if (localMax < a[i]) {
					localMax = a[i];
				}
			}
			if(localMax > globalMax) {
				setGlobalMax(localMax);
			}
		}

		void radixMulti() throws Exception {
			int numBit = 2;
			int sum = 0;
			int numDigits, rest;

			if(num == 0) { // bare en worker
				while(globalMax >= (1L << numBit)) {
					numBit++;
				}

				// bestem antall bit i numBits sifre
				numDigits = Math.max(1, numBit/NUM_BIT);
				bit = new int[numDigits];
				rest = (numBit % numDigits);

				// fordel bitene vi skal sortere på jevnt
				for(int i = 0; i < bit.length; i++) {
					bit[i] = numBit / numDigits;
					if(0 < rest--) {
						bit[i]++;
					}
				}
			}

			sync.await();

			for(int i = 0; i < bit.length; i++) {
				radixSort(bit[i], sum);
				sum += bit[i];
				sync.await();
				// swap arrays (pointers only)
				if(num == 0) { // bare en worker
					t = a;
					a = b;
					b = t;
				}
				sync.await();
			}
		}

		void radixSort(int maskLen, int bitShift) throws Exception {
			int mask = (1 << maskLen);
			int[] localCount = new int[mask];
			int sumCount = 0;

			// b) localCount=the frequency of each radix value in a
			for(int i = num*n/cores; i < (num+1)*n/cores; i++) {
				localCount[(a[i] >>> bitShift) & mask - 1]++;
			}
			allCount[num] = localCount;

			sync.await();

			// c) Add up in 'localCount' - accumulated values
			localCount = new int[localCount.length];
			for (int val = 0; val < localCount.length; val++) {
				for (int i = 0; i < num; i++) {
					sumCount += allCount[i][val];
				}

				localCount[val] = sumCount;
				for (int i = num; i < cores; i++) {
					sumCount += allCount[i][val];
				}
			}

			// d) move numbers in sorted order a to b
			for(int i = num*n/cores; i < (num+1)*n/cores; i++)
				b[localCount[(a[i] >>> bitShift) & (mask - 1)]++] = a[i];
		}
	}
}

/***********************************************************
* Oblig 3 - sekvensiell kode, INF2440 v2016.
*			Ifi, Uio, Arne Maus
************************************************************/
class MultiRadix {
	int n;
	int [] a;
	final int NUM_BIT = 7; // alle tall 6-11 OK

	void doIt (int len) {
		a = new int[len];
		Random r = new Random(123);
		for (int i = 0; i < len;i++) {
		   a[i] = r.nextInt(len);
		}
		a = radixMulti(a);
	} // end doIt

	int []  radixMulti(int [] a) {
		  long tt = System.nanoTime();
		  // 1-5 digit radixSort of : a[]
		  int max = a[0], numBit = 2, numDigits, n =a.length;
		  int [] bit ;

		 // a) finn max verdi i a[]
		  for (int i = 1 ; i < n ; i++)
			   if (a[i] > max) max = a[i];
		  while (max >= (1L<<numBit) )numBit++; // antall siffer i max

		  // bestem antall bit i numBits sifre
		  numDigits = Math.max(1, numBit/NUM_BIT);
		  bit = new int[numDigits];
		  int rest = (numBit%numDigits), sum =0;;

		  // fordel bitene vi skal sortere paa jevnt
		  for (int i = 0; i < bit.length; i++){
			  bit[i] = numBit/numDigits;
			  if ( rest-- > 0)  bit[i]++;
		  }

		  int[] t=a, b = new int [n];

		  for (int i =0; i < bit.length; i++) {
			  radixSort( a,b,bit[i],sum );	// i-te siffer fra a[] til b[]
			  sum += bit[i];
			  // swap arrays (pointers only)
			  t = a;
			  a = b;
			  b = t;
		  }
		  if (bit.length%2 != 0 ) {
			  // et odde antall sifre, kopier innhold tilbake til original a[] (n� b)
			  System.arraycopy (a,0,b,0,a.length);
		  }

		  double tid = (System.nanoTime() -tt)/1000000.0;
		  System.out.println("Sorterte "+n+" tall: " + tid + " millisek.");
		  testSort(a);
		  return a;
	 } // end radixMulti

	/** Sort a[] on one digit ; number of bits = maskLen, shiftet up 'shift' bits */
	 void radixSort ( int [] a, int [] b, int maskLen, int shift){
		  // System.out.println(" radixSort maskLen:"+maskLen+", shift :"+shift);
		  int  acumVal = 0, j, n = a.length;
		  int mask = (1<<maskLen) -1;
		  int [] count = new int [mask+1];

		 // b) count=the frequency of each radix value in a
		  for (int i = 0; i < n; i++) {
			 count[(a[i]>>> shift) & mask]++;
		  }

		 // c) Add up in 'count' - accumulated values
		  for (int i = 0; i <= mask; i++) {
			   j = count[i];
				count[i] = acumVal;
				acumVal += j;
		   }
		 // d) move numbers in sorted order a to b
		  for (int i = 0; i < n; i++) {
			 b[count[(a[i]>>>shift) & mask]++] = a[i];
		  }

	}// end radixSort

	void testSort(int [] a){
		for (int i = 0; i< a.length-1;i++) {
		   if (a[i] > a[i+1]){
			  System.out.println("SorteringsFEIL på plass: "+i +" a["+i+"]:"+a[i]+" > a["+(i+1)+"]:"+a[i+1]);
			  return;
		  }
	  }
	 }// end simple sorteingstest
}// end SekvensiellRadix