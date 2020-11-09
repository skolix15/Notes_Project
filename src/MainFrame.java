import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

	private JPanel mainPanel;
	private JButton seeAllListOfCustomersButton;
	private JButton seeAllNotesButton;
	private JButton changePasswordButton;
	private JButton logOutButton;
	private JPanel buttonsPanel;
	private JPanel labelPanel;
	
	private JLabel welcomeLabel;
	
	
	public MainFrame() {
		
		mainPanel = new JPanel(new BorderLayout());
		buttonsPanel = new JPanel();
		labelPanel = new JPanel();
		
		seeAllListOfCustomersButton = new JButton("Check the list of all Customers");
		seeAllNotesButton = new JButton("Check your notes");
		changePasswordButton = new JButton("Change password");
		logOutButton = new JButton("Log out");
		
		/*Key listener for "changePasswordButton" button.
		 * When the enter is clicked, "changePasswordButton" button will be activated.
		 */
		
		   changePasswordButton.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	changePasswordButton.doClick();
	            }
	        }

	    });
		   
		/*Action listener for the button that is able to change the password.*/
		
		changePasswordButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				String inputPassword = JOptionPane.showInputDialog(null,"Enter new password: ");
				
				if(inputPassword == null) {
					// do nothing
				}
				else if(inputPassword.equals(""))
	    			  JOptionPane.showMessageDialog(null,"You dind't write new password.","Error..",JOptionPane.ERROR_MESSAGE);
				else {
					JOptionPane.showMessageDialog(null,"Your password changed successfully","Perfect",JOptionPane.PLAIN_MESSAGE);
					IdentityFrame.password = inputPassword;
					
					try
					{
						String directoryPath = "C://Password";
					    String fileName = "//Password.txt";
					    String fullPath = directoryPath + fileName;
					         
					    FileWriter fw = new FileWriter(fullPath,false);   //the true will append the new data
					    fw.write(IdentityFrame.password);						  //appends the string to the file
					    fw.close();
					    
					}
					
					catch(IOException ioe)
					
					{
					    System.err.println("IOException: " + ioe.getMessage());
					}
					
					dispose();
					new IdentityFrame();
					}
			}
			
		});
		
		/*Action listener for log out button*/
		
		logOutButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				new IdentityFrame();
				dispose();
				
			}
			
		});
		
		/*Key listener for "logOutButton" button.
		 * When the enter is clicked, "logOutButton" button will be activated.
		 */
		
		logOutButton.addKeyListener(new KeyAdapter() {
		        
		        public void keyPressed(KeyEvent e) {
		            if(e.getKeyCode() == KeyEvent.VK_ENTER){
		            	logOutButton.doClick();
		            }
		        }

		    });
		

		/*Key listener for "seeAllListOfCustomersButton" button.
		 * When the enter is clicked, "seeAllListOfCustomersButton" button will be activated.
		 */
		
		seeAllListOfCustomersButton.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	seeAllListOfCustomersButton.doClick();
	            }
	        }

	    });
		
		/*Action listener for "seeAllListOfCustomersButton" button*/
		
		seeAllListOfCustomersButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				new ListOfCustomersFrame();
				}
			});
		
		/*Key listener for "seeAllNotesButton" button.
		 * When the enter is clicked, "seeAllNotesButton" button will be activated.
		 */
		
		seeAllNotesButton.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	seeAllNotesButton.doClick();
	            }
	        }

	    });
		
		/*Action listener for "seeAllNotesButton" button*/
		
		seeAllNotesButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				new NotesFrame();
				}
			});
		

		
		welcomeLabel = new JLabel();
		
		welcomeLabel.setText("WELCOME TO YOUR NOTEBOOK");
		
		labelPanel.add(welcomeLabel);
		
		buttonsPanel.add(seeAllListOfCustomersButton);
		
		buttonsPanel.add(seeAllNotesButton);
		
		buttonsPanel.add(changePasswordButton);
		
		buttonsPanel.add(logOutButton);
		
		labelPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		mainPanel.add(labelPanel,BorderLayout.NORTH);
		mainPanel.add(buttonsPanel,BorderLayout.CENTER);
		
		labelPanel.setBackground(Color.gray);
		buttonsPanel.setBackground(Color.gray);
		
		this.setContentPane(mainPanel);
		this.setResizable(false);
		
		// Set frame in the center of the screen. */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		this.setSize(400,300);
		this.setTitle("Main Page");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
