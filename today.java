import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class today extends JFrame {
	public today() {
		ImageIcon[] imagecom= {new ImageIcon("c:\\food\\food1.png"), new ImageIcon("c:\\food\\food2.png"),
				new ImageIcon("c:\\food\\food3.png"), new ImageIcon("c:\\food\\food4.png"), 
				new ImageIcon("c:\\food\\food5.png") };
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH) + 1;
		int date = cal.get(cal.DATE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		setTitle("³¯Â¥");
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel time = new JLabel(year + "-" + month + "-" + date);
		JLabel food = new JLabel();
		contentPane.add(time);
		time.setFont(new Font("Godic", Font.BOLD, 20));
		
		if(date == 26) {
			food.setIcon(imagecom[0]);
		}
		if(date == 27) {
			food.setIcon(imagecom[1]);
		}
		if(date == 28) {
			food.setIcon(imagecom[2]);
		}
		
		if(date == 29) {
			food.setIcon(imagecom[3]);
		}
		
		if(date == 30) {
			food.setIcon(imagecom[4]);
		}
		contentPane.add(food);
		setVisible(true);
		setSize(500, 250);
	}
	public static void main(String[] args) {
		today frame = new today();
	}
}
