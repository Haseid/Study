// har fått god hjelp fra mine medstudenter, har derfor kommentert
// veldig detaljert for å vise at jeg forstår det jeg skriver

// import til scanner
import java.io.*;
import java.util.*;

class Oblig6 {

	// attribut for tidtakning
	protected static long tidtakerStart;

	// main metode
	public static void main(String[] args) {

		// starter tidtakningen
		tidtakerStart = System.nanoTime();

		// feilmelding i tilfelle feile argumenter
		if (args.length != 3) {
			System.out.println("Argumentfeil");
		} else {
			// lagrer attributter for argumentene
			int antTrader = Integer.parseInt(args[0]);
			String innFil = args[1];
			String utFil = args[2];

			// starter opp innlesningen
			Run starter = new Run(innFil, utFil, antTrader);
		}
	}
}

// Starterklasse
class Run {

	// attributter for innlesningen
	private int antOrd;
	private String innFil;
	private String[] ordliste;

	// attributter for utskrivning
	private String utFil;

	// attributter for trådene
	private int antTrader;

	// attributter for beholderen
	private SortertBeholder beholder;

	// konstruktør som tar inn filnavn og starter innlesningen og lager beholder
	public Run(String innFil, String utFil, int antTrader) {

		this.innFil = innFil;
		this.utFil = utFil;
		this.antTrader = antTrader;
		lesFil();
		this.beholder = new SortertBeholder(antOrd, utFil);
		tradLager();
	}

	// metode for å lese inn filen
	private void lesFil() {

		// attributter for scanner
		Scanner filLeser;
		String tmpOrd;

		try {
			// starter scanner
			filLeser = new Scanner(new File(innFil));
			// leser inn antall ord
			antOrd = Integer.parseInt(filLeser.nextLine());
			// opprettter ordlisten med antall ordplasser
			ordliste = new String[antOrd];

			// løkke som går gjennom hele filen
			for (int i = 0; i < antOrd; i++) {
				tmpOrd = filLeser.nextLine();
				// tester om det er en tom linje
				if (tmpOrd != null) {
					ordliste[i] = tmpOrd;
				}
			}
		} catch (Exception e) {
			// feilmelding for innlesning
			System.out.println("Feil ved innlesningen");
		}
	}

	// metode som lager alle trådene
	public void tradLager() {

		// går gjennom antall tråder
		for(int i = 0; i < antTrader; i++) {

			// finner startpossisjonen til denne tråden til ordlisten
			int indexStart = (i*antOrd) / antTrader;
			// finner sluttpossisjonen til denne tråden til ordlisten
			int indexSlutt = ((i+1)*antOrd) / antTrader;

			// starter opp en ny tråd
			new Sorterer(beholder, ordliste, indexStart, indexSlutt).start();
		}
	}
}

// sorterings klasse / tråd
class Sorterer extends Thread {

	// attributter for tråden
	private SortertBeholder beholder;
	private String[] ordliste;
	private int indexStart;
	private int indexSlutt;
	private int antOrd; // antall ord som skal sorteres av denne tråden

	// konstruktør
	public Sorterer(SortertBeholder beholder, String[] ordliste, int indexStart, int indexSlutt) {
		this.beholder = beholder;
		this.ordliste = ordliste;
		this.indexStart = indexStart;
		this.indexSlutt = indexSlutt;
		this.antOrd = indexSlutt-indexStart;
	}

	// metode som sorterer trådens gitte array-lengde
	// lager et nytt array som er sortert og lagrer denne i beholder klassen
	// sorterer med algoritmen boble-sortering
	public void run() {

		// lager en midlertidig array med lengden av ord den skal sortere
		String[] tmpOrdliste = new String[antOrd];
		//index til det nye arrayet
		int indexTeller = 0;

		//løkke som kjører fra gitte possisjoner i ordlista
		for(int i = indexStart; i < indexSlutt; i++) { 
			// kopierer disse ordene over til det nye arrayet
			tmpOrdliste[indexTeller] = ordliste[i];
			// plusser på indexen til det nye arrayet
			indexTeller++;
		}

		// verdi som sier om det er skjedd en endring i arrayet
		boolean endret = true;

		// while-løøke som går så lenge det er skjedd en endring i det nye arrayet
		while(endret) {

			// setter enrding til false
			endret = false;
			// løkke som går gjennom det nye arrayet
			for(int i = 0; i < antOrd-1; i++) { // indexen må være antOrd-1, fordi vi tester med i+1

				// tester verdien av et ord og sammenligner det med det neste ordet
				if(tmpOrdliste[i].compareTo(tmpOrdliste[i+1]) > 0) {
					// lagrer det "lavere verdi"-ordet midlertidig
					String tmpOrd = tmpOrdliste[i];
					// flytter det "høyere verdi"-ordet opp i arrayet
					tmpOrdliste[i] = tmpOrdliste[i+1];
					// plasserer det "lavere verdi"-ordet under det "høyere verdi"-ordet
					tmpOrdliste[i+1] = tmpOrd;
					// setter at en endring er gjort
					endret = true;
				}
			}	
		}
		// lagrer det sorterte arrayet
		beholder.leggTilListe(tmpOrdliste);
	}
}

