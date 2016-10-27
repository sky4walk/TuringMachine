//written by André Betz 
//http://www.andrebetz.de

import ParameterElement;

class ProgrammListe
{
	int m_Speicherstelle;
	int Position;
	String m_Name;
	String m_Label;
	ParameterElement m_ParamUebergabe1;
	ParameterElement m_ParamUebergabe2;
	boolean m_Var;

	ProgrammListe(){
		m_Name = "";
		m_Label = "";
		m_ParamUebergabe1 = null;	
		m_ParamUebergabe2 = null;
		m_Var = false;
	}
	public String getM_Name() {
		return m_Name;
	}
	public int getM_Speicherstelle() {
		return m_Speicherstelle;
	}
	public void setM_Name(String string) {
		m_Name = string;
	}
	public void setM_Speicherstelle(int i) {
		m_Speicherstelle = i;
	}
	public String getM_Label() {
		return m_Label;
	}
	public void setM_Label(String string) {
		m_Label = string;
	}
	public int getPosition() {
		return Position;
	}
	public void setPosition(int i) {
		Position = i;
	}
	public ParameterElement getM_m_ParamUebergabe1() {
		return m_ParamUebergabe1;
	}
	public void setM_m_ParamUebergabe1(ParameterElement element) {
		m_ParamUebergabe1 = element;
	}
	public ParameterElement getM_m_ParamUebergabe2() {
		return m_ParamUebergabe2;
	}
	public void setM_m_ParamUebergabe2(ParameterElement element) {
		m_ParamUebergabe2 = element;
	}
	public boolean isM_Var() {
		return m_Var;
	}
	public void setM_Var(boolean b) {
		m_Var = b;
	}
};