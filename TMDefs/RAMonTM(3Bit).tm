(000,0)

(PxxxPxxxL000D110L001D011L010D100L011D001Lx)


# geht zum linken Startpunkt P
(000,0,000,0,L) (000,1,000,1,L) (000,x,000,x,L) (000,y,000,y,L) (000,P,001,P,R) (000,L,000,L,L) (000,D,000,D,L)
# suche Position
(001,x,002,0,R) (001,y,004,1,R) (001,P,009,P,R) 
# gehe bis zum ersten L �r gemerktes 0
(002,0,003,x,L) (002,1,006,y,R) (002,x,002,x,R) (002,y,002,y,R) (002,P,002,P,R) (002,L,002,L,R) (002,D,002,D,R)
# Erolg, geh wieder zur�ck
(003,0,001,0,R) (003,1,001,1,R) (003,x,003,x,L) (003,y,003,y,L) (003,P,003,P,L) (003,L,003,L,L) (003,D,003,D,L)
# gehe bis zum ersten L �r gemerktes 1
(004,0,006,x,R) (004,1,005,y,L) (004,x,004,x,R) (004,y,004,y,R) (004,P,004,P,R) (004,L,004,L,R) (004,D,004,D,R)
# Erolg, geh wieder zur�ck
(005,0,001,0,R) (005,1,001,1,R) (005,x,005,x,L) (005,y,005,y,L) (005,P,005,P,L) (005,L,005,L,L) (005,D,005,D,L)
# nicht geunden, n�chstes Label und Resete das Band oder Halt bei x 
(006,x,110,x,L)                            (006,0,006,x,R) (006,1,006,y,R) (006,L,007,L,L) (006,D,006,D,R) 
# gehe zur�ck zum ersten P
(007,0,008,x,L) (008,1,007,y,L) (007,x,007,x,L) (007,y,007,y,L) (007,P,008,P,L) (007,L,007,L,L) (007,D,007,D,L)
# Resete P-Register
(008,0,008,x,L) (008,1,008,y,L) (008,x,008,x,L) (008,y,008,y,L) (008,P,001,P,R) 

# Beehlsblock
# gehe zur ersten Zahl vor
(009,0,010,x,R) (009,1,011,y,R) (009,x,009,x,R) (009,y,009,y,R) (009,P,009,P,R) (009,L,009,L,R) (009,D,009,D,R)
# CLR oder INC
(010,0,012,x,R) (010,1,013,y,R) 
# DEC oder I
(011,0,014,x,R) (011,1,015,y,R) 
# CLR 00
(012,0,012,x,R) (012,1,012,y,R) (012,L,012,L,R) (012,D,054,D,R)
# INC 01
(013,0,013,x,R) (013,1,013,y,R) (013,L,013,L,R) (013,D,072,D,R)
# DEC 10
(014,0,014,x,R) (014,1,014,y,R) (014,L,014,L,R) (014,D,091,D,R)
# I 11
(015,0,015,x,R) (015,1,015,y,R) (015,L,015,L,R) (015,D,016,D,R)

# I-Beehl i !A Goto B
# kopiere Inhalt der n�chsten Zelle in das R-Register
# lies Zahl und gehe bis P oder erste Zahl 0
(016,0,017,x,L) (016,1,019,y,L) (016,x,016,x,R) (016,y,016,y,R) (016,P,016,P,R) (016,L,016,L,R) (016,D,016,D,R)
# 0 gelesen, gehe bis P oder Zahl 
(017,0,018,0,R) (017,1,018,1,R) (017,x,017,x,L) (017,y,017,y,L) (017,P,018,P,R) (017,L,017,L,L) (017,D,017,D,L)
# schreibe 0
(018,x,021,0,R) (018,y,021,0,R) 
# 1 gelesen, gehe bis P oder Zahl 
(019,0,020,0,R) (019,1,020,1,R) (019,x,019,x,L) (019,y,019,y,L) (019,P,020,P,R) (019,L,019,L,L) (019,D,019,D,L)
# schreibe 1
(020,x,021,1,R) (020,y,021,1,R) 
#schau nach ob schon zu Ende wenn nicht, dann noch eins sonst weiter
(021,x,016,x,R) (021,y,016,y,R) (021,L,022,L,R)

