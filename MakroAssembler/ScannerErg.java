//written by André Betz 
//http://www.andrebetz.de

class ScannerErg{
	String Word;
	int spos;
	int TermSignNr;
	public int getSpos() {
		return spos;
	}
	public int getTermSignNr() {
		return TermSignNr;
	}
	public String getWord() {
		return Word;
	}
	public void setSpos(int i) {
		spos = i;
	}
	public void setTermSignNr(int i) {
		TermSignNr = i;
	}
	public void setWord(String string) {
		Word = string;
	}
}