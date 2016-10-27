// (C) by http://www.andrebetz.de 2004
// PrePorcess fügt alle an den mit #include "DateiName" stehenden Dateien zusammen
import java.io.*;

class PreProcess
{
	String m_DatName;
	String ParseStr = "#include";
	StringBuffer m_File;	
	
	PreProcess(String DatName){
		m_DatName = DatName;
		m_File = new StringBuffer();
	}
	
	void Start(){
		ScanDatei(m_DatName,m_File);	
	}
	
	String GetDatei(){
		return m_File.toString();
	}
	
	void ScanDatei(String DateiName, StringBuffer Datei){
		StringBuffer temp = ReadFile(DateiName);
		int Pos = 0;
		int LastPos = 0;
		int BeginMakro = 0;
		while(Pos<temp.length()){
			Pos = ScanFile(temp,Pos,ParseStr);
			if(Pos>=0){
				BeginMakro = Pos;		
				Pos += ParseStr.length();
				String DatNam = GetIncFileName(temp,Pos);
				Pos += DatNam.length();
				CopyIncToDatei(Datei,temp,LastPos,BeginMakro);
				LastPos = Pos;
				ScanDatei(DatNam,Datei);
			}else{
				Pos = temp.length();
				CopyIncToDatei(Datei,temp,LastPos,temp.length());					
			}
		}
	}

	void CopyIncToDatei(StringBuffer Dest,StringBuffer Src, int From, int To){
		if(To>From){
			char[] chars = new char[To-From];
			Src.getChars(From,To,chars,0);
			Dest.append(chars);
		}	
	}
	
	String GetIncFileName(StringBuffer Datei,int Pos){
		String IncFileNam = "";
		while(Datei.charAt(Pos)!='\"' && 
		      Datei.charAt(Pos)!='\n' && 
			  Datei.charAt(Pos)!='\r' && 
		      Pos<Datei.length()){
			Pos++;		
		}
		Pos++;
		while(Datei.charAt(Pos)!='\"' && 
			  Datei.charAt(Pos)!='\n' && 
			  Datei.charAt(Pos)!='\r' && 
			  Pos<Datei.length()){
			  	IncFileNam += Datei.charAt(Pos);
				Pos++;		
		}
		return IncFileNam;
	}
	
	int ScanFile(StringBuffer Datei, int StartPos, String Token ){
		String strDat = Datei.toString();
		for(int i=StartPos;i<(Datei.length()-Token.length());i++){
			if(strDat.regionMatches(i,ParseStr,0,ParseStr.length())){
				return i;		
			}
		}
		return -1;
	}
	
	StringBuffer ReadFile(String FileName){
		StringBuffer actFile = new StringBuffer();
		FileReader in;
		if(FileName==null){
			return null;
		}
		try {
			File f = new File(FileName);
			in = new FileReader(f);
			char[] buffer = new char[128];
			int len;
			while((len = in.read(buffer))!=-1) {
				actFile.append(new String(buffer,0,len));
			}
			in.close();
		}
		catch(IOException e) {
			return null;
		}

		return actFile;
	}
	
	public static void main(String[] args) {
		if(args.length!=1){
			System.exit(0);
		}

		PreProcess preprocessor = new PreProcess(args[0]);
		preprocessor.Start();
		System.out.println(preprocessor.GetDatei());
	}
}