MAKRO:JMP(A)
   BNZ(t1,A)
t1: 1
END

MAKRO:CLR(A)
Start:BNZ(A,t1)
      JMP(End)
#ist DEC ein Grundbefehl, so muss die Zeile t1: DEC ein und die
#Zeile t1: INC auskommentiert werden, wegen Rekursion
#t1:   DEC(A)
t1:   INC(A)
      JMP(Start)
END

#Existiert in der RAMonTM schon
#MAKRO:INC(A)
#      JMP(Start)
#RLen: 0
#Start:CLR(RLen)
#      DEC(Rlen)
#S1:   DEC(A)
#      DEC(Rlen)
#      BNZ(RLen,S1)
#END

MAKRO:DEC(A)
      JMP(Start)
RLen: 0
Start:CLR(RLen)
      INC(Rlen)
S1:   INC(A)
      INC(Rlen)
      BNZ(RLen,S1)
END

MAKRO:BZ(A,B)
      BNZ(A,End)
      JMP(B)
END

MAKRO:CPY(A,B)
      JMP(Start) 
t1:   0
Start:CLR(B)
      BZ(A,End)
S1:   DEC(A)
      INC(t1)
      INC(B)
      BNZ(A,S1)
S2:   DEC(t1)
      INC(A)
      BNZ(t1,S2)
END

MAKRO:ADD(A,B,C)
      JMP(Start)
t1:   0
t2:   0
Start:CPY(A,C)
      CPY(B,t2)
      BZ(t2,End)
S1:   INC(C)
      DEC(t2)
      BNZ(t2,S1)
END

MAKRO:SUB(A,B,C)
      JMP(Start)
t1:   0
t2:   0
Start:CPY(A,C)
      CPY(B,t2)
      BZ(t2,End)
S1:   DEC(C)
      DEC(t2)
      BNZ(t2,S1)
END


MAKRO:EQU(A,B,C)
      JMP(Start)
t1:   0
Start:SUB(A,B,t1)
      BZ(t1,C)
END


MAKRO:NEQ(A,B,C)
      JMP(Start)
t1:   0
Start:SUB(A,B,t1)
      BNZ(t1,C)
END


MAKRO:GT(A,B,C)
      JMP(Start)
t1:   0
t2:   0
Start:EQU(A,B,End)
      CPY(A,t1)
      CPY(B,t2)
S1:   BZ(t1,End)
      BZ(t2,C)
      DEC(t1)
      DEC(t2)
      JMP(S1)
END


MAKRO:GTE(A,B,C)
      JMP(Start)
t1:   0
t2:   0
Start:EQU(A,B,C)
      CPY(A,t1)
      CPY(B,t2)
S1:   BZ(t1,End)
      BZ(t2,C)
      DEC(t1)
      DEC(t2)
      JMP(S1)
END


MAKRO:MUL(A,B,C)
      JMP(Start)
t1:   0
t2:   0
t3:   0
Start:CLR(C)
      CLR(t2)
      BZ(A,End)
      BZ(B,End)
      CPY(B,t1)
S1:   ADD(A,t2,t3)
      CPY(t3,t2)
      DEC(t1)
      BNZ(t2,C)
      CPY(t2,C)
END


MAKRO:DIV(A,B,C)
      JMP(Start)
t1:   0
t2:   0
t3:   0
Start:CLR(C)
      CLR(t3)
      BZ(A,End)
      BZ(B,End)
      GT(B,A,End)
      CPY(A,t1)
S1:   SUB(t1,B,t2)
      CPY(t2,t1)
      INC(C)
      GT(B,t1,End)
      JMP(S1)
END


MAKRO:NAND(A,B,C)
      JMP(Start)
t1:   0
t2:   1
Start:CLR(C)
      ADD(A,B,t1)
      GT(t1,t2,End)
      INC(C)
END

#berechnet die Modulo-Funktion: C = A % B
MAKRO:MODULO(A,B,C)
      JMP(Start)
t1:   0
t2:   0
Start:DIV(A,B,t1)
      MUL(B,t1,t2)
      SUB(A,t2,C)
END

#liefert die L?nge der Speicherstelle
MAKRO:SIZEOF(A)
      JMP(Start)
t1:   0
t2:   2
Start:CLR(A)
      DEC(t1)
s1:   DIV(t1,t2,t1)
      INC(A)
      BNZ(t1,s1)
END

#Byteweise NAND-Funktion: C = NOT(A & B)
MAKRO:B_NAND(A,B,C)
      JMP(Start)
M:    0
t1:   0
t2:   0
d:    2
Start:CLR(C)
s1:   MODULO(A,d,t1)
      MODULE(B,d,t2)
      DIV(A,d,A)
      DIV(B,d,B)
      NAND(t1,t2,M)
      MUL(C,d,C)
      ADD(C,M,C)
      BNZ(t1,s1)
END

MAKRO:Var2Adr(A,B)
      INC(R1:A)
      DEC(A)
      CPY(B,R1)
END

MAKRO:Adr2Var(A,B)
      INC(R1:A)
      DEC(A)
      CPY(R1,B)
END

MAKRO:LD(A,B,C)
      JMP(Start)
t1:   0
r0:   0
Start:Adr2Var(A,r0)
      ADD(r0,B,t1)
      Var2Adr(A,t1)
      CPY(A,C)
      Var2Adr(A,r0)
END      

MAKRO:ST(A,B,C)
      JMP(Start)
t1:   0
r0:   0
Start:Adr2Var(A,r0)
      ADD(r0,B,t1)
      Var2Adr(A,t1)
      CPY(C,A)
      Var2Adr(A,r0)
END      

MAKRO: NOT(A,B)
       JMP(Start)
t:     0
Start: DEC(t)
       DIV(t,A,B)
END

MAKRO:POP(Stk,Pos,Val)
      ST(Stk,Pos,Val)
      INC(Pos)      
END

MAKRO:PSH(Stk,Pos,Val)
      DEC(Pos)      
      LD(Stk,Pos,Val)
END

MAKRO:JSR(Stk,Pos,Next,Jump)
      JMP(Start)
Adr:  0
t  :  0
Start:LD(Next,t,Adr)
      PSH(Stk,Pos,Adr)
      JMP(Jump)
END

MAKRO:RTS(Stk,Pos)
      JMP(Start)
Adr:  0
t:    1
Start:POP(Stk,Pos,Adr)
      CPY(Adr,R1)
      BNZ(t,R1:Adr)
END

MAKRO:OUT(A)
      JMP(Start)
t: 0
Start:CPY(A,t)
END

MAKRO:FKT_BSP()
      JMP(Start)
Stack[10]: 0
StkPos:    0
temp:      0
Start:JSR(Stack,StkPos,Next,Sub1)
Next: JMP(End)
Sub1:INC(temp)
     RTS(Stack,StkPos)
END