André Betz http://www.andrebetz.de 2003

(Alle Programme laufen mit Java 1.4.2)

1. Verzeichnisse
----------------
MakroAssembler
TMDefs
TMSimulator
Konverter


Erläuterungen zu den RAMonTM(...).tm Files in TMDDefs
-----------------------------------------------------

Die Maschine RAMonTM(3Bit).tm ist im Buch 'Das eindimensionale Universum' vorgestellt 
und erläutert. RAMonTM(3Bit)Light.tm enthält nur 2 CPU-Befehle, nämlich BNZ und INC,
die vollkommen für die universalität ausreichen.

RAMonTM(2Bit).tm ist eine Neuimplementation der Maschine RAMonTM(3Bit)Light.tm und auf 
4 Symbole reduziert. Weiterhin ist es nicht notwendig das Band vorzuinitialisieren.

RAMonTM(1Bit).tm ist mit Hilfe des Konverters Convert.java entstandene 2 Symbole-
Maschine aus RAMonTM(2Bit).tm. Die 4 Symbole wurden zu: 0 -> 00, 1-> 01, * -> 10, H -> 11
konvertiert

Erläuterungen zur Turing-Maschine in TMSimulator
-----------------------------------------------
Compiliere alle *.java-Files und starte TMGui.bat. Öffne mit 'open' eine der Maschinen *.tm
Mit der Taste 'Step' wird die Maschine soviel Schritte abgearbeitet, wie in
dem Edit-Feld daneben steht. Die ANzahl '0' bedeutet, dass dei Maschine bi szum Ende
läuft (Vorsicht bei unendlichen Schleifen -> mit Taskmanager beenden). Mit der
Taste 'Reset' beginnt die Maschine wieder von vorne.

Erläuterungen zu den Konvertern
-------------------------------

Der Konverter 'Converter' ist in der Lage eine Maschine mit 4 Symbolen (0,1,*,H)
in eine Maschine mit nur 2 Symbolen zu transformieren. Dies geschieht mit
dem Java-Programm Converter.java. Als Konsoleneingabe erwartet es den Datei-
Namen einer Turingmaschine. Die BatchDatei ConvRam2Bit2Ram1Bit.bat wandelt
automatisch die RAMonTM(2Bit).tm in eine 2 Symbol-Maschine um und erzeugt die
Datei RAMonTM(1Bit).tm. Aus jedem Zustand werden 19 Zustände erzeugt.

Die 4 Symbole werden zu: 0 -> 00, 1-> 01, * -> 10, H -> 11
Diese Konversion ist ein nachtraegliche Ergänzung zum Buch, in der der Beweis fehlt, dass eine
Maschine mit n-Symbolen gleichwertig mit einer Maschine mit 2 Symbolen ist.
Die Konversion erfolgt nach folgendem Prinzip:

           11:->W0(W)|W1(W)|W*(W)|wH(W)->GoR2(M)|GoL2(M)->S2
          /
        1/\10:->W0(W)|W1(W)|W*(W)|wH(W)->GoR2(M)|GoL2(M)->S2
        /  
Read(S1)\  01:->W0(W)|W1(W)|W*(W)|wH(W)->GoR2(M)|GoL2(M)->S2
        0\/
          \00:->W0(W)|W1(W)|W*(W)|wH(W)->GoR2(M)|GoL2(M)->S2

Der Konverter Lif2Var.java wandelt ein File mit der Endung *.lif, das eine Beschreibung eines 
zellularen Automaten beinhaltet in eine eindimensionale variable um, die dann im Assembler-
Programm ZAonRAM.asm eingefügt werden kann und dies dann ausführt. Somit ist es möglich
ZAs, die mit speziellen ZA-Programmen entworfen worden sind auf einer Turing Maschine zu simulieren.

Der Konverter TM2TS.java wandelt eine Turing Machine mit 2 Symbolen (0,1) in ein Tag System mit P=2
um. Dies ist nach der Beschreibung "Universality of TAG Systems with p=2", von John Cocke und Marvin Minsky
implementiert. 

Der Konverter TS2CTS.java konvertiert ein Tag System in ein Cyclic Tag System um. Dabei
bedeutet der Schalter Rule110, dass dei speziellen bedingungen für Regel110 beachtet werden.


Der Konverter CT2Rule110.java wandelt ein Cyclic Tag System in eine Konfiguration für die 
Regel110 um. Dazu müssen die Bedingungen, die bei der Umwandlung von TS2CTS.java gelten
beachtet werden.

MakroAssembler
--------------

Der makroAssembler wandelt einen AssemblerCode in eine Folge aus 4 Zeichen um, die von 
RAMonTM2Bit interpretiert werden und ausgeführt werden kann. Mit dem Converter kann dann
diese TuringBandDefinition in die 1BitMaschine umgewandelt werden. Im Ordner TMNDefs sind 
aus dem Buch bekannten Makros in den Files mit *.asm.

Der Assembler hat folgende Synthax:
MACRO_DEF := 'MACRO',':',<Name>,'(',[VAR],')','\n',BEFEHL|ZAHL,'END';
BEFEHL := BEFEHL,'\n',BEFEHL,'\n';
BEFEHL := [<labe>,':'],<Name>,'(',[VAR],')';
VAR := VAR,',',VAR;
VAR := [<label>:]<Name>;
ZAHL := <labe>,':',ZIFFER;
ZIFFER := ZIFFER,',',ZIFFER;
ZIFFER := '0','1','2','3','4','5','6','7','8','9';
Das letzte Makro ist das StartMakro

TMonRAM.asm sind die Makros für die Simulation der TuringMaschine auf der RAM-Maschine
ZAonRAM.asm sind die Makros für die Simulation eines zellularen Automaten (Game of Life)


UTMonGOL
--------

Im Ordner TMDefs befinden sich Files mit der Endung *.lif. Diese sind definitionen für 
einen zellularen Automaten (Game of Life)

UTMonGOL.lif ist eine SImulation einer Turing Maschine in Game of Life (siehe S88)
Paradies.lif ist ein Beispiel für eine Konstruktion in Game of Life, die nicht produziert, 
hergeleitet werden kann.



Viel Spass damit
mail@andrebetz.de

