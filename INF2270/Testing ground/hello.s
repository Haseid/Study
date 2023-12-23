	.section __DATA,__data
str:
	.asciz "Hello world!\n"

	.section __TEXT,__text
	.global printer
	.globl _printer

printer:
_printer:
	pushl	%ebp		# Standard funksjonsstart
	movl	%esp,%ebp	#

	pushl	str

	call _printf

	popl	%ebp		# Standard
	ret	
