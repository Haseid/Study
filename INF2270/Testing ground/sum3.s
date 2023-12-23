.global sum3
.global _sum3

sum3:
_sum3:
	push %ebp				#dytter ebp(base-pointer) til stack'en
	movl %esp, %ebp			#flytter esp(stack-pointer) til ebp(base-pointer)

	movl 8(%ebp), %eax		#flytter ebp[8] til eax
	addl 12(%ebp), %eax		#adder ebp[12] til eax
	addl 16(%ebp), %eax		#adder ebp[16] til eax

	pop %ebp				#reset'er ebp(base-pointer)
	ret
