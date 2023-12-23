	.extern	fread, fwrite
	.text

# ---------------------------------------------------------------
	.globl	readbyte
 # Navn:		readbyte
 # Synopsis:	Leser en byte fra en binarfil.
 # C-signatur:	int readbyte (FILE *f)
 # Registre:	eax

readbyte:
	pushl	%ebp			# Standard funksjonsstart
	movl	%esp,%ebp		# 
	
	pushl	8(%ebp)			# para 4	f
	pushl	$1				# para 3	1
	pushl	$1				# para 2	1
	leal	-16(%ebp), %eax # loader addressen til para 1 i eax
	pushl	%eax			# para 1	b

	call	fread			# kaller på fread()
	
	cmpl	$0, %eax		# om eax >= 0
	jg		rb_if
	movl	$-1, %eax		# feil retur -1
	jmp	 rb_slutt

rb_if:
	movzb	-16(%ebp), %eax	# zero-extender svaret til eax
	jmp		rb_slutt

rb_slutt:
	popl 	%ebp			# fjerner parameterene fra stacken
	popl 	%ebp			#
	popl 	%ebp			#
	popl 	%ebp			#

	popl	%ebp			# Standard
	ret						# Retur

# ---------------------------------------------------------------
	.globl	readutf8char
 # Navn:		readutf8char
 # Synopsis:	Leser et Unicode-tegn fra en binarfil.
 # C-signatur:	long readutf8char (FILE *f)
 # Registre:
	
readutf8char:
	pushl	%ebp			# Standard funksjonsstart
	movl	%esp,%ebp		#

	pushl	8(%ebp)			# pusher FILE *f til stacken
	call	readbyte		# kaller på readbyte
	popl	%ebp			# fjerner parameterene fra stacken

	popl	%ebp			# Standard
	ret						# retur.

# ---------------------------------------------------------------
	.globl	writebyte
 # Navn:		writebyte
 # Synopsis:	Skriver en byte til en binarfil.
 # C-signatur:	void writebyte (FILE *f, unsigned char b)
 # Registre:	eax

writebyte:
	pushl	%ebp			# Standard funksjonsstart
	movl	%esp,%ebp		#

	pushl	8(%ebp)			# para 4	f
	pushl	$1				# para 3	1
	pushl	$1				# para 2	1
	leal	12(%ebp), %eax	# loader addressen til para 1 i eax
	pushl	%eax			# para 1	b

	call	fwrite			# kall på fwrite()

	popl	%ebp			# fjerner parameterene fra stacken
	popl	%ebp			#
	popl	%ebp			#
	popl	%ebp			#

	popl	%ebp			# Standard
	ret						# retur.

# ---------------------------------------------------------------
	.globl	writeutf8char
 # Navn:		writeutf8char
 # Synopsis:	Skriver et tegn kodet som UTF-8 til en binarfil.
 # C-signatur:	void writeutf8char (FILE *f, unsigned long u)
 # Registre:	eax, edx og ecx

writeutf8char:
		pushl	%ebp				# Standard funksjonsstart
		movl	%esp, %ebp			#

		subl	$24, %esp			# setter av plass til (4)byte vi kan trenge 
		movl	$0, -12(%ebp)		# setter 0 i -12(%ebp)

# Ser hvor mange bytes vi trenger
wutf_if_1b:							# 12(%edp) = unicode tegn
		cmpl	$0x7F, 12(%ebp)		# ser om 12(%ebp) <= 0x7F
		ja		wutf_if_2b
		movl	$1, -12(%ebp)		# da trenger vi bare 1 byte
		jmp	 	wutf_encode
wutf_if_2b:
		cmpl	$0x7FF, 12(%ebp) 	# ser om 12(%ebp) <= 0x7FF
		ja		wutf_if_3b
		movl	$2, -12(%ebp)		# da trenger vi 2 bytes
		jmp	 	wutf_encode
wutf_if_3b:
		cmpl	$0xFFFF, 12(%ebp)	# ser om 12(%ebp) <= 0xFFFF
		ja		wutf_if_4b
		movl	$3, -12(%ebp)		# da trenger vi 3 bytes
		jmp	 	wutf_encode
wutf_if_4b:							# antar her at 12(%ebp) <= 0x10FFFF
		movl	$4, -12(%ebp)		# da trenger vi 4 bytes
# nå vet vi hvor mange bytes vi trenger
# antal byte lagret i -12(%ebp)

