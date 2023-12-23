.global nineteen
.global _nineteen

nineteen:
_nineteen:
	push %ebp			#Pusher base-pointer
	movl %esp, %ebp		#Flytter stakk-pointer til base-pointer

	movl $19, %eax		#Flytter verdien 17 i registret

	pop %ebp			#Poper base-pointer tilbake
	ret
