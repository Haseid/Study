import java.util.*;
import java.util.concurrent.*;

class Oblig2 {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int method = Integer.parseInt(args[1]);
		long startTime;
		double endTime;
		double msPrPrime;
		double msPrFac;

		if (method == 0) {
			EratosthenesSil sequential = new EratosthenesSil(n);
			System.out.println("Sekvensiellt:");

			// Sekvensiell primtall
			startTime = System.nanoTime();
			sequential.generatePrimes();
			endTime =  (System.nanoTime()-startTime)/1000000.0;
			sequential.countPrimes();
			msPrPrime = endTime/sequential.antPrime;
			System.out.println("Generering av alle primtall <= "+n+": "+String.format("%.8g", endTime)+" millisek");
			System.out.println("Eratosthenes sil: "+String.format("%.8g", msPrPrime)+" millisek/primtall");
			System.out.println("Fant "+sequential.antPrime+" primtall\n");

			// Sekvensiell faktorisering
			startTime = System.nanoTime();
			sequential.factorizeLast100();
			endTime =  (System.nanoTime()-startTime)/1000000.0;
			msPrFac = endTime/100;
			System.out.println("\n100 faktoriseringer: "+String.format("%.4g", endTime)+" millisek");
			System.out.println(String.format("%.4g", msPrFac)+" millisek/faktorisering");
		} else if (method == 1) {
			EratosthenesSilParallel parallel = new EratosthenesSilParallel(n);
			System.out.println("Parallellt:");

			// Parallell primtall
			startTime = System.nanoTime();
			parallel.generatePrimes();
			endTime =  (System.nanoTime()-startTime)/1000000.0;
			parallel.countPrimes();
			msPrPrime = endTime/parallel.antPrime;
			System.out.println("Generering av alle primtall <= "+n+": "+String.format("%.8g", endTime)+" millisek");
			System.out.println("Eratosthenes sil: "+String.format("%.8g", msPrPrime)+" millisek/primtall");
			System.out.println("Fant "+parallel.antPrime+" primtall\n");

			// Parallell faktorisering
			startTime = System.nanoTime();
			parallel.factorizeLast100();
			endTime =  (System.nanoTime()-startTime)/1000000.0;
			msPrFac = endTime/100;
			System.out.println("\n100 faktoriseringer: "+String.format("%.4g", endTime)+" millisek");
			System.out.println(String.format("%.4g", msPrFac)+" millisek/faktorisering");
		}
	}
}

class EratosthenesSilParallel {
	public int antPrime = 0;
	private int n;
	private int cores;
	private int nextPrime = 2;
	private boolean[] primeArr;
	private Threads[] threads;
	private ThreadsFac[] threadsFac;
	private CyclicBarrier cb;
	private ArrayList<Integer> allPrimes = new ArrayList<Integer>();

	public EratosthenesSilParallel(int n) {
		this.n = n+1;
		this.primeArr = new boolean[this.n];
		cores = Runtime.getRuntime().availableProcessors();
		cb = new CyclicBarrier(cores+1);
		threads = new Threads[cores];
		threadsFac = new ThreadsFac[cores];

		for (int i = 0; i<cores; i++) {
			threads[i] = new Threads();
		}

		for (int i = 0; i<cores; i++) {
			threadsFac[i] = new ThreadsFac((n-100)+(100*i/cores), (n-100)+(100*(i+1)/cores));
		}

		setAllPrime();
	}

	public void generatePrimes() {
		for (int i = 0; i<cores; i++) {
			threads[i].start();
		}
		try {
				cb.await();
		} catch (Exception e) {
			System.out.println(e);
			return;
		}

	}

