//written by André Betz 
//http://www.andrebetz.de

import MakroElement;
import ParameterElement;

class BefehlElement {
	BefehlElement m_Next;
	MakroElement m_Connected;
	String m_BefehlName;
	ParameterElement m_ParameterListe;	
	String m_Label;
	boolean m_Zahl;
	int[] m_Zahlen;
	int m_Offset;
	
	BefehlElement() {
		m_Zahl = false;
		m_Next = null;
		m_ParameterListe = null;
		m_BefehlName = null;
		m_Zahlen = null;
		m_Connected = null;
	}
	
	public BefehlElement getM_Next() {
		return m_Next;
	}
	public void setM_Next(BefehlElement element) {
		m_Next = element;
	}

	public String getM_BefehlName() {
		return m_BefehlName;
	}
	public void setM_BefehlName(String string) {
		m_BefehlName = string;
	}

	public ParameterElement getM_ParameterListe() {
		return m_ParameterListe;
	}
	public void setM_ParameterListe(ParameterElement element) {
		m_ParameterListe = element;
	}
	public String getM_Label() {
		return m_Label;
	}
	public void setM_Label(String string) {
		m_Label = string;
	}
	public boolean isM_Zahl() {
		return m_Zahl;
	}
	public void setM_Zahl(boolean b) {
		m_Zahl = b;
	}
	public int[] getM_Zahlen() {
		return m_Zahlen;
	}
	public void setM_Zahlen(int laenge) {
		m_Zahlen = new int[laenge];
	}
	public MakroElement getM_Connected() {
		return m_Connected;
	}
	public void setM_Connected(MakroElement element) {
		m_Connected = element;
	}
	public int getM_Offset() {
		return m_Offset;
	}
	public void setM_Offset(int i) {
		m_Offset = i;
	}

}