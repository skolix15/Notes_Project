import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

public class AddCustomerFrame extends JFrame{
	
	private JPanel textFieldsPanel;
	private JPanel firstNamePanel;
	private JPanel secondNamePanel;
	private JPanel buttonsPanel;
	private JPanel mainPanel;
	private JTextField textFieldForFirstName;
	private JTextField textFieldForSecondName;
	private JTextArea textAreaForNotes;
	private JButton addCustomerButton;
	private JButton returnButton;
	private JLabel title;
	
	public AddCustomerFrame() {
		
		textFieldsPanel = new JPanel(new BorderLayout());
		firstNamePanel = new JPanel();
		secondNamePanel = new JPanel();
		buttonsPanel = new JPanel();
		mainPanel = new JPanel(new BorderLayout());
		
		
		title = new JLabel("Add Customer");
		addCustomerButton = new JButton("Confirm");
		returnButton = new JButton("Return in previous Page");
		
		/*Listener for the "addCustomerButton" button
		 *The code takes what is written in the fields from the user. 
		 *If at least one text field is empty then a message will appear to the user.
		 *Otherwise, the new customer will be created and will be added in the list with all
		 *the customers in the Central Registry.*/
		
		addCustomerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				String firstName = "";
				String secondName = "";
				String notes = "";
				
				firstName = textFieldForFirstName.getText();
				secondName = textFieldForSecondName.getText();
				notes = textAreaForNotes.getText();

				if(firstName.equals("") || secondName.equals("")){
					JOptionPane.showMessageDialog(null, "You didn't fill in all fields..", "Error", JOptionPane.ERROR_MESSAGE);
				}

				else {
					
					
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					String lastModified = formatter.format(date);
					Customer customer = new Customer(firstName, secondName, notes, lastModified);

					CentralRegistry.addCustomer(customer);

					dispose();
					new AddCustomerFrame();
				}
			}
		});
		
		/*Listener for the "returnButton" button. This frame will be close and the frame with
		 *the list of all customers will be opened*/
		
		returnButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				new ListOfCustomersFrame();
				
			}
			
		});
		
		/*Further down the code edits every text field for color, name, dimension*/
		
		textFieldForFirstName = new JTextField();
		textFieldForFirstName.setBackground(Color.white);
		textFieldForFirstName.setBorder(new TitledBorder("First Name"));
		textFieldForFirstName.setPreferredSize(new Dimension(600,54));
		
		firstNamePanel.add(textFieldForFirstName);
		firstNamePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		
		textFieldForSecondName = new JTextField();
		textFieldForSecondName.setBackground(Color.white);
		textFieldForSecondName.setBorder(new TitledBorder("Second Name"));
		textFieldForSecondName.setPreferredSize(new Dimension(600,54));
		
		secondNamePanel.add(textFieldForSecondName);
		secondNamePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		textAreaForNotes = new JTextArea(5,5);
		
		JScrollPane scroll = new JScrollPane(textAreaForNotes);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		textAreaForNotes.setBackground(Color.white);
		textAreaForNotes.setBorder(new TitledBorder("Notes"));
		textAreaForNotes.setPreferredSize(new Dimension(600,54));
		scroll.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
		textAreaForNotes.setBounds(0, 0, 100, 100);
		textAreaForNotes.setPreferredSize(new Dimension(100, 1000));
		
		/*Create the layout of the text fields in the text field panel*/

		textFieldsPanel.add(title);
		textFieldsPanel.add(firstNamePanel,BorderLayout.NORTH);
		textFieldsPanel.add(secondNamePanel,BorderLayout.CENTER);
		textFieldsPanel.add(scroll,BorderLayout.SOUTH);
		
		buttonsPanel.add(addCustomerButton);
		buttonsPanel.add(returnButton);
		
		buttonsPanel.setBackground(Color.gray);
		
		mainPanel.add(textFieldsPanel,BorderLayout.NORTH);
		mainPanel.add(buttonsPanel,BorderLayout.CENTER);
		mainPanel.setBorder(
	            BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.setContentPane(mainPanel);
		this.setResizable(false);
		
		// Set frame in the center of the screen. */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		this.setSize(700,400);
		this.setTitle("Add Customer");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
