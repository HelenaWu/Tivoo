package output;

import general.Event;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import processing.DateTimeFilter;
import processing.ExcludeKeywordsFilter;
import processing.Filter;
import processing.KeywordTitleFilter;


public class Viewer extends JPanel {
	
	public static final Dimension SIZE = new Dimension(1000, 600);

	private String myHTML;
	private String myTitle;
	private List<Event> myCalendarEvents;
	// calendar
	private JEditorPane myPage;
	private JLabel myStatus;
	
	// navigation
	private JTextField mySelectionArea1;
	private JTextField mySelectionArea2;
	private JButton mySortByDayButton;
	private JButton mySortByRangeButton;
	private JButton mySortByKeywordButton;
	private JButton mySortNotKeywordButton;
	private JButton myCalButton;

	public Viewer(List<Event> calendarEvents, String title) throws IOException {
		String path = getClass().getClassLoader().getResource(".").getPath();
		myHTML = "file://"+path.substring(0, path.length()-4);
		myCalendarEvents = calendarEvents;
		myTitle = title;
		// add components to frame
		setLayout(new BorderLayout());
		// must be first since other panels may refer to page
		add(makePageDisplay(), BorderLayout.CENTER);
		add(makeInputPanel(), BorderLayout.NORTH);
		add(makeInformationPanel(), BorderLayout.SOUTH);
		// control the navigation
		enableButtons();
	}
	
	private boolean isDate(String input) {
		if(input.length() == 10) {
			if(input.charAt(2) == '/' & input.charAt(5) == '/') {
				return true;
			}
		}
		return false;
	}
	
	// only enable buttons when useful to user
    private void enableButtons ()
    {
//        myBackButton.setEnabled(myModel.hasPrevious());
//        myNextButton.setEnabled(myModel.hasNext());
//        myHomeButton.setEnabled(myModel.getHome() != null);
    	mySortByDayButton.setEnabled(isDate(mySelectionArea1.getText()) & mySelectionArea2.getText().length() == 0);
    	mySortByRangeButton.setEnabled(isDate(mySelectionArea1.getText()) & isDate(mySelectionArea2.getText()));
    	mySortByKeywordButton.setEnabled(mySelectionArea1.getText().length() >= 1);
    	mySortNotKeywordButton.setEnabled(mySelectionArea1.getText().length() >= 1);
    	myCalButton.setEnabled(true);
    }

    // convenience method to create HTML page display
	private JComponent makePageDisplay () throws IOException
	{
        // displays the web page
        myPage = new JEditorPane();
        myPage.setPreferredSize(SIZE);
        // allow editor to respond to link-clicks/mouse-overs
        myPage.setEditable(false);
        myPage.addHyperlinkListener(new LinkFollower());
        update();
		return new JScrollPane(myPage);
	}
	
	private void update() throws IOException {
		myPage.setPage(myHTML+"index.html");
	}
	private void update(String path) throws IOException {
		myPage.setPage(path);
	}
	
    // organize user's options for controlling/giving input to model
    private JComponent makeInputPanel () throws IOException
    {
//        JPanel panel = new JPanel(new BorderLayout());
    	JPanel panel = new JPanel();
        panel.add(makePanel1(), BorderLayout.NORTH);
        panel.add(makePanel2(), BorderLayout.NORTH);
        return panel;
    }

    // make the panel where "would-be" clicked URL is displayed
    private JComponent makeInformationPanel ()
    {
        // BLANK must be non-empty or status label will not be displayed in GUI
        myStatus = new JLabel(" ");
        return myStatus;
    }

    // make user-entered URL/text field and back/next buttons
    private JComponent makePanel1 () throws IOException
    {
    	JPanel panel = new JPanel();
		mySortByDayButton = new JButton("Show Day");
		mySortByDayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sortByDay();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		panel.add(mySortByDayButton);

		mySortByRangeButton = new JButton("Show Range of Days");
		mySortByRangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sortByRange();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		panel.add(mySortByRangeButton);
		
		
		
		// Text fields
		mySelectionArea1 = new JTextField(15);
		mySelectionArea1.getDocument().addDocumentListener(new changeLooker());
		panel.add(mySelectionArea1);
		mySelectionArea2 = new JTextField(15);
		mySelectionArea2.getDocument().addDocumentListener(new changeLooker());
		panel.add(mySelectionArea2);
		
		return panel;
    }
    
    private class changeLooker implements DocumentListener {
    	public void changedUpdate(DocumentEvent e) {
		    enableButtons();
		  }
		public void insertUpdate(DocumentEvent arg0) {
			enableButtons();
		}
		public void removeUpdate(DocumentEvent arg0) {
			enableButtons();
		}
    }
    
    private JComponent makePanel2 () throws IOException {
    	JPanel panel = new JPanel();
    	
    	mySortByKeywordButton = new JButton("Keyword");
		mySortByKeywordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sortByKeyword();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		panel.add(mySortByKeywordButton);
		
		mySortNotKeywordButton = new JButton("NOT Keyword");
		mySortNotKeywordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sortNotKeyword();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		panel.add(mySortNotKeywordButton);
		
		myCalButton = new JButton("Home Cal");
		myCalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		panel.add(myCalButton);
		return panel;
    }
    
    private void sortByDay() throws IOException {
	    try{	
    		Filter filter = new DateTimeFilter(myCalendarEvents);
	    	myCalendarEvents = filter.filter(mySelectionArea1.getText());
	    	HTMLWriter out = new DayWriter(myTitle, myCalendarEvents);
	    	out.writeCal();
	    	update();
	    } catch (IOException e) {
	    	System.err.println("Bad input.");
	    }
    }
    
    private void sortByRange() throws IOException {
    	try {
	    	Filter filter = new DateTimeFilter(myCalendarEvents);
	    	myCalendarEvents = filter.filter(mySelectionArea1.getText(),mySelectionArea2.getText());
	    	HTMLWriter out = new MonthWriter(myTitle, myCalendarEvents, mySelectionArea1.getText(), mySelectionArea2.getText());
	    	out.writeCal();
	    	update();
    	} catch (IOException e) {
	    	System.err.println("Bad input.");
	    }
    }
    private void sortByKeyword() throws IOException {
    	try {
	    	Filter filter = new KeywordTitleFilter(myCalendarEvents);
	    	myCalendarEvents = filter.filter(mySelectionArea1.getText());
	    	HTMLWriter out = new ListWriter(myTitle, myCalendarEvents);
	    	out.writeCal();
	    	update();
    	} catch (IOException e) {
	    	System.err.println("Bad input.");
	    }
    }
    private void sortNotKeyword() throws IOException {
    	try {
	    	Filter filter = new ExcludeKeywordsFilter(myCalendarEvents);
	    	myCalendarEvents = filter.filter(mySelectionArea1.getText());
	    	HTMLWriter out = new ListWriter(myTitle, myCalendarEvents);
	    	out.writeCal();
	    	update();
    	} catch (IOException e) {
	    	System.err.println("Bad input.");
	    }
    }
    
    private class LinkFollower implements HyperlinkListener
    {
        public void hyperlinkUpdate (HyperlinkEvent evt)
        {
            // user clicked a link, load it and show it
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
            	evt.getURL().toString();
            	try {
					update(evt.getURL().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
}