# starter å kode om til UTF-8
wutf_encode:
		movl	-12(%ebp), %eax		# setter antall byte til "i"
		subl	$1, %eax			# trekker 1 fra "i" så løkka går riktig
		movl	%eax, -16(%ebp)		# bruker -16(%ebp) som "i" teller (nedover)

wutf_loop:
		cmpl	$0, -16(%ebp)		# ser om "i" > 0
		jle		wutf_was_1b			# ferdig med å encode mellom bytes, og hopper for fikse sluttbyte'et
		movl	12(%ebp), %eax		# kopierer unicode tegnet til eax
		andl	$0x3F, %eax			# &-oppererer unicode tegnet med 0x3F
		orl		$0x80, %eax			# |-oppererer nye eax med 0x80
		movl	%eax, %ecx			# flytter det inn i ecx
		leal	-21(%ebp), %edx		# kopierer adressen til -21(%edp) til edx, vi bruker -21(%ebp) til å lage / lagre utf8 tegnet
		movl	-16(%ebp), %eax		# kopierer "i" til eax
		addl	%edx, %eax			# adder edx til eax
		movb	%cl, (%eax)			# kopierer 8 bit fra %cl(low byte av ecx - 8 bit) til eax
		shrl	$6, 12(%ebp)		# bit-shitfer mot høyre på 12(%ebp) med 6, "c >> 6"
		subl	$1, -16(%ebp)		# trekker fra 1 på "i"
		jmp		wutf_loop

# håndtere bakdelen av UTF-8 encode'en
# sjekker svare fra byte-mengde sjekken og håndtere
# vært enkelttilfelle av byte-mengden til tegnet
wutf_was_1b:						# ser om det er 1 byte
		movl	12(%ebp), %eax		# flytter unicode tegnet til eax
		cmpl	$1, -12(%ebp)		# ser om det er 1 byte
		jne		wutf_was_2b
		andl	$0x7F, %eax			# &-oppererer unicode tegnet med 0x7F
									# regner med msb er 0
		jmp		wutf_funckaller
wutf_was_2b:
		cmpl	$2, -12(%ebp)		# ser om det er 2 bytes
		jne		wutf_was_3b
		andl	$0x1F, %eax			# &-oppererer unicode tegnet med 0x1F
		orl		$0xC0, %eax			# |-oppererer unicode tegnet med 0xC0, legger på 110xxxxx
		jmp		wutf_funckaller
wutf_was_3b:
		cmpl	$3, -12(%ebp)		# ser om det er 3 bytes
		jne		wutf_was_4b
		andl	$0xF, %eax			# &-oppererer unicode tegnet med 0xF
		orl		$0xE0, %eax			# |-oppererer unicode tegnet med 0xE0, legger på 1110xxxx
		jmp		wutf_funckaller
wutf_was_4b:							# antar her at det er 4 bytes
		andl	$0x7, %eax			# &-oppererer unicode tegnet med 0x7
		orl		$0xF0, %eax			# |-oppererer unicode tegnet med 0xF0, legger på 11110xxx

wutf_funckaller:					# looper gjennom antall bytr for å bruke funcsjonen writebyte
		movb	%al, -21(%ebp)		# kopierer 8 bit fra %al(low byte av eax - 8 bit) til -21(%ebp)
		movl	$0, -16(%ebp)		# bruker -16(%ebp) som "i" teller (oppover)

wutf_loop2:
		movl	-16(%ebp), %eax		# oppdaterer middlertidig "i"
		cmpl	-12(%ebp), %eax		# bruker antall byte vi trengte til å vite hvor mange ganger vi looper
		jge		wutf_slutt			# vi er ferdig
		leal	-21(%ebp), %edx		# kopierer adressen til -21(%ebp) til  edx
		addl	%edx, %eax			# legger til edx i eax
		movzbl	(%eax), %eax		# gjør om eax til zero-extended long så vi bare får 1 byte
		subl	$8, %esp			# rydde opp i "reservert" minne i esp
		pushl	%eax				# push'er eax (1 byte) til stacken	
		pushl	8(%ebp)				# push'er FILE *f til stacken 
		call	writebyte			# kaller på writebyte
		addl	$16, %esp			# rydde opp i "reservert" minne i esp
		addl	$1, -16(%ebp)		# legger til "i" i loopen
		jmp		wutf_loop2

wutf_slutt:
		mov		%ebp, %esp			# rydde opp i "reservert" minne i esp
		pop		%ebp				# Standard
		ret							# Retur
