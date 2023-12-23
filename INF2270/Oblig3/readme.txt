Jeg ble ikke helt ferdig med min besvarelse, fikk
til 6/8 tester

"cmpl	a, b" kommenterer jeg som sjekken, selv om
det er jumpet etterpå som fagtisk viser hvilken
opperasjon du gjør.

Jeg har gått ut i fra denne algoritmen:
http://www.herongyang.com/Unicode/UTF-8-UTF-8-Encoding-Algorithm.html
Jeg har optimalisert den og endret den så den
skulle være lettere å skrive i assembler.
Jeg er ganske sikker på at jeg har bommet noen
steder på å frigjøre noen plasser.

Resten av oppgaven er å gjøre om UTF-8 til
Unicode, jeg har ikke fått gjort dette, men jeg
har en rimelig god tanke om hvordan; først finne
hvor mange bytes det er (finner fort ut pga UTF8
byte indexeringen), så splitte opp indexeringen
fra verdien og legge det sammen til slutt.

Jeg har jobbet og forvekslet ideer med:
William Haugan (williha)
