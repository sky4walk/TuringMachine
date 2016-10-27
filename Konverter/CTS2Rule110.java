// written by André Betz 
// http://www.andrebetz.de
import TM2TS;
import TS2CTS;
import TuringMachine;
import TMStringHelper;
// translated from a Haskell-Program written by 
// Mirko Rahn http://www.stud.uni-karlsruhe.de/~uyp0/
// who has translated it from Mathematica
// I also don't understand what this program is doing in detail
class Struct_D{
	public int x;
	public int y;
	public Struct_D Next;
	public Struct_D(){
		Next = null;
	}
	public int fstD(){
		return x; 	
	}
	public int sndD(){
		return y; 	
	}
	public boolean IsEqual(Struct_D a){
		if((a.x == x)&&(a.y == y)){
			return true;
		}
		return false;
	}
	public Struct_D Copy(){
		Struct_D cp = new Struct_D();
		cp.x = x;
		cp.y = y;
		return cp;
	}
	public StringBuffer ToStringBuffer(){
		StringBuffer Out = new StringBuffer();
		Struct_D work_D = this;
		Out.append("D ");
		if(work_D.x<0){
			Out.append("(");
			Out.append(work_D.x);
			Out.append(")");
		}else{
			Out.append(work_D.x);
		}
		Out.append(" ");
		if(work_D.y<0){
			Out.append("(");
			Out.append(work_D.y);
			Out.append(")");
		}else{
			Out.append(work_D.y);
		}
		Out.append(" ");
		return Out;
	}
};

class Struct_S{
	public Struct_D D;
	public int a;
	public int b;
	public Tape c;
	public Struct_S Next;
	public Struct_S(){
		D = new Struct_D();
		Next = null;
	}
	public Struct_S Copy(){
		Struct_S S = new Struct_S();
		(S.D).x = D.x; 
		(S.D).y = D.y;
		S.a = a;
		S.b = b;
		S.c = TMStringHelper.CopyTape(c); 
		return S;
	}
	public boolean eq(Struct_S S){
		if((a!=S.a)||(b!=S.b)){
			return false;
		}
		if(!TMStringHelper.Ident(c,S.c)){
			return false;
		}
		return true;
	}
	public StringBuffer ToStringBuffer(){
		StringBuffer Out = new StringBuffer();
		Struct_S work_S = this;
		Struct_D work_D = null;
		Tape work_c = null;
		while(work_S!=null){
			work_D = work_S.D;
			Out.append("S (");
			Out.append(work_D.ToStringBuffer());
			Out.append(") ");
			Out.append(work_S.a);
			Out.append(" ");
			Out.append(work_S.b);
			Out.append(" [");
			work_c = work_S.c;
			Out.append(TMStringHelper.Tape2String(work_c));			
			Out.append("]");
			if(work_S.Next!=null){
				Out.append(",");
			}
			work_S = work_S.Next;
		}
		return Out;
	}
};

class Struct_DS{
	public Struct_D D;
	public Struct_S S;
	public Struct_DS Next;
	public int Nr;
	public Struct_DS(){
		Nr = 0;
		Next = null;
		D = new Struct_D();
		S = new Struct_S();
	}
	public Struct_DS Copy(){
		Struct_DS NeuDS = new Struct_DS();
		(NeuDS.D).x = D.x;
		(NeuDS.D).y = D.y;
		((NeuDS.S).D).x = (S.D).x;
		((NeuDS.S).D).y = (S.D).y;
		(NeuDS.S).a = S.a;
		(NeuDS.S).b = S.b;
		(NeuDS.S).c = S.c;
		(NeuDS.S).Next = S.Next;
		return NeuDS;
	}
	public Struct_DS LastStructDS(){
		Struct_DS ret = this;
		Struct_DS EndStructDS = null;
		while(ret!=null){
			EndStructDS = ret;
			ret = ret.Next;
		}
		return EndStructDS;
	}
	public boolean IsEqual(Struct_DS a){
		if(!S.eq(a.S)){
			return false;
		}
		if(!D.IsEqual(a.D)){
			return false;
		}
		return true;
	}
	public Struct_DS Last(){
		Struct_DS workDS = this;
		Struct_DS LastDS = null;
		while(workDS!=null){
			LastDS = workDS;
			workDS = workDS.Next;
		}
		return LastDS;
	}
	public StringBuffer ToStringBuffer(){
		StringBuffer Out = new StringBuffer();
		Struct_DS work_DS = this;
		Struct_S work_S = null;
		Struct_D work_D = null;
		
		Out.append("[");
		while(work_DS!=null){
			work_D = work_DS.D;
			Out.append("(");
			Out.append(work_D.ToStringBuffer());
			Out.append(",[");			
			work_S = work_DS.S;
			Out.append(work_S.ToStringBuffer());
			Out.append("])");
			if(work_DS.Next!=null){
				Out.append(",");
			}
			work_DS = work_DS.Next;
		}
		Out.append("]");
		
		return Out;
	}
};

