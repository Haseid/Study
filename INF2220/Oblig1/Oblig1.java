import java.util.*;
import java.io.*;

/*
*  __  __                                   __     
* /\ \/\ \                           __    /\ \    
* \ \ \_\ \     __      ____     __ /\_\   \_\ \   
*  \ \  _  \  /'__`\   /',__\  /'__`\/\ \  /'_` \  
*   \ \ \ \ \/\ \_\.\_/\__, `\/\  __/\ \ \/\ \_\ \ 
*    \ \_\ \_\ \__/.\_\/\____/\ \____\\ \_\ \___,_\
*     \/_/\/_/\/__/\/_/\/___/  \/____/ \/_/\/__,_ /
*
*
*
*	I denne oppgaven har jeg brukt noe engelsk og norsk om hverandre, dette skal ikke gjenta seg.
*/
class Oblig1 {
	public static void main(String[] args) {
		System.out.println("\033[2J\n"); // Clearing screen, kan hende at ikke fungerer på linux / windows
		new Run();
	}
}

class Run {

	//atributter for Run klasse
	Scanner scan = new Scanner(System.in);
	String ordre = "";
	Ordtre ordtre = new Ordtre(); // binary tree
	char[] alphabet = "abcdefghijklmnopqrstuvwxyzæøå".toCharArray(); // alfabetet i et char array

	// Konstruktør
	public Run() {
		innlesning();
		aSmallChange();
		statistics();
		menu();
	}

	// metode som finner andre mulige ord
	public void genererteOrd(String ord) {

		int ant = 0;

		// finner mulige skrivefeil
		String[] nyeOrd = typo1(ord);
		nyeOrd = combine(nyeOrd, typo2(ord));
		nyeOrd = combine(nyeOrd, typo3(ord));
		nyeOrd = combine(nyeOrd, typo4(ord));

		// fjerner duplikater i nyeOrd
		nyeOrd = new HashSet<String>(Arrays.asList(nyeOrd)).toArray(new String[0]);

		// printer ut mulige ord
		System.out.println("\nDid you mean:");
		for (int i = 0; i < nyeOrd.length; i++) {
			if (ordtre.inneholder(nyeOrd[i])) {
				System.out.println(nyeOrd[i]);
				ant++;
			}
		}
		// printer ut antall gode ord funnet
		System.out.println("\nNumber of positive answers: " + ant);
	}

	// metode som combinerer to string arrayer
	public String[] combine(String[] liste1, String[] liste2) {

		// denne motden er veldig tung, men den var rask å lage
		List<String> liste3 = new ArrayList<String>(liste1.length + liste2.length);
		Collections.addAll(liste3, liste1);
		Collections.addAll(liste3, liste2);
		return liste3.toArray(new String[liste3.size()]);
	}

	// metode for skrivefeil
	public String[] typo4(String ord) {
		char[] tmpOrd;
		String[] words = new String[(ord.length())];

		for (int i = 0; i < (ord.length()); i++) {
			tmpOrd = ord.toCharArray();
			tmpOrd[i] = 0;
			words[i] = (new String(tmpOrd)).trim();
		}

		return words;
	}

	// metode for skrivefeil
	public String[] typo3(String ord) {
		int teller = 0;
		String tmpOrd;
		String[] words = new String[(ord.length()+1) * (alphabet.length)];

		for (int i = 0; i <= ord.length(); i++) {
			for (int j = 0; j < alphabet.length; j++) {
				// setter inn en bokstav innimellom ordet
				tmpOrd = ord.substring(0, i) + alphabet[j] + ord.substring(i, ord.length());
				words[teller++] = tmpOrd;
			}
		}
		return words;
	}

	// metode for skrivefeil
	public String[] typo2(String ord) {
		int teller = 0;
		char[] tmpOrd;
		String[] words = new String[(ord.length()) * (alphabet.length)];

		for (int i = 0; i < ord.length(); i++) {
			tmpOrd = ord.toCharArray();

			for (int j = 0; j < alphabet.length; j++) {
				tmpOrd[i] =  alphabet[j];
				words[teller++] = new String(tmpOrd);
			}
		}
		return words;
	}

	// metode for skrivefeil
	public String[] typo1(String ord) {
		char[] word_array = ord.toCharArray();
		char[] tmp;
		String[] words = new String[word_array.length-1];

		for(int i = 0; i < word_array.length - 1; i++){
			tmp = word_array.clone();
			words[i] = swap(i, i+1, tmp);
		}
		return words;
	}

	// oppgitt metode som bytter to bokstaver på spesifiserte plasser
	public String swap(int a, int b, char[] ord){
		char tmp = ord[a];
		ord[a] = ord[b];
		ord[b] = tmp;
		return new String(ord);
	}

	// metode som printer ut statestikk
	public void statistics() {

		int dybde = ordtre.treDybde();
		System.out.println("Statistics:");
		System.out.println("Tree depth: " + dybde);

		for (int i = 0; i <= dybde; i++) {
			System.out.println("Nodes on tree level " + i + ":\t" + ordtre.antallIdybde(i));		
		}

		System.out.println("Average depth of all nodes: " + ordtre.avgDepth());
		System.out.println("First word: " + ordtre.finnMinste());
		System.out.println("Final word: " + ordtre.finnStorste());
	}

	// metode som tar bort og setter inn ordet familie
	public void aSmallChange() {
		ordtre.taBort("familie");
		ordtre.settinn("familie");
	}

