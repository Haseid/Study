// Samarbeidet med Johannes Christensen (Johannec) og William Haugan (Williha) og litt med Karim
import easyIO.*;
import java.util.*;

class Oblig5 {
	public static void main(String[] args) {
		Planlegger plan = new Planlegger();
		plan.lesFil();
		plan.lesFraOgTil();
	}
}

class Planlegger {
	In tast = new In();
	Out skriv = new Out();
	String fraStasjon;
	String tilStasjon;

	HashMap <String,Stasjon> stasjoner = new HashMap <String,Stasjon>();
	HashMap <Integer,Linje> linjer = new HashMap <Integer,Linje>();

	void lesFil() {

		In fil = new In("TrikkOgTbane.txt");
		Linje l = null;

		while (!fil.endOfFile()) {
			String ord = fil.inWord();

			if (ord.equals("*Linje*")) {
				int linjeNr = fil.inInt();
				l = new Linje(linjeNr);
				linjer.put(linjeNr, l);

			} else {

				if (stasjoner.containsKey(ord)) {
					Stasjon s = stasjoner.get(ord);
					s.linjeArr.add(l);
					l.stasjonArr.add(s);

				} else {
					Stasjon s = new Stasjon(ord);
					s.linjeArr.add(l);
					l.stasjonArr.add(s);
					stasjoner.put(ord, s);
				}
			}
		}
	}

	void lesFraOgTil() {
		int valgFeil1 = 0;
		int valgFeil2 = 0;
		Stasjon s1 = null;
		Stasjon s2 = null;

		while(valgFeil1 == 0) {
			skriv.out("Stasjon du reiser fra (eks: Carl-Berners-plass): ");
			fraStasjon = tast.inWord();

			if (stasjoner.containsKey(fraStasjon) && !(fraStasjon.equals("Ringen"))) {
				valgFeil1++;

				while (valgFeil2 == 0) {
					skriv.out("Skriv inn ditt reisemal: ");
					tilStasjon = tast.inWord();

					if (stasjoner.containsKey(tilStasjon) && !(fraStasjon.equals(tilStasjon)) && !(tilStasjon.equals("Ringen"))) {
						s1 = stasjoner.get(fraStasjon);
						s2 = stasjoner.get(tilStasjon);
						beregnRuter(s1, s2);
						valgFeil2++;

					} else {
						skriv.out("Stasjonen ikke gyldig\n");
					}
				}

			} else {
				skriv.out("Stasjonen ikke gyldig\n");
			}
		}
	}

	String transportType(int linje) {
		if (linje <= 6) {
			return "t-bane";
		} else {
			return "trikke";
		}
	}

	String retning (Stasjon fra, Stasjon til) {
		return null;
	} 

