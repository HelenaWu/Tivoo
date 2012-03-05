package general;


import java.io.IOException;

import javax.swing.JFrame;

import org.jaxen.JaxenException;
import GUI.*;

public class Main {
	public static void main (String[] args) throws JaxenException, IOException{

		Viewer display = new Viewer();

		JFrame frame = new JFrame("Tivoo!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(display);
        frame.pack();

        frame.setVisible(true);	
        }

}
