#include <stdio.h>
#include <string.h>

/*
* Funksjon som tar imot bitverdi og gjør den
* om til anigtt char
*/
char transform(int i)
{
	if (i == 0)			/* 00 */
		return ' ';
	else if (i == 1)	/* 01 */
		return ':';
	else if (i == 2)	/* 10 */
		return '@';
	else if (i == 3)	/* 11 */
		return '\n';
	return '1'; 		/* Fjerner en return warning */

}

/*
* Funksjon som pakker ut den komprimerte fila
*/
void decode(char *input_file)
{
	FILE *read;
	read = fopen(input_file, "r");
	char c, tmp[4];
	int counter = 0;

	while((c = fgetc(read)) != EOF) /* Leser inn ett og ett tegn */
	{
		/*
		* 4 verdier som tar sin fjerde-del av den komprimerte byten 
		*/
		tmp[0] = transform((c&198) >> 6);	/* xx 00 00 00 */
		tmp[1] = transform((c&48) >> 4);	/* 00 xx 00 00 */
		tmp[2] = transform((c&12) >> 2);	/* 00 00 xx 00 */
		tmp[3] = transform(c&3);			/* 00 00 00 xx */
		printf("%c%c%c%c", tmp[0], tmp[1], tmp[2], tmp[3]);
	}
	printf("\n");
	fclose(read);

}

/*
* Komprimerings funskjon, tar in input-fil som skal
* komprimeres til gitt ut-fil
*/
void encode(char *input_file, char *output_file)
{
	FILE *read;
	FILE *write;
	char c, e;
	int tmp;
	int counter = 0;
	read = fopen(input_file, "r");
	write = fopen(output_file, "w");

	while((c = fgetc(read))!= EOF) /* Leser inn hele input_file */
	{
		if (c == ' ')			/* 00 */
			tmp = 0;
		else if (c == ':')		/* 01 */
			tmp = 1;
		else if (c == '@')		/* 10 */
			tmp = 2;
		else if (c == '\n')		/* 11 */
			tmp = 3;

		if (counter == 0)		/* xx 00 00 00 */
		{
			e |= tmp << 6;
			counter++;
		}
		else if (counter == 1)	/* 00 xx 00 00 */
		{
			e |= tmp << 4;
			counter++;
		}
		else if (counter == 2)	/* 00 00 xx 00 */
		{
			e |= tmp << 2;
			counter++;
		}
		else if (counter == 3)	/* 00 00 00 xx */
		{
			e |= tmp;
			fprintf(write, "%c",e );	/* Lagrer 4 tegn til en spesiell char */
			counter = 0;
			e = '\0';			/* Nullstiller e */
		}
	}
	fclose(read);
	fclose(write);
}

/*
* Funksjon som skriver ut veiledning til hvordan
* man bruker programmet
*/
void feilmeld(char *s)
{
	printf("\nUsage: %s 'command' 'input_file' 'output_file'\n", s);
	printf("\nwhere \"command\" is one of the following:\n");
	printf("\n\tp\tprint input_file\n\t\t(output_file is ignored if specified)\n");
	printf("\te\tencode/compress input_file to output_file\n");
	printf("\td\tdecode/uncompress and print input_file\n\t\t(output_file is ignored if specified)\n\n");
}

/*
* Main funksjonen
*/
int main(int argc, char *argv[])
{
	char line[80];
	FILE *file;

	if (argc != 4)						/* Feil argumenter */
	{	
		feilmeld(argv[0]);
		return -1;
	}

	if (!strcmp(argv[1], "p"))			/* komando p (print) */
	{
		file = fopen (argv[2], "r");

		while(fgets(line, sizeof(line), file))
		{
			printf("%s", line);
		}
		printf("\n");
		fclose(file);
	}
	else if (!strcmp(argv[1], "e"))		/* komando e (encode) */
		encode(argv[2], argv[3]);
	else if (!strcmp(argv[1], "d"))		/* komando d (decode) */
		decode(argv[2]);
	else								/* Feil komando, skriver ut feilmelding */
	{
		feilmeld(argv[0]);
		return -1;
	}
}














