class CTS2Rule110 extends TuringMachine
{
	Tape m_CTSTape;
	Tape m_Rule110Config;
	CTSProduction m_CTSProd;
	Tape Rule110_SymOne;
	Tape Rule110_SymTwo;
	
	String SymOne = "1,0,0,1,1,0,1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1," +  
					"1,1,0,0,0,1,0,0,0,0,0,1,1,0,1,1,1,1,1,0,0,0,1," +
					"0,0,1,1,0,1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1," +
					"1,0,0,0,1,0,0,0,0,0,1,1,0,1,1,1,1,1,0,0,0,1,0," +
					"0,1,1,0,1,1,1,1,1,0,1,0,0,1,1,1,0,0,1,1,0,1,1," +
					"1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,0,1,0,0," +
					"1,1,0,0,0,0,1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1," +
					"1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,0";
	
	String SymTwo = "1,0,0,1,1,0,1,1,1,1,1,0,1,0,0,1,1,1,0,0,1,1,0," +
					"1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,0,1," +
					"0,0,1,1,0,1,1,1,1,1,0,1,0,0,1,1,1,0,0,1,1,0,1," +
					"1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,0,1,0," +
					"0,1,1,0,1,1,1,1,1,0,1,0,0,1,1,1,0,0,1,1,0,1,1," + 
					"1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,0,1,0,0," +
					"1,1,0,0,0,0,1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1," +
					"1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,0";
					
	String c[] = {"22,11,3,39,3,1,63,12,2,48,5,4,29,26,4,43,26,4,23,3,4,47,4,4",
				  "22,11,3,39,3,1,87,6,2,32,2,4,13,23,4,27,16,4",
				  "17,22,4,23,24,4,31,29,4,17,22,4,39,27,4,47,4,4",
				  "17,22,4,47,18,4,15,19,4,17,22,4,39,27,4,47,4,4",
				  "41,16,4,47,18,4,15,19,4,17,22,4,39,27,4,47,4,4"};
												
	int struct_s[][] = {{3,1,10,   4, 8,2},
		 				{3,1, 1, 619,15,2},
		 				{3,1,10,4956,18,2},
		 				{0,9,10,   4, 8,1},
		 				{5,9,14,   1, 1,1}};
												
	public CTS2Rule110(String Rule110TM,Tape CTSTape,CTSProduction CTSProd){
		super(Rule110TM);
		m_CTSProd = CTSProd;
		m_CTSTape = CTSTape;
		m_Rule110Config = null;
		Rule110_SymOne = TMStringHelper.String2Tape(SymOne);
		Rule110_SymTwo = TMStringHelper.String2Tape(SymTwo);		
	}
	
	
	public void Convert(){ 
		if(CheckProdLen(m_CTSProd)){
			m_Rule110Config = func_CT2Rule110();
			PrintAll();
		}
	}
	
