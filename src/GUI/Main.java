package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static final String TITLE = "Tivoo";

	public static void main(String[] args) {
		Model model = new Model();
		Viewer display = new Viewer(model);
		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(2000, 2000));
		frame.getContentPane().add(display);
		frame.pack();
		frame.setVisible(true);
	}
}
