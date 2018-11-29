
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
@SuppressWarnings("serial")
public class Naver extends JFrame{
	int i = 1;
	public Naver() throws IOException {

		setTitle("¸Þ¸ðÀå");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		String url = "https://news.naver.com";
        Document doc = null;
        
        try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        Elements element2 = doc.select("div.com_header");
        Elements element = doc.select("ul.newsnow_txarea");
        String title = element2.select("h4").text().substring(0, 9);
		JTextArea ht = new JTextArea("", 15, 40);
		JScrollPane scroll = new JScrollPane(ht);
		ht.setLineWrap(true);
		Container contentPane = getContentPane();
		contentPane.add(scroll);

		ht.append(title + "\n\n");
		for(Element el : element.select("div.newsnow_tx_inner")) {
        	ht.append(i + ") " + el.text() + "\n");
        	i++;
        	Jsoup.connect(el.getElementsByAttribute("href").attr("href"));
        }
		
		setSize(480, 400);
		setVisible(true);
	}
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Naver frame = new Naver();

	}

}
