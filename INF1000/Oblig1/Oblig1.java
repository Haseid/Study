class Oblig1 {
    public static void main(String[] args) {
    	System.out.println("bla");
	
	// Variabler for regnversdager og regnmengde i de gitte måneder
	int maiDager = 22;
	int maiRegn = 89;
	int juniDager = 18;
	int juniRegn = 127;
	int juliDager = 8;
	int juliRegn = 19;
	int avrgJuliRegn = 81;

	// Utregninger for gitte krav i oppgaven
	int totalRegn = maiRegn + juniRegn + juliRegn;                                        // Den totale nedbøren i mai, juni og juli
	double avrgRegnRegndager = ((double)totalRegn) / (maiDager + juniDager + juliDager);  // Gjennomsnittet nedbør av regndagene i mai, juni og juli
	double normalProsenten = ((double) juliRegn) / avrgJuliRegn * 100;                    // Prosentandelen av normalnedbøren det regnet i juli
	
	System.out.println("\n"
			   + "Totalt hele sommeren 2013 har det falt " + totalRegn + " mm nedbør.\n"
			   + "De dagene det regnet i sommeren 2013 regnet det gjennomsnittlig ca. " + Math.round(avrgRegnRegndager) +" mm.\n"
			   + "I juli 2013 regnet det ca. " + Math.round(normalProsenten) + "% av den normale nedbørmengden.\n");
    }
}