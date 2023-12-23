import java.util.*;
import java.lang.*;
import java.io.*;

class Oblig4 {
	public static void main(String[] args) {
		new Test();
	}
}

interface Lik {
	// sjekker om to ting er like er like
	boolean samme(String str);
}

interface Avtaleleger {
	// henter ut avtalenummret
	int avtalenummer(int nr);
}

interface AbstraktTabell<T> extends Iterable<T> {
	// grensesnitt for en tabell som inneholder noe av typen T
	boolean settInn(T ting, int index);
	T finnObjekt(int index);
}

interface AbstraktSortertEnkelListe <T extends Comparable <T> & Lik> extends Iterable<T> {
	// grensesnitt for en sortert enkel liste som inneholder noe av typen T
	void settInnNyttElement(T l);
	T finnElement(String s);
}

interface Pille {
	int pillemengde();
}

interface Liniment {
	int cm3mengde();

}

interface Injeksjon {
	int virkemiddelmengde();
}

abstract class Legemidler {
	String navn;
	String form;
	int uniktNummer;
	int pris;
	static int unikteller;

	abstract void skrivUt();

	Legemidler(String navn, int pris) {
		this.navn = navn;
		this.uniktNummer = unikteller;
		this.pris = pris;
		unikteller++;
	}
}

abstract class A extends Legemidler {
	int narkotiskStyrke;

	A(String navn, int pris, int narkotiskStyrke) {
		super(navn, pris);
		this.narkotiskStyrke = narkotiskStyrke;
	}

	public int styrke() {
		return narkotiskStyrke;
	}
}

abstract class B extends Legemidler { 
	int vanedannelse;

	B(String navn, int pris, int vanedannelse) {
		super(navn, pris);
		this.vanedannelse = vanedannelse;
	}

	public int vanedannende() {
		return vanedannelse;
	}
}

abstract class C extends Legemidler {
	C(String navn, int pris) {
		super(navn, pris);
	}
}

class Apille extends A implements Pille {
	int pillerIEsken;

	Apille(String navn, int pris, int narkotiskStyrke, int pillerIEsken) {
		//konstruktør for a pille, veldig lik for alle legemidler
		super(navn, pris, narkotiskStyrke);
		this.pillerIEsken = pillerIEsken;
		this.form = "Pille";
	}

	public int pillemengde(){
		return pillerIEsken;
	}	

	public void skrivUt(){
		//skriver ut passende info om legemidlet, også veldig lik for de andre klassende
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", a, " +  pris + ", " + pillerIEsken + ", " + narkotiskStyrke);
	}
}

class Aliniment extends A implements Liniment {
	int cm3;

	Aliniment(String navn, int pris, int narkotiskStyrke, int cm3) {
		super(navn, pris, narkotiskStyrke);
		this.cm3 = cm3;
		form = "Liniment";
	}

	public int cm3mengde() {
		return cm3;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", a, " +  pris + ", " + cm3 + ", " + narkotiskStyrke);
	}
}

class Ainjeksjon extends A implements Injeksjon {
	int mengde;
	
	Ainjeksjon(String navn, int pris, int narkotiskStyrke, int mengde) {
		super(navn, pris, narkotiskStyrke);
		this.mengde = mengde;
		form = "Injeksjon";
	}

	public int virkemiddelmengde() {
		return mengde;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", a, " +  pris + ", " + mengde + ", " + narkotiskStyrke);
	}
}

class Bpille extends B implements Pille {
	int pillerIEsken;

	Bpille(String navn, int pris, int vanedannelse, int pillerIEsken) {
		super(navn, pris, vanedannelse);
		this.pillerIEsken = pillerIEsken;
		form = "Pille";
	}

	public int pillemengde(){
		return pillerIEsken;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", b, " +  pris + ", " + pillerIEsken + ", " + vanedannelse);
	}
}

class Bliniment extends B implements Liniment {
	int cm3;

	Bliniment(String navn, int pris, int vanedannelse, int cm3) {
		super(navn, pris, vanedannelse);
		this.cm3 = cm3;
		form = "Liniment";
	}

	public int cm3mengde() {
		return cm3;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", b, " +  pris + ", " + cm3 + ", " + vanedannelse);
	}
}

class Binjeksjon extends B implements Injeksjon {
	int mengde;
	
