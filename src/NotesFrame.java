import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NotesFrame extends JFrame{
	
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private Box textFieldsPanel;

	private JButton saveButton;
	private JButton returnButton;
	private JPanel searchPanel;
	private JPanel barPanel;

	
	public NotesFrame() {
		
		/*Taking the number of all customers from the central registry*/

		int numberOfCustomers = CentralRegistry.getAllListOfCustomers().size();
		
		/*Creating panels and buttons for this frame*/
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(LineBorder.createBlackLineBorder());

		buttonsPanel = new JPanel();
		textFieldsPanel = Box.createVerticalBox();

		
		mainPanel = new JPanel(new BorderLayout());
		
		saveButton = new JButton("Save Changes");
		returnButton = new JButton("Return in previous Page");
		
		searchPanel= new JPanel();
		
		barPanel = new JPanel();
		

		saveButton.setMnemonic('S');
		
		
		/*Listener for "returnButton" button. If it is clicked this frame will close
		 *and in front of user will be opened the main frame*/
		
		returnButton.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	returnButton.doClick();
	            }
	        }

	    });
		
		returnButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				new MainFrame();
				
			}
			
			
		});
		
		
		/*Key listener for "saveButton" button.
		 * When the enter is clicked, "saveButton" button will be activated.
		 */
		saveButton.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	saveButton.doClick();
	            }
	        }

	    });
		
		
		/*Action listener for "saveButton" button.
		 *Everything that the user is writing in the text fields of every customer
		 *will be saved in the base when the user click the save button*/
		
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				ArrayList<Customer> oldNotes = CentralRegistry.getAllListOfCustomers();

				Container container = textFieldsPanel;
				int count = 0;
				
				/*Getting from all the text fields what has been written and correspond to every 
				 *customer's notes the counterpart written text */

				for (Component component : container.getComponents()) {
					
					if (component instanceof JScrollPane) {
						String customerName = CentralRegistry.getAllListOfCustomers().get(count).getFullName();
						Customer customer = CentralRegistry.searchForCustomer(customerName);
						
						JTextArea jTextAreaComponent = (JTextArea) ((JScrollPane) component).getViewport().getView();
						
						String[] temp = jTextAreaComponent.getText().split("\n");	
						
						
						String customerNotes = "";
						for (int i = 0; i < temp.length; i++) {
							customerNotes += temp[i];
							customerNotes += "\n";
						}
						
						if(!oldNotes.get(count).getNotes().equals(customerNotes)) {
							customer.setNotes(customerNotes);
							customer.writeCustomerInAFile();
						}

					}

					count += 1;

				}
				
				count = 0;
				
				/*Print a message to user*/

				JOptionPane pane = new JOptionPane("Your changes are saved", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = pane.createDialog(null, "Save successful");
				dialog.setModal(false);
				dialog.setVisible(true);
				
				/*Timer for the printed message*/

				new Timer(2500, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
					}
				}).start();
				
				
			}
				
		});

		/*Refresh the frame with the new information*/


		for(int i=0;i<CentralRegistry.getAllListOfCustomers().size();i++) {
			
			Customer customer = CentralRegistry.getAllListOfCustomers().get(i);
			
			JTextArea textArea = new JTextArea(100,50);
			JScrollPane scroll = new JScrollPane(textArea);
		    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			textArea.setEditable(true);
			textArea.append(customer.getNotes());
			textArea.setBorder(new TitledBorder(customer.getFullName() + " ( " + customer.getLastModified() + " )"));
			textArea.setBackground(Color.white);
			textArea.setCaretPosition(0);
			scroll.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			
			textFieldsPanel.add(scroll);
			
		}
		
		/*Set a scroll pane*/
		
		
		JScrollPane scrollPane = new JScrollPane(textFieldsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
													ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        
        
        /* Creating search bar for the storage table. By writing the name of a drug in the
	     * search bar the user could find if a drug exists in the storage table. */
	    
	    JLabel searchLabel = new JLabel("Search...");
	    JTextField searchTextField = new JTextField();
	    
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	searchTextField.requestFocus();
	        }
	      });
	    
	    searchTextField.setPreferredSize(new Dimension(200,24));
	    
	    /* Creating listener for the search bar */
	    
	    searchTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
            	
                String filter = searchTextField.getText().toLowerCase();  
                int count = 0;
				ArrayList<Integer> indexes = new ArrayList<Integer>(CentralRegistry.getAllListOfCustomers().size());
				
				if(filter.length() != 0)  {

					for(int i=0;i<CentralRegistry.getAllListOfCustomers().size();i++) {
						
						Customer customer = CentralRegistry.getAllListOfCustomers().get(i);

						if(customer.getFullName().toLowerCase().contains(filter))
							indexes.add(i);
						else
							indexes.add(-1);
						
					}
					
					for(Component component : textFieldsPanel.getComponents()){           	    	
            			
	            	    if(component instanceof JScrollPane){
	            	    	
	            	    	if(count==indexes.get(count)) {
	            	    		
	            	    		JTextArea textAreaComponent = (JTextArea) ((JScrollPane) component).getViewport().getView();

	                       	 
	                	    	textAreaComponent.setBackground(Color.yellow);
	                	    	}
	            	    	else{
	            	    		
	            	    		JTextArea textAreaComponent = (JTextArea) ((JScrollPane) component).getViewport().getView();

	                          	 
	                	    	textAreaComponent.setBackground(Color.white);
	                	    	}
	            	    	}
	            	    count++;
	            	    }
					}
				else{
                for(Component component : textFieldsPanel.getComponents()){           	    	
        			
            	    if(component instanceof JScrollPane) {
            	    	
            	    	JTextArea textAreaComponent = (JTextArea) ((JScrollPane) component).getViewport().getView();

            	    	textAreaComponent.setBackground(Color.white);
            	    	}
            	    }
                mainPanel.revalidate();
                }
            }
        });

        /*Code in order to edit the panels*/
	    
	    searchPanel.add(searchLabel);
	    searchPanel.add(searchTextField);
	    
	    searchPanel.setBackground(Color.gray);
	    searchPanel.setBorder(
	            BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        textFieldsPanel.setBounds(0, 0, 100, 100);
        textFieldsPanel.setPreferredSize(new Dimension(numberOfCustomers * 10, numberOfCustomers * 150));
        
        buttonsPanel.setBackground(Color.gray);
        
		buttonsPanel.add(saveButton);
		buttonsPanel.add(returnButton);
		
		buttonsPanel.setBorder(
	            BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		barPanel.add(searchPanel);
		barPanel.add(buttonsPanel);
		
		barPanel.setBorder(
	            BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		barPanel.setBackground(Color.gray);
		
		mainPanel.add(scrollPane);
		mainPanel.add(barPanel,BorderLayout.SOUTH);
		
		
		mainPanel.setBackground(Color.gray);
		
		mainPanel.setBorder(
	            BorderFactory.createEmptyBorder(10, 10, 10, 10));


		this.setContentPane(mainPanel);

		
		// Set frame in the center of the screen. */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 4;
		int y = (screenSize.height - this.getHeight()) / 10;
		this.setLocation(x, y);

		
		this.setSize(800,700);
		this.setTitle("Notebook");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
