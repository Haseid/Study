import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

class Oblig5 { //Del 2
	public static void main(String[] args) {

		if (args.length == 1) { //Tilfelle ett argument
			SudokuBeholder sudokuBeholder = new SudokuBeholder();
			Innlesning innlesning = new Innlesning(args[0], sudokuBeholder);
			innlesning.brettTest.forste.fyllUtRestenAvBrettet();
			ArrayList <String> sudokuLosninger = sudokuBeholder.taUt();
			int i = 1;

			System.out.println("Fant " + sudokuBeholder.hentAntallLosninger() + " Løsning(er)");
			for (String s : sudokuLosninger) {
				System.out.println(i + ": " + s);
				i++;
			}
		} else if (args.length == 2){ //tilfelle to argumenter
			SudokuBeholder sudokuBeholder = new SudokuBeholder();
			Innlesning innlesning = new Innlesning(args[0], sudokuBeholder);
			innlesning.brettTest.forste.fyllUtRestenAvBrettet();
			ArrayList <String> sudokuLosninger = sudokuBeholder.taUt();
			FileWriter utfil;
			int i = 1;

			try {
				utfil = new FileWriter(args[1]);
				utfil.write("Fant " + sudokuBeholder.hentAntallLosninger() + " Løsning(er)\n");
				for (String s : sudokuLosninger) {
					utfil.write(i + ": " + s + "\n");
					i++;
				}
				utfil.close();
			} catch (IOException e) {

			}
		} else { // tilfele ingen eller andre ting
			new Window();
		}

	}
}

class Window extends JFrame {

	//fikk stor hjelp av Ken Vuong (kenv)
	//hjalp til for det meste med å få boksene på riktig plass

	SudokuBeholder sudokuBeholder;
	Innlesning innlesning;
	ArrayList <String> sudokuLosninger;

	String losning;
	String filNavn;
	int dimensjon;
	int losningsnr;
	int brettStr;

	JPanel menyPanel;
	JLabel infoPanel;
	JPanel brettPanel;
	JLabel[][] ruter;


