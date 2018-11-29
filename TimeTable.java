package programming;


import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
@SuppressWarnings("serial")
public class TimeTable extends JFrame{
	String grade[] = {"학년선택"};
	String icstable[] = {"학년선택", "1학년A반", "1학년B반" , "2학년정통", "2학년디방", "3학년정통", "3학년디방", "4학년정통", "4학년디방" };
	String comtable[] = {"학년선택", "1학년     ", "2학년     ", "3학년     ", "4학년     " };
	String depart[] = {"학과선택", "정보통신소프트웨어학과", "컴퓨터공학과"};
	ImageIcon[] imageics= {new ImageIcon("c:\\table\\ics\\A.png"),new ImageIcon("c:\\table\\ics\\1A.png"), new ImageIcon("c:\\table\\ics\\1B.png"),
			new ImageIcon("c:\\table\\ics\\2A.png"), new ImageIcon("c:\\table\\ics\\2B.png"),
			new ImageIcon("c:\\table\\ics\\3A.png"), new ImageIcon("c:\\table\\ics\\3B.png"),
			new ImageIcon("c:\\table\\ics\\4A.png"), new ImageIcon("c:\\table\\ics\\4B.png")};
	ImageIcon[] imagecom= {new ImageIcon("c:\\table\\ics\\A.png"), new ImageIcon("c:\\table\\com\\1com.png"),
			new ImageIcon("c:\\table\\com\\2com.png"), new ImageIcon("c:\\table\\com\\3com.png"), 
			new ImageIcon("c:\\table\\com\\4com.png") };
	JLabel la;
	JLabel dd;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TimeTable() {
		setTitle("LIST");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		JComboBox grd = new JComboBox(grade);
		JComboBox ics = new JComboBox(icstable);
		JComboBox com = new JComboBox(comtable);
		JComboBox dep = new JComboBox(depart);
		
		grd.setPreferredSize(new Dimension(100, 28));
		ics.setPreferredSize(new Dimension(100, 28));
		com.setPreferredSize(new Dimension(100, 28));
		dep.setPreferredSize(new Dimension(200, 28));
		
		dep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox dep = (JComboBox)e.getSource();
				int depindex = dep.getSelectedIndex();
				if(depindex == 0) {
					grd.setVisible(true);
					ics.setVisible(false);
					com.setVisible(false);
				}
				if(depindex == 1) {
					grd.setVisible(false);
					ics.setVisible(true);
					com.setVisible(false);
					ics.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							la.setVisible(true);
							JComboBox jcb = (JComboBox)e.getSource();
							int icsindex = jcb.getSelectedIndex();
							la.setIcon(imageics[icsindex]);
						}
					});
				}	
				if(depindex == 2) {
					grd.setVisible(false);
					com.setVisible(true);
					ics.setVisible(false);
					com.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							la.setVisible(true);
							JComboBox jcb = (JComboBox)e.getSource();
							int comindex = jcb.getSelectedIndex();
							la.setIcon(imagecom[comindex]);
						}
					});
				}
			}
		});
		com.setVisible(false);
		ics.setVisible(false);
		this.add(dep);
		this.add(ics);
		this.add(com);
		this.add(grd);
		la = new JLabel(imageics[0]);
		la.setVisible(false);
		this.add(la);
		this.setLocationRelativeTo(null);
		this.setSize(550,730);
		this.setResizable(false);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new TimeTable();
	}

}
