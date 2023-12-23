INF2270 - Oblig1 - Lars Haseid (larshalv)

Oppgaven går ut på å multiplisere et 4-bits tall med +/- 10(des).
Måten jeg løste dette på var å multiplisere tallet først med 2,
så multiplisere tallet med 8, også plusse de to tallene med
hverandre.

Når jeg skal multiplisere et binært tall med 2 trenger jeg bare
å legge på en 0 bakerst, så når jeg skal multiplisere med 8 kan
jeg legge på tre 0 bakerst. så det jeg gjør er å bit-shift'e.

Etter det legger jeg de to tallene sammen ved hjelp av ett
halv-adder og seks full-adder'e. 

Når jeg skal multiplisere tallet med -10 må jeg gjøre produktet
negativt, da inverterer jeg tallet og plusser på 1 og legger på
fortegnsbit.

Sannhetsverditabell:

F  A3 A2 A1 A0 ⎟ C  V  S7 S6 S5 S4 S3 S2 S1 S0
----------------------------------------------
0   0  0  0  0 ⎟ 0  0   0  0  0  0  0  0  0  0
0   0  0  0  1 ⎟ 0  0   0  0  0  0  1  0  1  0
0   0  0  1  0 ⎟ 0  0   0  0  0  1  0  1  0  0
0   0  0  1  1 ⎟ 0  0   0  0  0  1  1  1  1  0
0   0  1  0  0 ⎟ 0  0   0  0  1  0  1  0  0  0
0   0  1  0  1 ⎟ 0  0   0  0  1  1  0  0  1  0
0   0  1  1  0 ⎟ 0  0   0  0  1  1  1  1  0  0
0   0  1  1  1 ⎟ 0  0   0  1  0  0  0  1  1  0
0   1  0  0  0 ⎟ 0  0   0  1  0  1  0  0  0  0
0   1  0  0  1 ⎟ 0  0   0  1  0  1  1  0  1  0
0   1  0  1  0 ⎟ 0  0   0  1  1  0  0  1  0  0
0   1  0  1  1 ⎟ 0  0   0  1  1  0  1  1  1  0
0   1  1  0  0 ⎟ 0  0   0  1  1  1  1  0  0  0
0   1  1  0  1 ⎟ 0  0   1  0  0  0  0  0  1  0
0   1  1  1  0 ⎟ 0  0   1  0  0  0  1  1  0  0
0   1  1  1  1 ⎟ 0  0   1  0  0  1  0  1  1  0
1   0  0  0  0 ⎟ 1  1   0  0  0  0  0  0  0  0
1   0  0  0  1 ⎟ 0  1   1  1  1  1  0  1  1  0
1   0  0  1  0 ⎟ 0  1   1  1  1  0  1  1  0  0
1   0  0  1  1 ⎟ 0  1   1  1  1  0  0  0  1  0
1   0  1  0  0 ⎟ 0  1   1  1  0  1  1  0  0  0
1   0  1  0  1 ⎟ 0  1   1  1  0  0  1  1  1  0
1   0  1  1  0 ⎟ 0  1   1  1  0  0  0  1  0  0
1   0  1  1  1 ⎟ 0  1   1  0  1  1  1  0  1  0
1   1  0  0  0 ⎟ 0  1   1  0  1  1  0  0  0  0
1   1  0  0  1 ⎟ 0  1   1  0  1  0  0  1  1  0
1   1  0  1  0 ⎟ 0  1   1  0  0  1  1  1  0  0
1   1  0  1  1 ⎟ 0  1   1  0  0  1  0  0  1  0
1   1  1  0  0 ⎟ 0  1   1  0  0  0  1  0  0  0
1   1  1  0  1 ⎟ 0  1   0  1  1  1  1  1  1  0
1   1  1  1  0 ⎟ 0  1   0  1  1  1  0  1  0  0
1   1  1  1  1 ⎟ 0  1   0  1  1  0  1  0  1  0