	Binjeksjon(String navn, int pris, int vanedannelse, int mengde) {
		super(navn, pris, vanedannelse);
		this.mengde = mengde;
		form = "Injeksjon";
	}
	
	public int virkemiddelmengde() {
		return mengde;
	}
	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", b, " +  pris + ", " + mengde + ", " + vanedannelse);
	}
}

class Cpille extends C implements Pille {
	int pillerIEsken;
	
	Cpille(String navn, int pris, int pillerIEsken) {
		super(navn, pris);
		this.pillerIEsken = pillerIEsken;
		form = "Pille";
	}
	
	public int pillemengde(){
		return pillerIEsken;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", c, " +  pris + ", " + pillerIEsken);
	}
}

class Cliniment extends C implements Liniment {
	int cm3;
	
	Cliniment(String navn, int pris, int cm3) {
		super(navn, pris);
		this.cm3 = cm3;
		form = "Liniment";
	}
	
	public int cm3mengde() {
		return cm3;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", c, " +  pris + ", " + cm3);
	}
}

class Cinjeksjon extends C implements Injeksjon {
	int mengde;
	
	Cinjeksjon(String navn, int pris, int mengde) {
		super(navn, pris);
		this.mengde = mengde;
		form = "Injeksjon";
	}
	
	public int virkemiddelmengde() {
		return mengde;
	}

	public void skrivUt(){
		System.out.println("Info om legemiddel: " + uniktNummer + ", " + navn + ", " + form + ", c, " +  pris + ", " + mengde);
	}
}

class Resepter {
	int uniktNummer;
	int eierAvResept;
	int reit;
	String blue;
	Legemidler legemiddel;
	Leger utgiver;

	static int unikteller;

	Resepter(int eierAvResept, int reit, String blue, Legemidler legemiddel, Leger utgiver) {
		//konstruktør
		this.uniktNummer = unikteller;
		this.eierAvResept = eierAvResept;
		this.reit = reit;
		this.blue = blue;
		this.legemiddel = legemiddel;	// eget objekt
		this.utgiver = utgiver;			// eget objekt
		unikteller++;
	}

	public void skrivUt() {
		//Skriver ut passende info om resepten
		System.out.println("Resept: " + uniktNummer + ", eiers nummer; " + eierAvResept + ", reit; " + reit + ", farge " + blue + ", utgiver: " + utgiver.navn);
	}

	public int pris() {
		return legemiddel.pris;
	}

}

class BlaResept extends Resepter {

	BlaResept(int eierAvResept, int reit, String blue, Legemidler legemiddel, Leger utgiver) {
		super(eierAvResept, reit, blue, legemiddel, utgiver);
	}

	public int pris() {
		return 0;
	}
}

class Leger implements Lik, Comparable<Leger> {
	String navn;
	String legetype = "Vanlig lege";
	//Oppretter en liste av respter som setter inn eldste respt først
	EldstForstReseptListe reseptliste = new EldstForstReseptListe();

	Leger(String navn) {
		//superkonstruktør
		this.navn = navn;
	}

	public boolean samme(String str) {
		//oppretter metoden fra grensersnittet
		//sammenligner to stringer
		return navn.equals(str);
	}

	public int avtalenummer(int nr) {
		//metode skulle sette avtalenr, men vi fant bedre løsninger
		return nr;
	}
	
	public int compareTo(Leger l) {
		//metode for å sammenligne objekter
		return navn.compareTo(l.navn);
	}

	public void skrivUt() {
		//metode for å skrive ut info om leger
		System.out.println("Lege: " + navn + ", legetype: " + legetype);
	}
	
}

class AvtaleLege extends Leger implements Avtaleleger {
	//subklasse av leger
	int avtalenummer;

	AvtaleLege(String navn, int avtalenummer) {
		//konstruktør
		super(navn);
		this.avtalenummer = avtalenummer;
		legetype = "Avtalelege";
	}

	public void skrivUt() {
		//metode som skriver ut info om subklassen til leger
		System.out.println("Lege: " + navn + ", legetype: " + legetype + ", avtalenummer: " + avtalenummer);
	}
}

class Spesialist extends Leger {
	
	Spesialist(String navn) {
		//konstruktør
		super(navn);
		legetype = "Spesialist";
	}

