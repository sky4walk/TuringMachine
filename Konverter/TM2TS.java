//written by André Betz 
//http://www.andrebetz.de

// wandelt eine Turing Maschine mit 2 Symbolen (0,1) in ein TAG-System mit p=2 um
// aus "Universality of TAG Systems with p=2", von John Cocke und Marvin Minsky
//import java.io.*;
import TuringMachine;
import TSProduction;

class SimpleList
{
	SimpleList m_Next;
	SimpleList(){
		m_Next = null;
	}	
	public SimpleList getM_Next() {
		return m_Next;
	}
	public void setM_Next(SimpleList list) {
		m_Next = list;
	}
}

class SingleState
{
	SingleState m_Next;
	String  m_StateN;
	String  m_StateF_0;
	String  m_StateF_1;
	String  m_Write;
	String  m_Move;

	public SingleState(String StateN,String Write,String Move, String StateF_0, String StateF_1) {
		m_StateF_0 = StateF_0;
		m_StateF_1 = StateF_1;
		m_StateN = StateN;
		m_Write  = Write;
		m_Move   = Move;
		m_Next = null;
	}
		
	public String getM_Move() {
		return m_Move;
	}
	public SingleState getM_Next() {
		return m_Next;
	}
	public String getM_StateF_0() {
		return m_StateF_0;
	}
	public String getM_StateF_1() {
		return m_StateF_1;
	}
	public String getM_StateN() {
		return m_StateN;
	}
	public String getM_Write() {
		return m_Write;
	}
	public void setM_Move(String string) {
		m_Move = string;
	}
	public void setM_Next(SingleState state) {
		m_Next = state;
	}
	public void setM_StateF_0(String string) {
		m_StateF_0 = string;
	}
	public void setM_StateF_1(String string) {
		m_StateF_1 = string;
	}
	public void setM_StateN(String string) {
		m_StateN = string;
	}
	public void setM_Write(String string) {
		m_Write = string;
	}
}

class TM2TS extends TuringMachine 
{
	String m_TransState;
	SingleState NoReadStates;
	TSProduction TSProd;
	Tape TSTape;
	
	TM2TS(String fn) {
		super(fn);
		NoReadStates = null;
		m_TransState = null;
		TSTape = null;
		TSProd = null;
	}
	
	protected void convert2NoRead(){
		SingleState workState = null;
		SingleState tempState1 = null;
		SingleState tempState2 = null;
		TMState stPos = Start;
		while(stPos!=null) {
			String FirstState1 = stPos.GetStateF();
			String Read1       = stPos.GetRead();
			String Write1      = stPos.GetWrite();
			String NState1     = stPos.GetStateN();
			String Move1       = stPos.GetMove();
			stPos = stPos.GetNext();
			String FirstState2 = stPos.GetStateF();
			String Write2      = stPos.GetWrite();
			String Read2       = stPos.GetRead();
			String NState2     = stPos.GetStateN();
			String Move2       = stPos.GetMove();
			stPos = stPos.GetNext();
			
			tempState1 = new SingleState(FirstState1+"_0",Write1,Move1,NState1+"_0",NState1+"_1");
			tempState2 = new SingleState(FirstState2+"_1",Write2,Move2,NState2+"_0",NState2+"_1");
			tempState1.setM_Next(tempState2);
			if(workState!=null){
				workState.setM_Next(tempState1);
			}
			workState = tempState2;
			
			if(NoReadStates==null){
				NoReadStates = tempState1;
			}	
		}
		if(Pos.GetSign().equals("0")){
			m_TransState = m_actState + "_0";
		}else{
			m_TransState = m_actState + "_1";
		}
	}

	// produces a list with the length 2^n and adds the List start
	protected SimpleList Binary2Unary(int n, SimpleList Start){
		SimpleList First = new SimpleList();
		SimpleList Last = First;
		for(int i=0;i<n;i++){
			SimpleList tempFirst = First;
			while(tempFirst!=null){
				SimpleList NewList = new SimpleList();
				NewList.setM_Next(First);
				First = NewList;				
				Last = tempFirst;
				tempFirst = tempFirst.getM_Next();
			}
		}
		Last.setM_Next(Start);
		return First;
	}
	
