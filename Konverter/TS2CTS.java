//written by André Betz 
//http://www.andrebetz.de

// converts a Tag System to a Cyclic Tag System described in 
// A New Kind of Science by Stephen Wolfram

import TM2TS;
import TSProduction;
import TMStringHelper;

class CTSProduction extends TSProduction
{
	int m_Cycle;
	static int  m_PosNumCnt = 0;
	
	static void Reset(){
		m_PosNumCnt = 0;
	}
	
	public CTSProduction(){
		super();
		m_Cycle = 0;
		CTSProduction.m_PosNumCnt++;
	}
	public CTSProduction(Tape Produce){
		super("",Produce);
		if(CTSProduction.m_PosNumCnt%2==0){
			m_Start = "0";			
		}else{
			m_Start = "1";			
		}
		m_Cycle = CTSProduction.m_PosNumCnt/2;
		CTSProduction.m_PosNumCnt++;
	}
	public int getM_Cycle() {
		return m_Cycle;
	}
	public void setM_Cycle(int i) {
		m_Cycle = i;
	}
}

class TS2CTS
{
	int m_ProdCount;
	boolean m_Rule110;
	Tape m_TSTape;
	Tape m_Transform;
	Tape m_CTSTape;
	TSProduction m_TSProd;
	CTSProduction m_CTSProd;
	String SymOne = "0";
	String SymTwo = "1";
		
	public TS2CTS(Tape TSTape,TSProduction TSProd){
		m_ProdCount = 0;
		m_CTSTape = null;
		m_TSTape = TSTape;
		m_TSProd = TSProd;
		m_CTSProd = null;
		m_Transform = null;
		m_Rule110 = false;	
	}
	
	public CTSProduction get_CTSProd(){
		return m_CTSProd;
	}
	
	public Tape get_CTSTape(){
		return m_CTSTape;	
	}
	
	public void Convert(boolean Rule110){
		int k = TSProdCount();		
		int AddSym = 0;
		m_Rule110 = Rule110;
		if(m_Rule110){
			AddSym = k%6; // missing symbols for Rule110
		}
		TSProduction SymTrans = SymbolTransform(k,AddSym);
		m_CTSTape = GenerateCTSTape(SymTrans,m_TSTape,0);
		m_CTSProd = GenerateCTSProds(SymTrans,m_TSProd,k,AddSym);
		System.out.println("("+TMStringHelper.Tape2String(m_CTSTape)+")");
		PrintCTSProd();
	}
	
	protected int TSProdCount(){
		int ProdNums = 0;
		TSProduction tempProd = m_TSProd;
		while(tempProd!=null){
			ProdNums++;
			tempProd = tempProd.getM_Next();
		}
		return ProdNums;
	}
	protected TSProduction SymbolTransform(int SymNum,int AddNum){
		int ProdNums = 0;
		TSProduction tempProd = m_TSProd;
		TSProduction TransSym = null;
		TSProduction LastTrans = null; 
		while(tempProd!=null){
			Tape workTape = TMStringHelper.GenTape(SymNum+AddNum,SymOne);
			TMStringHelper.GoTapePos(workTape,ProdNums).SetSign(SymTwo);			
			TSProduction tempTrans = new TSProduction(tempProd.getM_Start(),workTape);
			if(TransSym==null){
				TransSym = tempTrans;
			}else{
				LastTrans.setM_Next(tempTrans);
			}
			LastTrans = tempTrans;			 
			ProdNums++;			
			tempProd = tempProd.getM_Next();
		}
		return TransSym;
	}
	
	protected TSProduction FindTransProd(TSProduction Transform,Tape workTSTape){
		TSProduction tempProd = Transform;
		while(tempProd!=null){
			if(workTSTape.GetSign().equals(tempProd.getM_Start())){
				return tempProd;
			}
			tempProd = tempProd.getM_Next();
		}
		return null;
	}
	
	protected Tape GenerateCTSTape(TSProduction Transform, Tape TSTape,int ProdNum){
		Tape workTSTape = TSTape;
		Tape TransCTSTape = null;
		Tape LastCTSTape = null;
		if(workTSTape!=null&&workTSTape.GetSign().equals("")){
			return null;
		}
		while(workTSTape!=null){
			TSProduction TransProd = FindTransProd(Transform,workTSTape);
			Tape tempTapeTrans = TMStringHelper.CopyTape(TransProd.getM_Produce());			
			if(TransCTSTape==null){
				TransCTSTape = tempTapeTrans;
			}else{
				LastCTSTape.SetNext(tempTapeTrans);			
			}
			LastCTSTape = TMStringHelper.GoToEnd(tempTapeTrans);
			workTSTape = workTSTape.GetNext();
		}
		return TransCTSTape;
	}

	protected CTSProduction GenerateCTSProds(TSProduction SymTrans, TSProduction TSProd,int PNum,int AddNum){
		TSProduction workTSProd = TSProd;
		CTSProduction.Reset();
		CTSProduction CTSProd = null;
		CTSProduction LastCTSProd = null;
		int ProdNum = 0; 
		while(workTSProd!=null){
			Tape TransTapeProd = GenerateCTSTape(SymTrans,workTSProd.getM_Produce(),PNum+AddNum);
			CTSProduction tempCTSProd = new CTSProduction(null);
			tempCTSProd.setM_Next(new CTSProduction(TransTapeProd));
			if(CTSProd==null){
				CTSProd = tempCTSProd;
			}else{
				LastCTSProd.setM_Next(tempCTSProd);
			}
			LastCTSProd = (CTSProduction)tempCTSProd.getM_Next(); 
			ProdNum++;
			workTSProd = workTSProd.getM_Next();
		}
		for(int i=0;i<ProdNum+2*AddNum;i++){
			CTSProduction tempCTSProd = new CTSProduction(null);
			tempCTSProd.setM_Next(new CTSProduction(null));
			LastCTSProd.setM_Next(tempCTSProd);
			LastCTSProd = (CTSProduction)tempCTSProd.getM_Next(); 
		}
		return CTSProd;
	}
	
	protected void PrintCTSProd(){
		CTSProduction CTSProd = m_CTSProd;
		while(CTSProd!=null){
			System.out.println("("+CTSProd.getM_Cycle()+","+CTSProd.getM_Start()+"->"+TMStringHelper.Tape2String(CTSProd.getM_Produce())+")");
			CTSProd = (CTSProduction)CTSProd.getM_Next();
		}
	}
	
	public static void main(String[] args) {
		if(args.length==0) {
			// example of the ANKOS Book Page 669
			// initial Tape: aa
			// a -> cb
			// b -> a
			// c -> acbc
			Tape DemoTape = TMStringHelper.String2Tape("a,b,c");
			TSProduction DemoProd = new TSProduction("a",TMStringHelper.String2Tape("c,b"));
			DemoProd.setM_Next(new TSProduction("b",TMStringHelper.String2Tape("")));
			DemoProd.getM_Next().setM_Next(new TSProduction("c",TMStringHelper.String2Tape("a,c,b,c")));
			TS2CTS CTSConverter = new TS2CTS(DemoTape,DemoProd);
			CTSConverter.Convert(true);					
		}
		if(args.length==1) {		
			TM2TS TSConverter = new TM2TS(args[0]);
			TSConverter.convert();
			TS2CTS CTSConverter = new TS2CTS(TSConverter.GetTSTape(),TSConverter.GetTSProduction());					
			CTSConverter.Convert(true);					
		}
	}
}