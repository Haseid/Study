#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

/*
* Struct node for lenkeliste som kan holde en string 
*/
struct node
{
	char *s;
	struct node *next;
};

/*
* Funksjon som printer ut input-filen
*/
void print(struct node *root)
{
	while(root) {
		printf("\t%s", root->s);
		root = root->next;
	}
}

/*
* Funksjon som printer ut en tilfeldig linje fra input-filen
*/
void rdm(int listSize, struct node *root)
{
	srand(time(NULL)); /* Nullstiller rand-Funksjonen */
	int randomNr = rand()%listSize; /* Finner et tilfeldig tall mellom 0 til listSize-1 */
	int i;

	for (i = 0; i < randomNr; i++)
	{
		root = root->next;
	}
	printf("\t%s", root->s);
}

/*
* Funksjon som returnerer 1 om c er en vokal
* returnerer 0 om c ikke er en vokal
*/
int isVowels(char c)
{
	char *vowels = (char*)"aeiouy";
	int i;

	for (i = 0; i < strlen(vowels); i++)
	{
		if (c == vowels[i])
		{
			return 1;
		}
	}
	return 0;
}

/*
* Funksjon som finner alle vokaler i input-filen
* og erstatter dem med en og en vokal, til alle
* vokaler er gått gjennom
*/
void replace(struct node *root)
{
	char *vowels = (char*)"aeiouy"; /* All vowels */
	int i, j;

	for (i = 0; i < strlen(vowels); i++) /* Går gjennom alle vokalene */
	{	
		struct node *tmpRound = root;
		printf("Replace vowels with vowel '%c':\n\t", vowels[i]);

		while(tmpRound) /* Går gjennom nodelista */
		{
			for (j = 0; j < strlen(tmpRound->s); j++) /* Går gjennom alle char'ene */
			{
				if (isVowels(tmpRound->s[j])) /* Tester om det er en vokal */
				{
					printf("%c" ,vowels[i]);
				}
				else
				{
					printf("%c" ,tmpRound->s[j]);
				}
			}
			tmpRound = tmpRound->next;
			printf("\t");
		}
		printf("\n");
	}
}

/*
* Funksjon som fjerner alle vokalene til input-filen
*/
void rmv(struct node *root)
{	
	printf("Remove vowels:\n");
	int i;

	while(root) /* Går gjennom lista */
	{
		for (i = 0; i < strlen(root->s); i++) /* Går gjennom alle char'ene */
		{
			if (isVowels(root->s[i])) /* Tester om det er en vokal */
			{	
				memmove(&root->s[i], &root->s[i+1], strlen (root->s) - i); /* Supermetode som fjerner char på angitt plass */
			}
		}
		root = root->next;
	}
}

/*
* Funksjon som finner antall tegn i input-filen
* lengden kan varier utifra 'Æ', 'Ø' og 'Å'
*/
void len(struct node *root)
{
	int length = 0;

	while(root)
	{
		length += strlen(root->s);
		root = root->next;
	}
	printf("\tThe text is %d characters long\n", length);
}

/*
* Main funksjon
*/
int main(int argc, char *argv[])
{	
	struct node *root, *current;
	int listSize = 0;
	char line[80];
	FILE *file;

	/*
	* Sjekker om det er riktige argumenter
	*/
	if (argc != 3)
	{
		printf("\nUsage: %s 'command' 'input_file'\n", argv[0]);
		printf("\nwhere \"command\" is one of the following:\n");
		printf("\n\tprint\tprint input_file\n");
		printf("\trdm\tprint a random line\n");
		printf("\treplace\treplace the vowels with all the other vowels\n");
		printf("\trmv\tremove vowels\n");
		printf("\tlen\tprint the number of characters in the input_file\n\n");
		return -1;
	}

	/*
	* Innlesning av fil
	*/
	file = fopen (argv[2], "r");
	
	while(fgets(line, sizeof(line), file))
	{
		struct node *tmp = malloc(sizeof(struct node)); /* Allokerer minne til en node */
		tmp->s = malloc(sizeof(line)); /*Allokere minne til Stringen */
		strcpy(tmp->s, line); /* Lagrer linja med tekst */
		tmp->next = NULL; /* Safer at det ikke blir et minne i et minne */
		listSize++; /* Teller opp antall linjer */

		if (!(root && current)) /* Setter in det første ordet i lista */
		{
			root = current = tmp; 
		}
		else /* Setter in resten av ordene i lista */
		{
			current->next = tmp;
			current = tmp;
		}
	}
	fclose(file); /* Lokker filen */

	/* 
	* Kaller på oppgitt metode
	*/
	if (strcmp(argv[1], "print") == 0)
	{
		printf("\nCommand: %s\n", argv[1] );
		print(root);
	}
	else if (strcmp(argv[1], "rdm") == 0)
	{
		printf("\nCommand: %s\n", argv[1] );
		rdm(listSize, root);
	}
	else if (strcmp(argv[1], "replace") == 0)
	{
		printf("\nCommand: %s\n", argv[1] );
		replace(root);
	}
	else if (strcmp(argv[1], "rmv") == 0)
	{
		printf("\nCommand: %s\n", argv[1] );
		rmv(root);
		print(root);
	}
	else if (strcmp(argv[1], "len") == 0)
	{
		printf("\nCommand: %s\n", argv[1] );
		len(root);
	}
	printf("\n");
	return 0;
}