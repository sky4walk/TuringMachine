//written by André Betz 
//http://www.andrebetz.de

import BefehlElement;
import ParameterElement;

class MakroElement
{
	MakroElement m_Next;
	String m_MakroName;
	BefehlElement BefehlListe;
	ParameterElement m_ParameterListe;
	int m_CallCounter;
	int m_Offset;
	int m_Length;
	String StartLabel;
	String NextLabel;
	
	MakroElement() {
		m_Next = null;
		BefehlListe = null;
		m_ParameterListe = null;
		m_MakroName = null;
		m_CallCounter = 0;
		m_Offset = 0;
		m_Length = 0;
		StartLabel = "";
		NextLabel = "END"; 	
	}
	
	public String getM_MakroName() {
		return m_MakroName;
	}
	public void setM_MakroName(String MakroName) {
		m_MakroName = MakroName;
	}

	public MakroElement getM_Next() {
		return m_Next;
	}
	public void setM_Next(MakroElement element) {
		m_Next = element;
	}

	public BefehlElement getBefehlListe() {
		return BefehlListe;
	}
	public void setBefehlListe(BefehlElement element) {
		BefehlListe = element;
	}

	public ParameterElement getM_ParameterListe() {
		return m_ParameterListe;
	}
	public void setM_ParameterListe(ParameterElement element) {
		m_ParameterListe = element;
	}
	public int getM_CallCounter() {
		return m_CallCounter;
	}
	public void setM_CallCounter(int i) {
		m_CallCounter = i;
	}
	public int getM_Offset() {
		return m_Offset;
	}
	public void setM_Offset(int i) {
		m_Offset = i;
	}
	public int getM_Length() {
		return m_Length;
	}
	public void setM_Length(int i) {
		m_Length = i;
	}
	public String getStartLabel() {
		return StartLabel;
	}
	public void setStartLabel(String string) {
		StartLabel = string;
	}
	public String getNextLabel() {
		return NextLabel;
	}
	public void setNextLabel(String string) {
		NextLabel = string;
	}
}