	protected Tape func_CT2Rule110(){
		Tape res = null;
		Struct_DS g2b = func_g2b();
		int y1 = (g2b.D).y - 11;
		int y1b;
		int x1 = 0;
		int nr = g2b.Nr;
		
		if(y1<0){
			y1b = y1+30;
		}else{
			y1b = y1;
		}
		
		Struct_DS last_g2b = g2b.Last();
		Struct_S S = last_g2b.S;
		while(S!=null){
			if((S.D).y == y1b){
				x1 += (S.D).x + TMStringHelper.TapeLen(S.c);
			}
			S = S.Next;
		}
		
		Struct_DS work_g2b = g2b;
		Struct_S g3 = new Struct_S();
		(g3.D).x = x1;
		(g3.D).y = y1b;
		
		while(work_g2b!=null){
			g3 = func_sadd(g3,work_g2b);
			work_g2b = work_g2b.Next;
		}
		
		int len_g3c = TMStringHelper.TapeLen(g3.c);
		double sp_f = ((double)(5 * len_g3c) / (double)(28 * nr));
		int sp = (int)sp_f;
		if(sp_f>(double)sp){
			sp++;
		}
		sp += 2;
		
		Struct_S S5 = func_struct(5);
		Struct_DS firstDS = null;
		Struct_DS lastDS = null;
		for(int i=0;i<3;i++){		
			Struct_DS structDS1 = new Struct_DS();
			(structDS1.D).x = sp * 28 + 6;
			(structDS1.D).y = 1;
			structDS1.S = func_struct(5);
			
			Struct_DS structDS2 = new Struct_DS();
			(structDS2.D).x = 398;
			(structDS2.D).y = 1;
			structDS2.S = func_struct(5);
			
			Struct_DS structDS3 = new Struct_DS();
			(structDS3.D).x = 342;
			(structDS3.D).y = 1;
			structDS3.S = func_struct(5);
			
			Struct_DS structDS4 = new Struct_DS();
			(structDS4.D).x = 370;
			(structDS4.D).y = 1;
			structDS4.S = func_struct(5);
			
			structDS1.Next = structDS2;
			structDS2.Next = structDS3;
			structDS3.Next = structDS4;
			if(firstDS==null){
				firstDS = structDS1;
				lastDS = structDS4;
			}else{
				lastDS.Next = structDS1;
				lastDS = structDS4;
			}			
		}

		Struct_S g4 = new Struct_S();
		(g4.D).x = 17;
		(g4.D).y = 1;

		while(firstDS!=null){
			g4 = func_sadd(g4,firstDS);
			firstDS = firstDS.Next;
		}
		
		Tape g5 = null;

		int RepNr = 1 + 2*sp + 24*TMStringHelper.TapeLen(m_CTSTape);
		for(int i=0;i<RepNr;i++){
			g5 = TMStringHelper.Add(g5,func_bg(1,14));
		}

		Tape work_CTSTape = m_CTSTape;
		while(work_CTSTape!=null){
			if(work_CTSTape.GetSign().equals("0")){
				g5 = TMStringHelper.Add(g5,Rule110_SymOne);
			}else{
				g5 = TMStringHelper.Add(g5,Rule110_SymTwo);
			}
			work_CTSTape = work_CTSTape.GetNext();
		}

		g5 = TMStringHelper.Add(g5,func_bg(1,9));

		int bgNr = 60 - (g2b.D).x + (g3.D).x;
		if((g2b.D).y < (g3.D).y){
			bgNr += 8;
		}
		g5 = TMStringHelper.Add(g5,func_bg(6,bgNr));
		
		len_g3c = TMStringHelper.TapeLen(g5);

		Tape Conf110 = null;
		Conf110 = TMStringHelper.Add(g4.c,func_bg(4,11));
		Conf110 = TMStringHelper.Add(Conf110,g5);
		Conf110 = TMStringHelper.Add(Conf110,g3.c);
			
		return Conf110;
	}
	
