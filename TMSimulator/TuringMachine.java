//written by André Betz 
//http://www.andrebetz.de

import Tape;
import TMState;
import java.io.*;


public class TuringMachine {
  TMState Start;
  TMState Last;
  String m_actState;
  Tape Pos;
  Tape OldPos;
  Tape Begin;
  String m_Right;
  int m_StartPos;

  public TuringMachine() {
	Pos = null;
	Begin = null;
	Tape.m_PosNumCnt = 0;
	m_Right = "R";
  }
  
  public TuringMachine(String fn) {
  	Pos = null;
  	Begin = null;
	Tape.m_PosNumCnt = 0;
  	Load(fn);
    m_Right = "R";
  }

  protected void Init(String state,int TpPos) {
    m_actState = state;
    m_StartPos = TpPos;
	OldPos = Pos;
  }

  protected TMState AddState(String state1, String read, String state2, String write, String move) {
    if(Start == null) {
      Start = new TMState(state1,read,state2,write,move);
      Last = Start;
    } else {
      Last.SetNext(new TMState(state1,read,state2,write,move));
      Last = Last.GetNext();
    }
    return Last;
  }

  protected Tape AddTape(String sign) {
    if(Pos == null) {
      Pos = new Tape(sign);
      Begin = Pos;
    } else {
      Pos.SetNext(new Tape(sign));
      Pos = Pos.GetNext();
    }
    return Pos;
  }

  protected void LoadTape(String Tape) {
    Tape next;
    int len = Tape.length();
    for(int i=0;i<len;i++) {
      next = AddTape(String.valueOf(Tape.charAt(i)));
      if(i==0) {
        Begin = next;
      }
    }
    Pos = GoTapePos(m_StartPos);
  }

  protected Tape GoTapePos(int TpPos) {
  	Tape ActTp = Begin;
  	while(ActTp!=null) {
  	  if(ActTp.GetPosNum()==TpPos) {
  	  	return ActTp;
  	  }
	  ActTp = ActTp.GetNext();
  	}
  	return null;
  }
  
  protected TMState FindTransition(String state, String sign) {
    TMState stPos = Start;
    while(stPos!=null) {
      String tst1 = stPos.GetStateF();
      if (state.equals(stPos.GetStateF())) {
        if (sign.equals(stPos.GetRead())) {
          return stPos;
        }
      }
      stPos = stPos.GetNext();
    }
    return null;
  }

