
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("deprecation")
class CalendarDataManager1{ // 6*7�迭�� ��Ÿ�� �޷� ���� ���ϴ� class
	static final int CAL_WIDTH = 7;
	final static int CAL_HEIGHT = 6;
	int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
	int calYear;
	int calMonth;
	int calDayOfMon;
	final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int calLastDate;
	Calendar today = Calendar.getInstance();
	Calendar cal1;

	public CalendarDataManager1(){ 
		setToday(); 
	}
	public void setToday(){
		calYear = today.get(Calendar.YEAR); 
		calMonth = today.get(Calendar.MONTH);
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
		makeCalData(today);
	}
	private void makeCalData(Calendar cal){
		// 1���� ��ġ�� ������ ��¥�� ���� 
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
		if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
		else calLastDate = calLastDateOfMonth[calMonth];
		// �޷� �迭 �ʱ�ȭ
		for(int i = 0 ; i<CAL_HEIGHT ; i++){
			for(int j = 0 ; j<CAL_WIDTH ; j++){
				calDates[i][j] = 0;
			}
		}
		// �޷� �迭�� �� ä���ֱ�
		for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
			if(i == 0) k = calStartingPos;
			else k = 0;
			for(int j = k ; j<CAL_WIDTH ; j++){
				if(num <= calLastDate) calDates[i][j]=num++;
			}
		}
	}
	private int leapCheck(int year){ // �������� Ȯ���ϴ� �Լ�
		if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
		else return 0;
	}
	public void moveMonth(int mon){ // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
		calMonth += mon;
		if(calMonth>11) while(calMonth>11){
			calYear++;
			calMonth -= 12;
		} else if (calMonth<0) while(calMonth<0){
			calYear--;
			calMonth += 12;
		}
		cal1 = new GregorianCalendar(calYear,calMonth,calDayOfMon);
		makeCalData(cal1);
	}
}
public class Main extends CalendarDataManager1 {
	JPanel calOpPanel;
	JButton todayBut;
	JLabel todayLab;
	JButton lYearBut;
	JButton lMonBut;
	JLabel curMMYYYYLab;
	JButton nMonBut;
	JButton nYearBut;
	ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

	JPanel calPanel;
	JButton weekDaysName[];
	JButton dateButs[][] = new JButton[6][7];
	listenForDateButs lForDateButs = new listenForDateButs(); 

	JPanel infoPanel;
	JLabel infoClock;

	JPanel memoPanel;
	JLabel selectedDate;
	JTextArea memoArea;
	JScrollPane memoAreaSP;
	JPanel memoSubPanel;
	JButton saveBut; 
	JButton delBut; 
	JButton clearBut;

