//written by André Betz 
//http://www.andrebetz.de

class TSProduction
{
	String m_Start;
	Tape m_Produce;
	TSProduction m_Next;
	
	public TSProduction(){
		m_Start = null;
		m_Produce = null;
		m_Next = null;
	}
	
	public TSProduction(String Start,Tape Produce){
		m_Start = Start;
		m_Produce = Produce;
		m_Next = null;
	}
	
	public TSProduction getM_Next() {
		return m_Next;
	}
	public void setM_Next(TSProduction production) {
		m_Next = production;
	}
	public Tape getM_Produce() {
		return m_Produce;
	}
	public String getM_Start() {
		return m_Start;
	}
	public void setM_Produce(Tape tape) {
		m_Produce = tape;
	}
	public void setM_Start(String string) {
		m_Start = string;
	}
}