	protected Struct_DS func_g2b(){
		Struct_DS g2 = func_g2();
		Struct_DS copy_g2 = null;
		Struct_DS workcopy_g2 = null;	
		int count = 0;
		int Sum = 1;

		while(Sum!=0)
		{
			Struct_DS work_g2 = g2;
			//copy g2 to the End of copy_g2
			while(work_g2!=null){
				if(copy_g2==null){
					copy_g2 = work_g2.Copy();
					workcopy_g2 = copy_g2;
				}else{
					workcopy_g2.Next = work_g2.Copy();
					workcopy_g2 = workcopy_g2.Next;			
				}		
				work_g2 = work_g2.Next;
			}
			copy_g2.Nr = count;
			count++;
			// Sums the D.y 
			work_g2 = copy_g2;
			Sum = 0;
			while(work_g2!=null){
				Sum +=(work_g2.D).y;
				work_g2 = work_g2.Next;
			}		
			Sum %= 30;
		}
		return copy_g2;	
	}
	
	protected Struct_DS func_g2(){
		Struct_DS ret = null;
		Struct_DS work_ret = null;
		Struct_DS g1 = func_g1();
		Struct_DS work_g1 = g1;
		Struct_S s3 = func_struct(3);
		while(work_g1!=null){
			Struct_DS g1b = SixBefore(g1,work_g1);
			Struct_DS neuDS = new Struct_DS();
			if(((work_g1.D).x == 22)        &&
			   ((work_g1.D).y == 11)        &&
			   ((work_g1.D).IsEqual(g1b.D)) &&  
			   ((work_g1.S).eq(s3))         &&
			   ((work_g1.S).eq(g1b.S))){
					(neuDS.D).x = 20;
					(neuDS.D).y = 8;
					neuDS.S = s3;
			}else{
					neuDS.D = work_g1.D;
					neuDS.S = work_g1.S;
			}
			if(ret==null){
				ret = neuDS;
				work_ret = ret;			
			}else{
				work_ret.Next = neuDS;
				work_ret = work_ret.Next;
			}
			work_g1 = work_g1.Next;
		}
		return ret;
	}
	
	protected Struct_DS func_g1(){
		Struct_DS ret = null;
		Struct_DS work = null;
		CTSProduction workProds = m_CTSProd;
		while(workProds!=null){
			Struct_DS temp = null;
			if(workProds.getM_Produce()==null){
				temp = func_p3(2);
			}else{
				Struct_DS temp2 = null;
				temp = func_p3(1);
				temp2 = temp.LastStructDS();
				temp2.Next = func_p3(3);
				temp2 = temp2.LastStructDS();
				int dif = Integer.valueOf(workProds.getM_Produce().GetSign(),10).intValue();
				temp2.Next = func_p3(5-dif);
				temp2 = temp2.LastStructDS();
				Tape ruleEl = workProds.getM_Produce();
				int rulelen = TMStringHelper.TapeLen(ruleEl);
				for(int i=1;i<rulelen;i++){
					temp2.Next = func_p3(4);
					temp2 = temp2.LastStructDS();
					dif = Integer.valueOf(ruleEl.GetSign(),10).intValue();
					temp2.Next = func_p3(5-dif);
					temp2 = temp2.LastStructDS();
					ruleEl = ruleEl.GetNext();
				}
			}
			if(work==null){
				ret = temp;
				work = ret;
			}else{
				work = work.LastStructDS();
				work.Next = temp;
			}
			workProds = (CTSProduction)workProds.getM_Next();
		}
		return ret;
	}
	
	
	protected Struct_DS func_p3(int n){
		n--;
		Struct_DS ret = null;
		Struct_DS workD = null;
		Tape[] liste = func_parts(3,TMStringHelper.String2Tape(c[n]));
		for(int i=0;i<liste.length;i++){
			if(ret==null){
				workD = new Struct_DS();
				ret = workD; 
			}else{
				workD.Next = new Struct_DS();
				workD = workD.Next;
			}
			(workD.D).x = Integer.valueOf(liste[i].GetSign(),10).intValue();
			(workD.D).y = Integer.valueOf(liste[i].GetNext().GetSign(),10).intValue();
			int z = Integer.valueOf(liste[i].GetNext().GetNext().GetSign(),10).intValue();
			workD.S = func_struct(z);
		}
		return ret;
	}
	
