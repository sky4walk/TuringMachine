//written by André Betz 
//http://www.andrebetz.de
import TMState;
import Tape;
import TuringMachine;

// wandelt eine 2 Bit Maschine in eine 1 Bit Maschine um

public class Converter extends TuringMachine {
	Converter(String fn) {
		super(fn);
	}
	
	public boolean Convert() {
		String BitTape = ConvertTape();
		String FirstState = Start.GetStateF();
		String First = "(" + FirstState + "_R,0)";
		System.out.println(First);
		System.out.println(BitTape);
		String BitTransitionen = ConvertTransitionen();
		return true;
	}

	protected String GetBits(String Bit2) {
		// 0 -> 00
		// 1 -> 01
		// * -> 10
		// H -> 11
		String Bits = "0";
		if(Bit2.equals("0")) {
			Bits = "00";	
		} else if(Bit2.equals("1")) {
			Bits = "01";	
		} else if(Bit2.equals("*")) {
			Bits = "10";	
		} else if(Bit2.equals("H")) {
			Bits = "11";	
		} 
		return Bits;			
	}
	protected String ConvertTransitionen() {
		String BitTransitionen = "";
		TMState stPos = Start;
		
		while(stPos!=null) {
			String FirstState1 = stPos.GetStateF();
			String Read1       = stPos.GetRead();
			String Write1      = stPos.GetWrite();
			String NState1     = stPos.GetStateN();
			String Move1       = stPos.GetMove();
			stPos = stPos.GetNext();
			String Write2      = stPos.GetWrite();
			String Read2       = stPos.GetRead();
			String NState2     = stPos.GetStateN();
			String Move2       = stPos.GetMove();
			stPos = stPos.GetNext();
			String Write3      = stPos.GetWrite();
			String Read3       = stPos.GetRead();
			String NState3     = stPos.GetStateN();
			String Move3       = stPos.GetMove();
			stPos = stPos.GetNext();
			String Write4      = stPos.GetWrite();
			String Read4       = stPos.GetRead();
			String NState4     = stPos.GetStateN();
			String Move4       = stPos.GetMove();
			stPos = stPos.GetNext();
			
			BitTransitionen = "# (" + FirstState1 + "," + Read1 + "," + NState1 + "," + Write1 + "," + Move1 +")\t";
			BitTransitionen += "(" + FirstState1 + "," + Read2 + "," + NState2 + "," + Write2 + "," + Move2 +")\t";
			BitTransitionen += "(" + FirstState1 + "," + Read3 + "," + NState3 + "," + Write3 + "," + Move3 +")\t";
			BitTransitionen += "(" + FirstState1 + "," + Read4 + "," + NState4 + "," + Write4 + "," + Move4 +")\n";

			BitTransitionen += "(" + FirstState1 + "_R,0,"   + FirstState1 + "_R0,0,R)\t";
			BitTransitionen += "(" + FirstState1 + "_R,1,"   + FirstState1 + "_R1,1,R)\n";
			BitTransitionen += "(" + FirstState1 + "_R0,0,"  + FirstState1 + "_R00W,0,L)\t";
			BitTransitionen += "(" + FirstState1 + "_R0,1,"  + FirstState1 + "_R01W,1,L)\n";
			BitTransitionen += "(" + FirstState1 + "_R1,0,"  + FirstState1 + "_R10W,0,L)\t";
			BitTransitionen += "(" + FirstState1 + "_R1,1,"  + FirstState1 + "_R11W,1,L)\n";
			
			BitTransitionen += CreateWriteStates(FirstState1, Write1, Write2, Write3, Write4,
			                                                  Move1, Move2, Move3, Move4,
			                                                  NState1, NState2, NState3, NState4);
			System.out.println(BitTransitionen);
		}

		return BitTransitionen;
	}
	
