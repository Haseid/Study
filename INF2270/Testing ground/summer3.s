.global summer3
.global _summer3

summer3:
_summer3:
	
	movl $0, %eax			#eax til 0
	addl 8(%esp), %eax		#adder på tall 1 til eax
	addl 12(%esp), %eax		#adder på tall 2 til eax
	addl 16(%esp), %eax		#adder på tall 3 til eax
	ret