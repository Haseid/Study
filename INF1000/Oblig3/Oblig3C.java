import easyIO.*;

class Oblig3C {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Bruk: > java Oblig3C <tekstfil>");
		} else {
			OrdAnalyse oa = new OrdAnalyse(args[0]);
			oa.ordLeser();
			oa.ordPar();
		} 
	}
}

class OrdAnalyse {

		String filNavn;
	    String[] alleOrdArr = new String[5000];
	    int[] tellerUnik = new int[5000];
	    int antUnikeOrd = 0;
	    int ordTeller = 0;

	    // Constructer, henter inn args[0].
		OrdAnalyse(String filNavn) {

			this.filNavn = filNavn;
		}

	// Oppgave A.
	void ordLeser() {

	    In fil = new In(filNavn);

	    // Starter innlesningen av ord.
	    while (fil.endOfFile() == false) {
			String ord = fil.inWord().toLowerCase();
			ordTeller++;
			int ordID = -1;

			// Loop som tester ord.
			for (int i = 0; i < antUnikeOrd; i++) {

				// Sjekker om ordet er lest gjennom før og returnerer en ID til ordet.
				if (ord.equals(alleOrdArr[i])) {
					ordID = i;
					break;
				}
			}

			// Separerer unike og gjentatte ord, og teller dem.
			if (ordID == -1) {
				alleOrdArr[antUnikeOrd] = ord;
				tellerUnik[antUnikeOrd] = 1;
				antUnikeOrd++;
			} else {
				tellerUnik[ordID]++;
			}
	    }
	}

	// Utskrivning til oppsummering.
	void oppsummering() {

	    Out oppsum = new Out("oppsummering.txt");
	    oppsum.outln("Antall ord: " + ordTeller);
		oppsum.outln("Antall unike ord: " + antUnikeOrd);

		for (int i = 0; 0 < alleOrdArr.length; i++) {
			if (alleOrdArr[i] != null) {
				oppsum.out(alleOrdArr[i],50);
				oppsum.outln(tellerUnik[i]);
			} else {
				break;
			}
		}
		oppsum.close();
	}

	// Oppgave B.
	void ordBruk() {

		int hoyeste = 0;
		int index = 0;

		// Finner indexen til ordet som er brukt mest, og antal ganger det er brukt.
		for (int i = 0; i < alleOrdArr.length; i++) {
			if (tellerUnik[i] > hoyeste) {
				hoyeste = tellerUnik[i];
				index = i;
			}
		}

		System.out.println("\nOrdet med flest forekomster er: \"" + alleOrdArr[index] + "\""
							+ "\nOrdet ble brukt " + hoyeste + " ganger\n");

		// skriver ut alle orde som er brukt mer eller likt 10% av det mest brukte ordet.
		for (int i = 0; i < alleOrdArr.length; i++) {
			if (tellerUnik[i] >= hoyeste * 0.1) {
				System.out.println(alleOrdArr[i] + "\t\t" + tellerUnik[i]);

			}
		}
		
	}

	// Oppgave C.
	void ordPar() {

		int[][] antallPar = new int[antUnikeOrd][antUnikeOrd];
		In fil = new In(filNavn);
		String ord1 = fil.inWord().toLowerCase();

		// Lokke som setter inn ordene i 2D arrayen.
		while (fil.endOfFile() == false) {
			String ord2 = fil.inWord().toLowerCase();

			for (int i =0; i < antUnikeOrd; i++) {

				if (ord1.equals(alleOrdArr[i])) {

					for (int j = 0; j < antUnikeOrd; j++) {

						if (ord2.equals(alleOrdArr[j])) {
							antallPar [i][j] ++;
						}
					}
				}
			}
			// Bytter ord1 til ord2 for å sjekke alle parrene.
			ord1 = ord2;
		}

		// Utskrivning.
		System.out.println("\nAlle ordpar som har alice foran i ordparet:\n");
		String forsteOrd = "alice";

		for (int i = 0; i < antUnikeOrd; i++) {
			if (forsteOrd.equals(alleOrdArr[i])) {
				for (int j = 0; j < antUnikeOrd; j++) {
					if (antallPar[i][j] > 0) {
						System.out.println(alleOrdArr[i] + "\t" + alleOrdArr[j]);
					}
				}
			}
		}
	}
}