  public String Step() {
    if(Pos!=null) {
      String read = Pos.GetSign();
      TMState newTrans = FindTransition(m_actState,read);
      if(newTrans!=null) {
        m_actState = newTrans.GetStateN();
        Pos.SetSign(newTrans.GetWrite());
		OldPos = Pos;
        if(m_Right.equals(newTrans.GetMove())) {
          Pos = Pos.GetNext();
        } else {
          Pos = Pos.GetLast();
        }
        return Trans2String(newTrans) + '\t' + GetTape();
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  protected String GetTape() {
    Tape TSign = Begin;
    String Out = "";
    while(TSign!=null) {
      if(TSign==OldPos) {
      	Out += "[" + TSign.GetSign() + "]";	
      }else {
      	Out += TSign.GetSign();
      }
      TSign = TSign.GetNext();
    }
    return Out;
  }

  protected String Trans2String(TMState st) {
    String out;
    out = "("  + st.GetStateF() + "," + st.GetRead();
    out += "," + st.GetStateN() + "," + st.GetWrite();
    out += "," + st.GetMove() + ")";
    return out;
  }

  protected int DelNoSigns(String Input, int spos) {
	int newpos = spos;
	int slen = Input.length();
	if(newpos<slen) {
		char sign = Input.charAt(newpos);
		while((sign==' '||sign=='\n'||sign=='\r'||sign=='\t')&&(newpos<slen)) {
			newpos++;
			if(newpos<slen) {
				sign = Input.charAt(newpos);
			}
		}
	}
  	return newpos;
  }
  
  protected int DelComment(String Input, int spos) {
	int newpos = spos;
	int slen = Input.length();
	char sign = Input.charAt(newpos);
	while((sign!='\n')&&(newpos<slen)) {
		newpos++;
		if(newpos<slen) {
			sign = Input.charAt(newpos);
		}
	}
	return newpos;  		
  }
  
  protected boolean ParseInputString(String Input) {
  	int automstate = 0;
  	int len = Input.length();
  	int spos = 0;
  	char sign;
  	boolean failure = false;
  	String InputRead1 = "";
	String InputRead2 = "";
	String InputRead3 = "";
	String InputRead4 = "";
	String InputRead5 = "";
  	
  	while(spos<len) {
		spos = DelNoSigns(Input,spos);
		if(spos<len) {
			sign = Input.charAt(spos);	
	  		switch(automstate) {
	  			case 0:
	  			if (sign=='(') {
	  				automstate = 1;
	  			}else if (sign=='#'){
					spos = DelComment(Input,spos);
	  			}else {
					automstate = 20;
					break;
	  			}
	  	    	break;
	  	    	case 1:
	  	    	if(sign==',') {
	  	    		automstate = 2;
	  	    	}else{
	  	    		InputRead1 += sign; 
	  	    	}  	    	
	  	    	break;
				case 2:
				if(sign==')') {
					automstate = 3;
					Init(InputRead1,Integer.valueOf(InputRead2,10).intValue());
					InputRead1 = "";
					InputRead2 = "";
				}else{
					InputRead2 += sign; 
				}  	    	
				break;
				case 3:
				if (sign=='(') {
					automstate = 4;
				}else if (sign=='#'){
					spos = DelComment(Input,spos);
				}else {
					automstate = 20;
				}
				break;
				case 4:
				if(sign==')') {
					automstate = 5;
					LoadTape(InputRead1);
					InputRead1 = "";
				}else{
					InputRead1 += sign; 
				}  	    	
				break;
				case 5:
				if (sign=='(') {
					automstate = 6;
				}else if (sign=='#'){
					spos = DelComment(Input,spos);
				}else {
					automstate = 20;
				}
				break;
				case 6:
				if(sign==',') {
					automstate = 7;
				}else{
					InputRead1 += sign; 
				}  	    	
				break;
				case 7:
				if(sign==',') {
					automstate = 8;
				}else{
					InputRead2 += sign; 
				}  	    	
				break;
				case 8:
				if(sign==',') {
					automstate = 9;
				}else{
					InputRead3 += sign; 
				}  	    	
				break;
				case 9:
				if(sign==',') {
					automstate = 10;
				}else{
					InputRead4 += sign; 
				}  	    	
				break;
				case 10:
				if(sign==')') {
					automstate = 5;
					AddState(InputRead1,InputRead2,InputRead3,InputRead4,InputRead5);
					InputRead1 = "";
					InputRead2 = "";
					InputRead3 = "";
					InputRead4 = "";
					InputRead5 = "";
				}else{
					InputRead5 += sign; 
				}  	    	
				break;

	  	    	default:
	  	    	  failure = false;
	  	    	break;
	  		}
			spos++;
	  	}
  	}
	return failure;  		
  }
  
  protected boolean Load(String FileName) {
	String readinput = "";
	if(FileName==null){
		return false;
	}
	try {
		File f = new File(FileName);
		FileReader in = new FileReader(f);
		char[] buffer = new char[128];
		int len;
		while((len = in.read(buffer))!=-1) {
			readinput += new String(buffer,0,len);
		}
	}
	catch(IOException e) {
	}
	
	return ParseInputString(readinput);  	
  }


  public static void main(String[] args) {
	if(args.length!=1) {
		System.exit(0);
	}
	
    TuringMachine TM1 = new TuringMachine(args[0]);

    String out1;
    while((out1 = TM1.Step())!=null) {
      System.out.println(out1);
    }
  }

}