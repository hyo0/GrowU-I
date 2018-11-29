
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
		setTitle("�޸���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		//�޴��� �� ����������
		JMenuItem ExitMemo = new JMenuItem("������(X)");
		ExitMemo.setMnemonic(java.awt.event.KeyEvent.VK_X); // �޴��ٰ� �������·δ� X�� ������ ����Ű
		file.add(NewMemo);
		file.add(OpenMemo);
		file.add(SaveMemo);
		file.addSeparator();
		file.add(ExitMemo);
		menubar.add(file);
		setJMenuBar(menubar);
		
		txtMemo.setLineWrap(true);
		Font font = new Font("Malgun Gothic", Font.BOLD, 15); //�ؽ�Ʈ ���� ��Ʈ ��Ʈ������ ��Ʈ���� ����
		txtMemo.setFont(font);
		Color c = new Color(243, 231, 195);  //�ؽ�Ʈ���� ������
		txtMemo.setBackground(c);

		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5)); //��ġ
		contentPane.add(scrollPane);
		contentPane.setBackground(Color.WHITE); // ��ü ������ ����
		

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
				int result = JOptionPane.showConfirmDialog(null,"�����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
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