	Window() {
		//konstruktør for vindom som gjør det meste
		super("Sudoku løser");
		this.setLayout(new FlowLayout());

		menyPanel = new JPanel();
		infoPanel = new JLabel();
		brettPanel = new JPanel();

		//Lager "Neste løsning" knappen
		final JButton nesteLosning = new JButton("Neste løsning");
		nesteLosning.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nesteBrett();
						Window.this.remove(infoPanel);
						String solutionString = ("Løsning " + (losningsnr + 1) + " av " + sudokuBeholder.hentAntallLosninger());
						infoPanel = new JLabel(solutionString);
						Window.this.add(infoPanel);
						losningsnr++;
					if (losningsnr >= sudokuBeholder.hentAntallLosninger()) {
						nesteLosning.setEnabled(false);
					}
				}
			}
		);
		nesteLosning.setEnabled(false);

		// lager "Finn løsninger" knappen
		final JButton solve = new JButton("Finn løsninger");
		solve.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innlesning.brettTest.forste.fyllUtRestenAvBrettet();
					sudokuLosninger = sudokuBeholder.taUt();
					losningsnr = 0;
					nesteLosning.setEnabled(true);
					solve.setEnabled(false);
					nesteLosning.doClick();
				}
			}
		);
		solve.setEnabled(false);

		//Lager "Åpne fil"
		JButton apneFil = new JButton("Åpne fil");
		apneFil.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filNavn();
					if (filNavn != null) {

						Window.this.remove(infoPanel);
						Window.this.remove(brettPanel);

						sudokuBeholder = new SudokuBeholder();
						innlesning = new Innlesning(filNavn, sudokuBeholder);
						dimensjon = innlesning.dimensjon;
						brettStr = innlesning.dimensjon;
						brettRuter();

						Window.this.setBounds(700, 300, (brettStr*50)+90, (brettStr*50)+100);
						Window.this.add(brettPanel);
						Window.this.revalidate();
						Window.this.repaint();

						solve.setEnabled(true);
						nesteLosning.setEnabled(false);
					}
				}
			}
		);

		menyPanel.add(apneFil);
		menyPanel.add(solve);
		menyPanel.add(nesteLosning);

		this.add(menyPanel);
		this.setBounds(700, 300, 390, 70);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void nesteBrett() {
		//Gir ut et løst brett på GUIen
		losning = sudokuLosninger.get(losningsnr);
		String[] linje = losning.split("//");
		char[][] ferdig = new char[dimensjon][dimensjon];

		for (int i = 0; i < dimensjon; i++) {
			ferdig[i] = linje[i].toCharArray();
		}

		//Oppdaterer alle rutene til det nye brettet
		for (int i = 0; i < dimensjon; i++) {
			for (int j = 0; j < dimensjon; j++) {
				String s = ferdig[i][j] + "";
				ruter[i][j].setText(s.toUpperCase() + "");
			}	
		}
	}

	private void filNavn() {
		// velger hvilken fil man åpner ig finner navnet på filen
		JFileChooser chooser = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Tekst filer", "txt");
		chooser.setFileFilter(filter);
		int returVerdi = chooser.showOpenDialog(this);
		filNavn = chooser.getSelectedFile().getName();
	}

	private void brettRuter() {
		//her lager vi først brettet
		brettPanel = new JPanel(new GridLayout(brettStr, brettStr));
		brettPanel.setPreferredSize(new Dimension(50*brettStr, 50*brettStr));

		// setter sammen brettdimensjonene
		int boksX = innlesning.x;
		int boksy = innlesning.y;
		ruter = new JLabel[brettStr][brettStr];

		// lager verdier for kantene til ruten, blir viktig for tykkelsen av rammen
		int nord = 1;
		int sor = 1;
		int ost = 1;
		int vest = 1;

		for (int i = 0; i < brettStr; i++) {
			// tilbakestiler nord og sør kanten
			nord = 1; sor = 1;

			// setter alle nord og sør kanter til bokser til å bli tykkere
			// samtidig som vi gjør at toppen av brette blir tykkere
			if (i % (brettStr/boksX) == 0) { nord = 2; }
			if (i == 0) { nord = 4; }
			if (i % (brettStr/boksX) == (boksy - 1)) { sor = 2; }
			if (i == (brettStr - 1)) { sor = 4; }
			
			for (int j = 0; j < brettStr; j++) {
				// tilbakestiller vest og øst
				vest = 1; ost = 1;
				
				// setter alle øst og "vestkanter" til boksene til å være tykkere
				// samtidig som at vi gjør sidene tykke
				if (j % (brettStr/boksy) == 0) { vest = 2; }
				if (j == 0) { vest = 4; }
				if (j % (brettStr/boksy) == (boksX - 1)) { ost = 2; }
				if (j == (brettStr - 1)) { ost = 4; }

				// fikser slik at alle 0 blir blanke ruter
				int tall = innlesning.brett[i][j].tall;
				ruter[i][j] = new JLabel((tall == 0 ? "" : Integer.toString(tall, 36).toUpperCase() + ""));

				//lager font og plasering av tekstene i rutene
				ruter[i][j].setFont(new Font("SansSerif", Font.BOLD, 32));
				ruter[i][j].setHorizontalAlignment(SwingConstants.CENTER);

				// lagrer de modifiserte kantene på ruten
				ruter[i][j].setBorder(BorderFactory.createMatteBorder(nord, vest, sor, ost, Color.black));

				
				// fikser på bakgrunen til alle boksene slik at det blir visuelt penere
				if (((i/boksy) + (j/boksX)) % 2 == 0) {
					ruter[i][j].setBackground(Color.gray);
				} else {
					ruter[i][j].setBackground(Color.white);
				}
				
				ruter[i][j].setOpaque(true);
				brettPanel.add(ruter[i][j]);
			}
		}
	}
}

class Innlesning {
	Scanner innfil;
	int y, x, dimensjon;
	Rute[][] brett;
	Brett brettTest;

	Innlesning(String filnavn, SudokuBeholder sudokuBeholder) {
		lesInn(filnavn);
		settNestepekere();
		brettTest = new Brett(brett, y, x, dimensjon, sudokuBeholder);
	}