	//generate the unary list M = Sum(0..i,a[i]*2^i), N = Sum(0..i,b[i]*2^i)
	protected void GenTS_Strg(){
		SimpleList List_M = null;
		SimpleList List_N = null;
		int TapePos = m_StartPos-1;
		Tape TSign = super.Begin;
		//generate List
		while(TSign!=null&&TSign.GetPosNum()<m_StartPos) {
			String Bits = TSign.GetSign();
			if(Bits.equals("1")){
				List_M = Binary2Unary(TapePos,List_M);
			}			
			TSign = TSign.GetNext();
			TapePos--;
		}
		if(TSign!=null){
			TSign = TSign.GetNext();			
		}
		TapePos = 0;
		while(TSign!=null) {
			String Bits = TSign.GetSign();
			if(Bits.equals("1")){
				List_N = Binary2Unary(TapePos,List_N);
			}			
			TSign = TSign.GetNext();
			TapePos++;
		}
		//generate Tag System Tape
		Tape tempTape = new Tape("A_"+m_TransState);
		tempTape.ResetPos();
		TSTape = tempTape;
		tempTape.SetNext(new Tape("x_"+m_TransState));
		tempTape = tempTape.GetNext();		
		while(List_M!=null){
			tempTape.SetNext(new Tape("al_"+m_TransState));
			tempTape = tempTape.GetNext();		
			tempTape.SetNext(new Tape("x_"+m_TransState));
			tempTape = tempTape.GetNext();		
			List_M = List_M.getM_Next();
		}
		tempTape.SetNext(new Tape("B_"+m_TransState));
		tempTape = tempTape.GetNext();		
		tempTape.SetNext(new Tape("x_"+m_TransState));
		tempTape = tempTape.GetNext();		
		while(List_N!=null){
			tempTape.SetNext(new Tape("be_"+m_TransState));
			tempTape = tempTape.GetNext();		
			tempTape.SetNext(new Tape("x_"+m_TransState));
			tempTape = tempTape.GetNext();		
			List_N = List_N.getM_Next();
		}
	}