	protected Struct_S func_sadd(Struct_S D1b, Struct_DS D2st){
		Struct_D D1 = D1b.D;
		Tape b = D1b.c;
		Struct_D D2 = D2st.D;
		Struct_S st = D2st.S;
		Struct_S ret = new Struct_S();
		Struct_S workStructS = st;
		Tape b2 = null;
		int x = D1.x;
		int y = D1.y;
		int dx = D2.x;
		int dy = D2.y;
		int x1 = dx - x;
		int y1 = dy - y;
		int x2 = 0;
		int lenSt = length_Struct_S(st);
		
		while(y1>0){
			if(lenSt==30){
				x1 += 8;
				y1 -= 30; 
			}else{
				x1 -= 2;
				y1 -= 3;
			}
		}
		
		y1 *= (-1);
		
		while(workStructS!=null){
			int x3 = (workStructS.D).x;
			int sty = (workStructS.D).y;
			if(sty==y1){
				int pl = workStructS.a;
				Tape sb = workStructS.c;
				b2 = func_bg(pl-x1-x3,x1+x3);
				b2 = TMStringHelper.Add(b2,sb);
				x2 = x3 + TMStringHelper.TapeLen(sb);
				break;
			}
			workStructS = workStructS.Next;	
		}
		
		(ret.D).x = x2;
		(ret.D).y = y1;
		ret.c = TMStringHelper.Add(b,b2);
		
		return ret;
	}
	
	protected int length_Struct_S(Struct_S S){
		int len = 0;
		while(S!=null){
			len++;
			S = S.Next;
		}
		return len;
	}
	
	protected Struct_S func_struct(int s){
		s--;
		int x  = struct_s[s][0];
		int pl = struct_s[s][1];
		int pr = struct_s[s][2];
		int b  = struct_s[s][3];
		int bl = struct_s[s][4];
		int p  = struct_s[s][5];
		int k = p;
		
		Struct_S gr = new Struct_S();
		Struct_S g = null;
		Struct_S Liste = null;
		Struct_S workListe = null;
		 
		(gr.D).x = x;
		(gr.D).y = 0;
		gr.a = pl;
		gr.b = pr;
		gr.c = integerDigits(b,2,bl); 
		
		Struct_S g1 = gr.Copy();
		
		while(k>0){
			g = func_ev(g1);
			
			if(Liste==null){
				Liste = g1;
				workListe = Liste; 
			}else{
				workListe.Next = g1;
				workListe = workListe.Next;
			}
			
			if(g.eq(gr)){
				k--;
			}
			g1 = g;
		}		
		return Liste;	
	}
	
	protected Struct_S func_ev(Struct_S S){
		int pl = S.a;
		int pr = S.b;
		Tape b = S.c;
		Struct_S erg = new Struct_S();
		
		Tape temp = null;
		// calc r
		Tape r = null;
		Tape workTape = null;
		workTape = use_list_convolve(pl,pr,b);
		while(workTape!=null){
			int res = Integer.valueOf(workTape.GetSign(),10).intValue();
			res = pow2(res);
			// .&. => res AND 110
			res = res & 110;	
			// Signum
			if(res>0){
				res = 1; 
			}else if(res<0){
				res = -1;
			}else{
				res = 0;
			}
			if(r==null){
				r = new Tape(String.valueOf(res));
				temp = r;
			}else{
				temp.SetNext(new Tape(String.valueOf(res)));
				temp = temp.GetNext();
			}
			workTape = workTape.GetNext();
		}
				
		// calc lr
		int lr = TMStringHelper.TapeLen(r);
		// calc pl1
		temp = use_position(pl+3,r);
		if(temp==null){
			temp = new Tape(String.valueOf(lr));
		}else{
			temp.SetNext(new Tape(String.valueOf(lr)));
		}
		int pl1 = Integer.valueOf(temp.GetSign(),10).intValue();
		// calc pr1
		temp = use_position(pr+5-lr,r);
		if(temp==null){
			temp = new Tape("1");
		}
		temp = TMStringHelper.GoToEnd(temp);
		int pr1 = Integer.valueOf(temp.GetSign(),10).intValue();
		if(pr1<pl1){
			pr1 = pl1;
		}
		
		(erg.D).x = (S.D).x + pl1 - 2;	
		(erg.D).y = (S.D).y + 1;
		erg.a = pl1 + (pl + 2) % 14;	
		erg.b = 1 + (pr + 4) % 14 + pr1 - lr;
		
		//calc S.c
		erg.c = null;
		Tape posTp = TMStringHelper.Fill(pl1,pr1);
		while(posTp!=null){
			int iPos = Integer.valueOf(posTp.GetSign(),10).intValue();
			temp = TMStringHelper.GoTapePos2(r,iPos-1);
			if(erg.c==null){
				erg.c = new Tape(temp.GetSign());
				workTape = erg.c;							
			}else{
				workTape.SetNext(new Tape(temp.GetSign()));
				workTape = workTape.GetNext();
			}
			posTp = posTp.GetNext();
		}
		return erg;
	}
	
