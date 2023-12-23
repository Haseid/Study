class Oblig4 {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("bruk: >java Oblig4 <n>");
		} else {
			Start s = new Start(Integer.parseInt(args[0]));
			s.sekvensiell();
			s.parallell();
		}
	}
}

class Start {
	int n;
	int[] x, y;
	int MAX_X = 30, MAX_Y = 30;
	TegnUt t;

	Start(int n) {
		this.n = n;
		x = new int[n];
		y = new int[n];

		NPunkter punkter = new NPunkter(n);
		punkter.fyllArrayer(x ,y);
	}

	void sekvensiell() {
		System.out.println("\nSekvensiellt:");
		IntList coHull = new IntList();
		Sekvensiell s = new Sekvensiell(x, y, n, coHull);

		long start = System.nanoTime();
		s.start();
		double tid = (System.nanoTime()-start) / 1000000.0;
		System.out.println(n + " punkter: " + tid + " millisek.");

		if (n == 100)
			t = new TegnUt(this, s.coHull, "Sekvensiellt");
		if (n == 1000) {
			System.out.println("Innhylling: ");
			for(int i = 0; i < s.coHull.size(); i++)
				System.out.print(s.coHull.get(i) + " ");
			System.out.println();
		}
	}

	void parallell() {
		System.out.println("\nParallelt:");
		IntList coHull = new IntList();
		Parallell p = new Parallell(x, y, n, coHull);

		long start = System.nanoTime();
		p.start();
		double tid = (System.nanoTime()-start) / 1000000.0;
		System.out.println(n + " punkter: " + tid + " millisek.");

		if (n == 100)
			t = new TegnUt(this, p.coHull, "Parallelt");
		if (n == 1000) {
			System.out.println("Innhylling: ");
			for(int i = 0; i < p.coHull.size(); i++)
				System.out.print(p.coHull.get(i) + " ");
			System.out.println();
		}
	}
}

class Sekvensiell {
	int n;
	int[] x, y;
	IntList coHull;

	Sekvensiell(int x[], int y[], int n, IntList coHull) {
		this.x = x;
		this.y = y;
		this.n = n;
		this.coHull = coHull;
	}

	void start() {
		int p1 = 0;
		int p2 = 0;

		for(int i = 0; i < n; i++)
			if (x[i] < x[p1])
				p1 = i;
			else if (x[i] > x[p2])
				p2 = i;

		coHull.add(p1);
		fyll_ut(p1, p2);
		coHull.add(p2);
		fyll_ut(p2, p1);
	}

	void fyll_ut(int p1, int p2) {
		int p3 = finn_p3(p1, p2);
		if (p3 != -1) {
			fyll_ut(p1, p3);
			coHull.add(p3);
			fyll_ut(p3, p2);
		}
	}

	int finn_p3(int p1, int p2) {
		int p3 = -1;
		int p3_lengde = -1;

		for(int i = 0; i < n; i++) {
			int linjeligningen = (y[p1]-y[p2])*x[i] + (x[p2]-x[p1])*y[i] + (y[p2]*x[p1]-y[p1]*x[p2]);

			if (linjeligningen > p3_lengde &&  i != p1 && i!= p2)
				if (linjeligningen != 0) {
					p3_lengde = linjeligningen;
					p3 = i;
				} else if (((x[p2] > x[p1]) && (x[i] > x[p1]) && (x[i] < x[p2]) ) || ((x[p1] > x[p2]) && (x[i] < x[p1]) && (x[i] > x[p2]))) {
					p3_lengde = linjeligningen;
					p3 = i;
				} else if (((y[p2] > y[p1]) && (y[i] > y[p1]) && (y[i] < y[p2]) ) || ((y[p2] < y[p1]) && (y[i] < y[p1]) && (y[i] > y[p2]))) {
					p3_lengde = linjeligningen;
					p3 = i;
				}
		}
		return p3;
	}
}