// fletter klasse / tråd
class Listefletter extends Thread {

	// attributter for Listefletter
	private SortertBeholder beholder;
	private String[] sortertListe1;
	private String[] sortertListe2;
	private String[] ferdigSortertListe;

	// konstruktør
	public Listefletter(SortertBeholder beholder, String[] sortertListe1, String[] sortertListe2) {
		this.beholder = beholder;
		this.sortertListe1 = sortertListe1;
		this.sortertListe2 = sortertListe2;
		this.ferdigSortertListe = new String[sortertListe1.length + sortertListe2.length];
	}

	public void run() {
		// teller for sortertListe1
		int teller1 = 0;
		// teller for sortertListe2
		int teller2 = 0;

		// løkke som går gjennom begge arrayen tilsammen
		for(int i = 0; i < ferdigSortertListe.length; i++) {

			// sjekker om tellern er mindre enn lengden på Liste1
			if(teller1 < sortertListe1.length) {
				
				// sjekker om tellern er mindre enn Liste2
				if(teller2 < sortertListe2.length) {
					
					// tester om verdien til Liste1 er mindre enn Liste2
					if(sortertListe1[teller1].compareTo(sortertListe2[teller2]) < 0) {
						// setter inn Liste1 sin verdig i den nye lista
						// plusser på teller etter plassering til Liste1
						ferdigSortertListe[i] = sortertListe1[teller1++];

					// om verdien til Liste1 er større enn Liste2 sin verdi
					} else {
						// setter inn Liste2 sin verdi i den nye lista
						// plusser på telleren til Liste2
						ferdigSortertListe[i] = sortertListe2[teller2++];
					}

				// om telleren til Liste2 er større enn lengden av Liste2
				} else {
					// setter inn Liste1 sin verdig i den nye lista
					// plusser på teller etter plassering til Liste1
					ferdigSortertListe[i] = sortertListe1[teller1++];
				}

			// om telleren til Liste1 er større enn lengden av Liste1
			} else {
				// setter inn Liste2 sin verdi i den nye lista
				// plusser på telleren til Liste2
				ferdigSortertListe[i] = sortertListe2[teller2++];
			}
		} // slutt på for-løkka

		// legger til den ferdig fletta liste i beholderklassen
		beholder.leggTilListe(ferdigSortertListe);
	}
}

class SortertBeholder {

	// attributter for beholderklassen
	private int antOrd;
	private String utFil;
	private FileWriter filSkriver;
	private String[] forrigeListe = null;

	// attributt for tidtakning
	private long tidtakerSlutt;

	// konstruktør
	public SortertBeholder(int antOrd, String utFil) {
		this.antOrd = antOrd;
		this.utFil = utFil;
	}

	// metode som legger til lister i beholderen og
	// kaller opp nye tråder så lenge lista ikke er ferdig for å flette resten
	synchronized public void leggTilListe(String[] liste) {

		// sjkekker om Lista er ferdig sortert
		if(antOrd == liste.length) {
				
			try {
				// starter utskrivningen
				filSkriver = new FileWriter(utFil);

				// løkke som går gjennom hele lista
				for(String ord: liste) {
					//skriver ut ett ord med "return"
					filSkriver.write(ord + "\n");
				}

				// lukker utskrivningen
				filSkriver.close();
				
				// setter slutt tiden på programmet
				tidtakerSlutt = System.nanoTime();
				// regner ut tiden i sekunder
				double tid = (tidtakerSlutt - new Oblig6().tidtakerStart) / 1000000000.0;
				// skriver ut tiden
				System.out.println("\nProgrammet bukte " + tid + " sekunder fra start til slutt\n");

			} catch(Exception e) {
				// feilmelding for utskrivningen
				System.out.println("Feil ved utskrivningen");
			}
		}

		// tester om det er en liste fra før som denne kan kombineres med
		if(forrigeListe != null) {
			// lager en ny fletter
			new Listefletter(this, liste, forrigeListe).start();
			// sletter den forrige listen som nå blir kombinert til en ny liste
			forrigeListe = null;

		// om det ikke er noen liste den nye kan kombineres med
		} else {
			// da lagres denne listen så den kan kombineres med den neste
			forrigeListe = liste;
		}
	}
}














































