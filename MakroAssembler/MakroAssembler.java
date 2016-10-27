//written by André Betz 
//http://www.andrebetz.de

import java.io.*;
import ScannerErg;
import BefehlElement;
import MakroElement;
import ParameterElement;
import ProgrammListe;


class MakroAssembler extends Converter
{
	MakroElement m_MakroListe;
	ProgrammListe[] m_Memory;
	int m_Output;
	int tiefe;
	String EndString = "END";
	String Command_BNZ = "BNZ";
	String Command_INC = "INC";
	
	MakroAssembler(String CompDat,String LinkDat) {
		super(LinkDat);
		Start(CompDat);
	}

	protected void Start(String CompDat){
		tiefe = 0;
		m_MakroListe = null;
		m_Memory = null;
		if(LoadProgram(CompDat)){
			ExpandMacros();
//only for Debug			
			PrintMemory();
//			printTMBand();
		}		
	}
	
	protected boolean LoadProgram(String FileName) {
		StringBuffer readinput = new StringBuffer();
	  	try {
			File f = new File(FileName);
		  	FileReader in = new FileReader(f);
		  	char[] buffer = new char[128];
		  	int len;
		  	while((len = in.read(buffer))!=-1) {
			  readinput.append(new String(buffer,0,len));
		  	}
	  	}
	  	catch(IOException e) {
	  	}
	
	  	return ParseASM(readinput);  	
	}

// Parser BNF
//	MACRO_DEF := 'MACRO',':',NAME,'(',[VAR],')','\n',BEFEHL|ZAHL,'END';
//	BEFEHL := BEFEHL,'\n',BEFEHL,'\n';
//	BEFEHL := [LABEL,':'],NAME,'(',[VAR],')';
//	VAR := VAR,',',VAR;
//	VAR := [LABEL:],NAME;
//	ZAHL := LABEL,':',ZIFFER,[',',Ziffer];
//  LABEL := NAME,!'END';
//  NAME := ALPHA,[ZIFFER];
//  ZIFFER := ZIFFER,ZIFFER;
//  ZIFFER := '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9';
//  ALPHA := ALPHA,ALPHA
//  ALPHA := 'A-Z'|'a-z';

	protected int DoesContain(char Sign, char[] SignList){
		int count = 0;
		while(count<SignList.length){
			if(Sign == SignList[count]){
				return count;	
			}
			count++;
		}
		return -1;
	}
	
	protected boolean IsNumerical(char Sign){
		char a[] = {'0','1','2','3','4','5','6','7','8','9'};
		if(DoesContain(Sign,a)>=0){
			return true;
		}
		return false;	
	}
	
	protected int DelNoSigns(String Input, int spos, char[] NoSigns) {
		int newpos = spos;
		int slen = Input.length();
		if(newpos<slen) {
			char sign = Input.charAt(newpos);
			while((DoesContain(sign,NoSigns)>=0)&&(newpos<slen)) {
				newpos++;
				if(newpos<slen) {
					sign = Input.charAt(newpos);
				}
			}
		}
		return newpos;
	}

	protected ScannerErg GetWord(String Data, int spos, char[] Endsigns,char[] DelSigns){
		char sign;
		ScannerErg scnErg = new ScannerErg();
		scnErg.setTermSignNr(-1);
		int endpos = Data.length();
		String Word = "";
		while(spos<endpos){
			spos = DelNoSigns(Data.toString(),spos,DelSigns);
			sign = Data.charAt(spos);
			int nr = DoesContain(sign,Endsigns);
			if(nr>=0){
				scnErg.setTermSignNr(nr);
				spos++;
				break;
			}
			if(sign!=' '){
				Word += sign; 		
			}
			spos++;
		}
		scnErg.setSpos(spos);
		scnErg.setWord(Word.toUpperCase());
		return scnErg;
	}

