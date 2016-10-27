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

#--------------------New--------------------

MAKRO:GET1DFROM2DPOS(X,Y,LenX,Pos1D)
      JMP(Start)
t1:   0
Start:MUL(LenX,Y,t1)
      ADD(t1,X,Pos1D)
END

MAKRO:    ZA()
          JMP(Start)
DimXa:    0
DimYa:    0
DimX:     20
DimY:     20
# Feldgroesse x*y=20
F1[20]:   0,0,0,0,0,0
Ft[20]:   0,0,0,0,0,0
CntX:     0
CntY:     0
Adr1D:    0
Dead:     0
Alife:    1
Cont:     0
t1:       0
Birth:    3
Birth2    6
Survive:  2
Start:  SUB(DimX,Survive,DimXa)
        SUB(DimY,Survive,DimYa)
S1:     GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Sum)
        INC(CntX)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,Sum,t1)
        INC(CntX)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,t1,Sum)
        INC(CntY)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,Sum,t1)
        DEC(CntX)
        DEC(CntX)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,t1,Sum)
        INC(CntY)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,Sum,t1)
        INC(CntX)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,t1,Sum)
        INC(CntX)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,Sum,t1)
        INC(CntX)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(F1,Adr1D,Cont)
        ADD(Cont,Sum,t1)
        DEC(CntX)
        DEC(CntY)
        GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        EQU(t1,Survive,Next)
        EQU(t1,Birth,Set)
#auskommentieren der nächsten Zeile ermoeglicht einfache reversible Formen
#       EQU(t1,Birth2,Set)
        ST(Ft,Adr1D,Dead)
        JMP(Next)
Set:    ST(Ft,Adr1D,Alife)
Next:   DEC(CntY)
        GT(DimXa,CntX,S1)
        CLR(CntX)
        INC(CntY)
        GT(DimYa,CntY,S1)
        CLR(CntX)
        CLR(CntY)
S2:     GET1DFROM2DPOS(CntX,CntY,DimX,Adr1D)
        LD(Ft,Adr1D,Cont)
        ST(F1,Adr1D,Cont)
        INC(CntX)
        GT(DimX,CntX,S2)
        CLR(CntX)
        INC(CntY)
        GT(DimY,CntY,S2)
        CLR(CntX)
        CLR(CntY)
        JMP(S1)
END



