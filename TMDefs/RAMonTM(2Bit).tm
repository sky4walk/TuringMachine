# RAM-Simulation (C) 2003 by www.andrebetz.de
# ===========================================
# 4 Symbole: 0,1,*,H (2 Bit)
# Befehle:  BNZ(p,j) := 0, INC(p)   := 1
# BNZ(p,j) := if [p]!= 0 then PC = j else PC = (PC + 3) mod N
# INC(p)   := [p] = ([p] + 1) mod N, PC = (PC + 2) mod N
# RAM Aufbau: *PC*AD*BNZ*ppp*jjj*INC*ppp*
#
# Aufbau-Schema
# -------------
# sucheFeld(PC)
# kopiere([PC+1],AD)
# if befehl(PC) == 0 then
#    sucheFeld(AD)
#    if [AD] == 0 then
#       PC = (PC + 3) mod N
#       Return
#    else
#       sucheFeld(PC)
#       kopiere([PC+2],PC)
#       Return
#    end
# else
#    sucheFeld(AD)
#    [AD] = ([AD] + 1) mode N
#    PC = (PC + 2) mod N
#    Return
# end
# 
# Initialisierung
(S00,0)
#
# Turing Band
# PC  AD  F1      F2      F3      F4      F5      F6      F7      F8
(*000*000*000*001*001*011*010*000*011*000*100*000*101*000*110*000*111*000*)
#
# Transitionen
#    0               1               *             H
# Suchen der angegebenen Speicherzelle: sucheFeld(PC)
(S00,0,S01,H,R) (S00,1,S04,H,R) (S00,*,S00,*,R) (S00,H,---,H,R)
#0
(S01,0,S01,0,R) (S01,1,S01,1,R) (S01,*,S02,*,R) (S01,H,---,H,R)
(S02,0,S02,0,R) (S02,1,S02,1,R) (S02,*,S03,*,R) (S02,H,---,H,R)
(S03,0,S07,H,L) (S02,1,S23,1,R) (S03,*,---,*,R) (S03,H,---,H,R)
#1
(S04,0,S04,0,R) (S04,1,S04,1,R) (S04,*,S05,*,R) (S04,H,---,H,R)
(S05,0,S05,0,R) (S05,1,S05,1,R) (S05,*,S06,*,R) (S05,H,---,H,R)
(S06,0,S26,0,R) (S06,1,S09,H,L) (S06,*,---,*,R) (S06,H,---,H,R)
# 00 rueck
(S07,0,S07,0,L) (S07,1,S07,1,L) (S07,*,S07,*,L) (S07,H,S08,0,R)
(S08,0,S15,H,R) (S08,1,S17,H,R) (S08,*,S15,H,R) (S07,H,---,H,R)
# 01 rueck
(S09,0,S09,0,L) (S09,1,S09,1,L) (S09,*,S09,*,L) (S09,H,S10,1,R)
(S10,0,S19,H,R) (S10,1,S21,H,R) (S10,*,S21,H,R) (S10,H,---,H,R)
# 10 rueck
(S11,0,S11,0,L) (S11,1,S11,1,L) (S11,*,S11,*,L) (S11,H,S12,0,R)
(S12,0,---,H,R) (S12,1,---,H,R) (S12,*,---,H,R) (S11,H,---,H,R)
# 11 rueck
(S13,0,S13,0,L) (S13,1,S13,1,L) (S13,*,S13,*,L) (S13,H,S14,1,R)
(S14,0,S19,H,R) (S14,1,S21,H,R) (S14,*,S21,H,R) (S13,H,---,H,R)
# 00 vor
(S15,0,S15,0,R) (S15,1,S15,1,R) (S15,*,S15,*,R) (S15,H,S16,0,R)
(S16,0,S07,H,L) (S16,1,S23,1,R) (S16,*,C00,H,R) (S15,H,---,H,R)
# 01 vor
(S17,0,S17,0,R) (S17,1,S17,1,R) (S17,*,S17,*,R) (S17,H,S18,0,R)
(S18,0,S26,0,R) (S18,1,S13,H,L) (S18,*,C00,*,R) (S17,H,---,H,R)
# 10 vor
(S19,0,S19,0,R) (S19,1,S19,1,R) (S19,*,S19,*,R) (S19,H,S20,1,R)
(S20,0,S07,H,L) (S20,1,S23,1,R) (S20,*,C00,*,R) (S19,H,---,H,R)
# 11 vor
(S21,0,S21,0,R) (S21,1,S21,1,R) (S21,*,S21,*,R) (S21,H,S22,1,R)
(S22,0,S26,0,R) (S22,1,S13,H,L) (S22,*,C00,H,R) (S21,H,---,H,R)
# nicht gefunden 0
(S23,0,S23,0,R) (S23,1,S23,1,R) (S23,*,S24,*,R) (S24,H,---,H,R)
(S24,0,S24,0,R) (S24,1,S24,1,R) (S24,*,S25,H,L) (S24,H,HLT,H,L)
(S25,0,S25,0,L) (S25,1,S25,1,L) (S25,*,S25,*,L) (S25,H,S29,0,L)
# nicht gefunden 1
(S26,0,S26,0,R) (S26,1,S26,1,R) (S26,*,S27,*,R) (S26,H,---,H,R)
(S27,0,S27,0,R) (S27,1,S27,1,R) (S27,*,S28,H,L) (S27,H,HLT,H,L)
(S28,0,S28,0,L) (S28,1,S28,1,L) (S28,*,S28,*,L) (S28,H,S29,1,L)
# zurück
(S29,0,S29,0,L) (S29,1,S29,1,L) (S29,*,S30,*,R) (S29,H,---,H,R)
#von vorne
(S30,0,S31,H,R) (S30,1,S33,H,R) (S30,*,S30,*,R) (S30,H,---,H,R)
#0
(S31,0,S31,0,R) (S31,1,S31,1,R) (S31,*,S31,*,R) (S31,H,S32,*,R)
(S32,0,S07,H,L) (S32,1,S23,1,R) (S32,*,---,*,R) (S32,H,---,H,R)
#1
(S33,0,S33,0,R) (S33,1,S33,1,R) (S33,*,S33,*,R) (S33,H,S34,*,R)
(S34,0,S26,0,R) (S34,1,S09,H,L) (S34,*,---,*,R) (S34,H,---,H,R)

