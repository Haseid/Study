import easyIO.*;

class Oblig3A {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Bruk: > java Oblig3A <tekstfil>");
		} else {
			OrdAnalyse oa = new OrdAnalyse(args[0]);
			oa.ordLeser();
			oa.oppsummering();
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

	// Oppgave A
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

	// Utskrivning til oppsummering
	void oppsummering() {

	    Out oppsum = new Out("oppsummering.txt");
	    oppsum.outln("Antall ord: " + ordTeller);
		oppsum.outln("Antall unike ord: " + antUnikeOrd + "\n");

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
}