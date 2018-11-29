
import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;


@SuppressWarnings({ "serial", "deprecation" })
public class Memo extends JFrame{
	JFrame frame = this;
	public Memo() {
		setTitle("메모장");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea txtMemo = new JTextArea("", 17, 31);
		JScrollPane scrollPane = new JScrollPane(txtMemo);
		
		JMenuBar menubar = new JMenuBar();
		//파일
		JMenu file = new JMenu("파일(F)");
		file.setMnemonic(java.awt.event.KeyEvent.VK_F);  // 파일 단축키 alt + F
		//메뉴바 안 새문서
		JMenuItem NewMemo = new JMenuItem("새문서(N)");
		NewMemo.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK)); // 새문서 CTRL + N 단축키
		NewMemo.setMnemonic(java.awt.event.KeyEvent.VK_N);  // 메뉴바가 열린상태로는 N만 눌러도 단축키
		//메뉴바 안 문서열기
		JMenuItem OpenMemo = new JMenuItem("열기(O)");
		OpenMemo.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK)); // 문서열기 CTRL + O 단축키
		OpenMemo.setMnemonic(java.awt.event.KeyEvent.VK_O); // 메뉴바가 열린상태로는 O만 눌러도 단축키
		//메뉴바 안 문서저장
		JMenuItem SaveMemo = new JMenuItem("저장(S)");
		SaveMemo.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK)); // 문서저장 CTRL + S 단축키
		SaveMemo.setMnemonic(java.awt.event.KeyEvent.VK_S); // 메뉴바가 열린상태로는 S만 눌러도 단축키
		//메뉴바 안 문서끝내기
		JMenuItem ExitMemo = new JMenuItem("끝내기(X)");
		ExitMemo.setMnemonic(java.awt.event.KeyEvent.VK_X); // 메뉴바가 열린상태로는 X만 눌러도 단축키
		file.add(NewMemo);
		file.add(OpenMemo);
		file.add(SaveMemo);
		file.addSeparator();
		file.add(ExitMemo);
		menubar.add(file);
		setJMenuBar(menubar);
		
		txtMemo.setLineWrap(true);
		Font font = new Font("Malgun Gothic", Font.BOLD, 15); //텍스트 파일 폰트 폰트사이즈 폰트굵기 설정
		txtMemo.setFont(font);
		Color c = new Color(243, 231, 195);  //텍스트공간 색상설정
		txtMemo.setBackground(c);

		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5)); //배치
		contentPane.add(scrollPane);
		contentPane.setBackground(Color.WHITE); // 전체 배경색상 설정
		

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
		
		ExitMemo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,"종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
				
			}
		});
		
		setSize(462, 460);
		setVisible(true);
		setResizable(false);
		
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Memo frame = new Memo();
	}
}