#Kopiere Adresse in das Adress-Feld: kopiere([PC+1],AD)
#gehe zum naechsten Feld
(C00,0,C00,0,R) (C00,1,C00,1,R) (C00,*,C01,*,R) (C00,H,---,H,R)
(C01,0,C01,0,R) (C01,1,C01,1,R) (C01,*,C02,*,R) (C01,H,---,H,R)
#nimm erstes Zeichen
(C02,0,C03,H,L) (C02,1,C08,H,L) (C02,*,END,*,R) (C02,H,---,H,R)
#merke 0 und gehe nach links zum H
(C03,0,C03,0,L) (C03,1,C03,1,L) (C03,*,C03,*,L) (C03,H,C04,H,L)
(C04,0,C04,0,L) (C04,1,C04,1,L) (C04,*,C04,*,L) (C04,H,C05,*,R)
(C05,0,C06,0,R) (C05,1,C06,0,R) (C05,*,---,*,R) (C05,H,---,H,R)
(C06,0,C07,H,R) (C06,1,C07,H,R) (C06,*,C20,H,R) (C06,H,---,H,R)
(C07,0,C07,0,R) (C07,1,C07,1,R) (C07,*,C07,*,R) (C07,H,C08,H,R)
(C08,0,C08,0,R) (C08,1,C08,1,R) (C08,*,C08,*,R) (C08,H,C15,0,R)
#merke 1 und gehe nach links zum H
(C09,0,C09,0,L) (C09,1,C09,1,L) (C09,*,C09,*,L) (C09,H,C10,H,L)
(C10,0,C10,0,L) (C10,1,C10,1,L) (C10,*,C10,*,L) (C10,H,C11,*,R)
(C11,0,C12,1,R) (C11,1,C12,1,R) (C11,*,---,*,R) (C11,H,---,H,R)
(C12,0,C13,H,R) (C12,1,C13,H,R) (C12,*,C22,H,R) (C12,H,---,H,R)
(C13,0,C13,0,R) (C13,1,C13,1,R) (C13,*,C13,*,R) (C13,H,C14,H,R)
(C14,0,C14,0,R) (C14,1,C14,1,R) (C14,*,C14,*,R) (C14,H,C15,1,R)
# Auswahl 0 oder 1
(C15,0,C16,H,L) (C15,1,C18,H,L) (C15,*,---,*,R) (C15,H,---,H,R)
#merke 0
(C16,0,C16,0,L) (C16,1,C16,1,L) (C16,*,C16,*,L) (C16,H,C17,H,L)
(C17,0,C17,0,L) (C17,1,C17,1,L) (C17,*,C17,*,L) (C17,H,C06,0,R)
#merke 1
(C18,0,C18,0,L) (C18,1,C18,1,L) (C18,*,C18,*,L) (C18,H,C19,H,L)
(C19,0,C19,0,L) (C19,1,C19,1,L) (C19,*,C19,*,L) (C19,H,C12,1,R)
# aufräumen 0
(C20,0,C20,0,R) (C20,1,C20,1,R) (C20,*,C20,*,R) (C20,H,C21,H,R)
(C21,0,C21,0,R) (C21,1,C21,1,R) (C21,*,C21,*,R) (C21,H,C24,0,R)
# aufräumen 1
(C22,0,C22,0,R) (C22,1,C22,1,R) (C22,*,C22,*,R) (C22,H,C23,H,R)
(C23,0,C23,0,R) (C23,1,C23,1,R) (C23,*,C23,*,R) (C23,H,C24,1,R)
#zurück zur Befehlsbestimmung
(C24,0,C24,0,L) (C24,1,C24,1,L) (C24,*,C24,*,L) (C24,H,B00,*,R)

