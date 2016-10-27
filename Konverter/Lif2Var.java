//written by André Betz 
//http://www.andrebetz.de
import java.io.*;
import TuringMachine;

// konvertiert *.lif Beschreibungen für zellulare Automaten
// in ein Array, das in ZAonRAM.asm als Feldvariablen eingesetzt
// werden kann

class Lif2Var extends TuringMachine 
{
	Lif2Var(String Datnam){
		StringBuffer input = LoadProgram(Datnam);
		int[] Point = new int[2];
		CountXY(Point,input);
		int size = Point[0] * Point [1];
		int[] Arr = new int[size];
		ClearFeld(Arr);
		FillFeld(Point,Arr,input);
		PrintArr(Point,Arr);
	}
	
	protected void PrintArr(int[] Point,int[] Arr){
		System.out.println("DimX: "+Point[0]);
		System.out.println("DimY: "+Point[1]);
		System.out.println("Ft["+Arr.length+"]: 0");
		System.out.print("F1["+Arr.length+"]: ");
		for(int i=0;i<Arr.length;i++){
			if(i!=0){
				System.out.print(",");
			}
			if(Arr[i]==1){	
				System.out.print("1");
			}else{
				System.out.print("0");
			}
		}
		System.out.print("\n");
	}
	
	protected void FillFeld(int[] Point, int[] Arr, StringBuffer Feld){
		int spos = 0;
		int x = 0;
		int y = 0;
		int ArrPos;
		int endpos = Feld.length();
		while(spos<endpos){
			if(spos<endpos) {
				char sign = Feld.charAt(spos);
				if (sign=='#'){
					spos = super.DelComment(Feld.toString(),spos);
				}else {
					ArrPos = GetCantor(x,y,Point);
					if(sign=='*'){
						Arr[ArrPos] = 1;
						x++;
					}else if(sign=='.'){
						x++;
					}else if(sign=='\n'){
						x = 0;
						y++;
					}
				}			
			}
			spos++;
		}				
	}
	
	protected void CountXY(int[] Point,StringBuffer Feld){
		Point[0] = 0;
		Point[1] = 0;
		int spos = 0;
		int x = 0;
		int endpos = Feld.length();
		while(spos<endpos){
			if(spos<endpos) {
				char sign = Feld.charAt(spos);
				if (sign=='#'){
					spos = super.DelComment(Feld.toString(),spos);
				}else {
					if(sign=='.'||sign=='*'){
						x++;						
					}else if(sign=='\n'){
						if(Point[0]<x){
							Point[0] = x;
						}
						Point[1]++;
						x = 0;
					}
				}			
			}
			spos++;
		}			
	}
	
	protected int GetCantor(int x, int y, int[] Point){
		return Point[1]*y+x;
	}
	
	protected void ClearFeld(int[] Arr){
		for(int i=0;i<Arr.length;i++){
			Arr[i] = 0;
		}
	}
	
	protected StringBuffer LoadProgram(String FileName) {
		StringBuffer readinput = new StringBuffer();
		try {
			File f = new File(FileName);
			FileReader in = new FileReader(f);
			char[] buffer = new char[128];
			int len;
			while((len = in.read(buffer))!=-1) {
			  readinput.append(new String(buffer,0,len));
			}
		}
		catch(IOException e) {
		}

		return readinput;  	
	}

			
	public static void main(String[] args) {
		if(args.length!=1) {
			System.exit(0);
		}
		
		Lif2Var conv = new Lif2Var(args[0]);
	}
};