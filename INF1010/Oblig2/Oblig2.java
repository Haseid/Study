class Oblig2 {
	public static void main(String[] args) {
		Test kjor = new Test();
		kjor.testPerson();
		kjor.gavmild();
		kjor.upopulereTing();
	}
}

class Person {
	private String navn;
	private Person [] kjenner;
	private Person [] likerIkke;
	// likerIkke = uvenner
	// venner blir da alle personer som pekes på av kjenner
	// unntatt personen(e) som pekes på av likerikke

	private Bok [] boker;
	private Plate [] plater;
	private Person forelsketI;
	private String artistIntressert;
	private int arstallIntressert;

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

		for (int i = 0; i < likerIkke.length; i++) {
			if (likerIkke[i] == p) {
				return true;
			}
		}
		return false;
	}

	public boolean erKjentMed (Person p) {
		// Sann hvis Dana kjenner personen p peker på.

		for (int i = 0; i < kjenner.length; i++) {
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
			for (int i = 0; i < kjenner.length; i++) {
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

		if (!navn.equals(p.navn) && !erUvennMed(p)) {
			for (int i = 0; i < likerIkke.length; i++) {
				if (likerIkke[i] == null) {
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
			for (int i = 0; i < likerIkke.length; i++) {
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

		for (int i = 0; i < kjenner.length; i++) {
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

		for (int i = 0; i < kjenner.length; i++) {
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

	public void samlerAv (String smlp, int ant) {
		// Det opprettes en ny array i personobjektet for å samle på
		// - bøker hvis parameteren smlp er boker
		// - plater hvis parameteren smlp er plater
		// ant er antall bøker/CD-er som personen kan samle på
		// ingenting gjøres hvis smlp peker på noe annet

		if (smlp.equals("boker")) {
			boker = new Bok[ant];
		} else if (smlp.equals("plater")) {
			plater = new Plate[ant];
		}
	}

	public void megetInteressertI (String artist) {
		// merker personobjektet som særlig interessert
		// i plater av artisten med dette navnet hvis personen samler på plater

		for (int i = 0; i < plater.length; i++) {
			if (plater[i] != null) {
				artistIntressert = artist;
			}
		}
	}

	public void megetInteressertI (int eldreEnn){
		// merker personobjektet som særlig interessert
		// i bøker som er eldre enn årstallet parameteren angir hvis personen
		// er samler av bøker

		for (int i = 0; i < boker.length; i++) {
			if (boker[i] != null) {
				arstallIntressert = eldreEnn;
			}
		}
	}

	public void samleBok (String forfatter, String tittel, int ar) {
		for (int i = 0; i < boker.length; i++) {
			if (boker[i] == null) {
				boker[i] = new Bok(forfatter, tittel, ar);
				break;
			}
		}
	}

	public void samlePlate (String artist, String tittel, int spor) {
		for (int i = 0; i < plater.length; i++) {
			if (plater[i] == null) {
				plater[i] = new Plate(artist, tittel, spor);
				break;
			}
		}
	}

	public Bok vilDuHaGaven(Bok gave) {
		int storrelse = 0;
		double halv;
		Bok p;
		if (boker != null) {
			halv = boker.length/2;
			for (int i = 0; i <boker.length; i++ ) {
				if (boker[i] != null) {
					if (gave.hentUtTittel().equals(boker[i].hentUtTittel())) {
						return gave;
					}
				} else {
					storrelse++;
				}
			}
			if ((halv > storrelse) || (gave.hentUtar() < arstallIntressert)) {
				if (storrelse == 0) {
					for (int i = 0; i < boker.length; i++ ) {
						if (gave.hentUtar() > boker[i].hentUtar()) {
							p = boker[i];
							boker[i] = gave;
							return p;
						}
					}
				} else {
					for (int i = 0; i < boker.length; i++ ) {
						if (boker[i] == null) {
							boker[i] = gave;
							return null;
						}
					}
				}
			}
		}
		return gave;
	}

	public Plate vilDuHaGaven(Plate gave) {
		int storrelse = 0;
		double halv;
		Plate p;
		if (plater != null) {
			halv = plater.length/2;
			for (int i = 0; i < plater.length; i++ ) {
				if (plater[i] != null) {
					if (gave.hentUtPTittel().equals(plater[i].hentUtPTittel())) {
						return gave;
					}
				} else {
					storrelse++;
				}
			}
			if ((halv > storrelse) || (gave.hentUtArtist().equals(artistIntressert))) {
				if (storrelse == 0) {
					for (int i = 0; i < plater.length; i++ ) {
						if (gave.hentUtArtist().equals(plater[i].hentUtArtist())) {
							p = plater[i];
							plater[i] = gave;
							return p;
						}
					}
				} else {
					for (int i = 0; i < plater.length; i++ ) {
						if (plater[i] == null) {
							plater[i] = gave;
							return null;
						}
					}
				}
			}
		}
		return gave;
	}
}

class Test {
	private Person [] allePersoner = new Person[7];
	private Bok [] enMengdeBoker = new Bok[8];
	private Plate [] enMengdePlater = new Plate[8];

	public void gavmild() {
		for (int i = 0; i < enMengdeBoker.length; i++) {
			for (int j = 0; j < allePersoner.length; j++) {
				if (enMengdeBoker[i] != null) {
					enMengdeBoker[i] = allePersoner[j].vilDuHaGaven(enMengdeBoker[i]);
				}
				if (enMengdePlater[i] != null) {
					enMengdePlater[i] = allePersoner[j].vilDuHaGaven(enMengdePlater[i]);
				}
			}
		}
	}

	public void upopulereTing() {
		System.out.println("\nBoker og plater ingen vil ha:");
		for (Bok p : enMengdeBoker) {
			if (p != null) {
				p.skrivUtBok();
			}
		}
		for (Plate p : enMengdePlater) {
			if (p != null) {
				p.skrivUtPlate();
			}
		}
	}

	public void testPerson() {
		Person lars = new Person("Lars", 3);
		Person ask = new Person("Ask", 3);
		Person dana = new Person("Dana", 3);
		Person tom = new Person("Tom", 3);
		Person bob = new Person("Bob", 3);
		Person rayan = new Person("Rayan", 3);
		Person phil = new Person("Phil", 3);

		lars.samlerAv("boker", 5);
		lars.samlerAv("plater", 5);
		lars.samleBok("Terje", "Rett pa java", 2008);
		lars.samlePlate("Queen", "Autumm", 11);
		lars.megetInteressertI("Queen");
		lars.megetInteressertI(1946);

		ask.samlerAv("boker", 5);
		ask.samlerAv("plater", 5);
		ask.samleBok("Annekatt", "Aurora", 1984);
		ask.samlePlate("ABBA", "Waterlo", 12);
		ask.megetInteressertI("Silya Nymoen");

		dana.samlerAv("boker", 5);
		dana.samlerAv("plater", 5);
		dana.samleBok("Platon", "Menon", 1857);
		dana.samlePlate("Marianne", "Lys", 12);
		dana.megetInteressertI(1900);

		tom.samlerAv("plater", 5);
		tom.samlePlate("Marianne", "Lys", 12);
		tom.samlePlate("Chris", "Castle", 8);
		tom.samlePlate("Bob Dylan", "Raindrops", 15);
		tom.megetInteressertI("Bob Dylan");

		bob.samlerAv("boker", 5);
		bob.samleBok("Mikkel", "Rose", 2004);
		bob.samleBok("Trond", "Stein", 1987);
		bob.samleBok("Erik", "Bilde", 2012);

		rayan.samlerAv("plater", 5);
		rayan.samlePlate("Marianne", "Lys", 12);
		rayan.samlePlate("Queen", "Autumm", 8);
		rayan.samlePlate("Bob Dylan", "Raindrops", 15);
		rayan.megetInteressertI("Maria");

		allePersoner[0] = lars;
		allePersoner[1] = ask;
		allePersoner[2] = dana;
		allePersoner[3] = tom;
		allePersoner[4] = bob;
		allePersoner[5] = rayan;
		allePersoner[6] = phil;

		enMengdeBoker[0] = new Bok("Tolken", "lotr", 1954);
		enMengdeBoker[1] = new Bok("Bertild", "Steinen", 1930);
		enMengdeBoker[2] = new Bok("Tore", "En pike", 2002);
		enMengdeBoker[3] = new Bok("Gjessing", "Gjess", 1842);
		enMengdeBoker[4] = new Bok("Andreas", "Flasken", 1922);
		enMengdeBoker[5] = new Bok("William", "Bobby", 1857);
		enMengdeBoker[6] = new Bok("Gisle", "Atlas", 2012);
		enMengdeBoker[7] = new Bok("Johannes", "Rett pa Java", 1586);

		enMengdePlater[0] = new Plate("Queen", "Rock", 9);
		enMengdePlater[1] = new Plate("Maria", "Synger i regn", 2);
		enMengdePlater[2] = new Plate("Johannes", "Altid en bad day", 188);
		enMengdePlater[3] = new Plate("Silya Nymoen", "Green", 22);
		enMengdePlater[4] = new Plate("Bob Dylan", "Going back", 15);
		enMengdePlater[5] = new Plate("Queen", "Paper", 17);
		enMengdePlater[6] = new Plate("B.S", "Aqua", 12);
		enMengdePlater[7] = new Plate("Smurfene", "Smurfehits", 8);

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

		/*
		lars.skrivUtAltOmMeg();
		ask.skrivUtAltOmMeg();
		dana.skrivUtAltOmMeg();
		tom.skrivUtAltOmMeg();
		*/
	}
}

class Bok {
	private String bokforfatter;
	private String boktittel;
	private int utgivelsesar;

	Bok(String forfatter, String tittel, int ar) {
		bokforfatter = forfatter;
		boktittel = tittel;
		utgivelsesar = ar;
	}

	public int hentUtar() {
		return utgivelsesar;
	}
	public String hentUtTittel() {
		return boktittel;
	}

	public void skrivUtBok() {
		System.out.println(bokforfatter + ", " + boktittel + ", " + utgivelsesar);
	}
}

class Plate {
	private String plateArtistnavn;
	private String platetittel;
	private int antPlatespor;

	Plate (String artistnavn, String tittel, int antSpor) {
		plateArtistnavn = artistnavn;
		platetittel = tittel;
		antPlatespor = antSpor;
	}

	public String hentUtArtist() {
		return plateArtistnavn;
	}

	public String hentUtPTittel() {
		return platetittel;
	}

	public void skrivUtPlate() {
		System.out.println(plateArtistnavn + ", " + platetittel + ", " + antPlatespor + " spor");
	}

}