#Befehl aussuchen: if befehl(PC+1) == 0 then
(B00,0,B00,0,R) (B00,1,B03,1,L) (B00,*,B01,*,L) (B00,H,---,H,R)
(B01,0,B01,0,L) (B01,1,B01,1,L) (B01,*,B01,*,L) (B01,H,B02,H,L)
(B02,0,B02,0,L) (B02,1,B02,1,L) (B02,*,IS00,*,R) (B02,H,---,H,R)
(B03,0,B03,0,L) (B03,1,B03,1,L) (B03,*,B03,*,L) (B03,H,B04,H,L)
(B04,0,B04,0,L) (B04,1,B04,1,L) (B04,*,AS00,*,R) (B04,H,---,H,R)

#BNZ-Befehl
#Finde Feld mit dieser Adresse in AD: sucheFeld(AD)
(IS00,0,IS01,H,R) (IS00,1,IS03,H,R) (IS00,*,---,*,R) (IS00,H,---,*,L)

#0 gemerkt
(IS01,0,IS01,0,R) (IS01,1,IS01,1,R) (IS01,*,IS01,*,R) (IS01,H,IS02,*,R)
(IS02,0,IS05,H,L) (IS02,1,IS24,1,R) (IS02,*,---,*,R) (IS02,H,---,H,R)

#1 gemerkt
(IS03,0,IS03,0,R) (IS03,1,IS03,1,R) (IS03,*,IS03,*,R) (IS03,H,IS04,*,R)
(IS04,0,IS27,0,L) (IS04,1,IS07,H,L) (IS04,*,---,*,R) (IS04,H,---,H,R)

#00 gemerkt rueck
(IS05,0,IS05,0,L) (IS05,1,IS05,1,L) (IS05,*,IS05,*,L) (IS05,H,IS06,0,R)
(IS06,0,IS13,H,R) (IS06,1,IS17,H,R) (IS06,*,IS21,H,R) (IS06,H,----,0,R)

#01 gemerkt rueck
(IS07,0,IS07,0,L) (IS07,1,IS07,1,L) (IS07,*,IS07,*,L) (IS07,H,IS08,1,R)
(IS08,0,IS15,H,R) (IS08,1,IS19,H,R) (IS08,*,IS22,H,R) (IS08,H,----,0,R)

#10 gemerkt rueck
(IS09,0,IS09,0,L) (IS09,1,IS09,1,L) (IS09,*,IS09,*,L) (IS09,H,IS10,0,R)
(IS10,0,IS24,0,R) (IS10,1,IS24,1,R) (IS10,*,IS21,H,R) (IS10,H,----,0,R)

#11 gemerkt rueck
(IS11,0,IS11,0,L) (IS11,1,IS11,1,L) (IS11,*,IS11,*,L) (IS11,H,IS12,1,R)
(IS12,0,IS15,H,R) (IS12,1,IS19,H,R) (IS12,*,IS22,H,R) (IS12,H,----,H,R)

#00 gemerkt vor
(IS13,0,IS13,0,R) (IS13,1,IS13,1,R) (IS13,*,IS13,*,R) (IS13,H,IS14,0,R)
(IS14,0,IS05,H,L) (IS14,1,IS24,1,R) (IS14,*,----,*,R) (IS14,H,----,0,R)

