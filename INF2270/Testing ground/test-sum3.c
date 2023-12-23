#include <stdio.h>

extern int sum3 (int a, int b, int c);

int main (void)
{
	printf("sum3(8, 16, 1) return = %d\n", sum3(8, 16, 1));
	return 0;
}