	JPanel frameBottomPanel;
	final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	int i = 1;
	int j = 1;
	JFrame frame;
	Calendar cal = Calendar.getInstance();
	@SuppressWarnings("static-access")
	int year = cal.get(cal.YEAR);
	@SuppressWarnings("static-ac"
			+ "cess")
	int month = cal.get(cal.MONTH) + 1;
	@SuppressWarnings("static-access")
	int date = cal.get(cal.DATE);
	//�ð�ǥ �迭
	String grade[] = {"�г⼱��"};
	String icstable[] = {"�г⼱��", "1�г�A��", "1�г�B��" , "2�г�����", "2�г���", "3�г�����", "3�г���", "4�г�����", "4�г���" };
	String comtable[] = {"�г⼱��", "1�г�     ", "2�г�     ", "3�г�     ", "4�г�     " };
	String depart[] = {"�а�����", "������ż���Ʈ�����а�", "��ǻ�Ͱ��а�"};
	ImageIcon[] imageics= {new ImageIcon("c:\\main\\table\\ics\\A.png"), new ImageIcon("c:\\main\\table\\ics\\1A.png"), 
			new ImageIcon("c:\\main\\table\\ics\\1B.png"), new ImageIcon("c:\\main\\table\\ics\\2A.png"), 
			new ImageIcon("c:\\main\\table\\ics\\2B.png"), new ImageIcon("c:\\main\\table\\ics\\3A.png"), 
			new ImageIcon("c:\\main\\table\\ics\\3B.png"), new ImageIcon("c:\\main\\table\\ics\\4A.png"), 
			new ImageIcon("c:\\main\\table\\ics\\4B.png")};
	ImageIcon[] imagecom= {new ImageIcon("c:\\main\\table\\ics\\A.png"), 
			new ImageIcon("c:\\main\\table\\com\\1com.png"), new ImageIcon("c:\\main\\table\\com\\2com.png"), 
			new ImageIcon("c:\\main\\table\\com\\3com.png"), new ImageIcon("c:\\main\\table\\com\\4com.png") };
	JLabel la;
	private JLabel naverLink;
	private JLabel daumLink;
	final String title = "�Ѷ�� ��Ƹ��";
	public static void main(String[] args) {
		new Main();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Main() {
		Color c = new Color(238, 238, 238);
		ImageIcon[] imagefood= {new ImageIcon("c:\\main\\food\\food1.png"), new ImageIcon("c:\\main\\food\\food2.png"),
				new ImageIcon("c:\\main\\food\\food3.png"), new ImageIcon("c:\\main\\food\\food4.png"), 
				new ImageIcon("c:\\main\\food\\food5.png") };
		frame =  new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		try{
			UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame) ;
		}catch(Exception e){
			System.out.println("ERROR : LookAndFeel setting failed");
		}
		JPanel MainPanel = new JPanel();
		MainPanel.setBackground(Color.GRAY);
		JPanel SubPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,25,0));
		JPanel spacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,25,0)); //�����г�
		spacePanel.setOpaque(false);
		spacePanel.setLayout(null);
		JPanel SubPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,11));
		JPanel SubPanel1_1 = new JPanel();
		SubPanel1_1.setLayout(new FlowLayout());
		JPanel SubPanel1_1_1 = new JPanel(); //�޸����ư �г�
		JPanel SubPanel1_1_2 = new JPanel(); //�̴ϰ��ӹ�ư �г�
		SubPanel1_1.add(SubPanel1_1_1);
		SubPanel1_1.add(SubPanel1_1_2);
		//SubPanel1_1_1 �г� �޸���
		JButton Memobt = new JButton(new ImageIcon("C:\\main\\table\\memo.png"));
		Memobt.setToolTipText("�޸���");
		Memobt.setBorderPainted(false);
		Memobt.setFocusPainted(false);
		Memobt.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame Memo = new JFrame("�޸���");
				Memo.setVisible(true);
				Memo.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						super.windowClosing(e);
						Memo.setVisible(false);
						Memo.dispose();
					}
				});
				JTextArea txtMemo = new JTextArea("", 17, 31);
				JScrollPane scrollPane = new JScrollPane(txtMemo);

				JMenuBar menubar = new JMenuBar();
				//����
				JMenu file = new JMenu("����(F)");
				file.setMnemonic(java.awt.event.KeyEvent.VK_F);  // ���� ����Ű alt + F
				//�޴��� �� ������
				JMenuItem NewMemo = new JMenuItem("������(N)");
				NewMemo.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK)); // ������ CTRL + N ����Ű
				NewMemo.setMnemonic(java.awt.event.KeyEvent.VK_N);  // �޴��ٰ� �������·δ� N�� ������ ����Ű
				//�޴��� �� ��������
				JMenuItem OpenMemo = new JMenuItem("����(O)");
				OpenMemo.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK)); // �������� CTRL + O ����Ű
				OpenMemo.setMnemonic(java.awt.event.KeyEvent.VK_O); // �޴��ٰ� �������·δ� O�� ������ ����Ű
				//�޴��� �� ��������
				JMenuItem SaveMemo = new JMenuItem("����(S)");
				SaveMemo.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK)); // �������� CTRL + S ����Ű
				SaveMemo.setMnemonic(java.awt.event.KeyEvent.VK_S); // �޴��ٰ� �������·δ� S�� ������ ����Ű

				file.add(NewMemo);
				file.add(OpenMemo);
				file.add(SaveMemo);
				menubar.add(file);
				Memo.setJMenuBar(menubar);

				txtMemo.setLineWrap(true);
				Font font = new Font("Malgun Gothic", Font.BOLD, 15); //�ؽ�Ʈ ���� ��Ʈ ��Ʈ������ ��Ʈ���� ����
				txtMemo.setFont(font);
				Color c = new Color(243, 231, 195);  //�ؽ�Ʈ���� ������
				txtMemo.setBackground(c);

				Memo.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5)); //��ġ
				Memo.add(scrollPane);
				Memo.setBackground(Color.WHITE); // ��ü ������ ����


				SaveMemo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						int result = chooser.showSaveDialog(frame);

						if (result != JFileChooser.APPROVE_OPTION)
							return;

						String filePath = chooser.getSelectedFile().getPath();

						BufferedWriter writer;
						try {
							writer = new BufferedWriter(new FileWriter(filePath));	
							writer.write(txtMemo.getText());		
							writer.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				});

				OpenMemo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						txtMemo.setText(null);
						JFileChooser chooser = new JFileChooser();
						int result = chooser.showOpenDialog(frame);

						if (result != JFileChooser.APPROVE_OPTION)
							return;

						String filePath = chooser.getSelectedFile().getPath();
						try {
							BufferedReader reader = new BufferedReader(new FileReader(filePath));
							while (true) {
								String line = reader.readLine();
								if (line == null)
									break;
								txtMemo.setText(txtMemo.getText() + line);
							}
							reader.close();

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				NewMemo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						txtMemo.setText(null);

					}
				});
				Memo.setSize(462, 460);
				Memo.setResizable(false);
			}
		});
		SubPanel1_1_1.add(Memobt);
		Memobt.setPreferredSize(new Dimension(180, 170));
		SubPanel1_1_1.setBackground(Color.WHITE);
		
		//SubPanel1_1_2 �̴ϰ��� �г�
		JButton MiniGamebt = new JButton(new ImageIcon("C:\\main\\table\\game.png"));
		MiniGamebt.setBorderPainted(false);
		MiniGamebt.setFocusPainted(false);
		MiniGamebt.setToolTipText("�̴ϰ���");
		MiniGamebt.setPreferredSize(new Dimension(180, 170));
		SubPanel1_1_2.add(MiniGamebt);
		SubPanel1_1_2.setBackground(Color.WHITE);

		//�� ������Ʈ�� ���� ������
		JPanel SubPanel1_2 = new JPanel();
		JLabel title = new JLabel(new ImageIcon("c:\\main\\table\\title.png"));
		SubPanel1_2.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		SubPanel1_2.add(title);
		
		//�н� �� ���糯¥
		JPanel SubPanel1_3 = new JPanel();
		JLabel time = new JLabel(year + " - " + month + " - " + date); // ���� ��¥
		time.setBorder(BorderFactory.createEmptyBorder(0 , 240 , 5 , 0));
		JLabel food = new JLabel();
		food.setBorder(BorderFactory.createEmptyBorder(0 , 40 , 0 , 0));
		SubPanel1_3.add(time);
		SubPanel1_3.add(food);
		time.setFont(new Font("Godic", Font.BOLD, 25));
		//���� ��¥ �� �Ĵ� �̹��� �ҷ�����
		if(date < 10) { time.setText(year + " - " + month + " - 0" + date); }
		if(date == 10) { food.setIcon(imagefood[0]); }
		if(date == 11) { food.setIcon(imagefood[1]); }
		if(date == 12) { food.setIcon(imagefood[2]); }
		if(date == 13) { food.setIcon(imagefood[3]); }
		if(date == 14) { food.setIcon(imagefood[4]); }
		
		//SubPanel2�� ���� �ð�ǥ �г�
		JPanel jp1 = new JPanel();
		JComboBox grd = new JComboBox(grade); //�г⼱�ø� �ִ� �޺��ڽ�
		JComboBox ics = new JComboBox(icstable); //������Ź�۰��а� �г⼱�� �޺��ڽ�
		JComboBox com = new JComboBox(comtable); //��ǻ�� ���а� �г⼱�� �޺��ڽ�
		JComboBox dep = new JComboBox(depart); // �а����� �޺��ڽ�
		grd.setPreferredSize(new Dimension(100, 28)); //�޺��ڽ��� ũ��� ��Ʈ����
		grd.setFont(new Font("Godic", Font.BOLD, 13)); 
		ics.setPreferredSize(new Dimension(100, 28));
		ics.setFont(new Font("Godic", Font.BOLD, 13));
		com.setPreferredSize(new Dimension(100, 28));
		com.setFont(new Font("Godic", Font.BOLD, 13));
		dep.setPreferredSize(new Dimension(200, 28));
		dep.setFont(new Font("Godic", Font.BOLD, 13));
		dep.addActionListener(new ActionListener() { //�а����� �޺��ڽ��� ������ ���
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox dep = (JComboBox)e.getSource();
				int depindex = dep.getSelectedIndex();
				if(depindex == 0) {    // �а�����,����,�İ� �� �а������� �����Ѱ��
					grd.setVisible(true); //�г⼱�ø� �ִ� �޺��ڽ��� ���̰��ϰ� ������ �Ⱥ��̰� ����
					ics.setVisible(false);
					com.setVisible(false);
					la.setVisible(false);
				}
				if(depindex == 1) {   // �а�����,����,�İ� �� ������ �����Ѱ��
					grd.setVisible(false);
					ics.setVisible(true); //���� �г⼱�� �޺��ڽ��� ���̰��ϰ� ������ �Ⱥ��̰� ����
					com.setVisible(false);
					ics.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							la.setVisible(true);
							JComboBox jcb = (JComboBox)e.getSource();
							int icsindex = jcb.getSelectedIndex(); //�г⼱���� �迭�� �޾ƿͼ� �̹������Ϲ迭�� �����ؼ� 
							la.setIcon(imageics[icsindex]);        //���� �迭�� �ð�ǥ�� ���
							la.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
						}
					});
				}	
				if(depindex == 2) {  //�а�����,����,�İ� �� �İ� �����Ѱ��
					grd.setVisible(false);
					com.setVisible(true); //�İ� �г⼱�� �޺��ڽ��� ���̰��ϰ� ������ �Ⱥ��̰� ����
					ics.setVisible(false);
					com.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							la.setVisible(true);
							JComboBox jcb = (JComboBox)e.getSource();
							int comindex = jcb.getSelectedIndex();
							la.setIcon(imagecom[comindex]);
							la.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
						}
					});
				}
			}
		});
		com.setVisible(false); //�⺻������ �İ��� ���� �г⼱�� �޺��ڽ��� �Ⱥ��̰Լ���
		ics.setVisible(false);
		jp1.add(dep);
		jp1.add(ics);
		jp1.add(com);
		jp1.add(grd);
		la = new JLabel(imageics[0]);
		la.setVisible(false); // �⺻���� �̹������� �Ⱥ��̰� ����
		jp1.add(la);
		frame.setLocationRelativeTo(null);

		//SubPanel2�� �߰� ���� ũ�Ѹ� �г�
		//���̹� ũ�Ѹ� ����
		JPanel jp2 = new JPanel();
		String url = "https://news.naver.com";
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		} //���̹����� �ҽ������� �����ͼ�

		Elements naverele1 = doc.select("div.com_header"); // div�� com_header�̶�� Ŭ������ ����
		Elements naverele2 = doc.select("ul.newsnow_txarea"); //ul�� newsnow_txarea��� Ŭ������ ����
		String navertitle = naverele1.select("h4").text().substring(0, 9); 
		// div�� com_header�̶�� Ŭ�������� h4�ؽ�Ʈ ��������
		JTextArea navernews = new JTextArea(); //�ؽ�Ʈ���� ����
		JScrollPane naverscroll = new JScrollPane(navernews); 
		naverscroll.setPreferredSize(new Dimension(390, 190)); // �ؽ�Ʈ �ڽ� ũ�⼳��
		navernews.setLineWrap(true); // �ؽ�Ʈ�ʰ��� �ڵ��ٹٲ�
		jp2.add(naverscroll);

		navernews.append("���̹� " + navertitle + "\n\n");
		 //ul�� newsnow_txarea��� Ŭ������ div.newsnow_tx_inner�� �ؽ�Ʈ�� �ҷ�����
		for(Element el : naverele2.select("div.newsnow_tx_inner")) {
			if(i == 10)navernews.append(i + ") " + el.text());
			if(i < 10 )navernews.append(i + ") " + el.text() + "\n");
			i++;
		}
		navernews.setEditable(false);  // �ؽ�Ʈ �ڽ� �����Ұ�
		//���̹� ���� �����۸�ũ����
		naverLink = new JLabel();
		naverLink.setText("<html> ���̹� ��纸�� : <a href=\"\">http://news.naver.com/</a></html>");
		naverLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		goWebsitenaver(naverLink);
		jp2.add(naverLink);
		naverLink.setFont(new Font("Godic", Font.BOLD, 15));
		naverLink.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 13, 0));

		String url1 = "https://media.daum.net";
		Document doc1 = null;

		try {
			doc1 = Jsoup.connect(url1).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Elements daumele1 = doc1.select("div.box_headline");
		Elements daumele2 = doc1.select("ul.list_headline");
		String daumtitle = daumele1.select("h3").text().substring(0, 4);
		JTextArea daumnews = new JTextArea();
		JScrollPane daumscroll = new JScrollPane(daumnews);
		daumscroll.setPreferredSize(new Dimension(390, 190));
		daumnews.setLineWrap(true);
		jp2.add(daumscroll);
		daumnews.append("���� " + daumtitle + "\n\n");
		for(Element el1 : daumele2.select("strong.tit_g")) {
			if(j == 10)daumnews.append(j + ") " + el1.text());
			if(j < 10 )daumnews.append(j + ") " + el1.text() + "\n");
			if(j > 10) break;
			j++;
		}
		daumnews.setEditable(false);
		daumLink = new JLabel();
		daumLink.setText("<html> ����	 ��纸�� : <a href=\"\">https://media.daum.net/</a></html>");
		daumLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		goWebsitedaum(daumLink);
		jp2.add(daumLink);
		daumLink.setFont(new Font("Godic", Font.BOLD, 15));
		
		//�������� ������ 
		
		JPanel jp3 = new JPanel();
		calOpPanel = new JPanel();
		todayBut = new JButton("Today");
		todayBut.setToolTipText("Today");
		todayBut.addActionListener(lForCalOpButtons);
		//���糯¥�� �󺧷μ���
		todayLab = new JLabel(today.get(Calendar.MONTH)+1+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));
		lYearBut = new JButton("<<"); //�����⵵ ��ư
		lYearBut.setToolTipText("Previous Year"); //��ư���� ���콺�ø��� �ߴ�  �ؽ�Ʈ
		lYearBut.addActionListener(lForCalOpButtons);
		lMonBut = new JButton("<"); //������ ��ư
		lMonBut.setToolTipText("Previous Month"); //��ư���� ���콺�ø��� �ߴ�  �ؽ�Ʈ
		lMonBut.addActionListener(lForCalOpButtons);
		curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
		//���� �޷��� �ް� �⵵ ��
		nMonBut = new JButton(">"); //������ ��ư
		nMonBut.setToolTipText("Next Month"); //��ư���� ���콺�ø��� �ߴ�  �ؽ�Ʈ
		nMonBut.addActionListener(lForCalOpButtons); 
		nYearBut = new JButton(">>"); //�����⵵��ư
		nYearBut.setToolTipText("Next Year"); //��ư���� ���콺�ø��� �ߴ�  �ؽ�Ʈ
		nYearBut.addActionListener(lForCalOpButtons);
		calOpPanel.setLayout(new GridBagLayout());
		GridBagConstraints calOpGC = new GridBagConstraints();
		//Grid���̾ƿ��� ��ķ� �ϴ°��̹Ƿ� 
		//��ķ� ��ġ���� x y width height
		calOpGC.gridx = 1;
		calOpGC.gridy = 1;
		calOpGC.gridwidth = 2;
		calOpGC.gridheight = 1;
		calOpGC.weightx = 1;
		calOpGC.weighty = 1;
		calOpGC.insets = new Insets(5,5,0,0);
		calOpGC.anchor = GridBagConstraints.WEST;
		calOpGC.fill = GridBagConstraints.NONE; //�����̳��� ũ�Ⱑ �����������
		calOpPanel.add(todayBut,calOpGC);

		calOpGC.gridwidth = 3;
		calOpGC.gridx = 2;
		calOpGC.gridy = 1;
		calOpPanel.add(todayLab,calOpGC);

		calOpGC.anchor = GridBagConstraints.CENTER;
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 1;
		calOpGC.gridy = 2;
		calOpPanel.add(lYearBut,calOpGC);

		calOpGC.gridwidth = 1;
		calOpGC.gridx = 2;
		calOpGC.gridy = 2;
		calOpPanel.add(lMonBut,calOpGC);

		calOpGC.gridwidth = 2;
		calOpGC.gridx = 3;
		calOpGC.gridy = 2;
		calOpPanel.add(curMMYYYYLab,calOpGC);

		calOpGC.gridwidth = 1;
		calOpGC.gridx = 5;
		calOpGC.gridy = 2;
		calOpPanel.add(nMonBut,calOpGC);

		calOpGC.gridwidth = 1;
		calOpGC.gridx = 6;
		calOpGC.gridy = 2;
		calOpPanel.add(nYearBut,calOpGC);

		calPanel=new JPanel();
		weekDaysName = new JButton[7];
		for(int i=0 ; i<CAL_WIDTH ; i++){
			weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
			weekDaysName[i].setBorderPainted(false);
			weekDaysName[i].setContentAreaFilled(false);
			weekDaysName[i].setFont(new Font("arial", Font.PLAIN, 8));
			weekDaysName[i].setForeground(Color.WHITE); // ������ ���� ��� ����
			if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50)); // �Ͽ��Ϲ����� ������ ����
			else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200)); //����Ϲ����� �Ķ�������
			else weekDaysName[i].setBackground(new Color(150, 150, 150)); //���Ϲ����� ����
			weekDaysName[i].setOpaque(true); // ���� ������ ä���
			weekDaysName[i].setFocusPainted(false); // ������ ��ư�����̶� �������� ��Ŀ���Ǵ� ���� ����
			calPanel.add(weekDaysName[i]);
		}
		for(int i=0 ; i<CAL_HEIGHT ; i++){
			for(int j=0 ; j<CAL_WIDTH ; j++){
				dateButs[i][j]=new JButton();
				dateButs[i][j].setBorderPainted(false); // �� ��ư �׵θ�����
				dateButs[i][j].setContentAreaFilled(false); // �� ��ư Ŭ�������� �Ķ������� ������ Ŭ���̺�Ʈ����
				dateButs[i][j].setBackground(Color.WHITE); // �� ������ ���
				dateButs[i][j].setOpaque(true);
				dateButs[i][j].addActionListener(lForDateButs);
				calPanel.add(dateButs[i][j]);
			}
		}
		calPanel.setLayout(new GridLayout(0,7,2,2));
		calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		calPanel.setBackground(c);

		showCal(); // �޷��� ǥ��

		selectedDate = new JLabel("<Html><font size=3>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
		selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
		
		memoPanel=new JPanel();
		memoPanel.setBorder(BorderFactory.createTitledBorder("�޸�"));
		memoArea = new JTextArea("",9,39);
		memoArea.setLineWrap(true);
		memoArea.setWrapStyleWord(true);
		memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		memoAreaSP.setPreferredSize(new Dimension(390, 70));
		readMemo();

		memoSubPanel=new JPanel();
		saveBut = new JButton("Save"); 
		saveBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					File f= new File("MemoData");
					if(!f.isDirectory()) f.mkdir();

					String memo = memoArea.getText();
					if(memo.length()>0){
						BufferedWriter out = new BufferedWriter(new FileWriter("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt"));
						String str = memoArea.getText();
						out.write(str);  
						out.close();
					}
				} catch (IOException e) {
				}
				showCal();
			}					
		});
		delBut = new JButton("Delete");
		delBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				memoArea.setText("");
				File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
				if(f.exists()){
					f.delete();
					showCal();
				}			
			}					
		});
		clearBut = new JButton("Clear");
		clearBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				memoArea.setText(null);
			}
		});
		memoSubPanel.add(saveBut);
		memoSubPanel.add(delBut);
		memoSubPanel.add(clearBut);
		memoPanel.setLayout(new BorderLayout());
		memoPanel.add(selectedDate, BorderLayout.NORTH);
		memoPanel.add(memoAreaSP,BorderLayout.CENTER);
		memoPanel.add(memoSubPanel,BorderLayout.SOUTH);
		memoPanel.setBackground(c);
		//calOpPanel, calPanel��  frameSubPanelWest�� ��ġ
		JPanel frameSubPanelWest = new JPanel();
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanel.setPreferredSize(calOpPanelSize);
		calOpPanel.setBackground(c);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.setBorder(BorderFactory.createTitledBorder("�޷�"));
		frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
		frameSubPanelWest.add(calPanel,BorderLayout.CENTER);
		calPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		frameSubPanelWest.setPreferredSize(new Dimension(400, 320));

		JPanel frameSubPanelEast = new JPanel();
		frameSubPanelEast.setLayout(new BorderLayout());
		frameSubPanelEast.add(memoPanel,BorderLayout.CENTER);

		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
		jp3.add(frameSubPanelWest, BorderLayout.WEST);
		jp3.add(frameSubPanelEast, BorderLayout.SOUTH);

		SubPanel1.add(SubPanel1_1);
		SubPanel1.add(SubPanel1_2);
		SubPanel1.add(SubPanel1_3);

		SubPanel2.add(jp1);
		SubPanel2.add(jp2);
		SubPanel2.add(jp3);

		MainPanel.setPreferredSize(new Dimension(1500, 800));
		MainPanel.setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		SubPanel1.setPreferredSize(new Dimension(1400, 200));
		SubPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		SubPanel1_1.setPreferredSize(new Dimension(400, 190));
		SubPanel1_2.setPreferredSize(new Dimension(450, 190));
		SubPanel1_3.setPreferredSize(new Dimension(400, 190));


		spacePanel.setPreferredSize(new Dimension(1400, 10));

		SubPanel2.setPreferredSize(new Dimension(1400, 520));
		SubPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		jp1.setPreferredSize(new Dimension(430, 500));
		jp2.setPreferredSize(new Dimension(430, 500));
		jp3.setPreferredSize(new Dimension(430, 500));

		SubPanel1.setBackground(Color.WHITE);
		SubPanel2.setBackground(Color.WHITE);
		SubPanel1_1.setBackground(Color.WHITE);
		SubPanel1_2.setBackground(Color.WHITE);
		SubPanel1_3.setBackground(Color.WHITE);
		jp1.setBackground(c);
		jp2.setBackground(c);
		jp3.setBackground(c);
		MainPanel.add(SubPanel1);
		MainPanel.add(spacePanel);
		MainPanel.add(SubPanel2);
		MainPanel.setBackground(c);
		jp1.setBorder(BorderFactory.createTitledBorder("�ð�ǥ"));
		jp2.setBorder(BorderFactory.createTitledBorder("�ǽð�����"));
		jp3.setBorder(BorderFactory.createTitledBorder("��������"));
		frame.setBackground(c);
		frame.add(MainPanel);
		frame.setLocation(50, 20);
		frame.setSize(1500, 800);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	private void readMemo(){
		try{
			File f = new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
			if(f.exists()){
				BufferedReader in = new BufferedReader(new FileReader("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt"));
				String memoAreaText= new String();
				while(true){
					String tempStr = in.readLine();
					if(tempStr == null) break;
					memoAreaText = memoAreaText + tempStr + System.getProperty("line.separator");
				}
				memoArea.setText(memoAreaText);
				in.close();	
			}
			else memoArea.setText("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void showCal(){
		for(int i=0;i<CAL_HEIGHT;i++){
			for(int j=0;j<CAL_WIDTH;j++){
				String fontColor="black";
				if(j==0) fontColor="red";
				else if(j==6) fontColor="blue";

				File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
				if(f.exists()){
					dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
				}
				else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

				JLabel todayMark = new JLabel("<html><font color=green>*</html>");
				dateButs[i][j].removeAll();
				if(calMonth == today.get(Calendar.MONTH) &&
						calYear == today.get(Calendar.YEAR) &&
						calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
					dateButs[i][j].add(todayMark);
					dateButs[i][j].setToolTipText("Today");
				}

				if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
				else dateButs[i][j].setVisible(true);
			}
		}
	}
	private class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == todayBut){
				setToday();
				lForDateButs.actionPerformed(e);
			}
			else if(e.getSource() == lYearBut) moveMonth(-12);
			else if(e.getSource() == lMonBut) moveMonth(-1);
			else if(e.getSource() == nMonBut) moveMonth(1);
			else if(e.getSource() == nYearBut) moveMonth(12);

			curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
			showCal();
		}
	}
	private class listenForDateButs implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int k=0,l=0;
			for(int i=0 ; i<CAL_HEIGHT ; i++){
				for(int j=0 ; j<CAL_WIDTH ; j++){
					if(e.getSource() == dateButs[i][j]){ 
						k=i;
						l=j;
					}
				}
			}

			if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today��ư�� ���������� �� actionPerformed�Լ��� ����Ǳ� ������ ���� �κ�

			cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);

			String dDayString = new String();
			int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
			if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
					&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
					&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
			else if(dDay >=0) dDayString = "D-"+(dDay+1);
			else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);

			selectedDate.setText("<Html><font size=3>"+(calMonth+1)+"/"+calDayOfMon+"/"+calYear+"&nbsp;("+dDayString+")</html>");

			readMemo();
		}
	}
	private void goWebsitenaver(JLabel naverwebsite) { 
		naverwebsite.addMouseListener(new MouseAdapter() { 
			@Override 
			public void mouseClicked(MouseEvent e) { 
				try { 
					try { 
						Desktop.getDesktop().browse(new URI("https://news.naver.com")); 
					} catch (IOException ex) { 
						Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex); 
					} 
				} 
				catch (URISyntaxException ex) {
				} 
			} 
		});
	}
	private void goWebsitedaum(JLabel daumwebsite) { 
		daumwebsite.addMouseListener(new MouseAdapter() { 
			@Override 
			public void mouseClicked(MouseEvent e) { 
				try { 
					try { 
						Desktop.getDesktop().browse(new URI("https://media.daum.net/")); 
					} catch (IOException ex) { 
						Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex); 
					} 
				} 
				catch (URISyntaxException ex) {
				} 
			} 
		});
	}
} 