#01 gemerkt vor
(IS15,0,IS15,0,R) (IS15,1,IS15,1,R) (IS15,*,IS15,*,R) (IS15,H,IS16,1,R)
(IS16,0,IS05,H,L) (IS16,1,IS24,1,L) (IS16,*,----,*,R) (IS16,H,----,0,R)

#10 gemerkt vor
(IS17,0,IS17,0,R) (IS17,1,IS17,1,R) (IS17,*,IS17,*,R) (IS17,H,IS18,0,R)
(IS18,0,IS28,0,L) (IS18,1,IS11,H,L) (IS18,*,----,*,R) (IS18,H,----,H,R)

#11 gemerkt vor
(IS19,0,IS19,0,R) (IS19,1,IS19,1,R) (IS19,*,IS19,*,R) (IS19,H,IS20,1,R)
(IS20,0,IS28,0,L) (IS20,1,IS11,H,L) (IS20,*,----,*,R) (IS20,H,----,0,R)

#0 gemerkt Ende
(IS21,0,IS21,0,R) (IS21,1,IS21,1,R) (IS21,*,IS21,*,R) (IS21,H,IT00,0,R)
#1 gemerkt Ende
(IS22,0,IS22,0,R) (IS22,1,IS22,1,R) (IS22,*,IS22,*,R) (IS22,H,IT00,1,R)
#Teste Feld auf Null: if [AD] == 0 then
(IT00,0,IT00,0,R) (IT00,1,IT00,1,R) (IT00,*,IT01,*,R) (IT00,H,---,H,R)
(IT01,0,IT01,0,R) (IT01,1,IC00,1,L) (IT01,*,IN00,*,L) (IT01,H,---,H,R)

#0 gemerkt falsch
(IS24,0,IS24,0,R) (IS24,1,IS24,1,R) (IS24,*,IS25,*,R) (IT24,H,---,H,R)
(IS25,0,IS25,0,R) (IS25,1,IS25,1,R) (IS25,*,IS26,H,L) (IT25,H,---,H,R)
(IS26,0,IS26,0,L) (IS26,1,IS26,1,L) (IS26,*,IS26,*,L) (IS26,H,IS27,0,L)
(IS27,0,IS27,0,L) (IS27,1,IS27,1,L) (IS27,*,IS00,*,R) (IT27,H,---,H,R)

#1 gemerkt falsch
(IS28,0,IS28,0,R) (IS28,1,IS28,1,R) (IS28,*,IS29,*,R) (IT28,H,---,H,R)
(IS29,0,IS29,0,R) (IS29,1,IS29,1,R) (IS29,*,IS30,H,L) (IT29,H,---,H,R)
(IS30,0,IS30,0,L) (IS30,1,IS30,1,L) (IS30,*,IS30,*,L) (IS30,H,IS31,1,L)
(IS31,0,IS31,0,L) (IS31,1,IS31,1,L) (IS31,*,IS00,*,R) (IT31,H,---,H,R)

#Adresse im ersten Feld (PC) um 3 erhöhen: PC = (PC + 3) mod N
(IN00,0,IN00,0,L) (IN00,1,IN00,1,L) (IN00,*,IN00,*,L) (IN00,H,IN01,*,L) 
(IN01,0,IN01,0,L) (IN01,1,IN01,1,L) (IN01,*,IN02,*,L) (IN01,H,---,H,R)
#um 3 erhöhen
(IN02,0,IN04,1,L) (IN02,1,IN03,0,L) (IN02,*,---,H,R)  (IN02,H,---,H,R)
(IN03,0,IN04,1,L) (IN03,1,IN03,1,L) (IN03,*,IN05,*,R) (IN03,H,---,H,R)
(IN04,0,IN04,0,L) (IN04,1,IN04,1,L) (IN04,*,IN05,*,R) (IN04,H,---,H,R)
#und nochmal um eins erhöhen
(IN05,0,IN05,0,R) (IN05,1,IN05,1,R) (IN05,*,IN06,*,L) (IN05,H,---,H,R)
(IN06,0,IN08,1,L) (IN06,1,IN07,0,L) (IN06,*,---,*,R)  (IN06,H,---,H,R)
(IN07,0,IN08,1,L) (IN07,1,IN07,1,L) (IN07,*,IN09,*,R) (IN07,H,---,H,R)
(IN08,0,IN08,0,L) (IN08,1,IN08,1,L) (IN08,*,IN09,*,R) (IN07,H,---,H,R)
# und das letzte Mal, dann haben wir 3
(IN09,0,IN09,0,R) (IN09,1,IN09,1,R) (IN09,*,IN10,*,L) (IN09,H,---,H,R)
(IN10,0,IN12,1,L) (IN10,1,IN11,0,L) (IN10,*,---,*,R)  (IN10,H,---,H,R)
(IN11,0,IN12,1,L) (IN11,1,IN11,1,L) (IN11,*,S00,*,R)  (IN11,H,---,H,R)
(IN12,0,IN12,0,L) (IN12,1,IN12,1,L) (IN12,*,S00,*,R)  (IN12,H,---,H,R)

