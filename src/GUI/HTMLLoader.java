package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class HTMLLoader extends JFrame {
	private JEditorPane pane;
	private String myUrl;

	public HTMLLoader(String url) throws IOException {
		pane = new JEditorPane();
		myUrl = url;
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pane, BorderLayout.CENTER);
		pane.setEditable(false);
		pane.setPreferredSize(new Dimension(800, 600));
		Font font = new Font("Serif", Font.BOLD, 20);
		pane.setFont(font);
		pane.addHyperlinkListener(new LinkFollower());
		JScrollPane scrollPane = new JScrollPane(pane);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		pack();
		pane.setPage(myUrl);
		setVisible(true);
	}

	private class LinkFollower implements HyperlinkListener {
		public void hyperlinkUpdate(HyperlinkEvent evt) {
			if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				// user clicked a link, load it and show it
				try {
					pane.setPage((evt.getURL().toString()));
				} catch (Exception e) {
					String s = evt.getURL().toString();
					JOptionPane.showMessageDialog(HTMLLoader.this,
							"loading problem for " + s + " " + e,
							"Load Problem", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
