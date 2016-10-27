//written by André Betz 
//http://www.andrebetz.de
import Tape;

class TMStringHelper{
	// converts symbols seperated with ',' in a string to TapeList
	public static Tape String2Tape(String Input){
		Tape StringTape = null;
		Tape LastSym = null;
		String Symbol = "";
		for(int i=0;i<=Input.length();i++) {
			char sign = ' ';
			if(i<Input.length()){
				sign = Input.charAt(i);
			}
			if(sign==','||i==Input.length()){
				Tape tempTape = new Tape(Symbol);
				if(StringTape == null){
					tempTape.ResetPos();
					StringTape = tempTape;
				}else{
					LastSym.SetNext(tempTape);
				}
				LastSym = tempTape;
				Symbol = "";
			}else{
				Symbol += sign;
			}
		}
		return StringTape;
	}
	// produce a Tape with length len and with Symbols Sym
	public static Tape GenTape(int len, String Sym){
		Tape returnTape = null;
		Tape LastTape = null;
		for(int i=0;i<len;i++){
			Tape tempTape = new Tape(Sym);
			if(returnTape==null){
				tempTape.ResetPos();
				returnTape = tempTape;
			}else{
				LastTape.SetNext(tempTape);
			}
			LastTape = tempTape;
		}
		return returnTape;
	}
	
	//returns Tape at the Position TpPos
	public static Tape GoTapePos(Tape Begin, int TpPos) {
		Tape ActTp = Begin;
		while(ActTp!=null) {
		  if(ActTp.GetPosNum()==TpPos) {
			return ActTp;
		  }
		  ActTp = ActTp.GetNext();
		}
		return null;
	  }
	public static Tape GoTapePos2(Tape Begin, int TpPos) {
		Tape ActTp = Begin;
		int PosCnt = 0;
		while(ActTp!=null) {
		  if(PosCnt==TpPos) {
			return ActTp;
		  }
		  PosCnt++;
		  ActTp = ActTp.GetNext();
		}
		return null;
	  }
	  // make a copy of the tape
	  public static Tape CopyTape(Tape orig){
	  	Tape tempTape = orig;
	  	Tape cpTape = null;
	  	Tape workTape = null;
		Tape LastTape = null;
	  	while(tempTape!=null){
	  		workTape = new Tape(tempTape.GetSign());
	  		if(cpTape==null){
	  			cpTape = workTape;
	  			cpTape.ResetPos();
	  		}else{
	  			LastTape.SetNext(workTape);
	  		}
	  		LastTape = workTape;
	  		tempTape = tempTape.GetNext();	  			  		
	  	}
	  	return cpTape;
	  }
	  // go to the End of a tape
	  public static Tape GoToEnd(Tape orig){
	  	Tape workTape = orig;
	  	Tape endTape = null;
		while(workTape!=null){
			endTape = workTape;
			workTape = workTape.GetNext();	  			  		
		}
		return endTape;
	  }
	  // prints the tape
	  public static String Tape2String(Tape orig){
	  	StringBuffer out = new StringBuffer();
		Tape workTape = orig;
		while(workTape!=null){
			out.append(workTape.GetSign());
			workTape = workTape.GetNext();
			if(workTape!=null){
				out.append(",");
			}	  			  		
		}
		return out.toString();	
	  }
	  
	  public static int TapeLen(Tape orig){
	  	int len = 0;
	  	while(orig!=null){
	  		len++;
	  		orig = orig.GetNext();
	  	}
	  	return len;
	  }
	  public static boolean Ident(Tape one, Tape two){
		if(TMStringHelper.TapeLen(one)!=TMStringHelper.TapeLen(two)){
			return false;
		}
		while(one!=null){
			if(!one.GetSign().equals(two.GetSign())){
				return false;
			}
			one = one.GetNext();
			two = two.GetNext();
		}
	  	return true;
	  }
	  
	  public static Tape Fill(int start,int end){
	  	Tape workTape = null;
	  	Tape tempTape = null;
	  	for(int i = start;i<=end;i++){
	  		if(workTape==null){
	  			workTape = new Tape(String.valueOf(i));
	  			tempTape = workTape;
	  		}else{
				tempTape.SetNext(new Tape(String.valueOf(i)));
				tempTape = tempTape.GetNext();
	  		}
	  	}
	  	return workTape;
	  }
	  public static Tape Add(Tape a,Tape b){
	  	Tape res = TMStringHelper.CopyTape(a);
	  	Tape temp = TMStringHelper.GoToEnd(res);
	  	if(temp!=null){
	  		temp.SetNext(TMStringHelper.CopyTape(b));
	  	}else{
			res = TMStringHelper.CopyTape(b);
	  	}
	  	return res;
	  }
}
