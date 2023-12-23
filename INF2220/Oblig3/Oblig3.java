import java.util.*;
import java.io.*;

class Oblig3 {
	public static void main(String[] args) {
		if (args.length == 2) {
			new Patternmatcher(args[0], args[1]);
		} else {
			System.out.println("USAGE: java Oblig3 \"needle.txt\" \"haystack.txt\"");
		}
	}
}

class Patternmatcher {

	// inisialiserer attributter
	final int CHAR_MAX = 256;
	final char wildcard = '_';
	int[] bad_char_shift = new int[CHAR_MAX];
	char[] needle;
	char[] haystack;
	ArrayList<Integer> matchesFound = new ArrayList<Integer>();

	// konstruktør
	Patternmatcher(String needle, String haystack) {
		readfile(needle, haystack);
		patternFinder();
		printAll();
	}

	// metode som kjører sammenligningen
	public void patternFinder() {
		if (needle.length > haystack.length){
			System.out.println("The needle is greater than the haystack");
			System.exit(0);
		}

		// fikser bad_char_shift 
		fixShift(needle.length);
		// inisialiserer attributter
		int offset = 0, scan = 0;
		int last = needle.length - 1;
		int maxoffset = haystack.length - needle.length;

		// endrer på bad_char_shift i forhold til needle og wildcard
		for(int i = 0; i < last; i++){
			if (needle[i] == '_') {
				fixShift(last-i);
			} else {
				bad_char_shift[needle[i]] = last - i;
			}
		}

		// starter å scanne
		while(offset <= maxoffset){
			scan = last;
			// starter sammenligningen
			while(check(scan, offset)){
				if(scan == 0){ // finner match
					matchesFound.add(offset);
					break;
				}
				scan--;
			}
			// endrer
			offset += bad_char_shift[haystack[offset + last]];
		}
	}

	// metode som fikser bad_char_shift
	public void fixShift(int value) {
		for (int i = 0; i<CHAR_MAX; i++) {
			bad_char_shift[i]= value;
		}
	}

	// metode som sjekker sammenligner char og wildcards
	public boolean check(int scan, int offset){
		return (needle[scan] == haystack[scan+offset] || needle[scan] == wildcard);
	}

	// metode som printer ut informasjon
	public void printAll() {
		System.out.println("Haystack:\t" + (new String(haystack)));
		System.out.println("Needle:\t\t" + (new String(needle)));
		for (int match : matchesFound) {
			System.out.print("Match found: ");
			for (int i = match; i<(match+needle.length); i++) {
				System.out.print(haystack[i]);
			}
			System.out.println(", at index: " + match);
		}
	}

	// metode som leser inn
	// antar at needle og haystack er skrevet på en linje
	public void readfile(String needleFile, String haystackFile){
		try {
			Scanner r = new Scanner(new File(needleFile));
			needle = r.nextLine().toCharArray();

			r = new Scanner(new File(haystackFile));
			haystack = r.nextLine().toCharArray();
			r.close();
		} catch (Exception e) {
			System.out.println("Error while reading files:");
			System.out.println(e);
			System.exit(0);
		}
	}
}