#markiere naechste I-Beehl Position mit P
#gehe zum Ende der Markierungen
(022,0,023,x,R) (022,1,023,y,R) (022,x,022,x,R) (022,y,022,y,R) (022,P,022,P,R) (022,L,022,L,R) (022,D,022,D,R)
#gehe bis zum D
(023,0,023,x,R) (023,1,023,y,R) (023,D,024,P,L)
#suche Label vom 2.P-Register
# gehe zum linken P
(024,0,024,x,L) (024,1,024,y,L) (024,x,024,0,L) (024,y,024,1,L) (024,P,025,P,R) (024,L,024,L,L) (024,D,024,D,L)
#lese Zeichen oder richtiges Label geunden
(025,x,026,0,R) (025,y,028,1,R) (025,L,033,L,L) 
#0 gelesen gehe zur ersten rechten Zahl
(026,0,027,x,L) (026,1,030,y,R) (026,x,026,x,R) (026,y,026,y,R) (026,P,026,P,R) (026,L,026,L,R) (026,D,026,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(027,0,025,0,R) (027,1,025,1,R) (027,x,027,x,L) (027,y,027,y,L) (027,P,027,P,L) (027,L,027,L,L) (027,D,027,D,L)
#1 gelesen gehe zur ersten rechten Zahl
(028,0,030,x,R) (028,1,029,y,L) (028,x,028,x,R) (028,y,028,y,R) (028,P,028,P,R) (028,L,028,L,R) (028,D,028,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(029,0,025,0,R) (029,1,025,1,R) (029,x,029,x,L) (029,y,029,y,L) (029,P,029,P,L) (029,L,029,L,L) (029,D,029,D,L)
# alsches Label markiere bis zum n�chsten L
(030,0,030,x,R) (030,1,030,y,R) (030,P,030,P,R) (030,L,031,L,L) (030,D,030,D,R)
# gehe bis zur Zahl links
(031,0,032,x,L) (031,1,032,y,L) (031,x,031,x,L) (031,y,031,y,L) (031,P,031,P,L) (031,L,031,L,L) (031,D,031,D,L)
# markiere bis linkem P
(032,0,032,x,L) (032,1,032,y,L) (032,P,025,P,R) 
#teste den Inhalt des dortigen Labels au 0
#gehe nach links bis erste Zahl
(033,0,033,x,L) (033,1,033,y,L) (033,P,034,P,L)
#markiere bis linken erstem P
(034,0,034,x,L) (034,1,034,y,L) (034,P,035,P,R)
#lasse eins rei
(035,x,036,0,R) (035,y,036,0,R)
#gehe bis erstem L
(036,x,036,x,R) (036,y,036,y,R) (036,P,036,P,R) (036,L,037,L,R)
#gehe bis zur ersten Zahl und teste au Null
(037,0,038,0,R) (037,1,039,1,R) (037,x,037,0,R) (037,y,037,1,R) (037,P,037,P,R) (037,L,037,L,R) (037,D,037,D,R)
# weiter rechts bis L oder vorher eine eins geunden
(038,0,038,0,R) (038,1,039,1,L) (038,L,045,L,L) 
#kopiere Teil nach P in den Label-Counter
#gehe zum ersten markierten Zeichen nach links
(039,0,039,0,L) (039,1,039,1,L) (039,x,040,x,R) (039,y,040,y,R) (039,P,039,P,L) (039,L,039,L,L) (039,D,039,D,L)
#markiere bis zum P
(040,0,040,x,R) (040,1,040,y,R) (040,P,041,D,R) (040,L,040,L,R) (040,D,040,D,R)
#kopiere
#merke Zeichen
(041,0,042,x,L) (041,1,043,y,L) (041,x,041,x,R) (041,y,041,y,R) (041,P,041,P,R) (041,L,041,L,R) (041,D,041,D,R)
#0 gemerkt, gehe bis zur ersten Zahl links und schreibe 0
(042,0,044,x,R) (042,1,044,x,R) (042,x,042,x,L) (042,y,042,y,L) (042,P,042,P,L) (042,L,042,L,L) (042,D,042,D,L)
#1 gemerkt, gehe bis zur ersten Zahl links und schreibe 0
(043,0,044,y,R) (043,1,044,y,R) (043,x,043,x,L) (043,y,043,y,L) (043,P,043,P,L) (043,L,043,L,L) (043,D,043,D,L)
#demarkiere n�chstes Zeichen oder ertig kopiert zum Anang zur�ck
(044,x,041,0,R) (044,y,041,1,R) (044,P,000,P,L)
#erhoehe Label-Counter um 3
#gehe zum ersten linken markierten Zeichen
(045,0,045,0,L) (045,1,045,1,L) (045,x,046,x,L) (045,y,046,y,L) (045,P,045,D,L) (045,L,045,L,L) (045,D,045,D,L)
#dann bis zum ersten P links
(046,x,046,x,L) (046,y,046,y,L) (046,P,047,P,L)
#addiere eins 3-mal drau
(047,0,048,y,L) (047,1,047,x,L) (047,x,048,y,L) (047,y,047,x,L) (047,P,049,P,R)
(048,0,048,x,L) (048,1,048,y,L) (048,x,048,x,L) (048,y,048,y,L) (048,P,049,P,R) 
#gehe wieder zur�ck
(049,x,049,x,R) (049,y,049,y,R) (049,P,050,P,L) 
#noch eins drau addieren 3mal
(050,0,051,y,L) (050,1,050,x,L) (050,x,051,y,L) (050,y,050,x,L) (050,P,052,P,R) 
(051,0,051,x,L) (051,1,051,y,L) (051,x,051,x,L) (051,y,051,y,L) (051,P,052,P,R) 
(052,x,052,x,R) (052,y,052,y,R) (052,P,053,P,L) 
(053,x,000,y,L) (053,y,053,x,L) (053,P,001,P,R) 

#CLR-Beehl
# kopiere Inhalt der n�chsten Zelle in das R-Register
# lies Zahl und gehe bis P oder erste Zahl 0
(054,0,055,x,L) (054,1,057,y,L) (054,x,054,x,R) (054,y,054,y,R) (054,P,054,P,R) (054,L,054,L,R) (054,D,054,D,R)
# 0 gelesen, gehe bis P oder Zahl 
(055,0,056,0,R) (055,1,056,1,R) (055,x,055,x,L) (055,y,055,y,L) (055,P,056,P,R) (055,L,055,L,L) (055,D,055,D,L)
# schreibe 0
(056,x,059,0,R) (056,y,059,0,R) 
# 1 gelesen, gehe bis P oder Zahl 
(057,0,058,0,R) (057,1,058,1,R) (057,x,057,x,L) (057,y,057,y,L) (057,P,058,P,R) (057,L,057,L,L) (057,D,057,D,L)
# schreibe 1
(058,x,059,1,R) (058,y,059,1,R) 
#schau nach ob schon zu Ende wenn nicht, dann noch eins sonst weiter
(059,x,054,x,R) (059,y,054,y,R) (059,L,060,L,R)
#suche Label vom 2.P-Register
# gehe links bis Zahl
(060,0,061,0,L) (060,1,061,1,L) (060,x,060,x,R) (060,y,060,y,R) (060,P,060,P,R) (060,L,060,L,R) (060,D,060,D,R)
# kehre um bis P
(061,0,061,x,L) (061,1,061,y,L) (061,x,061,0,L) (061,y,061,1,L) (061,P,062,P,R) (061,L,061,L,L) (061,D,061,D,L)
#lese Zeichen oder richtiges Label geunden
(062,x,063,0,R) (062,y,065,1,R) (062,L,069,L,R) 
#0 gelesen gehe zur ersten rechten Zahl
(063,0,064,x,L) (063,1,067,y,R) (063,x,063,x,R) (063,y,063,y,R) (063,P,063,P,R) (063,L,063,L,R) (063,D,063,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(064,0,062,0,R) (064,1,062,1,R) (064,x,064,x,L) (064,y,064,y,L) (064,P,064,P,L) (064,L,064,L,L) (064,D,064,D,L)
#1 gelesen gehe zur ersten rechten Zahl
(065,0,067,x,R) (065,1,066,y,L) (065,x,065,x,R) (065,y,065,y,R) (065,P,065,P,R) (065,L,065,L,R) (065,D,065,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(066,0,062,0,R) (066,1,062,1,R) (066,x,066,x,L) (066,y,066,y,L) (066,P,066,P,L) (066,L,066,L,L) (066,D,066,D,L)
# alsches Label markiere bis zum n�chsten L
(067,0,067,x,R) (067,1,067,y,R) (067,P,067,P,R) (067,L,068,L,L) (067,D,067,D,R)
# gehe bis zur Zahl linkem P
(068,0,068,x,L) (068,1,068,y,L) (068,x,068,x,L) (068,y,068,y,L) (068,P,062,P,R) (068,L,068,L,L) (068,D,068,D,L)
# gehe bis zur ersten Zahl rechts und setzte au 0
(069,0,070,x,R) (069,1,070,x,R) (069,x,069,x,R) (069,y,069,y,R) (069,P,069,P,R) (069,L,069,L,R) (069,D,069,D,R)
# l�sche bis L
(070,0,070,x,R) (070,1,070,x,R) (070,L,071,L,L)
# gehe bis erstem linkem P und markiere demarkiere, dann addiere 2 au Counter
(071,0,071,x,L) (071,1,071,x,L) (071,x,071,0,L) (071,y,071,1,L) (071,P,050,P,L) (071,L,071,L,L) (071,D,071,D,L)


#INC-Beehl
# kopiere Inhalt der n�chsten Zelle in das R-Register
# lies Zahl und gehe bis P oder erste Zahl 0
(072,0,073,x,L) (072,1,075,y,L) (072,x,072,x,R) (072,y,072,y,R) (072,P,072,P,R) (072,L,072,L,R) (072,D,072,D,R)
# 0 gelesen, gehe bis P oder Zahl 
(073,0,074,0,R) (073,1,074,1,R) (073,x,073,x,L) (073,y,073,y,L) (073,P,074,P,R) (073,L,073,L,L) (073,D,073,D,L)
# schreibe 0
(074,x,077,0,R) (074,y,077,0,R) 
# 1 gelesen, gehe bis P oder Zahl 
(075,0,076,0,R) (075,1,076,1,R) (075,x,075,x,L) (075,y,075,y,L) (075,P,076,P,R) (075,L,075,L,L) (075,D,075,D,L)
# schreibe 1
(076,x,077,1,R) (076,y,077,1,R) 
#schau nach ob schon zu Ende wenn nicht, dann noch eins sonst weiter
(077,x,072,x,R) (077,y,072,y,R) (077,L,078,L,R)
#suche Label vom 2.P-Register
# gehe zum linken P
(078,0,079,0,L) (078,1,079,1,L) (078,x,078,x,R) (078,y,078,y,R) (078,P,078,P,R) (078,L,078,L,R) (078,D,078,D,R)
# kehre um bis P
(079,0,079,x,L) (079,1,079,y,L) (079,x,079,0,L) (079,y,079,1,L) (079,P,080,P,R) (079,L,079,L,L) (079,D,079,D,L)
#lese Zeichen oder richtiges Label geunden
(080,x,081,0,R) (080,y,083,1,R) (080,L,087,L,R) 
#0 gelesen gehe zur ersten rechten Zahl
(081,0,082,x,L) (081,1,085,y,R) (081,x,081,x,R) (081,y,081,y,R) (081,P,081,P,R) (081,L,081,L,R) (081,D,081,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(082,0,080,0,R) (082,1,080,1,R) (082,x,082,x,L) (082,y,082,y,L) (082,P,082,P,L) (082,L,082,L,L) (082,D,082,D,L)
#1 gelesen gehe zur ersten rechten Zahl
(083,0,085,x,R) (083,1,084,y,L) (083,x,083,x,R) (083,y,083,y,R) (083,P,083,P,R) (083,L,083,L,R) (083,D,083,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(084,0,080,0,R) (084,1,080,1,R) (084,x,084,x,L) (084,y,084,y,L) (084,P,084,P,L) (084,L,084,L,L) (084,D,084,D,L)
# alsches Label markiere bis zum n�chsten L
(085,0,085,x,R) (085,1,085,y,R) (085,P,085,P,R) (085,L,086,L,L) (085,D,085,D,R)
# gehe bis zur Zahl linkem P
(086,0,086,x,L) (086,1,086,y,L) (086,x,086,x,L) (086,y,086,y,L) (086,P,080,P,R) (086,L,086,L,L) (086,D,086,D,L)
# gehe zur ersten Zahl rechts
(087,0,088,0,R) (087,1,088,1,R) (087,x,087,x,R) (087,y,087,y,R) (087,L,087,L,R) (087,D,087,D,R)
# gehe bis zum L
(088,0,088,0,R) (088,1,088,1,R) (088,L,089,L,L) 
# addiere
(089,0,090,1,L) (089,1,089,0,L) (089,D,071,D,L) 
(090,0,090,0,L) (090,1,090,1,L) (090,D,071,D,L) 


#DEC-Beehl
# kopiere Inhalt der n�chsten Zelle in das R-Register
# lies Zahl und gehe bis P oder erste Zahl 0
(091,0,092,x,L) (091,1,094,y,L) (091,x,091,x,R) (091,y,091,y,R) (091,P,091,P,R) (091,L,091,L,R) (091,D,091,D,R)
# 0 gelesen, gehe bis P oder Zahl 
(092,0,093,0,R) (092,1,093,1,R) (092,x,092,x,L) (092,y,092,y,L) (092,P,093,P,R) (092,L,092,L,L) (092,D,092,D,L)
# schreibe 0
(093,x,096,0,R) (093,y,096,0,R) 
# 1 gelesen, gehe bis P oder Zahl 
(094,0,095,0,R) (094,1,095,1,R) (094,x,094,x,L) (094,y,094,y,L) (094,P,095,P,R) (094,L,094,L,L) (094,D,094,D,L)
# schreibe 1
(095,x,096,1,R) (095,y,096,1,R) 
#schau nach ob schon zu Ende wenn nicht, dann noch eins sonst weiter
(096,x,091,x,R) (096,y,091,y,R) (096,L,097,L,R)

#suche Label vom 2.P-Register
# gehe zum linken P
(097,0,098,0,L) (097,1,098,1,L) (097,x,097,x,R) (097,y,097,y,R) (097,P,097,P,R) (097,L,097,L,R) (097,D,097,D,R)
# kehre um bis P
(098,0,098,x,L) (098,1,098,y,L) (098,x,098,0,L) (098,y,098,1,L) (098,P,099,P,R) (098,L,098,L,L) (098,D,098,D,L)
#lese Zeichen oder richtiges Label geunden
(099,x,100,0,R) (099,y,102,1,R) (099,L,106,L,R) 
#0 gelesen gehe zur ersten rechten Zahl
(100,0,101,x,L) (100,1,104,y,R) (100,x,100,x,R) (100,y,100,y,R) (100,P,100,P,R) (100,L,100,L,R) (100,D,100,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(101,0,099,0,R) (101,1,099,1,R) (101,x,101,x,L) (101,y,101,y,L) (101,P,101,P,L) (101,L,101,L,L) (101,D,101,D,L)
#1 gelesen gehe zur ersten rechten Zahl
(102,0,104,x,R) (102,1,103,y,L) (102,x,102,x,R) (102,y,102,y,R) (102,P,102,P,R) (102,L,102,L,R) (102,D,102,D,R)
# Zeichen sind gleich gehe wieder zur�ck zur n�chsten Zahl links
(103,0,099,0,R) (103,1,099,1,R) (103,x,103,x,L) (103,y,103,y,L) (103,P,103,P,L) (103,L,103,L,L) (103,D,103,D,L)
# alsches Label markiere bis zum n�chsten L
(104,0,104,x,R) (104,1,104,y,R) (104,P,104,P,R) (104,L,105,L,L) (104,D,104,D,R)
# gehe bis zur Zahl linkem P
(105,0,105,x,L) (105,1,105,y,L) (105,x,105,x,L) (105,y,105,y,L) (105,P,099,P,R) (105,L,105,L,L) (105,D,105,D,L)
# gehe zur ersten Zahl rechts
(106,0,107,0,R) (106,1,107,1,R) (106,x,106,x,R) (106,y,106,y,R) (106,L,106,L,R) (106,D,106,D,R)
#gehe bis erstes rechte L
(107,0,107,0,R) (107,1,107,1,R) (107,L,108,L,L) 
# subtrahiere 1
(108,0,108,1,L) (108,1,109,0,L) (108,D,071,D,L) 
(109,0,109,0,L) (109,1,109,1,L) (109,D,071,D,L) 