	void beregnRuter(Stasjon fraSt, Stasjon tilSt)	{
		int[] linjeFra = new int [12];
		int[] linjeTil = new int [12];
		Linje L1 = null;
		Linje L2 = null;
		int enkel =0;
		String endestasjon;
		String endestasjon2;
		int stasjonPlass1 = 0;
		int stasjonPlass2 = 0;

		for (int i = 0; i < fraSt.linjeArr.size(); i++) {
			L1 = fraSt.linjeArr.get(i);
			linjeFra[i] = L1.linjeNr;

			for (int j = 0; j < tilSt.linjeArr.size(); j++) {
				L2 = tilSt.linjeArr.get(j);
				linjeTil[j] = L2.linjeNr;

				while (linjeFra[i] == linjeTil[j] && enkel == 0) {

					
					for (int k = 0; k < L1.stasjonArr.size(); k++) {

						if ((L1.stasjonArr.get(k).stasjonsNavn).equals(fraStasjon)) {
							stasjonPlass1 = k;
						}

						if ((L2.stasjonArr.get(k).stasjonsNavn).equals(tilStasjon)) {
							stasjonPlass2 = k;
						}
					}

					if (stasjonPlass1 > stasjonPlass2) {
						endestasjon = (L2.stasjonArr.get(0).stasjonsNavn);
						
					} else {
						endestasjon = (L2.stasjonArr.get(L2.stasjonArr.size() -1).stasjonsNavn);

					}

					double tid = 0;
					double tid2 = 0;

					if (L2.linjeNr > 10 && stasjonPlass1 > stasjonPlass2) {
						stasjonPlass1 += 1;
						stasjonPlass2 += 1;
						tid = (stasjonPlass1 - stasjonPlass2) * 1.4;

					} else if (L2.linjeNr > 10 && stasjonPlass1 < stasjonPlass2) {
						stasjonPlass1 += 1;
						stasjonPlass2 += 1;
						tid = (stasjonPlass2 - stasjonPlass1) * 1.4;

					} else if (L2.linjeNr < 10 && stasjonPlass1 > stasjonPlass2) {
						stasjonPlass1 += 1;
						stasjonPlass2 += 1;
						tid = (stasjonPlass1 - stasjonPlass2) * 1.8;

					} else if (L2.linjeNr < 10 && stasjonPlass1 < stasjonPlass2) {
						stasjonPlass1 += 1;
						stasjonPlass2 += 1;
						tid = (stasjonPlass2 - stasjonPlass1) * 1.8;
					}

					String q = Format.center(tid,6,1);
					skriv.outln("\nDu kan ta " + transportType(linjeFra[i]) + " linje " + linjeFra[i] + " fra " + fraStasjon + " til stasjon " + tilStasjon + " i retning " + endestasjon + "\nEstimert reisetid: " + q);
					enkel++;
				}
			}
		}

		if (enkel == 0) {

			double kortest = 9999;
			String kFraStasjon = null;
			String kTilStasjon = null;
			String kMellomStasjon = null;
			String kType = null;
			String kType2 = null;
			String retning1 = null;
			String retning2 = null;
			int kLinje1 = 0;
			int kLinje2 = 0;
			String z = null;			

			for(int i = 0; i < fraSt.linjeArr.size(); i++) {
				Linje l = linjer.get(linjeFra[i]); //Finner stajsoner i linjene //Første linje

				for(int j = 0; j < l.stasjonArr.size(); j++) {
					Stasjon s = l.stasjonArr.get(j); // Finner linjene i stajsoene //Byttestasjonen

					for(int k = 0; k < s.linjeArr.size(); k++) {
						Linje l2 = s.linjeArr.get(k); //Andre linje

						for(int m = 0; m < l2.stasjonArr.size(); m++) {

							if(l2.stasjonArr.get(m).stasjonsNavn.equals(tilSt.stasjonsNavn)) {
								double tid = 0;
//-----------------------------------------------------------------------------------------------------------------------
								for (int n = 0; n < l.stasjonArr.size(); n++) {

									if ((l.stasjonArr.get(n).stasjonsNavn).equals(fraSt.stasjonsNavn)) {
										stasjonPlass1 = n;
									}
			
									if ((l.stasjonArr.get(n).stasjonsNavn).equals(s.stasjonsNavn)) {
										stasjonPlass2 = n;
									}
								}
			
								if (stasjonPlass1 > stasjonPlass2) {
									endestasjon = (l.stasjonArr.get(0).stasjonsNavn);
						
								} else {
									endestasjon = (l.stasjonArr.get(l.stasjonArr.size() -1).stasjonsNavn);
								}
//-----------------------------------------------------------------------------------------------------------------------
								if (l.linjeNr > 10 && stasjonPlass1 > stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid = (stasjonPlass1 - stasjonPlass2) * 1.4;

								} else if (l.linjeNr > 10 && stasjonPlass1 < stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid = (stasjonPlass2 - stasjonPlass1) * 1.4;

								} else if (l.linjeNr < 10 && stasjonPlass1 > stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid = (stasjonPlass1 - stasjonPlass2) * 1.8;

								} else if (l.linjeNr < 10 && stasjonPlass1 < stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid = (stasjonPlass2 - stasjonPlass1) * 1.8;
								}
								stasjonPlass1 = 0;
								stasjonPlass2 = 0;
//-----------------------------------------------------------------------------------------------------------------------
								for (int o = 0; o < l2.stasjonArr.size(); o++) {

									if ((l2.stasjonArr.get(o).stasjonsNavn).equals(s.stasjonsNavn)) {
										stasjonPlass1 = o;
									}
			
									if ((l2.stasjonArr.get(o).stasjonsNavn).equals(tilSt.stasjonsNavn)) {
										stasjonPlass2 = o;
									}
								}
			
								if (stasjonPlass1 > stasjonPlass2) {
									endestasjon2 = (l2.stasjonArr.get(0).stasjonsNavn);
						
								} else {
									endestasjon2 = (l2.stasjonArr.get(l2.stasjonArr.size() -1).stasjonsNavn);
								}
//------------------------------------------------------------------------------------------------------------------
								if (l2.linjeNr > 10 && stasjonPlass1 > stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid += (stasjonPlass1 - stasjonPlass2) * 1.4;

								} else if (l2.linjeNr > 10 && stasjonPlass1 < stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid += (stasjonPlass2 - stasjonPlass1) * 1.4;

								} else if (l2.linjeNr < 10 && stasjonPlass1 > stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid += (stasjonPlass1 - stasjonPlass2) * 1.8;
									
								} else if (l2.linjeNr < 10 && stasjonPlass1 < stasjonPlass2) {
									stasjonPlass1 += 1;
									stasjonPlass2 += 1;
									tid += (stasjonPlass2 - stasjonPlass1) * 1.8;
								}
//------------------------------------------------------------------------------------------------------------------------
								tid += 3;
								if (l2.linjeNr > 10) {
									tid += 5;
								} else if (l2.linjeNr < 10) {
									tid += 7.5;
								}
								String q = Format.center(tid,6,1);

								skriv.outln("\nDu kan ta " + transportType(linjeFra[i]) + " linje " + l.linjeNr + " fra "
											+ fraSt.stasjonsNavn + " til " + s.stasjonsNavn + " mot " + endestasjon
											+ ", bytter der til " + transportType(l2.linjeNr) + " linje " + l2.linjeNr + " fra " + s.stasjonsNavn + " til "
											+ tilSt.stasjonsNavn + " mot " + endestasjon2 + "\nEstimert reisetid: " + q + "min");

								if (kortest > tid) {
									kortest = tid;
									kFraStasjon = fraSt.stasjonsNavn;
									kTilStasjon = tilSt.stasjonsNavn;
									kMellomStasjon = s.stasjonsNavn;
									kType = transportType(linjeFra[i]);
									kType2 = transportType(l2.linjeNr);
									retning1 = endestasjon;
									retning2 = endestasjon2;
									kLinje1 = l.linjeNr;
									kLinje2 = l2.linjeNr;
								}
							}
						}
					}
				}
			}
			z = Format.center(kortest,6,1);
			
			skriv.outln("\n\n--------------------------------------------------------------------------------"
						+ "Raskeste rute: \n" + "Du kan ta " + kType + " linje " + kLinje1 + " fra "
						+ kFraStasjon + " til " + kMellomStasjon + " mot " + retning1
						+ ", bytter der til " + kType2 + " linje " + kLinje2 + " fra " + kMellomStasjon + " til "
						+ kTilStasjon + " mot " + retning2 + "\nEstimert reisetid: " + z + "min"
						+ "\n--------------------------------------------------------------------------------\n");
						
		}
	}
}

class Linje {
	int linjeNr;
	ArrayList<Stasjon> stasjonArr = new ArrayList<Stasjon>();

	Linje(int linjeNr) {
	this.linjeNr = linjeNr;
	}
}

class Stasjon {
	String stasjonsNavn;
	ArrayList<Linje> linjeArr = new ArrayList<Linje>();

	Stasjon(String stasjonsNavn){
		this.stasjonsNavn = stasjonsNavn;
	}
}

class Overgang {

}