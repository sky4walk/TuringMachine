//written by André Betz 
//http://www.andrebetz.de

import BefehlElement;
import MakroElement;

class ParameterElement {
	ParameterElement m_Next;
	ParameterElement m_Linked;
	MakroElement m_LinkedMakro;
	String m_ParameterName;
	String m_Label;
	int m_Offset;
	BefehlElement m_Position;
	
	ParameterElement() {
		m_Next = null;
		m_Linked = null;
		m_Position = null;
		m_ParameterName = null;
	}

	public String getM_ParameterName() {
		return m_ParameterName;
	}
	public void setM_Next(ParameterElement element) {
		m_Next = element;
	}

	public ParameterElement getM_Next() {
		return m_Next;
	}
	public void setM_ParameterName(String string) {
		m_ParameterName = string;
	}
	public String getM_Label() {
		return m_Label;
	}
	public void setM_Label(String string) {
		m_Label = string;
	}
	public int getM_Offset() {
		return m_Offset;
	}
	public void setM_Offset(int i) {
		m_Offset = i;
	}
	public ParameterElement getM_Linked() {
		return m_Linked;
	}
	public BefehlElement getM_Position() {
		return m_Position;
	}
	public void setM_Linked(ParameterElement element) {
		m_Linked = element;
	}
	public void setM_Position(BefehlElement element) {
		m_Position = element;
	}
	public MakroElement getM_LinkedCall() {
		return m_LinkedMakro;
	}
	public void setM_LinkedCall(MakroElement i) {
		m_LinkedMakro = i;
	}

}