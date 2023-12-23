// Skriv en kommentar om besvarelsen din her: ... 
// Sammarbeidet med William Haugan, brukernavn: williha
// ... 
import easyIO.*; 
 
class Oblig4 { 
	public static void main(String [] args) { 
 		Utsyn s = new Utsyn(); 
		 s.menylokke(); 
	} 
} 
 
class Student { 
	String navn; // studentens navn 
 	int saldo; // studentens saldo 
 
	 // Evt. metoder for å behandle en Student... 
} 
 
class Hybel { 
	Student leietager; // peker på et Student-objekt 
	int husleie; // 6000 hvis hybelen er i 3. etasje, ellers 5000. 

	// Evt. metoder for å behandle en Hybel... 
} 
 
class Utsyn { 
	In tast = new In();
	Out skriv = new Out();
	int maned;
	int ar;
	int totalfortjeneste;
	int totalAntallManeder;
	int manedsleieVanligHybel;
	int manedsleieToppEtasjeHybel;
 
	Hybel[][] hybler = new Hybel[3][6]; 
	// Variabler for økonomidata kan legges inn her... 
 
	// Konstanter: 
	final String FILNAVN = "hybeldata.txt"; 
	final String TOM_HYBEL = "TOM HYBEL"; 
  
 	// Konstruktør for klassen Utsyn 
 	Utsyn() { 
		// Her kan du lese datafilen "hybeldata.txt" og lagre hele innholdet 
		// i datastrukturene dine. Husk å opprette Hybel- og Student-objekter. 
		// Eksempel på innlesing av en saldo, for en gitt "etg" og "rom": 
		// hybler[etg][rom] = new Hybel(); 
		// hybler[etg][rom].leietager = new Student(); 
		// hybler[etg][rom].leietager.saldo = innfil.inInt(" ;");

 		In fil = new In(FILNAVN);

 		maned = fil.inInt(";");
 		ar = fil.inInt(";");
		totalfortjeneste = fil.inInt(";");
		totalAntallManeder = fil.inInt(";");
		manedsleieVanligHybel = fil.inInt(";");
		manedsleieToppEtasjeHybel = fil.inInt(";");

			//lage et Student objekt
			//lage et Hybel objekt
			//lagre disse på riktig plass

			//hybler[etg][rom] = new Hybel(); 
			//hybler[etg][rom].leietager = new Student(); 

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {

				//prosesserer én linje
				int etasje = fil.inInt(";");
				char romNavn = fil.inChar(";");
				int saldo = fil.inInt(";");
				String navn = fil.inLine();
				hybler[etg][rom] = new Hybel(); 
				hybler[etg][rom].leietager = new Student();

				hybler[etg][rom].leietager.navn = navn;
				hybler[etg][rom].leietager.saldo = saldo;
			}
		}
	} 
 
 	void menylokke() { 
 		int ordre = -1; 
 		while (ordre != 0) { 
 			// Skriv ut meny:  
			System.out.println("\n\nMeny:\n\n"
								+ "1. Skriv ut oversikt.\n"
							 	+ "2. Registrer ny leietager.\n"
								+ "3. Registrer betaling fra leietager.\n"
								+ "4. Registrer frivillig utflytting.\n"
								+ "5. Manedskjoring av husleie.\n"
								+ "6. Kast ut leietagere.\n"
								+ "7. Hoyne husleien.\n"
								+ "8. Avslutt.\n");

			// Les ordre fra bruker 
 			System.out.print("Tast in ordre: "); 
			ordre = tast.inInt(); 
 
			switch(ordre) { 
				case 1: skrivOversikt();break; 
				case 2: registrerNyLeietager();break; 
				case 3: registrerBetaling();break; 
				case 4: registrerUtflytting();break;
				case 5: manedsKjoringHusleie();break;
				case 6: kastUtLeietagere();break;
				case 7: hoyneHusleie();break;
				case 8: avslutt();
						ordre = 0;
						break;
				default: System.out.println("Tast inn et tall mellom 1 og 8.");
			}
		}
	}

	// Metoder for de forskjellige ordrene i menylokke() 
	void skrivOversikt() {
		skriv.out("\n" + "Hybel",7);
		skriv.out("Leietager",22);
		skriv.out("Saldo",7,Out.RIGHT);
		skriv.out("\n----- --------------------- -------");

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {
				skriv.out("\n" + (etg + 1) + "" + (char)(rom + 'A'),7);

				if (hybler[etg][rom].leietager.navn.equals(TOM_HYBEL)) {
					skriv.out("( LEDIG )",22);

				} else {
				skriv.out(hybler[etg][rom].leietager.navn,22);	
				}
				skriv.out(hybler[etg][rom].leietager.saldo,7,Out.RIGHT);
			}
		}

		skriv.out("\n\nManed/ar, og driftstid:",27);
		skriv.out(maned + "/" + ar + ", " + totalAntallManeder + " mnd. i drift");
		skriv.out("\nTotalfortjeneste:",26);
		skriv.out(totalfortjeneste + " kr");
	}

	void registrerNyLeietager() {

		String[] ledigHybel = new String[18];
		int ledigHybler = 0;
		int romID = 0;
		String hybelNavn;

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {

				if((hybler[etg][rom].leietager.navn).equals(TOM_HYBEL)) {
					hybelNavn = ((etg + 1) + "" + ((char)(rom + 'A')));
					ledigHybel[ledigHybler] = hybelNavn;
					ledigHybler++;
				}
			}
		}

		if(ledigHybler == 0) {
			skriv.out("\nDet finnes ingen ledige hybler");

		} else {
			skriv.out("\nDet finnes " + ledigHybler + " ledige hybler: ");
			skriv.out("\n---------------------------");

			for (int i = 0; i < ledigHybler; i++) {
				skriv.out("\n" + ledigHybel[i]);
			}

			skriv.out("\n\nHvilken hybel er onsket av kunden: ");
			String onsketHybel = tast.inWord().toUpperCase();


			for (int i = 0; i < ledigHybler; i++) {

				if (onsketHybel.equals(ledigHybel[i])) {
					romID++;
				}	
			}

			if (romID == 0) {
				skriv.out("\nRom: " + onsketHybel + " er ikke ledig");

			} else {
				skriv.out("\nRommet er ledig" + "\nOppgi navn: ");
				String nyLeietager = tast.inLine();

				for (int etg = 0; etg <= 2; etg++) {

					for (int rom = 0; rom <= 5; rom++) {
						hybelNavn = ((etg + 1) + "" + ((char)(rom + 'A')));

						if (onsketHybel.equals(hybelNavn)) {
							hybler[etg][rom].leietager.navn = nyLeietager;

							if (etg == 2) {
								hybler[etg][rom].leietager.saldo = (15000 - manedsleieToppEtasjeHybel);
								totalfortjeneste = totalfortjeneste + manedsleieToppEtasjeHybel;

							} else {
								hybler[etg][rom].leietager.saldo = (15000 - manedsleieVanligHybel);
								totalfortjeneste = totalfortjeneste + manedsleieVanligHybel;
							}
						}
					}
				}
			}
		}
	} 
	void registrerBetaling() {

		skriv.out("\nOppig hybelnavn til saldo: ");
		String onsketHybel = tast.inWord().toUpperCase();

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {
				String hybelNavn = ((etg + 1) + "" + ((char)(rom + 'A')));

				if (onsketHybel.equals(hybelNavn)) {

					if (hybler[etg][rom].leietager.navn.equals(TOM_HYBEL)) {
						skriv.out("\nHybelen er tom, venligst velg en annen hybel");

					} else {
						skriv.out("\nOppgi belop som skal legges til " + (hybler[etg][rom].leietager.navn) + ": ");
						int belop = tast.inInt();
						hybler[etg][rom].leietager.saldo = ((hybler[etg][rom].leietager.saldo) + belop);
						skriv.out("\n" + belop + " kr er lagt til saldo, " + (hybler[etg][rom].leietager.navn) + " har na " + (hybler[etg][rom].leietager.saldo) + " kr pa saldo");
					}
				}
			}
		}
	} 
	void registrerUtflytting() {
		skriv.out("\nOppig navn til leietager: ");
		String sokNavn = tast.inLine().toLowerCase();
		int feilmelding = 0;
		int feilmelding2 = 0;

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {
				String hybelNavn = ((etg + 1) + "" + ((char)(rom + 'A')));

				if (sokNavn.equals(hybler[etg][rom].leietager.navn.toLowerCase())) {
					feilmelding++;

					if (hybler[etg][rom].leietager.saldo >= 0) {
						hybler[etg][rom].leietager.navn = TOM_HYBEL;
						skriv.out("Restbelop pa saldo som refunderes: " + hybler[etg][rom].leietager.saldo + " kr");
						hybler[etg][rom].leietager.saldo = 0;
						feilmelding2++;
						skriv.out("Utflyttning fullfort");
					}
				}
			}
		}

		if (feilmelding == 0) {
			skriv.out("Navnet " + sokNavn + " finnes ikke i dette leiekomplekse");

		} else if (feilmelding2 == 0) {
			skriv.out(sokNavn + " er i underskudd pa saldo, bruk ordre 6: Kast ut leietager");
		}
	} 
	void manedsKjoringHusleie() {
		skriv.out("onsker du a utfore manedskjoring for maned " + maned + "/" + ar + " (j/n)?\n");
		String onske = tast.inLine().toLowerCase();
		int mandesfortjeneste = 0;
		int utgifterHybel = 1200 * 18;
		int utgifterFellesareal = 1700 * 3;

		if (onske.equals("n")) {
			skriv.out("Mandeskjoring avbrutt");

		} else if (onske.equals("j")) {
			maned++;
			totalAntallManeder++;

			if (maned > 12) {
				maned = 1;
				ar++;
			}

			totalfortjeneste = (totalfortjeneste - utgifterHybel - utgifterFellesareal);
			mandesfortjeneste = (mandesfortjeneste - utgifterHybel - utgifterFellesareal);

			for (int etg = 0; etg <= 2; etg++) {

				for (int rom = 0; rom <= 5; rom++) {
					String hybelNavn = ((etg + 1) + "" + ((char)(rom + 'A')));

					if (!(hybler[etg][rom].leietager.navn).equals(TOM_HYBEL)) {

						if (etg == 2) {

							if (hybler[etg][rom].leietager.saldo < manedsleieToppEtasjeHybel) {

								if (hybler[etg][rom].leietager.saldo > 0) {
									totalfortjeneste = totalfortjeneste + hybler[etg][rom].leietager.saldo;
									mandesfortjeneste = mandesfortjeneste + hybler[etg][rom].leietager.saldo;
									hybler[etg][rom].leietager.saldo = hybler[etg][rom].leietager.saldo - manedsleieToppEtasjeHybel;
								
								} else {
									hybler[etg][rom].leietager.saldo = hybler[etg][rom].leietager.saldo - manedsleieToppEtasjeHybel;
								}

							} else {
								totalfortjeneste = totalfortjeneste + manedsleieToppEtasjeHybel;
								mandesfortjeneste = mandesfortjeneste + manedsleieToppEtasjeHybel;
								hybler[etg][rom].leietager.saldo = (hybler[etg][rom].leietager.saldo - manedsleieToppEtasjeHybel);
							}

						} else {

							if (hybler[etg][rom].leietager.saldo < manedsleieVanligHybel) {

								if (hybler[etg][rom].leietager.saldo > 0) {
									totalfortjeneste = totalfortjeneste + hybler[etg][rom].leietager.saldo;
									mandesfortjeneste = mandesfortjeneste + hybler[etg][rom].leietager.saldo;
									hybler[etg][rom].leietager.saldo = hybler[etg][rom].leietager.saldo - manedsleieVanligHybel;
								
								} else {
									hybler[etg][rom].leietager.saldo = hybler[etg][rom].leietager.saldo - manedsleieVanligHybel;
								}

							} else {
								totalfortjeneste = totalfortjeneste + manedsleieVanligHybel;
								mandesfortjeneste = mandesfortjeneste + manedsleieVanligHybel;
								hybler[etg][rom].leietager.saldo = (hybler[etg][rom].leietager.saldo - manedsleieVanligHybel);
							}
						}
					}
				}
			}

			skriv.out("\nMandeskjoring for " + maned + "/" + ar + " er gjennomkjort"
						+ "\nSystemet har vert i drift i " + totalAntallManeder + " mnd"
						+ "\nHusleiesatsene for 1. og 2. etg er: " + manedsleieVanligHybel + " kr"
						+ "\nHusleiesatsen for 3. etg er: " + manedsleieToppEtasjeHybel + " kr"
						+ "\nManedens fortjeneste er: " + mandesfortjeneste + " kr"
						+ "\nTotal fortjeneste er: " + totalfortjeneste + " kr"
						+ "\nGjennomsnillitg manedsfortjeneste: " + (totalfortjeneste / totalAntallManeder) + " kr");

		} else {
			skriv.out("\nIkke gyldig svar, prov igjen");
		}
	} 
	void kastUtLeietagere() {
		int kastID = 0;

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {
				String hybelNavn = ((etg + 1) + "" + ((char)(rom + 'A')));

				if (etg == 2) {

					if (hybler[etg][rom].leietager.saldo < (0 - manedsleieToppEtasjeHybel)) {
						totalfortjeneste = (totalfortjeneste + 1500 - hybler[etg][rom].leietager.saldo);
						int krav = -hybler[etg][rom].leietager.saldo;
						tilkallHole(etg, rom, krav);
						hybler[etg][rom].leietager.saldo = 0;
						hybler[etg][rom].leietager.navn = TOM_HYBEL;
						kastID++;
					}

				} else {

					if (hybler[etg][rom].leietager.saldo < (0 - manedsleieVanligHybel)) {
						totalfortjeneste = (totalfortjeneste + 1500 - hybler[etg][rom].leietager.saldo);
						int krav = -hybler[etg][rom].leietager.saldo;
						tilkallHole(etg, rom, krav);
						hybler[etg][rom].leietager.saldo = 0;
						hybler[etg][rom].leietager.navn = TOM_HYBEL;
						kastID++;
					}
				}
			}
		}
		if (kastID == 0) {
			skriv.out("\nIngen leietagere hadde stort nok krav til a kastes ut");
		}
	} 

	void tilkallHole(int etasje, int rom, int krav) {
		String hybelNavn = ((etasje + 1) + "" + ((char)(rom + 'A')));
		Out torpedo = new Out("torpedo.txt", true);

		skriv.out("Rom: " + hybelNavn + " Leietager: " + hybler[etasje][rom].leietager.navn + " Krav: " + krav + "\n");
		torpedo.out("Rom: " + hybelNavn + " Leietager: " + hybler[etasje][rom].leietager.navn + " Krav: " + krav + "\n");
		torpedo.close();
	}
	void hoyneHusleie() {
		skriv.out("Din na verende husleie for 1. og 2. etasje er: " + manedsleieVanligHybel + " kr\n"
					+ "Oppgi ny manedsleie: ");
		manedsleieVanligHybel = tast.inInt();
		skriv.out("Din na verende husleie for 3. etasje er: " + manedsleieToppEtasjeHybel + " kr\n"
					+ "Oppgi ny manedsleie: ");
		manedsleieToppEtasjeHybel = tast.inInt();
		skriv.out("Manedsleien er endret");
	}
	void avslutt() {
		Out hybelfil = new Out(FILNAVN);

		hybelfil.out(maned + ";" + ar + ";" + totalfortjeneste + ";" + totalAntallManeder + ";" + manedsleieVanligHybel + ";" + manedsleieToppEtasjeHybel + ";\n");

		for (int etg = 0; etg <= 2; etg++) {

			for (int rom = 0; rom <= 5; rom++) {
				char hybelNavn = (char)(rom + 'A');
				int etgPrint = etg + 1;

				hybelfil.out(etgPrint + ";" + hybelNavn + ";" + hybler[etg][rom].leietager.saldo + ";" + hybler[etg][rom].leietager.navn + "\n");
			}
		}
		hybelfil.close();
	}
}// end class Utsyn 