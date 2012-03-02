package GUI;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.event.*;
import javax.swing.*;

import org.jaxen.JaxenException;
import org.jdom.JDOMException;

public class Viewer extends JPanel {
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JButton parseButton;
	private JButton dateFilterButton;
	private JButton includeKeywordsFilterButton;
	private JButton excludeKeywordsFilterButton;
	private JButton characteristicFilterButton;
	private JButton keywordTitleFitlerButton;
	private JButton sortByTitleButton;
	private JButton sortByTimeButton;
	private JButton sortByEndTimeButton;
	private JButton sortInReverseOrderButton;
	private JButton showPageButton;
	private Model model;

	/**
	 * Create a view of the given model of a web browser.
	 */
	public Viewer(Model model) {
		this.model = model;
		JPanel Panel = new JPanel();
		Panel.setLayout(new GridLayout(0, 1));
		Panel.add(menuBar());
		Panel.add(parsePanel());
		Panel.add(filteringPanel());
		Panel.add(sortingPanel());
		Panel.add(showPagePanel());

		setLayout(new BorderLayout());
		add(Panel);
	}

	private JComponent menuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("File loader");
		menuBar.add(menu);
		menuItem = new JMenuItem("Load File...");
		menuItem.addActionListener(new LoadFile());
		menu.add(menuItem);
		return menuBar;
	}

	private JComponent parsePanel() {
		JPanel panel = new JPanel();
		parseButton = new JButton("Parse");
		parseButton.addActionListener(new ParseAction());
		panel.add(parseButton);

		return panel;
	}

	private JComponent filteringPanel() {
		JPanel panel = new JPanel();

		JLabel filteringOptions = new JLabel("Filter Options:");
		panel.add(filteringOptions);

		dateFilterButton = new JButton("Dates");
		dateFilterButton.addActionListener(new DateFilterAction());
		panel.add(dateFilterButton);
		keywordTitleFitlerButton = new JButton("Title");
		keywordTitleFitlerButton.addActionListener(new TitleFilterAction());
		panel.add(keywordTitleFitlerButton);
		includeKeywordsFilterButton = new JButton("Include Keywords");
		includeKeywordsFilterButton
				.addActionListener(new IncludeKeywordsFilterAction());
		panel.add(includeKeywordsFilterButton);
		excludeKeywordsFilterButton = new JButton("Exculde Keywords");
		excludeKeywordsFilterButton
				.addActionListener(new ExcludeKeywordsFilterAction());
		panel.add(excludeKeywordsFilterButton);
		characteristicFilterButton = new JButton("Unique Characteristics");
		characteristicFilterButton
				.addActionListener(new CharacteristicFilterAction());
		panel.add(characteristicFilterButton);
		return panel;
	}

	private JComponent sortingPanel() {
		JPanel panel = new JPanel();

		JLabel sortOptions = new JLabel("Sorting Options:");
		panel.add(sortOptions);
		sortByEndTimeButton = new JButton("End Time");
		sortByEndTimeButton.addActionListener(new EndTimeSortAction());
		panel.add(sortByEndTimeButton);
		sortByTimeButton = new JButton("Start Time");
		sortByTimeButton.addActionListener(new StartTimeSortAction());
		panel.add(sortByTimeButton);
		sortByTitleButton = new JButton("Title");
		sortByTitleButton.addActionListener(new SortByTitleAction());
		panel.add(sortByTitleButton);
		sortInReverseOrderButton = new JButton("Reverse Order");
		sortInReverseOrderButton
				.addActionListener(new ReverseOrderSortAction());
		panel.add(sortInReverseOrderButton);
		return panel;
	}

	private JComponent showPagePanel() {
		JPanel panel = new JPanel();
		showPageButton = new JButton("Show Page");
		showPageButton.addActionListener(new ShowPageAction());
		panel.add(showPageButton);
		return panel;
	}

	private void parse() throws JaxenException, JDOMException, IOException {
		model.parse();
	}

	private class LoadFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			pickFile();
		}
	}

	private void pickFile() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			model.addFile(file);
		}
	}

	// ==================================================================================================================================================================

	private class ParseAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				parse();
			} catch (JaxenException e1) {
				e1.printStackTrace();
			} catch (JDOMException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class CharacteristicFilterAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String unique = JOptionPane
					.showInputDialog("Enter a single unique characteristic");
			model.characteristicFilter(unique);
		}
	}

	private class DateFilterAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String dates = JOptionPane
					.showInputDialog("Below enter the two dates you wish to filter by in this format (MM DD YYYY) and seperted by spaces");
			String[] proper = dates.split(" ");
			model.dateFilter(proper);
		}
	}

	private class ExcludeKeywordsFilterAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String keywords = JOptionPane
					.showInputDialog("Enter keywords that are not included separated by spaces");
			String[] proper = keywords.split(" ");
			model.excludeKeywordsFilter(proper);
		}
	}

	private class IncludeKeywordsFilterAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String keywords = JOptionPane
					.showInputDialog("Enter keywords that are included separated by spaces");
			String[] proper = keywords.split(" ");
			model.includeKeywordsFilter(proper);
		}
	}

	private class TitleFilterAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String titleKeywords = JOptionPane
					.showInputDialog("Enter part of the title.");
			model.titleFilter(titleKeywords);
		}
	}

	private class SortByTitleAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.sortTitle();
		}
	}

	private class StartTimeSortAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.sortstartTime();
		}
	}

	private class EndTimeSortAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.sortendTime();
		}
	}

	private class ReverseOrderSortAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String sort = JOptionPane
					.showInputDialog("Reverse (Title) (Start time) (End Time)...... Enter one below");
			model.sortReverseOrder(sort);
		}
	}

	private class ShowPageAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane
					.showInputDialog("Enter a title for the Calendar");
			model.makeCalendar(name);
			File file = new File("index.html");
			try {
				HTMLLoader foo = new HTMLLoader(file.toURI().toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
