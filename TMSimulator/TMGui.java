//written by André Betz 
//http://www.andrebetz.de

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;


class TMGui extends Frame implements ActionListener, TextListener
{
	TuringMachine TM;
	TextArea textarea;
	String m_fname;
	int m_Count;
	int m_Steps;
	
	public TMGui() {
		super("Turing Machine (C) 2003 by mail@andrebetz.de");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		TM = null;
		m_Steps = 5;
		
		textarea = new TextArea("",24,80);
		textarea.setFont(new Font("MonoSpaced",Font.PLAIN,12));
		textarea.setEditable(false);
		this.add("Center",textarea);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
		this.add(p,"South");
		
		Font font = new Font("SansSerif",Font.BOLD,14);
		
		Button openf = new Button("Open");
		openf.addActionListener(this);
		openf.setActionCommand("Open");
		p.add(openf);
		
		Button reset = new Button("Reset");
		reset.addActionListener(this);
		reset.setActionCommand("Reset");
		p.add(reset);
				
		Button step = new Button("Step");
		step.addActionListener(this);
		step.setActionCommand("Step");
		p.add(step);

		TextField text2 = new TextField(Integer.toString(m_Steps));
		text2.setFont(new Font("MonoSpaced",Font.PLAIN,12));
		text2.addActionListener(this);
		text2.addTextListener(this);
		p.add(text2);
		
		this.pack();		
	}
	
	public void textValueChanged(TextEvent e) {
		TextField tf = (TextField)e.getSource();
		String text = tf.getText();
		if (!text.equals("")) {
			m_Steps = Integer.valueOf(text,10).intValue();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Open")) {
			m_Count = 0;
			FileDialog f = new FileDialog(this,"Open TuringMachine",FileDialog.LOAD);

			FilenameFilter fnf = new FilenameFilter() {
				public boolean accept(File f, String str) {
					return 	f.getName().endsWith(".tm") || 
							f.getName().endsWith(".TM");
				}
			};
			
			f.setFilenameFilter(fnf);
			f.show();
			m_fname = f.getDirectory() + f.getFile();
			TM = new TuringMachine(m_fname);
			textarea.setText("");			
		} else if (cmd.equals("Step")) {
			if(TM!=null) {
				if(m_Steps==0)
				{
					String out;
					while ((out = TM.Step())!=null) {
						textarea.append(m_Count + "\t" + out + "\n");
												
						m_Count++;
					}
					textarea.append("End\n");					
				} else {
					for (int i=0;i<m_Steps;i++) {
						String out = TM.Step();
						if(out!=null) {
							textarea.append(m_Count + "\t" + out + "\n");
							m_Count++;
						} else {
							textarea.append("End\n");
							break;					
						}
					}
				}
			}			
		} else if (cmd.equals("Reset")) {
			m_Count = 0;
			TM = new TuringMachine(m_fname);
			textarea.setText("");						
		}
	}
	
	public static void main(String[] args) {
		TMGui gui = new TMGui();
		gui.setVisible(true);
	}	
}