	protected boolean ParseASM(StringBuffer ASMCode){
		MakroElement meActual = m_MakroListe;
		ParameterElement paramActual = null;
		BefehlElement commActual = null;
		int automstate = 0;
		int LabelCounter = 0;
		int len = ASMCode.length();
		int spos = 0;
		char sign;
		int failureState = 100;
		boolean failure = false;
		char DelSigns[] = {' ','\n','\r','\t'};
  	
		while(spos<len) {
			spos = super.DelNoSigns(ASMCode.toString(),spos);
			if(spos<len) {
				sign = ASMCode.charAt(spos);
				switch(automstate){
					case 0:
					{
						if (sign=='#'){
							spos = super.DelComment(ASMCode.toString(),spos);
						}else {
							automstate = 1;
							break;
						}
					}
					break;
					case 1:
					{
						char EndSigns[] = {':'};						
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSigns);
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getWord().equals("MACRO")||scnErg.getWord().equals("MAKRO")){
								automstate = 2;
							} else {
								automstate = failureState;
							}
						} else {
							automstate = failureState;
						}
						spos = scnErg.getSpos();
					}
					break;		
					case 2:
					{
						char EndSigns[] = {'(','\n'};
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							MakroElement me = new MakroElement();
							me.setM_MakroName(scnErg.getWord());
							if(meActual==null){
								m_MakroListe = me;
							} else {
								meActual.setM_Next(me);
							}
							meActual = me;
							if(scnErg.getTermSignNr()==0){
								automstate = 3; // Makro mit Variablen
								paramActual = null;
							}
							if(scnErg.getTermSignNr()==1){ 
								automstate = 4; 
							}							
						} else {
							automstate = failureState; 
						}
					}						
					break;
					case 3:
					{
						char EndSigns[] = {',',')'};
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getWord().length()!=0) {
								ParameterElement param = new ParameterElement();
								param.setM_ParameterName(meActual.getM_MakroName()+"_"+scnErg.getWord());
								if(paramActual==null){
									meActual.setM_ParameterListe(param);	
								} else {
									paramActual.setM_Next(param);
								}
								paramActual = param;
							}
							if(scnErg.getTermSignNr()==0){
								automstate = 3; // noch eine Variable
							}
							if(scnErg.getTermSignNr()==1){ 
								automstate = 4;
								while(spos<ASMCode.length()){
									if(ASMCode.charAt(spos)=='\n'){
										spos++;
										break;
									}
									spos++;
								}
								paramActual = null; 
							}							
						} else {
							automstate = failureState; 
						}
					}
					break;
					case 4:
					{
						if (sign=='#'){
							spos = super.DelComment(ASMCode.toString(),spos);
						}else {
							automstate = 5;
							break;
						}
					}
					break;				
					case 5:
					{
						char EndSigns[] =  {'(',':','[','\n'};
						char DelSignsNoRet[] = {' ','\r','\t'};
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSignsNoRet);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0||spos==len){
							if(scnErg.getTermSignNr()==0){
								BefehlElement commTemp = new BefehlElement();
								commTemp.setM_BefehlName(scnErg.getWord());
								commTemp.setM_Label(meActual.getM_MakroName()+"_L"+LabelCounter);
								LabelCounter++;
								if(meActual.getBefehlListe()==null){
									meActual.setBefehlListe(commTemp); 
								} else {
									commActual.setM_Next(commTemp);									
								}
								commActual = commTemp;
								automstate = 6;
							}
							if(scnErg.getTermSignNr()==1){ 
								automstate = 7;
								BefehlElement commTemp = new BefehlElement();
								if(scnErg.getWord().equals(EndString)){
									automstate = failureState;
								}
								if(scnErg.getWord().length()==0){
									commTemp.setM_Label(meActual.getM_MakroName()+"_L"+LabelCounter);
									LabelCounter++;
								}else{
									commTemp.setM_Label(meActual.getM_MakroName()+"_"+scnErg.getWord());
								}
								if(meActual.getBefehlListe()==null){
									meActual.setBefehlListe(commTemp); 
								} else {
									commActual.setM_Next(commTemp);									
								}
								commActual = commTemp;
							}
							if(scnErg.getTermSignNr()==2){
							//	Zahlenarray 
								automstate = 9;
								BefehlElement commTemp = new BefehlElement();
								if(scnErg.getWord().length()==0){
									automstate = failureState;
								}
								if(scnErg.getWord().equals(EndString)){
									automstate = failureState;
								}
								commTemp.setM_Label(meActual.getM_MakroName()+"_"+scnErg.getWord());
								commTemp.setM_Zahl(true);
								if(meActual.getBefehlListe()==null){
									meActual.setBefehlListe(commTemp); 
								} else {
									commActual.setM_Next(commTemp);									
								}
								commActual = commTemp;
								String ArrayLaenge = "";
								while(spos<ASMCode.length()){
									if(ASMCode.charAt(spos)==']'){
										spos++;
										break;
									}else{
										ArrayLaenge += ASMCode.charAt(spos);
									}
									spos++;
								}
								commActual.setM_Zahlen(Integer.valueOf(ArrayLaenge,10).intValue());
								while(spos<ASMCode.length()){
									if(ASMCode.charAt(spos)==':'){
										spos++;
										break;
									}
									spos++;
								}
							}
							if(scnErg.getTermSignNr()==3||spos==len){
								if(scnErg.getWord().equals(EndString)){
									automstate = 0;
								} else {
									paramActual = null;
									commActual = null;
									automstate = failureState;
								} 							
							} 
						}
					}
					break;				
					case 6:
					{
						char EndSigns[] = {',',')',':'};
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getWord().length()!=0) {
								ParameterElement param = new ParameterElement();
								if(paramActual==null){
									commActual.setM_ParameterListe(param);	
								} else {
									paramActual.setM_Next(param);
								}
								paramActual = param;
							}
							if(scnErg.getTermSignNr()==0){
								if(scnErg.getWord().length()!=0) {
									paramActual.setM_ParameterName(meActual.getM_MakroName()+"_"+scnErg.getWord());
									paramActual.setM_Label(meActual.getM_MakroName()+"_L"+LabelCounter);
									LabelCounter++;
								}
								automstate = 6; // noch eine Variable
							}
							if(scnErg.getTermSignNr()==1){ 
								if(scnErg.getWord().length()!=0) {
									paramActual.setM_Label(meActual.getM_MakroName()+"_L"+LabelCounter);
									LabelCounter++;
									if(scnErg.getWord().equals(EndString)){
										paramActual.setM_ParameterName(scnErg.getWord());
									}else{
										paramActual.setM_ParameterName(meActual.getM_MakroName()+"_"+scnErg.getWord());
									}
								}
								automstate = 4;
								while(spos<ASMCode.length()){
									if(ASMCode.charAt(spos)=='\n'){
										spos++;
										break;
									}
									spos++;
								}
								paramActual = null; 
							}							
							if(scnErg.getTermSignNr()==2){
								if(scnErg.getWord().length()==0) {
									paramActual.setM_Label(meActual.getM_MakroName()+"_L"+LabelCounter);
									LabelCounter++;
								}else{
									paramActual.setM_Label(meActual.getM_MakroName()+"_"+scnErg.getWord());
								}
								automstate = 8; // Label an der Funktionsvariablen
							}
						} else {
							automstate = failureState; 
						}
					}
					break;
					case 7:
					{
						char EndSigns[] = {'(','\n'};
						char DelSignsNoRet[] = {' ','\r','\t'};
						int oldpos = spos;
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSignsNoRet);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getTermSignNr()==0){
								// Befehl mit Label
								commActual.setM_BefehlName(scnErg.getWord());								
								paramActual = null;
								automstate = 6;
							}
							if(scnErg.getTermSignNr()==1){
								//eine Zahl
								commActual.setM_Zahl(true);
								commActual.setM_Zahlen(1);
								commActual.getM_Zahlen()[0] = Integer.valueOf(scnErg.getWord(),10).intValue();
								paramActual = null;
								automstate = 4;								
							}
						}
					}
					break;
					case 8:
					{
						char EndSigns[] = {',',')'};
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getWord().length()!=0) {
								paramActual.setM_ParameterName(meActual.getM_MakroName()+"_"+scnErg.getWord());
							}
							if(scnErg.getTermSignNr()==0){
								automstate = 6; // noch eine Variable
							}
							if(scnErg.getTermSignNr()==1){ 
								automstate = 4;
								while(spos<ASMCode.length()){
									if(ASMCode.charAt(spos)=='\n'){
										spos++;
										break;
									}
									spos++;
								}
								paramActual = null; 
							}
						}							
					}
					break;
					case 9:
					{
						char EndSigns[] =  {'\n'};
						char DelSignsNoRet[] = {' ','\r','\t'};
						ScannerErg scnErg = GetWord(ASMCode.toString(),spos,EndSigns,DelSignsNoRet);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getTermSignNr()==0){
								String Zahlen = scnErg.getWord()+'\n';
								char EndSigns2[] =  {'\n',','};
								int ZahlPos = 0;
								int ZahlNum = 0;
								while(ZahlPos<Zahlen.length()&&ZahlNum<commActual.getM_Zahlen().length){
									ScannerErg scnErg2 = GetWord(Zahlen,ZahlPos,EndSigns2,DelSignsNoRet);
									ZahlPos = scnErg2.getSpos();
									commActual.getM_Zahlen()[ZahlNum] = Integer.valueOf(scnErg2.getWord(),10).intValue();
									ZahlNum++;
									if(scnErg2.getTermSignNr()>=0){
										if(scnErg2.getTermSignNr()==0){
											break;
										}
									}else{
										automstate = failureState;
										break;														
									}
								}
							}
						}
						automstate = 4;
					}
					break;
					default:
						return false;
				}
			}
		}		
		return true;	 
	}

	protected boolean ExpandMacros(){
		MakroElement WorkMakros = m_MakroListe;
		int Position = 0;
		while(WorkMakros!=null){
			BefehlElement curComm = WorkMakros.getBefehlListe();
			while(curComm!=null){
				MakroElement actExpand = m_MakroListe;
				if(curComm.isM_Zahl()){
				}else{
					while(actExpand!=null){
						if(curComm.getM_BefehlName().equalsIgnoreCase(actExpand.getM_MakroName())){
							curComm.setM_Connected(actExpand);
							break;						
						}
						actExpand = actExpand.getM_Next();
					}
				}
				curComm = curComm.getM_Next();			
			}
			WorkMakros = WorkMakros.getM_Next();
		}

		int MemNeeded = CalculateOffsets(GetLastMakro());
		m_Memory = new ProgrammListe[MemNeeded];
		for(int i = 0;i<MemNeeded;i++){
			m_Memory[i] = new ProgrammListe();
		}
		CreateMemory(GetLastMakro(),0);		
		return true;		
	}
	
	protected void PrintMemory(){
		for(int i=0;i<m_Memory.length;i++){
			System.out.println(m_Memory[i].getM_Label()+":\t"+m_Memory[i].getM_Name());
		}
	}
	
	protected MakroElement GetLastMakro(){
		MakroElement LastMakro = m_MakroListe;
		if(LastMakro!=null){
			while(LastMakro.getM_Next()!=null){
				LastMakro = LastMakro.getM_Next();
			}
		}
		return LastMakro;	
	}
		
	protected int CalculateOffsets(MakroElement LastOne){
		int length = 0;
		int Position = 0;
		if(LastOne!=null){
			BefehlElement curComm = LastOne.getBefehlListe();			
			while(curComm!=null){
				MakroElement meEl = curComm.getM_Connected();
				if(meEl!=null){
					if(meEl.getM_Length()==0){
						tiefe++;
						length = CalculateOffsets(meEl);
						tiefe--;
					}else{
						length = meEl.getM_Length();			
					}
				}else{
					if(curComm.isM_Zahl()){
						length = curComm.getM_Zahlen().length;
					} else {
						if(curComm.getM_BefehlName().equals(Command_INC)){
							length = 2;
						} else if(curComm.getM_BefehlName().equals(Command_BNZ)){
							length = 3;
						}
					}
				}
				Position += length;
				curComm = curComm.getM_Next();			
			}
		}
		LastOne.setM_Length(Position);
		return Position;		
	}

	protected void copyZahl2Mem(BefehlElement curComm,int Position,int Call){
		for(int i=0;i<curComm.getM_Zahlen().length;i++){
			if(i==0){
				m_Memory[Position+i].setM_Label(curComm.getM_Label()+Call);
			}
			m_Memory[Position+i].setPosition(Position+i);
			m_Memory[Position+i].setM_Speicherstelle(curComm.getM_Zahlen()[i]);
			m_Memory[Position+i].setM_Var(true);
		}
	}

	protected void copyComm2Mem(BefehlElement curComm,int Position,int Call){
		if(curComm.getM_Label()!=""){
			m_Memory[Position].setM_Label(curComm.getM_Label()+Call);
		}
		m_Memory[Position].setPosition(Position);
		m_Memory[Position].setM_Name(curComm.getM_BefehlName());
		
		ParameterElement tempParam = curComm.getM_ParameterListe();
		if(tempParam.getM_Label()!=""){
			m_Memory[Position+1].setM_Label(tempParam.getM_Label()+Call);
		}
		m_Memory[Position+1].setPosition(Position);
		m_Memory[Position+1].setM_Name(tempParam.getM_ParameterName()+Call);
		
		tempParam = tempParam.getM_Next();
		if(tempParam!=null){
			if(tempParam.getM_Label()!=""){
				m_Memory[Position+2].setM_Label(tempParam.getM_Label()+Call);
			}
			m_Memory[Position+2].setPosition(Position);
			m_Memory[Position+2].setM_Name(tempParam.getM_ParameterName()+Call);
		}								
	}

	protected ParameterElement isParamInMakroParams(MakroElement mEl, String LabelName){
		ParameterElement tempParam = mEl.getM_ParameterListe();
		while(tempParam!=null){
			if(tempParam.getM_ParameterName().equals(LabelName)){
				return tempParam;
			}
			tempParam = tempParam.getM_Next(); 
		}
		return null;
	}
	
	protected void CreateParameterConnection(MakroElement LastOne,BefehlElement curComm){
		MakroElement meEl = curComm.getM_Connected();
		ParameterElement workParam1 = meEl.getM_ParameterListe();
		ParameterElement workParam2 = curComm.getM_ParameterListe();
		while(workParam1!=null&&workParam2!=null){
			workParam1.setM_Linked(workParam2);
			workParam2.setM_Linked(isParamInMakroParams(LastOne,workParam2.getM_ParameterName()));
			workParam2.setM_LinkedCall(LastOne);
			workParam1 = workParam1.getM_Next();
			workParam2 = workParam2.getM_Next();
		}
	}
	
	protected String getEndLabel(BefehlElement curComm,MakroElement LastOne){
		BefehlElement nextComm = curComm.getM_Next();
		if(nextComm==null){
			if(LastOne.getNextLabel().equals(EndString)){
				return EndString;
			}else{
				return LastOne.getNextLabel();
			} 			
		}else{
			return nextComm.getM_Label()+LastOne.getM_CallCounter();
		}
	}
	
	protected String getStartLabel(BefehlElement curComm,String FirstLabel,int Call,int pos){
		if(pos==0&&FirstLabel.length()!=0){
			return FirstLabel;
		}else{
			return curComm.getM_Label()+Call;
		}
	}
	
	protected void SetLabels(MakroElement LastOne, BefehlElement curComm, String FirstLabel, String EndLabel, int Position, int CommCounter){
		ParameterElement workParam = curComm.getM_ParameterListe();
		if(CommCounter==0&&LastOne.getStartLabel().length()!=0){
			m_Memory[Position].setM_Label(LastOne.getStartLabel());																	
		}
		while(workParam!=null){
			Position++;
			if(FirstLabel.equals(workParam.getM_ParameterName()+LastOne.getM_CallCounter())){
				m_Memory[Position].setM_Name(LastOne.getStartLabel());																	
			}else if(workParam.getM_ParameterName().equals(EndString)){
				m_Memory[Position].setM_Name(EndLabel);									
			}else{						
				ParameterElement tempParam = isParamInMakroParams(LastOne,workParam.getM_ParameterName());
				if(tempParam!=null){
					while(tempParam.getM_Linked()!=null){
						tempParam = tempParam.getM_Linked();
					}
					if(tempParam.getM_ParameterName().equals(EndString)){
						m_Memory[Position].setM_Name(tempParam.getM_LinkedCall().getNextLabel());
					}else if(tempParam.getM_ParameterName().equals(tempParam.getM_LinkedCall().getBefehlListe().getM_Label())){
						m_Memory[Position].setM_Name(tempParam.getM_LinkedCall().getStartLabel());
					}else{
						m_Memory[Position].setM_Name(tempParam.getM_ParameterName()+tempParam.getM_LinkedCall().getM_CallCounter());
					}									
				}
			}
			workParam = workParam.getM_Next();
		}
	}
	
	protected void CreateMemory(MakroElement LastOne,int Position){
		String FirstLabel = "";		
		if(LastOne!=null){
			String EndLabel = LastOne.getNextLabel();
			BefehlElement curComm = LastOne.getBefehlListe();
			int CommCounter = 0;
			while(curComm!=null){
				MakroElement meEl = curComm.getM_Connected();
				if(CommCounter==0){
					FirstLabel = curComm.getM_Label()+LastOne.getM_CallCounter();
				}
				if(meEl!=null){
					CreateParameterConnection(LastOne,curComm);
					meEl.setNextLabel(getEndLabel(curComm,LastOne));
					meEl.setStartLabel(getStartLabel(curComm,LastOne.getStartLabel(),LastOne.getM_CallCounter(),CommCounter));
					tiefe++;
					CreateMemory(meEl,Position);
					tiefe--;
					Position+=meEl.getM_Length();
				}else{
					if(curComm.isM_Zahl()){
						copyZahl2Mem(curComm,Position,LastOne.getM_CallCounter());
						if(CommCounter==0&&LastOne.getStartLabel().length()!=0){
							m_Memory[Position].setM_Label(LastOne.getStartLabel());																	
						}
						Position += curComm.getM_Zahlen().length;
					} else {
						copyComm2Mem(curComm,Position,LastOne.getM_CallCounter());
						if(curComm.getM_BefehlName().equals(Command_INC)){
							SetLabels(LastOne,curComm,FirstLabel,EndLabel,Position,CommCounter);
							Position+=2;
						} else if(curComm.getM_BefehlName().equals(Command_BNZ)){
							SetLabels(LastOne,curComm,FirstLabel,EndLabel,Position,CommCounter);
							Position+=3;
						}						
					}
				}
				CommCounter++;
				curComm = curComm.getM_Next();			
			}
			LastOne.setM_CallCounter(LastOne.getM_CallCounter()+1);
		}
	}

	protected int resolveLabels(){
		int missed = 0;
		int highestValue = m_Memory.length+1;
		for(int i=0;i<m_Memory.length;i++){
			if(!m_Memory[i].isM_Var()){
				String tempVar;
				tempVar = m_Memory[i].getM_Name();
				if(tempVar.equals(Command_INC)){
					m_Memory[i].setM_Speicherstelle(0);
				}else if(tempVar.equals(Command_BNZ)){
					m_Memory[i].setM_Speicherstelle(1);
				}else if(tempVar.equals(EndString)){
					m_Memory[i].setM_Speicherstelle(m_Memory.length);
				}else{
					boolean canResolve = false;
					for(int j=0;j<m_Memory.length;j++){
						if(m_Memory[j].getM_Label().equals(tempVar)){
							if(highestValue<j){
								highestValue = j;		
							}
							m_Memory[i].setM_Speicherstelle(j);
							canResolve = true;
							break;
						}
					}
					if(!canResolve){
						missed++;
					}
				}
			}else{
				int j = m_Memory[i].getM_Speicherstelle();
				if(highestValue<j){
					highestValue = j;		
				}			
			}
		}
		return highestValue;
	}

	protected int Int2BitLen(int Zahl){
		int BitLen = 0;
		while(Zahl!=0){
			Zahl /= 2;
			BitLen++;
		}
		return BitLen;
	}
	
	protected StringBuffer convertInt2Bits(int Zahl,int maxLen){
		StringBuffer bits = new StringBuffer(maxLen);
		bits.setLength(maxLen);
		for(int i=0;i<maxLen;i++){
			bits.setCharAt(i,'0');
		}
		int Bit = 0;
		while(Zahl!=0){
			maxLen--;
			Bit = Zahl % 2;
			if(Bit==0){
				bits.setCharAt(maxLen,'0');
			}else{
				bits.setCharAt(maxLen,'1');
			}
			Zahl /= 2;
		}
		return bits;
	}
	
	protected void printTMBand(){
		StringBuffer outBuf = new StringBuffer();
		int highestInt = resolveLabels();
		int BitLen = Int2BitLen(highestInt);
		TMState stPos = Start;
		outBuf.append("(" + Start.GetStateF() + ",0)\n\n");
		outBuf.append("(*");
		outBuf.append(convertInt2Bits(0,BitLen));
		outBuf.append("*");
		outBuf.append(convertInt2Bits(0,BitLen));
		outBuf.append("*");
		for(int i=0;i<m_Memory.length;i++){
			outBuf.append(convertInt2Bits(i,BitLen));
			outBuf.append("*");	
			outBuf.append(convertInt2Bits(m_Memory[i].getM_Speicherstelle(),BitLen));			
			outBuf.append("*");			
		}
		outBuf.append(")\n\n");	
		
		
		while(stPos!=null) {
			outBuf.append("("+stPos.GetStateF()+","+stPos.GetRead()+","+stPos.GetStateN()+","+stPos.GetWrite()+","+stPos.GetMove()+")\t");
			stPos = stPos.GetNext();
			outBuf.append("("+stPos.GetStateF()+","+stPos.GetRead()+","+stPos.GetStateN()+","+stPos.GetWrite()+","+stPos.GetMove()+")\t");
			stPos = stPos.GetNext();
			outBuf.append("("+stPos.GetStateF()+","+stPos.GetRead()+","+stPos.GetStateN()+","+stPos.GetWrite()+","+stPos.GetMove()+")\t");
			stPos = stPos.GetNext();
			outBuf.append("("+stPos.GetStateF()+","+stPos.GetRead()+","+stPos.GetStateN()+","+stPos.GetWrite()+","+stPos.GetMove()+")\n");
			stPos = stPos.GetNext();
		}
		System.out.println(outBuf.toString());
	}
	
	public static void main(String[] args) {
		if(args.length==1) {
			MakroAssembler assemble = new MakroAssembler(args[0],"../TMDefs/RAMonTM(2Bit).tm");
//			MakroAssembler assemble = new MakroAssembler(args[0],"");
		}
	}
}