	public void skrivUt() {
		//alle skrivut-metodene gjør omtrent det samme
		System.out.println("Lege: " + navn + ", legetype: " + legetype);
	}

}

class SpesialistAvtaleLege extends Spesialist implements Avtaleleger {
	int avtalenummer;
	//subklasse av avtaleleger som er spesialist

	SpesialistAvtaleLege(String navn, int avtalenummer){
		//konstruktør
		super(navn);
		this.avtalenummer = avtalenummer;
		legetype = "Spesialist og avtalelege";
	}

	public void skrivUt() {
		System.out.println("Lege: " + navn + ", legetype: " + legetype + ", avtalenummer: " + avtalenummer);
	}
}

//absttrace class Sortertenkelliste<T> implements abstraktsortertenkelliste<T>
class Personer {
	String navn;
	char kjonn;
	int uniktNummer;
	static int unikteller;
	YngstForstReseptListe reseptliste = new YngstForstReseptListe();
	// en beholder av klassen YngstForstReseptListe som inneholder han / hun sine resepter

	Personer(String navn, char kjonn) {
		//konstruktør
		this.navn = navn;
		this.uniktNummer = unikteller;
		this.kjonn = kjonn;
		unikteller++;
	}

	public void skrivUt() {
		System.out.println("Navn: " + navn + ", kjonn: " + kjonn + ", personnummer: " + uniktNummer);
	}
}

class Tabell <T> implements AbstraktTabell<T> {
	//legemidler skal lagres her
	//personer skal også lagres her
	//på gruppetimene vil vi få hjelp til å lage iterator over listen
	// lager et t element
	T[] apotekregister;
	int antall;
	
	Tabell(int lengde) { //konstruktør
		apotekregister = (T[]) new Object[lengde];
		antall = 0;
	}

	public boolean settInn(T ting, int index) {
		//setter inn et element, og returnerer true, hvis det ble satt inn
		if(apotekregister[index] == null) {
			apotekregister[index] = ting;
			return true;
		} else {
			return false;
		}
	}

	public T finnObjekt(int index) {
		//finner objekt basert på index
		return (T) apotekregister[index];
	}

	class MyIterator implements Iterator<T>{
		//lager iterator
		int teller = 0;

		public boolean hasNext(){
	    	if (antall > teller) {
				return true;
			} else {
				return false;
			}
		}

		public T next(){
			T tmp = apotekregister[teller];
			teller++;
			return tmp;
		}
		
		public void remove(){

		}
    }

	public Iterator<T> iterator() {
		return new MyIterator();
	}
}

class EnkelReseptListe implements Iterable <Resepter> {
	//skal kunne ta vare på resepter
	//skal ha første og sistepeker
	//en resept skal kunne være med i flere objekter av klassen
	//skal ha iterator over listen

	Node forste, siste;
	int antall;

	EnkelReseptListe(){
	// skal etablere datastrukturen for tom liste:
	 	antall = 0;
	}

	class Node {
	 	Node neste;
	 	Resepter resept;

	 	Node(Resepter r) {
	 		resept = r;
	 	}
	}

	public void settInn(Resepter r) {
		//setter inn reseptobjekt i node
		Node n = new Node(r);
		antall++;
		if(siste != null) {
			siste.neste = n;
			siste = n;
		} else {
			forste = n;
			siste = n;
		}
	}

	public Resepter finnResept(int reseptNr) {
		//finner objekt basert på reseptnr
		Node denne = forste;

		while (denne != null) {
			if (denne.resept.uniktNummer == reseptNr) {
				return denne.resept;
			}
			denne = denne.neste;
		}
		return null;
	}

	public Iterator<Resepter> iterator() {
		//returnerer iterator
		return new MyIterator();
	}

	class MyIterator implements Iterator <Resepter> {
		//definerer iterator
		Node denne = forste;

		public boolean hasNext() {
			return (denne != null);
		}

