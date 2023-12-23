class Oblig1 {
	public static void main(String[] args) {
		Test kjor = new Test();
		kjor.testPerson();
	}
}

class Person {
	private String navn;
	private Person [] kjenner;
	private Person [] likerIkke;
	// likerIkke = uvenner
	// venner blir da alle personer som pekes på av kjenner
	// unntatt personen(e) som pekes på av likerikke
	private Person forelsketI;
	Person (String n, int lengde) {
		navn = n;
		kjenner = new Person[lengde];
		likerIkke = new Person[lengde];
	}

	public String hentNavn() {
		return navn;
	}

	public boolean erUvennMed (Person p) {
		// Sann hvis dana er uvenn med personen 

		for (int i = 0; i < 3; i++) {
			if (likerIkke[i] == p) {
				return true;
			}
		}
		return false;
	}

	public boolean erKjentMed (Person p) {
		// Sann hvis Dana kjenner personen p peker på.

		for (int i = 0; i < 3; i++) {
			if (kjenner[i] == p) {
				return true;
			}
		}
		return false;
	}

	public void blirKjentMed (Person p) {
		// Dana blir kjent med p, bortsett fra hvis p peker
		// på Dana (Dana kan ikke være kjent med seg selv).

		if (!navn.equals(p.navn)) {
			for (int i = 0; i < 3; i++) {
				if (!erKjentMed(p) && (kjenner[i] == null)) {
					kjenner[i] = p;
				}
			}
		}
	}

	public void blirForelsketI (Person p) {
		// Dana blir forelsket i p, bortsett fra hvis p peker på Dana

		if (!navn.equals(p.navn)) {
			forelsketI = p;
		}
	}

	public void blirUvennMed (Person p) {
		// Dana blir uvenn med p, bortsett fra hvis p peker på Dana

		if (!navn.equals(p.navn)) {
			for (int i = 0; i < 3; i++) {
				if ((!erUvennMed(p)) && (likerIkke[i] == null)) {
					likerIkke[i] = p;
				}
			}
		}
	}

	public boolean erVennMed (Person p) {
		// returnerer sann hvis Dana kjenner p og ikke er uvenner med p

		if (erKjentMed(p) && !erUvennMed(p)) {
			return true;
		} else {
			return false;
		}
	}

	public void blirVennMed (Person p) {
		// samme virkning som blirKjentMed(p), men hvis Dana ikke
		// liker p dvs. (likerikke[i] == p) for en gitt i
		// blir likerikke[i] satt til null.

		if (erUvennMed(p)) {
			for (int i = 0; i < 3; i++) {
				if (likerIkke[i] == p) {
					likerIkke[i] = null;
				}
			}	
		}
		blirKjentMed(p);
	}

	public void skrivUtVenner() {
		// skriver ut navnet på dem Dana kjenner, unntatt dem hun ikke liker.

		for (Person p: kjenner) {
			if (erVennMed(p)) {
				System.out.println(p.navn);
			}
		}
	}

	public Person hentBestevenn() {
		// returtypen skal du bestemme
		// returnerer en peker til Danas bestevenn.
		// En persons bestevenn er for enkelhets skyld definert til å være
		// det objektet som pekes på av kjennerarrayens indeks 0.

		return kjenner[0];
	}

	public int antVenner() {
		// returnerer hvor mange venner Dana har
		// Disse metodene trenger du ikke lage selv, men gjør det gjerne for
		// øvelsens skyld:

		int v = 0;

		for (int i = 0; i < 3; i++) {
			if (erVennMed(kjenner[i])) {
				v++;
			}
		}

		return v;
	}

	public Person[] hentVenner() {
		// returnerer en array som peker på Danas venner
		// Arrayen skal være akkurat så lang at lengden er lik antallet venner,
		// og rekkefølgen skal være den samme som i kjenner-arrayen.
		int plass = 0;
		Person[] venner = new Person[antVenner()];

		for (int i = 0; i < 3; i++) {
			if (erVennMed(kjenner[i])) {
				venner[plass] = kjenner[i];
				plass++;
			}
		}
		return venner;
	}
	
	public void skrivUtKjenninger() {

		for (Person p: kjenner) {
			if (p!=null) System.out.print(p.hentNavn() + " ");
		}
		System.out.println("");
	}

	public void skrivUtLikerIkke() {

		for (Person p: likerIkke) {
			if (p!=null) System.out.print(p.hentNavn() + " ");
		}
		System.out.println("");
	}

	public void skrivUtAltOmMeg() {

		System.out.print(navn + " kjenner: ");
		skrivUtKjenninger();

		if (forelsketI != null) {
			System.out.println(navn + " er forelsket i: " + forelsketI.hentNavn());
		}
		if (likerIkke[0] != null){
			System.out.print(navn + " liker ikke: ");
			skrivUtLikerIkke();
		}
	}
}

class Test {

		public void testPerson() {

		Person lars = new Person("Lars", 3);
		Person ask = new Person("Ask", 3);
		Person dana = new Person("Dana", 3);
		Person tom = new Person("Tom", 3);

		System.out.println();

		lars.blirKjentMed(ask);
		lars.blirKjentMed(dana);
		lars.blirKjentMed(tom);

		ask.blirKjentMed(lars);
		ask.blirKjentMed(dana);
		ask.blirKjentMed(tom);
		ask.blirForelsketI(lars);
		ask.blirUvennMed(dana);
		ask.blirUvennMed(tom);

		dana.blirKjentMed(ask);
		dana.blirKjentMed(tom);
		dana.blirKjentMed(lars);
		dana.blirUvennMed(lars);
		dana.blirForelsketI(tom);

		tom.blirKjentMed(lars);
		tom.blirKjentMed(ask);
		tom.blirKjentMed(dana);
		tom.blirUvennMed(ask);
		tom.blirUvennMed(lars);
		tom.blirForelsketI(dana);

		lars.skrivUtAltOmMeg();
		ask.skrivUtAltOmMeg();
		dana.skrivUtAltOmMeg();
		tom.skrivUtAltOmMeg();
	}
}