	// metode som skriver ut menyen
	public void menu() {

		// løkke som tar imot brukerdata
		while(!ordre.equals("q")) {
			System.out.println("\nType \"q\" to exit the program\n"); 
			System.out.print("Type a word: ");

			ordre = (scan.nextLine()).toLowerCase(); // tar inn ordre
			System.out.println("\033[2J\n"); // Clearing screen, kan hende at ikke fungerer på linux / windows
			System.out.println(ordre);

			// tester om ordet er riktig, ellers kaller den metode for å finne mulige ord
			if(ordtre.inneholder(ordre)) {
				System.out.println("The word is correct");
			} else if(!ordtre.inneholder(ordre) && !ordre.equals("q")) {
				System.out.println("The word is incorrect");

				long startTimer = System.currentTimeMillis();
				genererteOrd(ordre);
				long endTimer = System.currentTimeMillis();
				double tid = (endTimer-startTimer);

				System.out.println("Time used: " + tid/1000 + " sec");
			}
		}
	}

	// innlesning for ordboken
	public void innlesning() {
		try {
			Scanner lesFil = new Scanner(new File("Dictionary.txt"));

			while (lesFil.hasNext()) {
				ordtre.settinn(lesFil.next());
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

// klasse som oppretter et binary tree som tar imot String verdier
// dette treet er veldig inspirert av treet boka i INF2220 definerer
class Ordtre {

	// attributter for ordtret
	Node root;
	int antNoder;

	// konstruktør
	public Ordtre() {
		root = null;
		antNoder = 0;
	}

	// metode som finner gjennomsnittelig dybde av alle noder
	public int avgDepth() {
		int depths = 0;
		for (int i = 0; i <= treDybde(); i++) {
			depths += i * antallIdybde(i);
		}
		return (depths / antNoder);
	}

	// åpen metode som kaller en indre metode
	public int antallIdybde(int dybde) {
		return antallIdybde(root, dybde);
	}

	// indre rekursiv metode som finner antall noder i en gitt dybde
	private int antallIdybde(Node n, int dybde) {
		if (n == null) {
			return 0;
		} else if(dybde == 0) {
			return 1;
		} else {
			dybde--; // trekker fra dybden før den går ned et nivå
			return (antallIdybde(n.left, dybde) + antallIdybde(n.right, dybde));
		}
	}

	// åpen metode som kaller en ondre metode som finner tre dybden
	public int treDybde() {
		return treDybde(root)-1;
	}

	// indre rekursiv metode som finner tredybden
	private int treDybde(Node n) {
		if (n == null) {
			return 0;
		} else {
			// velger ut den største verdien den finner
			return 1 + Math.max(treDybde(n.left), treDybde(n.right));
		}
	}

	// åpen metode som finner det størst elementet i treet
	public String finnStorste() {
		return finnStorste(root).ord;
	}

	// den indre rekursive metode som går gjennom hele treet for å finne det største elementet
	private Node finnStorste(Node n) {
		if(n != null)
			while(n.right != null)
				n = n.right;
		return n;
	}

	// åpen metode som finner det minste elementet
	public String finnMinste() {
		return finnMinste(root).ord;
	}

	// indre rekursiv metode som finner det minste elementet
	private Node finnMinste(Node n) {
		if(n != null) {
			while(n.left != null) {
				n = n.left;
			}
		}
		return n;
	}

	// åpen metode som tar bort det oppgitte elementet
	public void taBort(String s) {
		root = taBort(s, root);
	}

	// rekursiv metode som finner elementet, fjerner det og spleiser sammen treet igjen
	private Node taBort(String s, Node n) {
		if(n == null) {
			return n;
		}

		int verdi = s.compareTo(n.ord);
		if(verdi < 0) {
			n.left = taBort(s, n.left);
		} else if(verdi > 0) {
			n.right = taBort(s, n.right);
		} else if(n.left != null && n.right != null) {
			n.ord = finnMinste(n.right).ord;
			n.right = taBort(n.ord, n.right);
		} else {
			n = (n.left != null) ? n.left : n.right;
		}
		return n;
	}

	// åpen metode som gir true om ordtreet inneholder det oppgitte ordet ellers false
	public boolean inneholder(String s) {
		return inneholder(s, root);
	}

	// rekursiv metode som går gjennom treet for å finne det oppgitte ordet, returnerer true / false
	private boolean inneholder(String s, Node n) {
		if(n == null) {
			return false;
		}

		int verdi = s.compareTo(n.ord);
		if(verdi < 0){
			return inneholder(s, n.left);
		} else if (verdi > 0) {
			return inneholder(s, n.right);
		} else {
			return true;
		}
	}

	// åpen metode som setter inn det oppgitte ordet inn i tree
	public void settinn(String s) {
		root = settinn(s, root);
	}

	// indre rekursiv metide som setter inn det oppgitte ordet på riktig plass, dersom det ikke finnes fra før
	private Node settinn(String ord, Node n) {
		if (n == null) {
			antNoder++; // teller antall noder
			return new Node(ord);
		}

		int verdi = ord.compareTo(n.ord);
		if (verdi < 0) {
			n.left = settinn(ord, n.left);
		} else if (verdi > 0) {
			n.right = settinn(ord, n.right);
		}
		return n;
	}

	// indre node klasse som holder på elementene
	private class Node {

		// attributter for klassen
		String ord;
		Node left;
		Node right;

		// konstruktør
		Node(String s) {
			this(s, null, null);
		}

		// konstruktør 2 om vi skulle trenge å vite barna på forrhånd
		Node(String s, Node l, Node r) {
			this.ord = s;
			this.left = l;
			this.right = r;
		}
	}
}













