#an die Adresse springen auf die BNZ:
# sucheFeld(PC)
(IC00,0,IC00,0,L) (IC00,1,IC00,1,L) (IC00,*,IC00,*,L) (IC00,H,IC01,H,L) 
(IC01,0,IC01,0,L) (IC01,1,IC01,1,L) (IC01,*,IC02,*,L) (IC01,H,---,H,R)
(IC02,0,IC02,0,L) (IC02,1,IC02,1,L) (IC02,*,IP00,*,R) (IC02,H,---,H,R)

#Finde Feld mit dieser Adresse in PC: sucheFeld(PC)
(IP00,0,IP01,H,R) (IP00,1,IP03,H,R) (IP00,*,---,*,R) (IP00,H,---,*,L)

#0 gemerkt
(IP01,0,IP01,0,R) (IP01,1,IP01,1,R) (IP01,*,IP01,*,R) (IP01,H,IP02,*,R)
(IP02,0,IP05,H,L) (IP02,1,IP24,1,R) (IP02,*,---,*,R)  (IP02,H,---,H,R)

#1 gemerkt
(IP03,0,IP03,0,R) (IP03,1,IP03,1,R) (IP03,*,IP03,*,R) (IP03,H,IP04,*,R)
(IP04,0,IP27,0,L) (IP04,1,IP07,H,L) (IP04,*,---,*,R)  (IP04,H,---,H,R)

#00 gemerkt rueck
(IP05,0,IP05,0,L) (IP05,1,IP05,1,L) (IP05,*,IP05,*,L) (IP05,H,IP06,0,R)
(IP06,0,IP13,H,R) (IP06,1,IP17,H,R) (IP06,*,IP21,H,R) (IP06,H,----,0,R)

#01 gemerkt rueck
(IP07,0,IP07,0,L) (IP07,1,IP07,1,L) (IP07,*,IP07,*,L) (IP07,H,IP08,1,R)
(IP08,0,IP15,H,R) (IP08,1,IP19,H,R) (IP08,*,IP22,H,R) (IP08,H,----,0,R)

#10 gemerkt rueck
(IP09,0,IP09,0,L) (IP09,1,IP09,1,L) (IP09,*,IP09,*,L) (IP09,H,IP10,0,R)
(IP10,0,IP24,0,R) (IP10,1,IP24,1,R) (IP10,*,IP21,H,R) (IP10,H,----,0,R)

#11 gemerkt rueck
(IP11,0,IP11,0,L) (IP11,1,IP11,1,L) (IP11,*,IP11,*,L) (IP11,H,IP12,1,R)
(IP12,0,IP15,H,R) (IP12,1,IP19,H,R) (IP12,*,IP22,H,R) (IP12,H,----,H,R)

#00 gemerkt vor
(IP13,0,IP13,0,R) (IP13,1,IP13,1,R) (IP13,*,IP13,*,R) (IP13,H,IP14,0,R)
(IP14,0,IP05,H,L) (IP14,1,IP24,1,R) (IP14,*,----,*,R) (IP14,H,----,0,R)

#01 gemerkt vor
(IP15,0,IP15,0,R) (IP15,1,IP15,1,R) (IP15,*,IP15,*,R) (IP15,H,IP16,1,R)
(IP16,0,IP05,H,L) (IP16,1,IP24,1,L) (IP16,*,----,*,R) (IP16,H,----,0,R)

#10 gemerkt vor
(IP17,0,IP17,0,R) (IP17,1,IP17,1,R) (IP17,*,IP17,*,R) (IP17,H,IP18,0,R)
(IP18,0,IP28,0,L) (IP18,1,IP11,H,L) (IP18,*,----,*,R) (IP18,H,----,H,R)

