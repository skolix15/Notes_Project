import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IdentityFrame extends JFrame{
	
	private JPanel panel;
	private JButton logInButton;
	private JLabel titleOfPage;
	private JTextField textFieldForPassword;
	
	/*The password in order the user to get in the app*/
	public static String password;
	
	public IdentityFrame() {
		
		/*Reading the password from the base*/
		
		password = readAndReturnPasswordFromBase();
		
		panel = new JPanel();
		
		titleOfPage = new JLabel("Enter Password: ");
		textFieldForPassword = new JPasswordField(10);
		logInButton = new JButton("Log in");
		
		/*Listener for the text field of the password*/
		textFieldForPassword.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	logInButton.doClick();
	            }
	        }

	    });
		
		
		/*Listener for the log in button.
		 *Checking if the password is correct. If it is correct
		 *the Main Frame will open and the Identity frame will close. Otherwise, if it is not correct 
		 *a message will appear*/
		
		logInButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				String enteredPassword = textFieldForPassword.getText();
				
				if( enteredPassword.equals(password)) {
					dispose();
					new MainFrame();
				}
				
				else {
					JOptionPane.showMessageDialog(null,"Wrong Password","Error..",JOptionPane.ERROR_MESSAGE);
					textFieldForPassword.setText("");
				}
				
			}
	
		}	);
		
//		textFieldForPassword.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//		logInButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//		
		panel.add(titleOfPage);
		panel.add(textFieldForPassword);
		panel.add(logInButton);
		
		panel.setBorder(BorderFactory.createEmptyBorder(65,20,20,20));
		
		panel.setBackground(Color.gray);
		
		
		this.setContentPane(panel);
		this.setResizable(false);
		
		// Set frame in the center of the screen. */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		this.setSize(280,280);
		this.setTitle("Identity");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	/*A function that reaf from base the password and return it*/
	
	public String readAndReturnPasswordFromBase() {
		
		String password = "";
		
		try {
			
			File file = new File("C:\\Users\\UserName\\Documents\\Base\\Password\\Password.txt");

			BufferedReader br = new BufferedReader(new FileReader(file));
			
			password = br.readLine();
			
			br.close();
			
		} catch (IOException e) {
			
			System.err.println("IOException: " + e.getMessage());
		}
		
		return password;
	}

}
