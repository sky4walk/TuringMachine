//written by André Betz 
//http://www.andrebetz.de

public class Tape {
  String m_Sign;
  Tape m_Next;
  Tape m_Last;
  int m_PosNum;
  static int  m_PosNumCnt = 0;

  public Tape(int Pos) {
    m_Next = null;
    m_Last = null;
    m_PosNum = Pos;
  }

  public int GetPosNum() {
  	return m_PosNum;
  }

  public Tape(String Sign) {
    SetSign(Sign);
    m_Next = null;
    m_Last = null;
    m_PosNum = m_PosNumCnt;
    m_PosNumCnt++;
  }

  public void SetNext(Tape next) {
    m_Next = next;
    if(next!=null){
		m_Next.SetLast(this);
    }
  }

  public void SetLast(Tape last) {
    m_Last = last;
  }

  public Tape GetNext() {
    return m_Next;
  }

  public Tape GetLast() {
    return m_Last;
  }

  public void SetSign(String sign) {
    m_Sign = sign;
  }

  public String GetSign() {
    return m_Sign;
  }
  
  public void ResetPos(){
	m_PosNumCnt = 1;
	m_PosNum = 0;
  }
}