#11 gemerkt vor
(IP19,0,IP19,0,R) (IP19,1,IP19,1,R) (IP19,*,IP19,*,R) (IP19,H,IP20,1,R)
(IP20,0,IP28,0,L) (IP20,1,IP11,H,L) (IP20,*,----,*,R) (IP20,H,----,0,R)

#0 gemerkt Ende
(IP21,0,IP21,0,R) (IP21,1,IP21,1,R) (IP21,*,IP21,*,R) (IP21,H,IV0,0,R)
#1 gemerkt Ende
(IP22,0,IP22,0,R) (IP22,1,IP22,1,R) (IP22,*,IP22,*,R) (IP22,H,IV0,1,R)

#0 gemerkt falsch
(IP24,0,IP24,0,R) (IP24,1,IP24,1,R) (IP24,*,IP25,*,R) (IP24,H,---,H,R)
(IP25,0,IP25,0,R) (IP25,1,IP25,1,R) (IP25,*,IP26,H,L) (IP25,H,---,H,R)
(IP26,0,IP26,0,L) (IP26,1,IP26,1,L) (IP26,*,IP26,*,L) (IP26,H,IP27,0,L)
(IP27,0,IP27,0,L) (IP27,1,IP27,1,L) (IP27,*,IP00,*,R) (IP27,H,---,H,R)

#1 gemerkt falsch
(IP28,0,IP28,0,R) (IP28,1,IP28,1,R) (IP28,*,IP29,*,R) (IP28,H,---,H,R)
(IP29,0,IP29,0,R) (IP29,1,IP29,1,R) (IP29,*,IP30,H,L) (IP29,H,---,H,R)
(IP30,0,IP30,0,L) (IP30,1,IP30,1,L) (IP30,*,IP30,*,L) (IP30,H,IP31,1,L)
(IP31,0,IP31,0,L) (IP31,1,IP31,1,L) (IP31,*,IP00,*,R) (IP31,H,---,H,R)

# vier Felder vor zur 3.Speicherstelle, dort ist die SPrungadresse
(IV0,0,IV0,0,R) (IV0,1,IV0,1,R) (IV0,*,IV1,*,R)  (IV0,H,---,H,R)
(IV1,0,IV1,0,R) (IV1,1,IV1,1,R) (IV1,*,IV2,*,R)  (IV1,H,---,H,R)
(IV2,0,IV2,0,R) (IV2,1,IV2,1,R) (IV2,*,IV3,*,R)  (IV2,H,---,H,R)
(IV3,0,IV3,0,R) (IV3,1,IV3,1,R) (IV3,*,IV4,*,R)  (IV3,H,---,H,R)
(IV4,0,IV4,0,R) (IV4,1,IV4,1,R) (IV4,*,IK00,*,R) (IV4,H,---,H,R)

# kopiere dises feld nach PC: kopiere([PC+2],PC)
(IK00,0,IK01,H,L) (IK00,1,IK04,H,L) (IK00,*,----,*,L) (IK00,H,---,H,R)
# 0 gemerkt first
(IK01,0,IK01,0,L) (IK01,1,IK01,1,L) (IK01,*,IK01,*,L) (IK01,H,IK02,*,L)
(IK02,0,IK03,0,L) (IK02,1,IK03,0,L) (IK02,*,----,*,R) (IK02,H,---,H,R)
(IK03,0,IK07,H,R) (IK03,1,IK07,H,R) (IK03,*,IK14,H,R) (IK03,H,---,H,R)
# 1 gemerkt first
(IK04,0,IK04,0,L) (IK04,1,IK04,1,L) (IK04,*,IK04,*,L) (IK04,H,IK05,*,L)
(IK05,0,IK05,0,L) (IK05,1,IK06,0,L) (IK05,*,----,*,R) (IK05,H,---,H,R)
(IK06,0,IK08,H,R) (IK06,1,IK08,H,R) (IK06,*,IK15,H,R) (IK06,H,---,H,R)
#0 zurück
(IK07,0,IK07,0,R) (IK07,1,IK07,1,R) (IK07,*,IK07,*,R) (IK07,H,IK09,0,R)
#1 zurück
(IK08,0,IK08,0,R) (IK08,1,IK08,1,R) (IK08,*,IK08,*,R) (IK08,H,IK09,1,R)

