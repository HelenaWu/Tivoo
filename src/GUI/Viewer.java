package GUI;

import general.Event;

import input.CalendarParser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import processing.*;

import javax.swing.*;

import javax.swing.JEditorPane;
import javax.swing.event.*;

import org.jaxen.JaxenException;
import org.jdom.JDOMException;

import output.HTMLWriter;
import output.ListWriter;

import processing.*;


public class Viewer extends JPanel implements ActionListener {

    private JPanel pane;
    private JEditorPane webPage;
    private JButton loadButton, runButton,previewButton;
    private JComboBox filterList;
    private JFileChooser fc;
    private ArrayList<File> XMLs= new ArrayList<File>();
	private List<Event> calendarEvents = new ArrayList<Event>();
    
    
    public Viewer() throws IOException
    {
    	  	

        pane = new JPanel();
        setLayout(new BorderLayout());
        
   
        webPage = new JEditorPane();
        webPage.setEditable(false);
        webPage.setPreferredSize(new Dimension(800,600));
        webPage.setVisible(true);
        pane.add(webPage);
        add(webPage,BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(webPage);
        add(scrollPane,BorderLayout.NORTH);
        
        loadButton = new JButton("Load XML files");
        loadButton.addActionListener(this);
        
        runButton = new JButton("Run!");
        runButton.addActionListener(this);

        previewButton = new JButton("preview in HTML");
        previewButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(runButton);
        buttonPanel.add(previewButton);
        pane.add(buttonPanel,BorderLayout.PAGE_END);
        
        
       String[] filters = {"characteristic",
    		   				"date time", 
    		   				"Include keyword",
    		   				"Exclude keyword",
    		   				"Keyword title",
    		   				"sort by title",
    		   				"sort by startTime",
    		   				"sort by endTime",
    		   				"reverse order"};
       filterList = new JComboBox(filters);
       filterList.setSelectedIndex(0);
       filterList.addActionListener(this);
       pane.add(filterList);
       
       add(pane);
        
    }
    
    private class linkFollower implements HyperlinkListener{

		@Override
		public void hyperlinkUpdate(HyperlinkEvent evt) {
			 if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
	            {
	                // user clicked a link, load it and show it
	                try
	                {
	                	
	                    webPage.setPage((evt.getURL().toString()));
	                }
	                catch (Exception e)
	                {
	                    String s = evt.getURL().toString();
	                    JOptionPane.showMessageDialog(Viewer.this,
	                            "loading problem for " + s + " " + e,
	                            "Load Problem", JOptionPane.ERROR_MESSAGE);
	                }
	            }
			
		}
    	
    }

	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() ==loadButton){
			fc = new JFileChooser();
			 int returnVal = fc.showOpenDialog(Viewer.this); 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                XMLs.add(file);       
						

	            }
		}
		if(e.getSource() == runButton){
			for(File xmlFile: XMLs){
				
				try {
					calendarEvents.addAll(new CalendarParser.Factory().returnListOfEvents(xmlFile));
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
		}
		if(e.getSource()== previewButton){
			HTMLWriter writer = new ListWriter("DukeCal", calendarEvents);
			try {
				writer.writeCal();
				String path = new java.io.File(".").getCanonicalPath();
				webPage.setPage("file://" + path + "/index.html");
				webPage.addHyperlinkListener(new linkFollower());
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
			
		if(e.getSource()==filterList){
			String filterName = (String) filterList.getSelectedItem();
			Filter filter = null;
			
			if(!(filterName.startsWith("sort")) || filterName.startsWith("reverse")){
				String choice= (String)JOptionPane.showInputDialog(
	                    this,
	                    "Words to filter by:",
	                    "Filter",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "tags/dates/etc.");
				
				if ((choice == null) || (choice.length() == 0)) {
					
					JOptionPane.showMessageDialog(this, "the choice input is null");
					
				}
				String[] choices;
				try {
					choices = choice.split(" ");
				} catch (Exception e1) {
					throw new NullPointerException("input is :" + choice); 
				}
				if(filterName.equals("characteristic")){			
					filter = new CharacteristicFilter(calendarEvents);
				}
				if (filterName.equals("date time")){
					filter = new DateTimeFilter(calendarEvents);
				}
				if(filterName.equals("Include keyword")){
					filter = new IncludeKeywordsFilter(calendarEvents);
				}
				if(filterName.equals("Exclude keyword")){
					filter = new ExcludeKeywordsFilter(calendarEvents);
				}
				if(filterName.equals("Keyword title" )){
					filter = new KeywordTitleFilter(calendarEvents);
				}
	
				calendarEvents = filter.filter(choices);
			}
			else{
				Comparator<Event> comp = null;
				if(filterName.equals("sort by title")){
					comp = new TitleComparator();
				}
				if(filterName.equals("sort by startTime")){
					comp = new StartTimeComparator();
				}
				if(filterName.equals("sort by endTime")){
					comp = new EndTimeComparator();
				}
				if(filterName.equals("reverse order")){
					comp = new ReverseOrderComparator(new TitleComparator());
				}
	            Collections.sort(calendarEvents, comp);

			}
		}
	
		
	}




}