	protected int pow2(int k){
		int res = 1;
		for(int i=0;i<k;i++){
			res *= 2;
		}
		return res;
	}
	
	protected Tape use_list_convolve(int pl,int pr,Tape b){
		Tape res = func_bg(pl-2,2);
		Tape last = TMStringHelper.GoToEnd(res); 
		last.SetNext(TMStringHelper.CopyTape(b));
		last = TMStringHelper.GoToEnd(last);
		last.SetNext(func_bg(pr,2));
		return func_list_convolve(TMStringHelper.String2Tape("1,2,4"),res); 
	}
	
	protected Tape use_position(int n,Tape r){
		Tape workTape = null;
		Tape tempTape = null;
		int len = TMStringHelper.TapeLen(r);
		Tape bgr = func_bg(n,len);
		// calculate T n
		while(bgr!=null){
			int Sum =	Integer.valueOf(r.GetSign(),10).intValue() -
						Integer.valueOf(bgr.GetSign(),10).intValue();
			if(Sum<0){
				Sum *= -1;
			}
			if(tempTape==null){
				tempTape = new Tape(String.valueOf(Sum));
				workTape = tempTape;
			}else{
				tempTape.SetNext(new Tape(String.valueOf(Sum)));
				tempTape = tempTape.GetNext();
			}
			r = r.GetNext();
			bgr = bgr.GetNext(); 
		}
		return func_position("1",workTape);		
	}
	
	protected Tape func_position(String a,Tape Liste){
		Tape workTape = null;
		Tape tempTape = null;
		int cnt = 1;
		while(Liste!=null){
			if(a.equals(Liste.GetSign())){
				if(workTape==null){
					workTape = new Tape(String.valueOf(cnt));
					tempTape = workTape;
				}else{
					tempTape.SetNext(new Tape(String.valueOf(cnt)));
					tempTape = tempTape.GetNext();
				}
			}
			cnt++;
			Liste = Liste.GetNext();
		}
		return workTape;
	}
	
	protected Tape func_list_convolve(Tape Kernel,Tape Liste){
		Tape Result = null;
		Tape workResult = null;
		int KernelLen = TMStringHelper.TapeLen(Kernel);
		int ListLen = TMStringHelper.TapeLen(Liste);
		Kernel = TMStringHelper.GoToEnd(Kernel);
		for(int i=0;i<=(ListLen-KernelLen);i++){
			Tape tempKernel = Kernel;
			Tape tempListe = Liste;
			int Sum = 0;
			while(tempKernel!=null){
				Sum += 	Integer.valueOf(tempListe.GetSign(),10).intValue() *
						Integer.valueOf(tempKernel.GetSign(),10).intValue();
				tempKernel = tempKernel.GetLast();
				tempListe = tempListe.GetNext();
			}
			if(Result==null){
				workResult = new Tape(String.valueOf(Sum));
				Result = workResult;				
			}else{
				workResult.SetNext(new Tape(String.valueOf(Sum)));
				workResult = workResult.GetNext();
			}
			Liste = Liste.GetNext();
		}
		return Result;
	}
	
