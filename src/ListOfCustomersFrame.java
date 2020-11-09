import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ListOfCustomersFrame extends JFrame{
	
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private JPanel scrollPanel;
	private JButton addCustomerInTheListButton;
	private JButton returnButton;
	private JPopupMenu popupMenu;
	
	public ListOfCustomersFrame() {
		
		mainPanel = new JPanel(new BorderLayout());
		
		buttonsPanel = new JPanel();
		
		scrollPanel = new JPanel();
		
		addCustomerInTheListButton = new JButton("Add Customer");
		returnButton = new JButton("Return in previous Page");
		popupMenu = new JPopupMenu();
		
		/*With right click a list will appear with one only choice "delete" in order to delete a 
		 *customer if the user wants to*/
		
		JMenuItem iDelete = new JMenuItem("Delete");
		
		popupMenu.add(iDelete);
		
		/*Listener for the "addCustomerInListButton" button. If it is clicked, "AddCustomerFrame" frame
		 *will be opened and the previous frame will be closed.*/
		
		addCustomerInTheListButton.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	addCustomerInTheListButton.doClick();
	            }
	        }

	    });
		
		addCustomerInTheListButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				new AddCustomerFrame();
				
				}
			});
		
		/*Listener for the "returnButton" button. If it is clicked the current frame will close and the 
		 *Main frame will be opened*/
		
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
		
		/*Enter a scroll Pane in the customer's list
		 *Create first a model, insert inside the model the names of the customers 
		 *and then add the model in the scroll pane*/
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(int i=0;i<CentralRegistry.getAllListOfCustomers().size();i++) 
			model.addElement(CentralRegistry.getAllListOfCustomers().get(i).getFullName());
		   
		JList<String> list = new JList<String>(model);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		
		/*Mouse listener for the list*/
		
		list.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent e) {
	            if (SwingUtilities.isRightMouseButton(e)     // if right mouse button clicked
	                  && !list.isSelectionEmpty()            // and list selection is not empty
	                  && list.locationToIndex(e.getPoint())  // and clicked point is
	                     == list.getSelectedIndex()) 
	            	
	            {       //   inside selected item bounds
	               popupMenu.show(list, e.getX(), e.getY());
	            }
	         }
	      });
		
		/*listener for the delete button in the popmenu
		 *If it is clicked the customer will be deleted from the list with all the customers in the central registry
		 *and the base will be informed*/
		
		iDelete.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				String fullNameOfCustomer = list.getSelectedValue();
				
				CentralRegistry.deleteCustomer(fullNameOfCustomer);
				
				dispose();
				
				new ListOfCustomersFrame();
			}
			
		});
	  

		buttonsPanel.add(addCustomerInTheListButton);
		buttonsPanel.add(returnButton);
		
		
		scrollPanel.add(scrollPane);
		
		mainPanel.add(buttonsPanel,BorderLayout.NORTH);
		
		mainPanel.add(scrollPanel,BorderLayout.CENTER);
		
		/*Set color to the background of the frame*/
		
		scrollPanel.setBackground(Color.gray);
		buttonsPanel.setBackground(Color.gray);
		
		this.setContentPane(mainPanel);
		this.setResizable(false);
		
		// Set frame in the center of the screen. */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		this.setSize(400,300);
		this.setTitle("List Of All Customers");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