	public void factorizeLast100() {
		for (int i = 0; i<cores; i++) {
			threadsFac[i].start();
		}
		try {
				cb.await();
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
	}

	private void setAllPrime() {
		Arrays.fill(primeArr, true);
		primeArr[0] = false;
		primeArr[1] = false;
		primeArr[2] = true;
	}

	private void crossOut(int num) {
		primeArr[num] = false;
	}

	private boolean isPrime(int num) {
		if (num == 2) {
			return true;
		} else if ((num%2 == 0) || (num>n)) {
			return false;
		} else {
			return primeArr[num];
		}
	}

	public void countPrimes() {
		for (int i = 2; i<n; i++) {
			if (primeArr[i]) {
				allPrimes.add(i);
				antPrime++;
			}
		}
	}

	private void printAllPrimes() {
		for (int i = 2; i<n; i++) {
			if (primeArr[i]) {
				System.out.println(i);
			}
		}
	}

	private synchronized int nextPrime() {
		while(!isPrime(nextPrime)) {
			nextPrime++;
		}
		return nextPrime++;
	}

	private int nextPrime2(int num) {
		while(!isPrime(num)) 
			num++;
		return num;
	}

	private class Threads extends Thread {

		public void run() {
			int prime = nextPrime();
			while (prime<Math.sqrt(n)) {
				paraCrossOut(prime);
				prime = nextPrime();
			}
			try {
				cb.await();
			} catch (Exception e) {
				return;
			}
		}

		private void paraCrossOut(int num){
			for (int i = num*num; i<n; i+=num) {
				crossOut(i);
			}
		}
	}

	private class ThreadsFac extends Thread {
		int start;
		int end;

		ThreadsFac(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public void run() {
			int count = 0;
			for (int i = start; i < end; i++) {
				if (((start == (n-101)) || (end == n-1)) && !(count == 5)) {
					System.out.println(factorize(i));
					count++;
				}
				factorize(i);
			}
			try {
				cb.await();
			} catch (Exception e) {
				return;
			}
		}

		private String factorize(int num) {
			String answer = num + " = ";
			int prime = 2;

			while (!isPrime(num)) {
				if (num % prime == 0) {
					num = num/prime;
					answer += prime + "*";
				} else {
					prime = nextPrime2(++prime);
				}
			}
			answer += num;
			return answer;
		}
	}

	private class ThreadsFac2 extends Thread {
		ArrayList<Integer> primes;

		ThreadsFac2(ArrayList<Integer> primes) {
			this.primes = primes;
		}

	}
}

class EratosthenesSil {
	public int antPrime = 0;
	private int n;
	private String[] factorized = new String[100];
	private boolean[] primeArr;

	public EratosthenesSil(int n) {
		this.n = n+1;
		this.primeArr = new boolean[this.n];
		setAllPrime();
	}

	private void setAllPrime() {
		Arrays.fill(primeArr, true);
		primeArr[0] = false;
		primeArr[1] = false;
		primeArr[2] = true;
	}

	private void crossOut(int num) {
		primeArr[num] = false;
	}

	private boolean isPrime(int num) {
		if (num == 2) {
			return true;
		} else if ((num%2 == 0) || (num>n)) {
			return false;
		} else {
			return primeArr[num];
		}
	}

	private int nextPrime(int num) {
		while(!isPrime(num)) 
			num++;
		return num;
	}

	public void generatePrimes() {
		int num = 2;
		while (num<Math.sqrt(n)) {
			if (isPrime(num)) {
				for (int i = num*num; i<n; i+=num) {
					crossOut(i);
				}
			}
			num = nextPrime(++num);
		}
	}

	private void printAllPrimes() {
		for (int i = 2; i<n; i++) {
			if (primeArr[i]) {
				System.out.println(i);
			}
		}
	}

	public void countPrimes() {
		for (int i = 2; i<n; i++) {
			if (primeArr[i]) {
				antPrime++;
			}
		}
	}

	private String factorize(int num) {
		String answer = num + " = ";
		int prime = 2;

		while (!isPrime(num)) {
			if (num % prime == 0) {
				num = num/prime;
				answer += prime + "*";
			} else {
				prime = nextPrime(++prime);
			}
		}
		answer += num;
		return answer;
	}

	public void factorizeLast100() {
		int count = 0;
		for (int i = n-101; i < n-1; i++) {
			factorized[count++] = factorize(i);
		}
		System.out.println(factorized[0]);
		System.out.println(factorized[1]);
		System.out.println(factorized[2]);
		System.out.println(factorized[3]);
		System.out.println(factorized[4]);
		System.out.println(factorized[95]);
		System.out.println(factorized[96]);
		System.out.println(factorized[97]);
		System.out.println(factorized[98]);
		System.out.println(factorized[99]);
	}
}