(IK09,0,IK10,H,L) (IK09,1,IK12,H,L) (IK09,*,----,*,L) (IK09,H,---,H,R)
# 0 gemerkt
(IK10,0,IK10,0,L) (IK10,1,IK10,1,L) (IK10,*,IK10,*,L) (IK10,H,IK11,0,L)
(IK11,0,IK07,H,R) (IK11,1,IK07,H,R) (IK11,*,IK14,H,R) (IK11,H,---,H,R)
# 1 gemerkt
(IK12,0,IK12,0,L) (IK12,1,IK12,1,L) (IK12,*,IK12,*,L) (IK12,H,IK13,1,L)
(IK13,0,IK07,H,R) (IK13,1,IK07,H,R) (IK13,*,IK15,H,R) (IK13,H,---,H,R)
# aufräumen 0
(IK14,0,IK14,0,R) (IK14,1,IK14,1,R) (IK14,*,IK14,*,R) (IK14,H,IK16,0,R)
# aufräumen 1
(IK15,0,IK15,0,R) (IK15,1,IK15,1,R) (IK15,*,IK15,*,R) (IK15,H,IK16,1,R)
# und zurück
(IK16,0,IK16,0,L) (IK16,1,IK16,1,L) (IK16,*,IK16,*,L) (IK16,H,S00,*,R)

# INC Befehl
# Finde Feld mit dieser Adresse in AD: sucheFeld(AD)
(AS00,0,AS01,H,R) (AS00,1,AS03,H,R) (AS00,*,---,*,R) (AS00,H,---,*,L)
#0 gemerkt
(AS01,0,AS01,0,R) (AS01,1,AS01,1,R) (AS01,*,AS01,*,R) (AS01,H,AS02,*,R)
(AS02,0,AS05,H,L) (AS02,1,AS24,1,R) (AS02,*,---,*,R)  (AS02,H,---,H,R)

#1 gemerkt
(AS03,0,AS03,0,R) (AS03,1,AS03,1,R) (AS03,*,AS03,*,R) (AS03,H,AS04,*,R)
(AS04,0,AS27,0,L) (AS04,1,AS07,H,L) (AS04,*,---,*,R)  (AS04,H,---,H,R)

#00 gemerkt rueck
(AS05,0,AS05,0,L) (AS05,1,AS05,1,L) (AS05,*,AS05,*,L) (AS05,H,AS06,0,R)
(AS06,0,AS13,H,R) (AS06,1,AS17,H,R) (AS06,*,AS21,H,R) (AS06,H,----,0,R)

#01 gemerkt rueck
(AS07,0,AS07,0,L) (AS07,1,AS07,1,L) (AS07,*,AS07,*,L) (AS07,H,AS08,1,R)
(AS08,0,AS15,H,R) (AS08,1,AS19,H,R) (AS08,*,AS22,H,R) (AS08,H,----,0,R)

#10 gemerkt rueck
(AS09,0,AS09,0,L) (AS09,1,AS09,1,L) (AS09,*,AS09,*,L) (AS09,H,AS10,0,R)
(AS10,0,AS24,0,R) (AS10,1,AS24,1,R) (AS10,*,AS21,H,R) (AS10,H,----,0,R)

#11 gemerkt rueck
(AS11,0,AS11,0,L) (AS11,1,AS11,1,L) (AS11,*,AS11,*,L) (AS11,H,AS12,1,R)
(AS12,0,AS15,H,R) (AS12,1,AS19,H,R) (AS12,*,AS22,H,R) (AS12,H,----,H,R)

#00 gemerkt vor
(AS13,0,AS13,0,R) (AS13,1,AS13,1,R) (AS13,*,AS13,*,R) (AS13,H,AS14,0,R)
(AS14,0,AS05,H,L) (AS14,1,AS24,1,R) (AS14,*,----,*,R) (AS14,H,----,0,R)

#01 gemerkt vor
(AS15,0,AS15,0,R) (AS15,1,AS15,1,R) (AS15,*,AS15,*,R) (AS15,H,AS16,1,R)
(AS16,0,AS05,H,L) (AS16,1,AS24,1,L) (AS16,*,----,*,R) (AS16,H,----,0,R)

#10 gemerkt vor
(AS17,0,AS17,0,R) (AS17,1,AS17,1,R) (AS17,*,AS17,*,R) (AS17,H,AS18,0,R)
(AS18,0,AS28,0,L) (AS18,1,AS11,H,L) (AS18,*,----,*,R) (AS18,H,----,H,R)