	void lesInn(String filnavn) {
		try {
			innfil = new Scanner(new File(filnavn));
			char[] tmpLinje;
			y = Integer.parseInt(innfil.nextLine());
			x = Integer.parseInt(innfil.nextLine());
			dimensjon = y * x;
			brett = new Rute[dimensjon][dimensjon];

			for (int i = 0; i < dimensjon; i++) {
				tmpLinje = innfil.nextLine().toCharArray();
				for (int j = 0; j < dimensjon; j++) {
					if (tmpLinje[j] == '.') {
						//Leser inn 0 (tomme ruter)
						brett[i][j] = new UkjentRute(0);
					} else if (erTall(tmpLinje[j])){
						// Leser inn ferdi ruter (tall)
						brett[i][j] = new FyltRute(Integer.parseInt(""+tmpLinje[j]));
					} else {
						//Leser inn ferdig ruter (bokstaver)
						brett[i][j] = new FyltRute(tmpLinje[j] - 55);

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Det mangler et tall i filen");
		} catch (NoSuchElementException e) {
			System.out.println("Det mangler en rad i filen");
		}
	}

	//Metode som tester om en char er ett tall
	public boolean erTall(char c) {
		try {
			Integer.parseInt("" + c);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//Metode som setter alle ruter til å peke på neste rute
	public void settNestepekere() {
		for (int i = 0; i < dimensjon; i++) {
			for (int j = 0; j < dimensjon; j++) {
				if (j == dimensjon -1) {
					if(i != dimensjon -1) {
						brett[i][j].neste = brett[i+1][0];
					}
				} else {
					brett[i][j].neste = brett[i][j+1];
				}
			}
		}
	}
}

class SudokuBeholder {
	// en arraylist som lagrer ferdig løste sudokuer som string
	ArrayList <String> sudokuBeholder = new ArrayList <String>();
	int teller = 0;

	public void settInn(String s) {
		sudokuBeholder.add(s);
	}

	public ArrayList <String> taUt() {
		return sudokuBeholder;
	}

	public int hentAntallLosninger() {
		return sudokuBeholder.size();
	}
}

class Rute {
	Rute neste;
	Boks boks;
	Kolonne kolonne;
	Rad rad;
	int tall;
	int boksPlass;
	Brett brett;

	Rute(int tall) {
		this.tall = tall;
	}

	//lager hele brette som en string og returnerer den
	public String funnetLosning() {
		String s = "";
		for (int i = 0; i < brett.dimensjon; i++) {
			for (int j = 0; j < brett.dimensjon; j++) {
				if (brett.ruter[i][j].tall > 9) {
					s = s + Integer.toHexString(brett.ruter[i][j].tall);
				} else {
					s = s + brett.ruter[i][j].tall;
				}
			}
			s = s + "//";
		}
		return s;
	}

	//alle rutene peker på hvilket brett det er i
	public void settBrettPeker(Brett brett) {
		this.brett = brett;
	}

	// Finner ut om det er et gyldig tall som settes inn i brettet
	public boolean erAbsolutGyldig(int testTall) {
		if (rad.erGyldigTall(testTall) && kolonne.erGyldigTall(testTall) && boks.erGyldigTall(testTall)) {
			return true;
		} else {
			return false;
		}
	}

	//Finner ut hvilken "boksId" ruten har
	public void settBoksPlass(int boksPlass) {
		this.boksPlass = boksPlass;
	}

	//Rekursiv metode som går fra rute til rute og fyller inn tall
	public void fyllUtRestenAvBrettet() {
		if (tall == 0) { //tester om det er en tom rute (kunne brukt instance of her)
			for (int i = 1; i < (brett.dimensjon+1); i++) {
				if (erAbsolutGyldig(i)) { //tester om tallet vi putter in er gyldig
					tall = i;
					if (neste != null) { // sjekker at det ikke er siste rute
						neste.fyllUtRestenAvBrettet(); // går til neste rute
						tall = 0; // sletter det gamle tallet
					} else {
						brett.sudokuBeholder.settInn(funnetLosning()); //lagrer brettet
					}
				}
			}
		} else { // om det er en ferdig utfylt rute
			if (neste != null) { // ikke er den siste ruten
				neste.fyllUtRestenAvBrettet(); // går videre til neste rute
			} else {
				brett.sudokuBeholder.settInn(funnetLosning()); // lagrer brettet
			}
		}
	}
}

class FyltRute extends Rute {

	FyltRute(int tall) {
		super(tall);
	}

}

class UkjentRute extends Rute {

	UkjentRute(int tall) {
		super(tall);
	}
}
	
class Tabell {
	Rute[] ruter;

	Tabell(Rute[] ruter) {
		this.ruter = ruter;
		settPekere();
	}

	// metode som går gjennom kolonnen, raden eller boksen og finner ut om tallet er der fra før
	public boolean erGyldigTall(int testTall) {
		for (int i = 0; i < ruter.length; i++) {
			if (ruter[i].tall == testTall) {
				return false;
			}
		}
		return true;
	}

	public void settPekere() {

	}
}

class Boks extends Tabell {

	Boks(Rute[] ruter) {
		super(ruter);
	}

	public void settPekere() {
		for (int i = 0; i < ruter.length; i++) {
			ruter[i].boks = this;
		}
	}

}

class Kolonne extends Tabell {

	Kolonne(Rute[] ruter) {
		super(ruter);
	}

	public void settPekere() {
		for (int i = 0; i < ruter.length; i++) {
			ruter[i].kolonne = this;
		}
	}
}

class Rad extends Tabell {

	Rad(Rute[] ruter) {
		super(ruter);
	}

	public void settPekere() {
		for (int i = 0; i < ruter.length; i++) {
			ruter[i].rad = this;
		}
	}
}

class Brett {
	Rute[][] ruter; // todimensjonal tabell med pekere til alle rutene.
	int y, x, dimensjon;
	Rad[] rader;
	Kolonne[] kolonner;
	Boks[] bokser;
	Rute[] tmpRad, tmpKolonne, tmpBoks;
	Rute forste;
	SudokuBeholder sudokuBeholder;


	Brett(Rute[][] ruter, int y, int x, int dimensjon, SudokuBeholder sudokuBeholder) {
		this.ruter = ruter;
		this.forste = ruter[0][0];
		this.y = y;
		this.x = x;
		this.dimensjon = dimensjon;
		this.rader = new Rad[dimensjon];
		this.kolonner = new Kolonne[dimensjon];
		this.bokser = new Boks[dimensjon];
		this.sudokuBeholder = sudokuBeholder;

		settRutePeker(); // får alle rutene til å peke på brettet
		lagRaderOgKolonner();
		finnBoksPlass();
		lagBokser();
	}

	public void settRutePeker(){
		for(int i = 0; i < dimensjon; i++) {
		    for(int j = 0; j < dimensjon; j++) {
		    	ruter[i][j].settBrettPeker(this);
		    }
		}
	}

	//lager alle radene og kolonnene, peker fra rute til kolonne blir lagd i konstruktøren til kolonne, rad og boks
	public void lagRaderOgKolonner() {
		for (int i = 0; i < dimensjon; i++) {
			tmpRad = new Rute[dimensjon];
			tmpKolonne = new Rute[dimensjon];

			for (int j = 0; j < dimensjon; j++) {
				tmpRad[j] = ruter[i][j];
				tmpKolonne[j] = ruter[j][i];
			}
			rader[i] = new Rad(tmpRad);
			kolonner[i] = new Kolonne(tmpKolonne);
		}
	}

	//setter boksplassen til rutene
	public void finnBoksPlass() {
		int teller = 0;
		for(int i = 0; i < dimensjon; i++) {
		    for(int j = 0; j < dimensjon; j++) {
				int yPlass = ((teller/(y * dimensjon)) * y);
				int xPlass = ((teller % dimensjon) / x);
				int boksPlass = xPlass + yPlass;
				ruter[i][j].settBoksPlass(boksPlass);
				teller++;
			}
		}
	}

	//Lager alle boksene
	public void lagBokser() {
		for (int i = 0; i < dimensjon; i++) {
			tmpBoks = new Rute[dimensjon];
			int teller = 0;
			for (int j = 0; j < dimensjon; j++) {
				for (int k = 0; k < dimensjon; k++) {
					if (ruter[j][k].boksPlass == i) {
						tmpBoks[teller++] = ruter[j][k];
					}
				}
			}
			bokser[i] = new Boks(tmpBoks);
		}
	}
}










