	protected void GenProductions(){
		SingleState workState = NoReadStates;
		TSProduction tempProd = null;
		while(workState!=null){
			if(tempProd==null){
				tempProd = new TSProduction();
				TSProd = tempProd;
			}else{
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
			}
			if(workState.getM_Move()=="R"){
				// move right
				// 0:A->Cx 1:(A->Cxcx)
				tempProd.setM_Start("A_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("C_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateN()));
				if(workState.getM_Write().equals("1")){
					// Write 1
					// A->Cxc
					tempProd.getM_Produce().GetNext().SetNext(new Tape("c_"+workState.getM_StateN()));
					// al->xc
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("al_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("x_"+workState.getM_StateN()));
					tempProd.getM_Produce().SetNext(new Tape("c_"+workState.getM_StateN()));
					// B->xS
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("B_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("x_"+workState.getM_StateN()));
					tempProd.getM_Produce().SetNext(new Tape("S_"+workState.getM_StateN()));
				}else{
					// Write 0
					// al->cx
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("al_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("c_"+workState.getM_StateN()));
					tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateN()));
					// B->S
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("B_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("S_"+workState.getM_StateN()));
				}
				// Be->s
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("be_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("s_"+workState.getM_StateN()));
				// C->D1D0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("C_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("D1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("D0_"+workState.getM_StateN()));
				// c->d1d0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("c_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("d1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("d0_"+workState.getM_StateN()));
				// S->T1T0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("S_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("T1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("T0_"+workState.getM_StateN()));
				// s->t1t0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("s_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("t1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("t0_"+workState.getM_StateN()));
				// N odd: 
				// D1->Ax
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("D1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("A_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// d1->alx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("d1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("al_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// T1->Bx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("T1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("B_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// t1->Bex
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("t1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("be_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// N even
				// D0->xAx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("D0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("x_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().SetNext(new Tape("A_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().GetNext().SetNext(new Tape("x_"+workState.getM_StateF_0()));
				// d0->alx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("d0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("al_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_0()));
				// T0->Bx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("T0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("B_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_0()));
				// t0->Be
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("t0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("be_"+workState.getM_StateF_0()));
			}else{
				// move right
				// 0:B->Cx 1:(B->Cxcx)
				tempProd.setM_Start("B_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("C_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateN()));
				if(workState.getM_Write().equals("1")){
					// Write 1
					// B->Cxc
					tempProd.getM_Produce().GetNext().SetNext(new Tape("c_"+workState.getM_StateN()));
					// be->xc
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("be_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("x_"+workState.getM_StateN()));
					tempProd.getM_Produce().SetNext(new Tape("c_"+workState.getM_StateN()));
					// A->xS
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("B_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("x_"+workState.getM_StateN()));
					tempProd.getM_Produce().SetNext(new Tape("S_"+workState.getM_StateN()));
				}else{
					// Write 0
					// be->cx
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("be_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("c_"+workState.getM_StateN()));
					tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateN()));
					// A->S
					tempProd.setM_Next(new TSProduction());
					tempProd = tempProd.getM_Next();
					tempProd.setM_Start("A_"+workState.getM_StateN());				
					tempProd.setM_Produce(new Tape("S_"+workState.getM_StateN()));
				}
				// al->s
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("al_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("s_"+workState.getM_StateN()));
				// C->T1T0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("C_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("T1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("T0_"+workState.getM_StateN()));
				// c->t1t0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("c_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("t1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("t0_"+workState.getM_StateN()));
				// S->D1D0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("S_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("D1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("D0_"+workState.getM_StateN()));
				// s->d1d0
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("s_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("d1_"+workState.getM_StateN()));
				tempProd.getM_Produce().SetNext(new Tape("d0_"+workState.getM_StateN()));
				// N odd: 
				// D1->Bx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("D1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("B_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// d1->bex
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("d1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("be_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// T1->Ax
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("T1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("A_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// t1->alx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("t1_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("al_"+workState.getM_StateF_1()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_1()));
				// N even
				// D0->xBx
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("D0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("x_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().SetNext(new Tape("B_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().GetNext().SetNext(new Tape("x_"+workState.getM_StateF_0()));
				// d0->bex
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("d0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("be_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_0()));
				// T0->Ax
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("T0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("A_"+workState.getM_StateF_0()));
				tempProd.getM_Produce().SetNext(new Tape("x_"+workState.getM_StateF_0()));
				// t0->al
				tempProd.setM_Next(new TSProduction());
				tempProd = tempProd.getM_Next();
				tempProd.setM_Start("t0_"+workState.getM_StateN());				
				tempProd.setM_Produce(new Tape("al_"+workState.getM_StateF_0()));
			}
			// next state
			workState = workState.getM_Next();			
		}
	}

	protected void PrintTS(){
		StringBuffer out = new StringBuffer();
		out.append("(2)\n\n(");
		Tape tempTape = TSTape;
		while(tempTape!=null){
			out.append(tempTape.GetSign());
			tempTape = tempTape.GetNext();
			if(tempTape!=null){
				out.append(",");
			}
		}
		out.append(")\n\n");
		TSProduction tempProds = TSProd;
		while(tempProds!=null){
			out.append("(");
			out.append(tempProds.getM_Start());
			tempTape = tempProds.getM_Produce();
			while(tempTape!=null){
				out.append(",");
				out.append(tempTape.GetSign());
				tempTape = tempTape.GetNext();
			}
			out.append(")\n");
			tempProds = tempProds.getM_Next();
		}
		out.append("\n");
		System.out.print(out);
	}
	
	public Tape GetTSTape(){
		return TSTape; 
	}
	
	public TSProduction GetTSProduction(){
		return TSProd;
	}
	
	public void convert(){
		convert2NoRead();
		GenTS_Strg();
		GenProductions();
		PrintTS();			
	}
	
	public static void main(String[] args) {
			if(args.length!=1) {
				System.exit(0);
			}
			TM2TS Converter = new TM2TS(args[0]);
			Converter.convert();
	}
}