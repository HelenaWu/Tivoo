
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/* This class demos both the file chooser and Java's ability to display HTML
 * Use the file chooser to open an an HTML object.  The rendered version of the
 * HTML should appear in the window.
 */

public class Viewer extends JFrame implements ActionListener {

    JEditorPane pane;
    JButton loadButton, runButton;
    JComboBox filterList;
    
    
    public Viewer(String url) throws IOException
    {
        pane = new JEditorPane();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pane, BorderLayout.CENTER);
        pane.setEditable(false);
        pane.setPreferredSize(new Dimension(800,600));
        
        loadButton = new JButton("Load XML files");
        loadButton.addActionListener(this);
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(runButton);
        
        pane.addHyperlinkListener(new LinkFollower());
        pack();
        pane.setPage(url);
        setVisible(true);
    }
    
    
    
    private class LinkFollower implements HyperlinkListener
    {
        public void hyperlinkUpdate (HyperlinkEvent evt)
        {
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
                // user clicked a link, load it and show it
                try
                {
                    pane.setPage((evt.getURL().toString()));
                }
                catch (Exception e)
                {
                    String s = evt.getURL().toString();
                    JOptionPane.showMessageDialog(HTMLExample.this,
                            "loading problem for " + s + " " + e,
                            "Load Problem", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Tivoo!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new Viewer());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    
    public static void main(String[] args) throws IOException {
        JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            HTMLExample foo = new HTMLExample(file.toURI().toString());
            
        } 
        
       
       
        

    }

}