		public Resepter next() {
			if (hasNext()) {
				Resepter resept = denne.resept;
				denne = denne.neste;
				return resept;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}

class SortertEnkelListe <T extends Comparable <T> & Lik> implements AbstraktSortertEnkelListe <T> {
	//Lagring av Leger 
	//Skriv den generiske klassen SortertEnkelListe som implementerer 
	//AbstraktSortertEnkelListe som en enveisliste
	//restrikterer at elementene i lista MÅ implementere comparable og lik
	Node<T> forste;
	Node<T> siste;
	int antall;

	private class Node<T> {
	 	Node neste;
	 	T objektet;

	 	Node(T t) {
	 		objektet = t;
	 	}
	}

	SortertEnkelListe(){
	 	antall = 0;
	}


	public void settInnNyttElement(T t) {
		//setter inn et nytt element i lista
		Node<T> n = new Node<>(t);
		antall++;

		if(siste == null) {//Sjekker om vi har en tom liste
			forste = n;
			siste = n;
		} else { //Det generelle tilfellet
			Node<T> denne = forste;
			Node<T> forrige = forste;

			while(denne != null) {
				if(n.objektet.compareTo(denne.objektet) < 0) { //Sette inn helt forst i listen
					if(denne == forste) {
						forste = n;
					} else {
						forrige.neste = n;
					}
					n.neste = denne;
					return;
				}
				forrige = denne;
				denne = denne.neste;
			}
			forrige.neste = n;
			siste = n;
		}
	}

	public T finnElement(String s) {
		//returnerer element basert på string s
		Node<T> denne = forste;

		while (denne != null) {
			if (denne.objektet.samme(s)) {
				return denne.objektet;
			}
			denne = denne.neste;
		}
		return null;
	}

	public Iterator<T> iterator() {
		return new MyIterator<T>(forste);
	}

	class MyIterator<R> implements Iterator<R>{
		Node<R> denne;

		public MyIterator(Node<R> forste){
		    denne = forste;
		}

		public boolean hasNext() {
		    return (denne != null);
		}

		public R next() {
		    if (hasNext()) {
				R objektet = denne.objektet;
				denne = denne.neste;
				return objektet;
		    } else {
				throw new NoSuchElementException();
		    }
		}

		public void remove(){
		    throw new UnsupportedOperationException();
		}
    }
}

class EldstForstReseptListe extends EnkelReseptListe {
	//beholder som innehodler en leges resepter
	//kan bruke iterator fra SortertEnkelListe eller EnkelReseptListe
	//starter med eldste resept i iterasjon

	void settInnEldstForst(Resepter e) {
		Node n = new Node(e);
		if(siste == null) { //hvis lista er tom
			forste = n;
			siste = n;
		} else { //lista er ikke tom
			siste.neste = n;
			siste = n;
		}
	}
}

class YngstForstReseptListe extends EnkelReseptListe {
	//en beholder som inneholder en persons resepter
	//kan bruke iterator fra SortertEnkelListe eller EnkelReseptListe
	//starter med yngste resept i iterasjon
	void settInnYngstForst(Resepter y) {
		Node n = new Node(y);
		if(forste == null) {  //tom liste
			forste = n;
			siste = n;
		} else { //lista er ikke tom
			n.neste = forste;
			forste = n;
		}
	}
}

class Test {
	//testklassen som fyller programmet med informasjon
	Tabell<Personer> personbeholder = new Tabell<>(20);
	Tabell<Legemidler> legemiddelbeholder = new Tabell<>(40);
	SortertEnkelListe<Leger> legebeholder = new SortertEnkelListe<>();
	EnkelReseptListe reseptbeholder = new EnkelReseptListe();
	Scanner sc = new Scanner(System.in);
	int ordre = 0;

	Test(){
		//konstruktør
		lesFil();
		meny();
	}
	
	void lesFil() {
		// dette er en orgentlig svenske-metode, Jeg hadde ikke tid til å gjøre det bedre.
		//leser inn fra fil (data.txt)
		// denne tar bare inn data fra data.txt på nøyakte det formate med like mange objekter under vært felt
		try {
			Scanner lesFil = new Scanner(new File("data.txt"));
			String[] ord;

			//Leser inn personer
			lesFil.nextLine();
			for (int i = 0; i < 6; i++) {
				ord = lesFil.nextLine().split(",");
				int nummer = Integer.parseInt (ord[0].trim());
				String navn = ord[1].trim();
				char kjonn = (ord[2].trim()).charAt(0);

				Personer nyPers = new Personer(navn, kjonn);
				if (personbeholder.settInn(nyPers,nummer)) {
					System.out.println(navn + " har blitt registrert");
				}
			}

			// leser inn legemidlene og setter dem inn i tabellen
			lesFil.nextLine();
			lesFil.nextLine();
			for (int i = 0; i < 3; i++) {
				ord = lesFil.nextLine().split(",");
				int nummer = Integer.parseInt (ord[0].trim());
				String navn = ord[1].trim();
				String form = ord[2].trim();
				String type = ord[3].trim();
				int pris = Integer.parseInt (ord[4].trim());
				int mengde = Integer.parseInt (ord[5].trim());
				int styrke = 0;
				if (!type.equals("c")) {
					styrke = Integer.parseInt (ord[6].trim());
				}

				//vrient med mange klasser, hadde ikke så mye tid til å tenke ut en bedre måte
				//hadde selvfølgelig brukt flere hjelpemotder her
				if (type.equals("a")) {
					if (form.equals("pille")) {
						Apille nyMiddel = new Apille(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("liniment")) {
						Aliniment nyMiddel = new Aliniment(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("injeksjon")) {
						Ainjeksjon nyMiddel = new Ainjeksjon(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					}
				}

				if (type.equals("b")) {
					if (form.equals("pille")) {
						Bpille nyMiddel = new Bpille(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("liniment")) {
						Bliniment nyMiddel = new Bliniment(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("injeksjon")) {
						Binjeksjon nyMiddel = new Binjeksjon(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					}
				}

				if (type.equals("c")) {
					if (form.equals("pille")) {
						Cpille nyMiddel = new Cpille(navn, pris, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("liniment")) {
						Cliniment nyMiddel = new Cliniment(navn, pris, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("injeksjon")) {
						Cinjeksjon nyMiddel = new Cinjeksjon(navn, pris, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					}
				}
			}

			//leser inn legene og setter dem in legelista vår
			lesFil.nextLine();
			lesFil.nextLine();
			for (int i = 0; i < 5 ; i++) {
				ord = lesFil.nextLine().split(",");
				String navn = ord[0].trim();
				int spesialist = Integer.parseInt (ord[1].trim());
				int avtalenr = Integer.parseInt (ord[2].trim());

				if (spesialist != 0) {
					if (avtalenr != 0) {
						SpesialistAvtaleLege tmp = new SpesialistAvtaleLege(navn, avtalenr);
						legebeholder.settInnNyttElement(tmp);
						System.out.println("lege " + navn + " er registrert som SpesialistAvtaleLege");
					} else {
						Spesialist tmp = new Spesialist(navn);
						legebeholder.settInnNyttElement(tmp);
						System.out.println("lege " + navn + " er registrert som Spesialist");
					}
				} else if (avtalenr != 0) {
					AvtaleLege tmp = new AvtaleLege(navn, avtalenr);
					legebeholder.settInnNyttElement(tmp);
					System.out.println("lege " + navn + " er registrert som Avtalelege");
				} else {
					Leger tmp = new Leger(navn);
					legebeholder.settInnNyttElement(tmp);
					System.out.println("lege " + navn + " er registrert som lege");
				}
			}

			// leser til slutt inn resptene og legger dem inn i reseptbholderen
			lesFil.nextLine();
			lesFil.nextLine();
			for (int i = 0; i < 3; i++) {
				ord = lesFil.nextLine().split(",");
				int nr = Integer.parseInt(ord[0].trim());
				String farge = ord[1].trim();
				int personNr = Integer.parseInt(ord[2].trim());
				String legeNavn = ord[3].trim();
				int middelNr = Integer.parseInt(ord[4].trim());
				int reit = Integer.parseInt(ord[5].trim());
				if (farge.equals("b")) {
					farge = "bla";
				} else {
					farge = "hvit";
				}

				Personer pers = personbeholder.finnObjekt(personNr);
				Leger lege = legebeholder.finnElement(legeNavn);
				Legemidler legeMiddel = legemiddelbeholder.finnObjekt(middelNr);
				Resepter resept = new Resepter(personNr, reit, farge, legeMiddel, lege);
				reseptbeholder.settInn(resept);

				//passer på at respten blir riktig lagt inn
				lege.reseptliste.settInnEldstForst(resept);
				pers.reseptliste.settInnYngstForst(resept);

				if(reseptbeholder.finnResept(nr) != null) {
					System.out.println("resept " + resept.uniktNummer + ", " + resept.blue + ", for " + resept.legemiddel.navn + " er registrert");
				}
			}
		} catch(IOException e) {
		    e.printStackTrace();
		}
	}

	void meny() {
		while(ordre != 10) {
			//menyen til programmet
			System.out.println("Meny:");
			System.out.println("1: Opprett og legg inn et nytt legemiddel.");
			System.out.println("2: Opprett og legg inn en ny lege.");
			System.out.println("3: Opprett og legg inn en ny person.");
			System.out.println("4: Opprett og legg inn en ny resept.");
			System.out.println("5: Hent legemiddel.");
			System.out.println("6: Skriv ut info om alle legemidler, leger og personer"); // ALT
			System.out.println("7: Skriv ut info om en person(basert pa personnr) bare bla resepter");
			System.out.println("8: Skriv ut alle leger med avtaler. + legens resepter");
			System.out.println("9: Skriv ut personer med gyldige respter for vanedannende legemidler");
			System.out.println("10: Avslutt");

			ordre = sc.nextInt();
		
			switch(ordre) {
				//kjører en void for ulik ordre
				case 1: opprettLegemiddel(); break; //ferdig
				case 2: opprettLege(); break; //ferdig
				case 3: opprettPerson(); break;//ferdig
				case 4: opprettResept(); break;//ferdig
				case 5: hentLegemiddelPaResept(); break;//ferdig
				case 6: skrivAllInfo(); break; //ferdig
				case 7: skrivEnPerson(); break; //Ferdig
				case 8: skrivALegeResept(); break;
				case 9: skrivAllePersonerInfo(); break; //ferdig
				case 10: avslutt(); break;//ferdig
				default: System.out.println("Skriv ett tall mellom 1 og 12.");
			} //slutt switch
		} //slutt while
	} //slutt meny

	//ferdig
	void opprettLegemiddel() {
		//opprettelse av et legemiddel
		String navn;
		String type;
		String form;
		int pris;
		int mengde;
		int styrke;

		System.out.println("Skriv inn et navn pa legemiddelet.");
		navn = sc.next();
		System.out.println("Skriv inn mengde: ant piller/vol salve/cm3 injeksjon");
		mengde = sc.nextInt();
		System.out.println("Skriv inn styrke");
		styrke = sc.nextInt();
		System.out.println("Skriv inn en pris pa legemiddelet");
		pris = sc.nextInt();
		System.out.println("Skriv inn type: a/b/c");
		type = sc.next();
		if(!type.equals("a") && !type.equals("b") && !type.equals("c")) {
			System.out.println("Skriv inn enten a, b, eller c");
		} else {
			System.out.println("Skriv inn formen pa legemiddelet: pille/liniment/injeksjon.");
			form = sc.next();
			if(!form.equals("pille") && !form.equals("liniment") && !form.equals("injeksjon")) {
				System.out.println("Skriv inn pille, liniment eller injeksjon.");
			} else {
				//sjekker form og type og lager og setter inn objekt
				if (type.equals("a")) {
					if (form.equals("pille")) {
						Apille nyMiddel = new Apille(navn, pris, styrke, mengde);

						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("liniment")) {
						Aliniment nyMiddel = new Aliniment(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("injeksjon")) {
						Ainjeksjon nyMiddel = new Ainjeksjon(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					}
				}

				if (type.equals("b")) {
					if (form.equals("pille")) {
						Bpille nyMiddel = new Bpille(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("liniment")) {
						Bliniment nyMiddel = new Bliniment(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("injeksjon")) {
						Binjeksjon nyMiddel = new Binjeksjon(navn, pris, styrke, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					}
				}

				if (type.equals("c")) {
					if (form.equals("pille")) {
						Cpille nyMiddel = new Cpille(navn, pris, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("liniment")) {
						Cliniment nyMiddel = new Cliniment(navn, pris, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					} else if (form.equals("injeksjon")) {
						Cinjeksjon nyMiddel = new Cinjeksjon(navn, pris, mengde);
						if (legemiddelbeholder.settInn(nyMiddel, nyMiddel.uniktNummer)) {
							System.out.println(navn + ", " + form + " er registrert");
						}
					}
				}
			}
		}
	}	

	
	void opprettLege() {
		//opprettelse av lege
		String navn;
		String legetype = "Vanlig lege";
		boolean avtalelege = false;
		int avtalenummer = 0;
		String tmp;

		System.out.println("Skriv navnet pa legen.");
		navn = (sc.next() + " " + sc.next());
		System.out.println("Er legen spesialist? ja/nei");
		legetype = sc.next();
		if(legetype.equals("ja")) {
			legetype = "Spesialist";
		} else if(legetype.equals("nei")) {
			legetype = "Vanlig lege";
		} else {
			System.out.println("Feilmelding! Skriv ja/nei.");
		}
		System.out.println("Er legen avtalelege? ja/nei");
		tmp = sc.next();
		if(tmp.equals("ja")) {
			avtalelege = true;
			System.out.println("Skriv inn avtalenummer:");
			avtalenummer = sc.nextInt();
		} else if(tmp.equals("nei")) {

		} else {
			System.out.println("Feilmelding! Skriv ja/nei.");
		}

		//---------Lager legeobjekter----------
		if(legetype.equals("Vanlig lege") && (avtalelege == false)) {
			//opprett vanlig lege
			Leger tmpl = new Leger(navn);
			legebeholder.settInnNyttElement(tmpl);
			System.out.println("registrert");

		} else if(legetype.equals("Vanlig lege") && (avtalelege == true)) {
			//opprett vanlig avtalelege
			AvtaleLege tmpl = new AvtaleLege(navn, avtalenummer);
			legebeholder.settInnNyttElement(tmpl);
			System.out.println("registrert");

		} else if(legetype.equals("Spesialist") && (avtalelege == false)) {
			//opprett spesialist
			Spesialist tmpl = new Spesialist(navn);
			legebeholder.settInnNyttElement(tmpl);
			System.out.println("registrert");

		} else if(legetype.equals("Spesialist") && (avtalelege == true)) {
			//opprett spesialistavtalelege
			SpesialistAvtaleLege tmpl = new SpesialistAvtaleLege(navn, avtalenummer);
			legebeholder.settInnNyttElement(tmpl);
			System.out.println("registrert");

		} else {
			System.out.println("Noe gikk galt i opprettingen av legeobjekt!");
		}
	}

	
	void opprettPerson() {
		//opprettelsen av personobjekter
		String navn;
		char kjonn;
		int nr;

		System.out.println("Skriv inn et navn:");
		navn = (sc.next() + " " + sc.next());
		System.out.println("Skriv inn kjonn m/k");
		kjonn = (sc.next()).charAt(0);

		Personer tmp = new Personer(navn, kjonn);
		if(personbeholder.settInn(tmp, tmp.uniktNummer)) {
			System.out.println("registrert");
		}
	}

	
	void opprettResept() {
		//opprettelsen av resepter
		int eierAvResept;
		int reit;
		String farge;
		int legemiddelvalg;
		String utgiver;
		Resepter resept;
	
		System.out.println("Skriv inn personnr pa eieren av resepten:");
		eierAvResept = sc.nextInt();
	
		System.out.println("Skriv inn antall reit:");
		reit = sc.nextInt();
	
		System.out.println("Skriv inn om resepten er bla eller ikke. ja/nei");
		farge = sc.next();
		if(farge.equals("ja")) {
			farge = "bla";
		} else if(farge.equals("nei")) {
			farge = "hvit";
		} else {
			System.out.println("Skriv inn ja/nei");
		}

		System.out.println("legemiddeler");
		for (int i = 0; i < 40; i++) {
			if (legemiddelbeholder.finnObjekt(i) != null) {
				legemiddelbeholder.finnObjekt(i).skrivUt();
			}
		}
		System.out.println("Velg legemiddelet (nummer) du vil ha resept pa :");
		legemiddelvalg = sc.nextInt();

		System.out.println("Skriv inn navnet pa utgiveren av resepten.");
		sc.nextLine();
		utgiver = sc.nextLine();

		Personer pers = personbeholder.finnObjekt(eierAvResept);
		Leger lUtgiver = legebeholder.finnElement(utgiver);
		Legemidler l = legemiddelbeholder.finnObjekt(legemiddelvalg);

		if (farge.equals("bla")) {
			resept = new BlaResept(eierAvResept, reit, farge, l, lUtgiver);
		} else {
			resept = new Resepter(eierAvResept, reit, farge, l, lUtgiver);
		}
		reseptbeholder.settInn(resept);
		lUtgiver.reseptliste.settInnEldstForst(resept);
		pers.reseptliste.settInnYngstForst(resept);
	}

	void hentLegemiddelPaResept() {
		//henter et legemiddelobjekt basert på reseptnr
		int personnr; 
		int reseptNr;
		System.out.println("Skriv inn personnr:");
		personnr = sc.nextInt();
		System.out.println("Skriv inn reseptNr");
		reseptNr = sc.nextInt();

		for(Resepter r: reseptbeholder) {
			if((reseptNr == r.uniktNummer) && (personnr == r.eierAvResept)) {
				if (r.reit > 0) {
					System.out.println("Pris: " + r.pris());
					System.out.println("Utgiver: " + r.utgiver.navn);
					System.out.println("Eier av resept: " + personbeholder.finnObjekt(personnr).navn);
					r.legemiddel.skrivUt();
					r.reit--;

				} else {
					System.out.println("Ugyldig resept!");
				}
			}
		}
	}

	void skrivAllInfo() {
		//skriver ut all tilgjengelig informasjon om legemidler, leger og personer
		System.out.println("Alle legemidlene: ");
		for (int i = 0; i < 40; i++) {
			if (legemiddelbeholder.finnObjekt(i) != null) {
				legemiddelbeholder.finnObjekt(i).skrivUt();
			}
		}

		System.out.println("Alle leger: ");
		for (Leger l : legebeholder) {
			l.skrivUt();
		}

		System.out.println("Alle personer");
		for (int i = 0; i < 20; i++) {
			if (personbeholder.finnObjekt(i) != null) {
				personbeholder.finnObjekt(i).skrivUt();
			}
		}
	}
	
	void skrivEnPerson() {
		//skal skrive ut alle gyldige blå resepter personen har
		// + hvor mange injeksjonsdoser igjen.
		int personnr;
		System.out.println("Skriv inn personnr pa personen du onsker info om:");
		personnr = sc.nextInt();
		Personer p = personbeholder.finnObjekt(personnr);
		p.skrivUt();
		for(Resepter r: p.reseptliste) {
			if((r.reit > 0) && (r.blue.equals("bla"))) {
				r.skrivUt(); //skriver ut alt i ett. Ink injeksjoner
			}
		}
	}

	void skrivALegeResept() { 	
		//teller opp og skriver ut hvor mange narkotiske resepter avtaleleger har skrevet ut
		int teller = 0;
		for (Leger l : legebeholder) {
			if((l instanceof AvtaleLege) || (l instanceof SpesialistAvtaleLege)) {
				for(Resepter r: l.reseptliste) {
					if(r.legemiddel instanceof A) {
						teller++;
					}
				}
				System.out.println(l.navn + " har skrvet ut " + teller + " resepter av narkotika");
			}
		} 

	}
	
	void skrivAllePersonerInfo() {
		//skriver ut informasjon om alle personer + hvor mange vanedannende resepter som er gitt ut totalt
		// + hvor mange som er kvinner og hvor mange som er menn som har mottatt de vanedannende reseptene
		int antallM = 0;
		int antallK = 0;
		int teller = 0;
		for (int i = 0; i < 20; i++) {
			if (personbeholder.finnObjekt(i) != null) {
				System.out.println(personbeholder.finnObjekt(i).navn);
				for(Resepter r: personbeholder.finnObjekt(i).reseptliste) {
					if((r.legemiddel instanceof B) && (r.reit > 0)) {
						r.skrivUt();
						teller++;
						if(personbeholder.finnObjekt(r.eierAvResept).kjonn == 'm') {
							antallM++;
						}
						if(personbeholder.finnObjekt(r.eierAvResept).kjonn == 'k') {
							antallK++;
						}
					}
				}
			}
		}
		System.out.println("Det er totalt " + teller + " gyldige, vanedannende resepter.");
		System.out.println("Av disse er " + antallM + " menn, og " + antallK + " kvinner.");
	}

	void avslutt() {
		//avslutter programmet
		//hadde ikke tid til å lagre til fil her
	}

}// slutt Test