import easyIO.*;

class Fugleprog {
	public static void main(String[] args) {
		Hjelpeklasse hj = new Hjelpeklasse();
		hj.kommandolokke();
	}
}

class Hjelpeklasse {
        int meny() {
		In tast = new In();

		System.out.print("\n"
				   + "1. Registrer en fugleobservasjon.\n"
				   + "2. Skriv ut alle observasjoner av en fugletype.\n"
				   + "3. Skriv ut alle observasjoner på ett bestemt sted.\n"
				   + "4. Avslutt systemet.\n"
				   + "Gi et tall mellom 1 og 4: ");
		return tast.inInt();
	}
	void kommandolokke() {
		int aksjon = 0;

		while (aksjon != 4) {
			aksjon = meny();

			switch (aksjon) {
				case 1: registrer();break;
				case 2: skrivFugletype();break;
				case 3: skrivSted();break;
				case 4: break;
				default:
			}
		}
	}
	void registrer() {
	    In tast = new In();
	    
	    System.out.print("\n"
			       + "Skriv in Fugletype (Eks. Spurv): ");
	    String fugleType = tast.inWord().trim();
	    System.out.print("Skriv in kjønn (M, F: Male, Female): ");
	    String fugleKjonn = tast.inWord().trim();
	    System.out.print("Skriv in observasjons sted (Eks. Oslo): ");
	    String observasjonsSted = tast.inWord().trim();
	    System.out.print("Skriv in observasjons dato (Eks. april2013): ");
	    String observasjonsDato = tast.inWord().trim();
	    
	    Out fil = new Out("fugler.txt", true);
	    fil.outln(fugleType + "," + fugleKjonn + "," + observasjonsSted + "," + observasjonsDato);
	    fil.close();

	    System.out.println("Regestrering fullført.");
	}
	void skrivFugletype() {
	    In tast = new In();
	    In fil = new In("fugler.txt");

	    System.out.print("\nSkriv inn fugletypen du vil skrive ut: ");
	    String fugleType = tast.inWord();
	    System.out.println("\n" + fugleType);

	    while (fil.endOfFile() == false) {
		String info = fil.inLine();
		String[] infoArr = info.split(",");

		if (infoArr[0].equals(fugleType)) {
		    System.out.println(info);
		}
	    }	    
	}
	void skrivSted() {
	    In tast = new In();
	    In fil = new In("fugler.txt");

	    System.out.print("\nSkriv inn observasjons sted du vil skrive ut: ");
	    String fugleSted = tast.inWord();
	    System.out.println("\n" + fugleSted);

	    while (fil.endOfFile() == false) {
		String info = fil.inLine();
		String[] infoArr = info.split(",");

		if (infoArr[2].equals(fugleSted)) {
		    System.out.println(info);
		}
	    }
	}
}