	protected Tape[] func_parts(int PartLen, Tape workTape){
		Tape[] TapeList = null;
		if(PartLen>0){
			int TapeLen = TMStringHelper.TapeLen(workTape);
			int parts = TapeLen / PartLen;
			TapeList = new Tape[parts];
			for(int i=0;i<parts;i++){
				TapeList[i] = null;
				Tape tempTape = null;
				for(int j=0;j<PartLen;j++){
					if(TapeList[i]==null){
						tempTape = new Tape(workTape.GetSign());
						TapeList[i] = tempTape;				
					}else{
						tempTape.SetNext(new Tape(workTape.GetSign()));
						tempTape = tempTape.GetNext();
					}
					workTape = workTape.GetNext();
				}
			}
		}
		return TapeList;
	}
	
//	generate Background information 
	protected Tape func_bg(int gen,int len){
		int mod_rot = 14;
		Tape StartTape = null;
		Tape workTape = null;
//		Tape bgi = integerDigits(9976,2,0);
		Tape bgi = integerDigits(4988,2,mod_rot);
		Tape Bgi = Rotate(bgi,gen);
		Tape LastBgi = TMStringHelper.GoToEnd(Bgi);
		LastBgi.SetNext(Bgi);
		for(int i=0;i<len;i++){
			if(StartTape!=null){
				workTape.SetNext(new Tape(Bgi.GetSign()));
				workTape = workTape.GetNext();
			}else{
				workTape = new Tape(Bgi.GetSign());
				StartTape = workTape;
			}
			Bgi = Bgi.GetNext();
		}
		return StartTape; 
	}
	
	protected boolean CheckProdLen(CTSProduction CTSProd){
		CTSProduction workProd = CTSProd;
		while(workProd!=null){
			if(workProd.getM_Produce()!=null){
				int ProdLen = TMStringHelper.TapeLen(workProd.getM_Produce());
				if((ProdLen%6)!=0){
					return false;
				}
			}
			workProd = (CTSProduction)workProd.getM_Next();
		}
		return true;
	}

	protected void GenerateMissingProds(CTSProduction CTSProd){
		int ProdNum = 0;
		CTSProduction.Reset();
		CTSProduction workProd = CTSProd;
		CTSProduction LastCTSProd = null;
		if(workProd!=null){
			while(workProd!=null){
				ProdNum++;
				LastCTSProd = workProd;
				workProd = (CTSProduction)workProd.getM_Next();
			}
			int AddNum = (ProdNum/2)%6;
			if(LastCTSProd!=null){
				for(int i=0;i<ProdNum+2*AddNum;i++){
					CTSProduction tempCTSProd = new CTSProduction(null);
					tempCTSProd.setM_Next(new CTSProduction(null));
					LastCTSProd.setM_Next(tempCTSProd);
					LastCTSProd = (CTSProduction)tempCTSProd.getM_Next(); 											
				}
			}
		}
	}
	
	protected void DeleteVoidProds(CTSProduction CTSProd){
		CTSProduction temp = CTSProd;
		int count = 0;
		int LastPos = 0;
		while(temp!=null){
			CTSProduction temp2  = (CTSProduction)temp.getM_Next();
			if(temp.getM_Produce()!=null || temp2.getM_Produce()!=null){
				LastPos = count;				
			}
			if(temp2!=null){			
				temp = (CTSProduction)temp2.getM_Next();
			}else{
				temp2 = null;
			}
			count+=2;
		}
		count = 0;
		while(CTSProd!=null){
			if(count>LastPos){
				CTSProd.setM_Next(null);
				break;
			}
			CTSProd  = (CTSProduction)CTSProd.getM_Next();
			count++;			
		}
	}
	
	protected int CountProds(CTSProduction CTSProd)
	{
		int Num = 0;
		CTSProduction workProd = CTSProd;
		while(workProd!=null){
			Num++;
			workProd = (CTSProduction)workProd.getM_Next();
		}
		return Num;
	}
	