#11 gemerkt vor
(AS19,0,AS19,0,R) (AS19,1,AS19,1,R) (AS19,*,AS19,*,R) (AS19,H,AS20,1,R)
(AS20,0,AS28,0,L) (AS20,1,AS11,H,L) (AS20,*,----,*,R) (AS20,H,----,0,R)

#0 gemerkt Ende
(AS21,0,AS21,0,R) (AS21,1,AS21,1,R) (AS21,*,AS21,*,R) (AS21,H,AI00,0,R)
#1 gemerkt Ende
(AS22,0,AS22,0,R) (AS22,1,AS22,1,R) (AS22,*,AS22,*,R) (AS22,H,AI00,1,R)

#0 gemerkt falsch
(AS24,0,AS24,0,R) (AS24,1,AS24,1,R) (AS24,*,AS25,*,R) (AS24,H,---,H,R)
(AS25,0,AS25,0,R) (AS25,1,AS25,1,R) (AS25,*,AS26,H,L) (AS25,H,---,H,R)
(AS26,0,AS26,0,L) (AS26,1,AS26,1,L) (AS26,*,AS26,*,L) (AS26,H,AS27,0,L)
(AS27,0,AS27,0,L) (AS27,1,AS27,1,L) (AS27,*,AS00,*,R) (AS27,H,---,H,R)

#1 gemerkt falsch
(AS28,0,AS28,0,R) (AS28,1,AS28,1,R) (AS28,*,AS29,*,R) (AS28,H,---,H,R)
(AS29,0,AS29,0,R) (AS29,1,AS29,1,R) (AS29,*,AS30,H,L) (AS29,H,---,H,R)
(AS30,0,AS30,0,L) (AS30,1,AS30,1,L) (AS30,*,AS30,*,L) (AS30,H,AS31,1,L)
(AS31,0,AS31,0,L) (AS31,1,AS31,1,L) (AS31,*,AS00,*,R) (AS31,H,---,H,R)

# [AD] = ([AD] + 1) mode N
#Adresse im ersten Feld (PC) um 3 erhöhen: PC = (PC + 3) mod N
(AI00,0,AI00,0,R) (AI00,1,AI00,1,R) (AI00,*,AI01,*,R) (AI00,H,---,H,R)
(AI01,0,AI01,0,R) (AI01,1,AI01,1,R) (AI01,*,AI02,*,L) (AI01,H,---,H,R)
#um 1 erhöhen
(AI02,0,AI04,1,L) (AI02,1,AI03,0,L) (AI02,*,---,*,R)  (AI02,H,---,H,R)
(AI03,0,AI04,1,L) (AI03,1,AI03,1,L) (AI03,*,AI05,*,R) (AI03,H,---,H,R)
(AI04,0,AI04,0,L) (AI04,1,AI04,1,L) (AI04,*,AN00,*,R) (AI04,H,---,H,R)

#Adresse im ersten Feld (PC) um 3 erhöhen: PC = (PC + 2) mod N
(AN00,0,AN00,0,L) (AN00,1,AN00,1,L) (AN00,*,AN00,*,L) (AN00,H,AN01,*,L) 
(AN01,0,AN01,0,L) (AN01,1,AN01,1,L) (AN01,*,AN02,*,L) (AN01,H,---,H,R)
#um 3 erhöhen
(AN02,0,AN04,1,L) (AN02,1,AN03,0,L) (AN02,*,---,*,R)  (AN02,H,---,H,R)
(AN03,0,AN04,1,L) (AN03,1,AN03,1,L) (AN03,*,AN05,*,R) (AN03,H,---,H,R)
(AN04,0,AN04,0,L) (AN04,1,AN04,1,L) (AN04,*,AN05,*,R) (AN04,H,---,H,R)
#und nochmal um eANs erhöhen
(AN05,0,AN05,0,R) (AN05,1,AN05,1,R) (AN05,*,AN06,*,L) (AN05,H,---,H,R)
(AN06,0,AN08,1,L) (AN06,1,AN07,0,L) (AN06,*,---,*,R)  (AN06,H,---,H,R)
(AN07,0,AN08,1,L) (AN07,1,AN07,1,L) (AN07,*,S00,*,R)  (AN07,H,---,H,R)
(AN08,0,AN08,0,L) (AN08,1,AN08,1,L) (AN08,*,S00,*,R)  (AN07,H,---,H,R)


