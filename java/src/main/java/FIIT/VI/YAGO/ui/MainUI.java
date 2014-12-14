package FIIT.VI.YAGO.ui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainUI {

	private JFrame frame;
	private JTextField searchField;
	private JButton search;
	private JTextArea textArea_1;

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 825, 637);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		search = new JButton("Search");
		search.setBounds(317, 12, 117, 25);
		frame.getContentPane().add(search);

		searchField = new JTextField();
		searchField.setBounds(12, 15, 293, 19);
		frame.getContentPane().add(searchField);
		searchField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 67, 797, 538);
		frame.getContentPane().add(scrollPane);
		
		textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
	}

	public String getSearch() {
		return searchField.getText();
	}

	public void appendArea(String text) {
		this.textArea_1.append(text);
	}

	public void setTextArea(String text) {
		this.textArea_1.setText(text);
	}

	public void addSearchLister(ActionListener l) {
		this.search.addActionListener(l);
	}
}
