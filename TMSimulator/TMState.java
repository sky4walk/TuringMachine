//written by André Betz 
//http://www.andrebetz.de

public class TMState {
	TMState m_Next;
	String  m_StateN;
	String  m_Read;
	String  m_StateF;
	String  m_Write;
	String  m_Move;
	
	public TMState(String StateF,String Read, String StateN, String Write, String Move) {
		m_StateF = StateF;
		m_Read   = Read;
		m_StateN = StateN;
		m_Write  = Write;
		m_Move   = Move;
		m_Next   = null;
	}
	public TMState GetNext() {
		return m_Next;
	}
	
	public void SetNext(TMState next) {
		m_Next = next;
	}
	
	public String GetStateN() {
		return m_StateN;
	}
	
	public String GetRead() {
		return m_Read;
	}
	
	public String GetStateF(){
		return m_StateF;
	}
	
	public String GetWrite() {
		return m_Write;
	}
	
	public String GetMove() {
		return m_Move;
	}
	
}