class Parallell {
	int n, cores;
	int counter = 0;
	int[] x, y;
	IntList coHull, coHull_lower;
	Worker[] workers;

	Parallell(int[] x, int[] y, int n, IntList coHull) {
		this.x = x;
		this.y = y;
		this.n = n;
		this.coHull = coHull;
		coHull_lower = new IntList();
		cores = Runtime.getRuntime().availableProcessors();
		workers = new Worker[cores];
	}

	void start() {
		int p1 = 0;
		int p2 = 0;

		for(int i = 0; i < n; i++)
			if (x[i] < x[p1])
				p1 = i;
			else if (x[i] > x[p2])
				p2 = i;

		coHull.add(p1);
		int worker_upper = index();
		workers[worker_upper] = new Worker(p1, p2, 1, worker_upper,coHull, this);
		workers[worker_upper].start();

		int worker_lower = index();
		workers[worker_lower] = new Worker(p2, p1, 1, worker_lower, coHull_lower, this);
		workers[worker_lower].start();

		try {
			workers[worker_upper].join();
			workers[worker_lower].join();
		} catch(Exception e) {
			System.out.println(e);
		}

		for(int i = 0; i < coHull_lower.size(); i++)
			coHull.add(coHull_lower.get(i));
	}
	
	synchronized int index() {
		return counter++;
	}
}

class Worker extends Thread {
	int n, p1, p2, level, index, cores;
	int[] x, y;
	Parallell par;
	IntList upper_side, lower_side;

	Worker(int p1, int p2, int level, int index, IntList upper_side, Parallell par) {
		this.par = par;
		this.x = par.x;
		this.y = par.y;
		this.n = par.n;
		this.cores = par.cores;

		this.p1 = p1;
		this.p2 = p2;
		this.level = level;
		this.upper_side = upper_side;
		lower_side = new IntList();
		this.index = index;
	}

	public void run() {
		fyll_ut(p1, p2);
	}

	public void fyll_ut(int p1, int p2) {
		int p3 = finn_p3(p1, p2);
		if(p3 != -1)
			if(Math.pow(2, level) < cores) {
				if(index == 1)
					upper_side.add(p1);

				Worker worker_upper = new Worker(p1, p3, level+1, par.index(), upper_side, par);
				worker_upper.start();
				Worker worker_lower = new Worker(p3, p2, level+1, par.index(), lower_side, par);
				worker_lower.start();

				try {worker_upper.join();} catch(Exception e) {System.out.println(e);} //venter på øvre side
				upper_side.add(p3);

				try {worker_lower.join();} catch(Exception e) {System.out.println(e);} //venter på nedre side
				for(int i = 0; i < lower_side.size(); i++) {
					upper_side.add(lower_side.get(i));
				}
			} else {
				fyll_ut(p1, p3);
				upper_side.add(p3);
				fyll_ut(p3, p2);
			}
	}

	int finn_p3(int p1, int p2) {
		int p3 = -1;
		int p3_lengde = -1;

		for(int i = 0; i < n; i++) {
			int linjeligningen = (y[p1]-y[p2])*x[i] + (x[p2]-x[p1])*y[i] + (y[p2]*x[p1]-y[p1]*x[p2]);

			if (linjeligningen > p3_lengde &&  i != p1 && i!= p2)
				if (linjeligningen != 0) {
					p3_lengde = linjeligningen;
					p3 = i;
				} else if (((x[p2] > x[p1]) && (x[i] > x[p1]) && (x[i] < x[p2]) ) || ((x[p1] > x[p2]) && (x[i] < x[p1]) && (x[i] > x[p2]))) {
					p3_lengde = linjeligningen;
					p3 = i;
				} else if (((y[p2] > y[p1]) && (y[i] > y[p1]) && (y[i] < y[p2]) ) || ((y[p2] < y[p1]) && (y[i] < y[p1]) && (y[i] > y[p2]))) {
					p3_lengde = linjeligningen;
					p3 = i;
				}
		}
		return p3;
	}
}