	protected Tape integerDigits(int Number,int base,int length)
	{
		Tape workTape = null;
		int count = 0;
		while(Number!=0){
			int a = Number%base;
			Number /= base;
			Tape tempTape = new Tape(String.valueOf(a));
			tempTape.SetNext(workTape);
			workTape = tempTape;
			count++;
		}
		while(count<length){
			Tape tempTape = new Tape("0");
			tempTape.SetNext(workTape);
			workTape = tempTape;		
			count++;
		}
		return workTape;
	}
	
	protected Tape Rotate(Tape firstTape,int cycles){
		int len = TMStringHelper.TapeLen(firstTape);
		if(cycles<0){
			int poslen = cycles * (-1);
			poslen %= len;
			cycles = len - poslen;
		}else{
			cycles %= len;
		}
		for(int i=0;i<cycles;i++){
			Tape lastTape = TMStringHelper.GoToEnd(firstTape);
			lastTape.SetNext(firstTape);
			Tape NextTape = firstTape.GetNext();
			firstTape.SetNext(null);
			firstTape = NextTape;
		}
		return firstTape;		
	}
	
	protected Struct_DS SixBefore(Struct_DS firstDS, Struct_DS actDS){
		Struct_DS beforeDS = null;
		int frst_len = StructDS_len(firstDS);
		int act_len = StructDS_len(actDS);
		Struct_DS lastDS = firstDS.LastStructDS();
		lastDS.Next = firstDS;
		beforeDS = actDS;
		int cycles = frst_len - 6;
		 	
		for(int i=0;i<cycles;i++){
			beforeDS = beforeDS.Next;
		}
		
		lastDS.Next = null;
		
		return beforeDS;		
	}
	
	protected int StructDS_len(Struct_DS DS){
		int len = 0;
		while(DS!=null){
			len++;
			DS = DS.Next;
		}
		return len;
	}
	
	protected void PrintAll(){
		StringBuffer outBuf = new StringBuffer();
		outBuf.append("\"");
		outBuf.append( TMStringHelper.Tape2String(m_Rule110Config) );
		
		TMState stPos = Start;
		String FirstState = stPos.GetStateF();
		String First = "\"\n\n(" + FirstState + "," + ")\n\n";
		outBuf.append(First);
		
		while(stPos!=null) {
			outBuf.append("("+stPos.GetStateF()+","+stPos.GetRead()+","+stPos.GetStateN()+","+stPos.GetWrite()+","+stPos.GetMove()+")\n");
			stPos = stPos.GetNext();
		}
		System.out.println(outBuf.toString());
	}
	
	public static void main(String[] args) {
		String Rule110TM = "../TMDefs/Rule110.tm";
		if(args.length==0){
			Tape DemoTape = TMStringHelper.String2Tape("1,0,1");
			CTSProduction.Reset();
			CTSProduction DemoProd = new CTSProduction(TMStringHelper.String2Tape("0,0,0,0,0,0"));
			DemoProd.setM_Next(new CTSProduction(null));
			DemoProd.getM_Next().setM_Next(new CTSProduction(TMStringHelper.String2Tape("1,1,1,1,1,1")));
			DemoProd.getM_Next().getM_Next().setM_Next(new CTSProduction(null));
			CTS2Rule110 Rule110Converter = new CTS2Rule110(Rule110TM,DemoTape,DemoProd);
			Rule110Converter.Convert();
		}
		if(args.length==1){
			TM2TS TSConverter = new TM2TS(args[0]);
			TSConverter.convert();
			TS2CTS CTSConverter = new TS2CTS(TSConverter.GetTSTape(),TSConverter.GetTSProduction());					
			CTSConverter.Convert(true);
			CTS2Rule110 Rule110Converter = new CTS2Rule110(Rule110TM,CTSConverter.get_CTSTape(),CTSConverter.get_CTSProd());
			Rule110Converter.Convert();
		}	
	}
}
