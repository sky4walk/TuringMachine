# Universelle Turing-Maschine von Marvin Minsky

(00,10)

(11x1100y01x00001x01111x10011x11111y)

(00,0,00,a,L) (00,1,00,b,L) (00,a,00,a,L) (00,b,00,b,L) (00,x,00,x,L) (00,y,01,y,R)
(01,0,01,0,R) (01,1,01,1,R) (01,a,02,0,R) (01,b,04,1,R) (01,x,06,x,R)
(02,0,03,a,L) (02,1,05,b,R) (02,a,02,a,R) (02,b,02,b,R) (02,x,02,x,R) 
(03,0,03,0,L) (03,1,03,1,L) (03,a,03,a,L) (03,b,03,b,L) (03,x,03,x,L) (03,y,01,y,R)
(04,0,05,a,R) (04,1,03,b,L) (04,a,04,a,R) (04,b,04,b,R) (04,x,04,x,R)
(05,0,05,0,R) (05,1,05,1,R) (05,a,05,a,R) (05,b,05,b,R) (05,x,00,x,L) (05,y,26,y,R)
(06,0,07,a,L) (06,1,08,b,L) (06,a,06,a,R) (06,b,06,b,R) (06,x,06,x,R) 
(07,0,07,0,L) (07,1,07,1,L) (07,a,07,a,L) (07,b,07,b,L) (07,x,07,x,L) (07,y,09,y,R) 
(08,0,08,0,L) (08,1,08,1,L) (08,a,08,a,L) (08,b,08,b,L) (08,x,08,x,L) (08,y,10,y,R) 
(09,0,11,a,R) (09,1,11,a,R) (09,a,09,a,R) (09,b,09,b,R) (09,x,12,x,L)
(10,0,11,b,R) (10,1,11,b,R) (10,a,10,a,R) (10,b,10,b,R) (10,x,13,x,L)
(11,0,11,0,R) (11,1,11,1,R) (11,a,11,a,R) (11,b,11,b,R) (11,x,06,x,R)
(12,0,12,0,L) (12,1,12,1,L) (12,a,12,a,L) (12,b,12,b,L) (12,x,14,a,R) (12,y,12,y,L)
(13,0,13,0,L) (13,1,13,1,L) (13,a,13,a,L) (13,b,13,b,L) (13,x,14,b,R) (13,y,13,y,L)
(14,0,14,0,R) (14,1,14,1,R) (14,a,14,0,R) (14,b,14,1,R) (14,x,15,x,R) (14,y,14,y,R)
(15,0,16,0,L) (15,1,16,1,L) (15,a,15,a,R) (15,b,15,b,R) (15,x,15,x,R) (15,y,15,y,R)
(16,0,17,x,L) (16,1,18,x,L) (16,a,16,0,L) (16,b,16,1,L) (16,x,16,x,L) (16,y,16,y,L)
(17,0,17,0,L) (17,1,17,1,L) (17,a,20,0,L) (17,b,19,0,R) (17,x,17,x,L) (17,y,17,y,L)
(18,0,18,0,L) (18,1,18,1,L) (18,a,20,1,L) (18,b,19,1,R) (18,x,18,x,L) (18,y,18,y,L)
(19,0,21,x,R) (19,1,22,x,R)
(20,0,21,x,R) (20,1,22,x,R)
(21,0,21,0,R) (21,1,21,1,R) (21,a,21,a,R) (21,b,21,b,R) (21,x,00,a,L) (21,y,21,y,R)
(22,0,22,0,R) (22,1,22,1,R) (22,a,22,a,R) (22,b,22,b,R) (22,x,00,b,L) (22,y,22,y,R)