	protected String CreateWriteStates(String FirstState, String Write1, String Write2, String Write3, String Write4,
	                                                      String Move1,  String Move2,  String Move3,  String Move4,
	                                                      String NextS1, String NextS2, String NextS3, String NextS4 ) {
		String WriteStates = "";
		if(Write1.equals("0")) {
			WriteStates += "(" + FirstState + "_R00W,0,"   + FirstState + "_R00W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R00W,1,"   + FirstState + "_R00W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R00W0,0,"  + FirstState + "_R00W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R00W0,1,"  + FirstState + "_R00W00,0,L)\n";
		} else if(Write1.equals("1")) {
			WriteStates += "(" + FirstState + "_R00W,0,"   + FirstState + "_R00W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R00W,1,"   + FirstState + "_R00W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R00W0,0,"  + FirstState + "_R00W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R00W0,1,"  + FirstState + "_R00W00,1,L)\n";
		} else if(Write1.equals("*")) {
			WriteStates += "(" + FirstState + "_R00W,0,"   + FirstState + "_R00W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R00W,1,"   + FirstState + "_R00W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R00W0,0,"  + FirstState + "_R00W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R00W0,1,"  + FirstState + "_R00W00,0,L)\n";
		} else if(Write1.equals("H")) {
			WriteStates += "(" + FirstState + "_R00W,0,"   + FirstState + "_R00W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R00W,1,"   + FirstState + "_R00W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R00W0,0,"  + FirstState + "_R00W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R00W0,1,"  + FirstState + "_R00W00,1,L)\n";
		}
		if(Move1.equals("R")) {
			WriteStates += "(" + FirstState + "_R00W00,0,"   + FirstState + "_R00W00MR,0,R)\t";
			WriteStates += "(" + FirstState + "_R00W00,1,"   + FirstState + "_R00W00MR,1,R)\n";
			WriteStates += "(" + FirstState + "_R00W00MR,0," + NextS1 + "_R,0,R)\t";
			WriteStates += "(" + FirstState + "_R00W00MR,1," + NextS1 + "_R,1,R)\n";
		} else {
			WriteStates += "(" + FirstState + "_R00W00,0,"   + FirstState + "_R00W00MR,0,L)\t";
			WriteStates += "(" + FirstState + "_R00W00,1,"   + FirstState + "_R00W00MR,1,L)\n";
			WriteStates += "(" + FirstState + "_R00W00ML,0," + NextS1 + "_R,0,L)\t";
			WriteStates += "(" + FirstState + "_R00W00ML,1," + NextS1 + "_R,1,L)\n";
		}			
		if(Write2.equals("0")) {
			WriteStates += "(" + FirstState + "_R01W,0,"   + FirstState + "_R01W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R01W,1,"   + FirstState + "_R01W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R01W0,0,"  + FirstState + "_R01W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R01W0,1,"  + FirstState + "_R01W00,0,L)\n";
		} else if(Write2.equals("1")) {
			WriteStates += "(" + FirstState + "_R01W,0,"   + FirstState + "_R01W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R01W,1,"   + FirstState + "_R01W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R01W0,0,"  + FirstState + "_R01W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R01W0,1,"  + FirstState + "_R01W00,1,L)\n";
		} else if(Write2.equals("*")) {
			WriteStates += "(" + FirstState + "_R01W,0,"   + FirstState + "_R01W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R01W,1,"   + FirstState + "_R01W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R01W0,0,"  + FirstState + "_R01W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R01W0,1,"  + FirstState + "_R01W00,0,L)\n";
		} else if(Write2.equals("H")) {
			WriteStates += "(" + FirstState + "_R01W,0,"   + FirstState + "_R01W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R01W,1,"   + FirstState + "_R01W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R01W0,0,"  + FirstState + "_R01W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R01W0,1,"  + FirstState + "_R01W00,1,L)\n";
		}
		if(Move2.equals("R")) {
			WriteStates += "(" + FirstState + "_R01W00,0,"   + FirstState + "_R01W00MR,0,R)\t";
			WriteStates += "(" + FirstState + "_R01W00,1,"   + FirstState + "_R01W00MR,1,R)\n";
			WriteStates += "(" + FirstState + "_R01W00MR,0," + NextS2 + "_R,0,R)\t";
			WriteStates += "(" + FirstState + "_R01W00MR,1," + NextS2 + "_R,1,R)\n";
		} else {
			WriteStates += "(" + FirstState + "_R01W00,0,"   + FirstState + "_R01W00MR,0,L)\t";
			WriteStates += "(" + FirstState + "_R01W00,1,"   + FirstState + "_R01W00MR,1,L)\n";
			WriteStates += "(" + FirstState + "_R01W00ML,0," + NextS2 + "_R,0,L)\t";
			WriteStates += "(" + FirstState + "_R01W00ML,1," + NextS2 + "_R,1,L)\n";
		}			
		if(Write3.equals("0")) {
			WriteStates += "(" + FirstState + "_R10W,0,"   + FirstState + "_R10W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R10W,1,"   + FirstState + "_R10W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R10W0,0,"  + FirstState + "_R10W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R10W0,1,"  + FirstState + "_R10W00,0,L)\n";
		} else if(Write3.equals("1")) {
			WriteStates += "(" + FirstState + "_R10W,0,"   + FirstState + "_R10W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R10W,1,"   + FirstState + "_R10W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R10W0,0,"  + FirstState + "_R10W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R10W0,1,"  + FirstState + "_R10W00,1,L)\n";
		} else if(Write3.equals("*")) {
			WriteStates += "(" + FirstState + "_R10W,0,"   + FirstState + "_R10W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R10W,1,"   + FirstState + "_R10W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R10W0,0,"  + FirstState + "_R10W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R10W0,1,"  + FirstState + "_R10W00,0,L)\n";
		} else if(Write3.equals("H")) {
			WriteStates += "(" + FirstState + "_R10W,0,"   + FirstState + "_R10W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R10W,1,"   + FirstState + "_R10W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R10W0,0,"  + FirstState + "_R10W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R10W0,1,"  + FirstState + "_R10W00,1,L)\n";
		}
		if(Move3.equals("R")) {
			WriteStates += "(" + FirstState + "_R10W00,0,"   + FirstState + "_R10W00MR,0,R)\t";
			WriteStates += "(" + FirstState + "_R10W00,1,"   + FirstState + "_R10W00MR,1,R)\n";
			WriteStates += "(" + FirstState + "_R10W00MR,0," + NextS3 + "_R,0,R)\t";
			WriteStates += "(" + FirstState + "_R10W00MR,1," + NextS3 + "_R,1,R)\n";
		} else {
			WriteStates += "(" + FirstState + "_R10W00,0,"   + FirstState + "_R10W00MR,0,L)\t";
			WriteStates += "(" + FirstState + "_R10W00,1,"   + FirstState + "_R10W00MR,1,L)\n";
			WriteStates += "(" + FirstState + "_R10W00ML,0," + NextS3 + "_R,0,L)\t";
			WriteStates += "(" + FirstState + "_R10W00ML,1," + NextS3 + "_R,1,L)\n";
		}			
		if(Write4.equals("0")) {
			WriteStates += "(" + FirstState + "_R11W,0,"   + FirstState + "_R11W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R11W,1,"   + FirstState + "_R11W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R11W0,0,"  + FirstState + "_R11W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R11W0,1,"  + FirstState + "_R11W00,0,L)\n";
		} else if(Write4.equals("1")) {
			WriteStates += "(" + FirstState + "_R11W,0,"   + FirstState + "_R11W0,0,R)\t";
			WriteStates += "(" + FirstState + "_R11W,1,"   + FirstState + "_R11W0,0,R)\n";
			WriteStates += "(" + FirstState + "_R11W0,0,"  + FirstState + "_R11W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R11W0,1,"  + FirstState + "_R11W00,1,L)\n";
		} else if(Write4.equals("*")) {
			WriteStates += "(" + FirstState + "_R11W,0,"   + FirstState + "_R11W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R11W,1,"   + FirstState + "_R11W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R11W0,0,"  + FirstState + "_R11W00,0,L)\t";
			WriteStates += "(" + FirstState + "_R11W0,1,"  + FirstState + "_R11W00,0,L)\n";
		} else if(Write4.equals("H")) {
			WriteStates += "(" + FirstState + "_R11W,0,"   + FirstState + "_R11W0,1,R)\t";
			WriteStates += "(" + FirstState + "_R11W,1,"   + FirstState + "_R11W0,1,R)\n";
			WriteStates += "(" + FirstState + "_R11W0,0,"  + FirstState + "_R11W00,1,L)\t";
			WriteStates += "(" + FirstState + "_R11W0,1,"  + FirstState + "_R11W00,1,L)\n";
		}
		if(Move4.equals("R")) {
			WriteStates += "(" + FirstState + "_R11W00,0,"   + FirstState + "_R10W00MR,0,R)\t";
			WriteStates += "(" + FirstState + "_R11W00,1,"   + FirstState + "_R10W00MR,1,R)\n";
			WriteStates += "(" + FirstState + "_R11W00MR,0," + NextS4 + "_R,0,R)\t";
			WriteStates += "(" + FirstState + "_R11W00MR,1," + NextS4 + "_R,1,R)\n";
		} else {
			WriteStates += "(" + FirstState + "_R11W00,0,"   + FirstState + "_R11W00MR,0,L)\t";
			WriteStates += "(" + FirstState + "_R11W00,1,"   + FirstState + "_R11W00MR,1,L)\n";
			WriteStates += "(" + FirstState + "_R11W00ML,0," + NextS4 + "_R,0,L)\t";
			WriteStates += "(" + FirstState + "_R11W00ML,1," + NextS4 + "_R,1,L)\n";
		}			

		return WriteStates;
	}
	
	protected String ConvertTape() {
		String BitTape = "(";
		Tape TSign = super.Begin;
		while(TSign!=null) {
			BitTape += GetBits(TSign.GetSign());
			TSign = TSign.GetNext();
		}
		BitTape += ")";
		return BitTape;
	}
	
	public static void main(String[] args) {
		if(args.length!=1) {
			System.exit(0);
		}
		
		Converter conv = new Converter(args[0]);
		conv.Convert();
	}
}