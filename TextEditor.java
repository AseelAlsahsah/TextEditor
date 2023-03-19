import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TextEditor extends JFrame implements ActionListener, ItemListener
{
	JPanel npanel,epanel,spanel;
	JPanel AdditionalP;
	JMenuBar bar;
    JMenu File, Format,Help,Add;
    JMenuItem New,Open,Save,Exit;
    
    JCheckBoxMenuItem wrapping,darkTheme;
    int Wflag = 0,Tflag=0;
    
    JComboBox<String> font, fontColor,fontStyle,fontSize;
	String fontType[]={"Font","MONOSPACED", "SERIF", "ARIAL"};
	String colors[]={"Font Color","Green", "Blue", "Red", "Yellow","Magenta"};
	Color myColors[]={Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW,Color.MAGENTA};
	String Size[]={"Font size","11", "14", "16", "18", "20", "24", "28"};
	int sizes[] = {11,14,16,18,20,24,28};
	String Style[]={"Style","Bold", "Italic", "Bold+Italic"};
	
	JTextArea Area,Additional;
	JScrollPane scroll;
	JTabbedPane tp;
	
	JButton update;
	JLabel lb,lb2,lb3;
	
	Container c;
	
	public TextEditor() 
	{
		super("Text Editor");
		c=getContentPane();
		c.setLayout(new BorderLayout());
		
//North Panel
		npanel = new JPanel(new BorderLayout());
		bar = new JMenuBar();
		File = new JMenu("File");
        Format = new JMenu ("Format");
        Help = new JMenu ("Help");
        
//adding Bar components
        bar.add(File);
        bar.add(Format);
        bar.add(Help);
        
//File components		
        New = new JMenuItem("New");
        Save = new JMenuItem("Save");
        Open = new JMenuItem("Open");

        File.add(New);
        File.add(Open);
        File.add(Save);
        
//Format components        
        wrapping = new JCheckBoxMenuItem ("Wrapping");
        wrapping.addItemListener(this);
        Format.add(wrapping);
        darkTheme = new JCheckBoxMenuItem("Dark Theme");
        darkTheme.addItemListener(this);
        Format.add(darkTheme);
        
//Help components        
        Exit = new JMenuItem("Exit");
        Exit.addActionListener(this);
        Help.add(Exit);
        
        Add = new JMenu("Add");
        Help.add(Add);
        Additional = new JTextArea(10,20);
        AdditionalP = new JPanel();
        AdditionalP.add(Additional);
        tp=new JTabbedPane();  
		tp.setBounds(50,50,200,200);
		tp.add("comments",AdditionalP); 
		Add.add(tp);
        
        npanel.add(bar);
        
//East Panel
      	epanel = new JPanel(new GridLayout(4,2,2,2));
      	
      	font = new JComboBox<String>(fontType);
      	fontColor = new JComboBox<String>(colors);
      	fontStyle = new JComboBox<String>(Style);
      	fontSize = new JComboBox<String>(Size);
      	
      	font.setMaximumRowCount(3); 
      	fontColor.setMaximumRowCount(3);
      	fontStyle.setMaximumRowCount(3);
      	fontSize.setMaximumRowCount(3);
      	
      	font.addItemListener(this);
      	fontColor.addItemListener(this);
      	fontStyle.addItemListener(this);
      	fontSize.addItemListener(this);
      	
      	epanel.add(font);
      	epanel.add(fontColor);
      	epanel.add(fontStyle);
      	epanel.add(fontSize);
        
//center
      	Area = new JTextArea();
      	Area.setLineWrap(false);
      	scroll = new JScrollPane(Area);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

//South Panel
      	spanel = new JPanel(new GridLayout(1,4));
      	lb = new JLabel("Lines = 0 |  words = 0");
		lb.setFont(new Font("Arial",1,12));
		lb.setForeground(Color.GRAY);
		update = new JButton("Update");
		update.addActionListener(this);
		lb2 = new JLabel("		");
		lb3 = new JLabel("		");
		
		spanel.add(lb);
		spanel.add(update);
		spanel.add(lb2);
		spanel.add(lb3);
		
//adding panels		
        c.add(npanel, BorderLayout.NORTH);
        c.add(epanel, BorderLayout.EAST);
        c.add(scroll);
        c.add(spanel, BorderLayout.SOUTH);

        setSize(600,400);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//Help menu item
		if(e.getSource()==Exit)System.exit(0);
		
		//to count lines and words
		StringTokenizer words = new StringTokenizer(Area.getText());
		StringTokenizer lines = new StringTokenizer(Area.getText(),"\n");
		if(e.getSource()==update)
			lb.setText("Lines = "+lines.countTokens()+" |  words = "+words.countTokens());
			
	}
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getStateChange()==ItemEvent.SELECTED)
		{
			//east 
			if(e.getSource()==font)Area.setFont(new Font((String)font.getSelectedItem(),0, 14));
			if(e.getSource()==fontColor) Area.setForeground(myColors[fontColor.getSelectedIndex()-1]);
			if(e.getSource()==fontStyle)Area.setFont(new Font("default",fontStyle.getSelectedIndex(), 14));
			if(e.getSource()==fontSize)Area.setFont(new Font("default",0, sizes[fontSize.getSelectedIndex()-1]));
			
			//Format menu items
			if (e.getSource()==wrapping)
				if (Wflag==0){
					Wflag = 1;
					Area.setLineWrap(true);
				}
				else{
					Wflag = 0;
					Area.setLineWrap(false);
				}
			
			if (e.getSource()==darkTheme)
				if (Tflag==0){
					Tflag = 1;
					Area.setOpaque(true);
					Area.setBackground(Color.BLACK);
					Area.setForeground(Color.WHITE);
				}
				else{
					Tflag = 0;
					Area.setOpaque(true);
					Area.setBackground(Color.WHITE);
					Area.setForeground(Color.BLACK);
				}
		}
	}
	public static void main (String args[])
	{
		TextEditor pe = new TextEditor();
		pe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
}
