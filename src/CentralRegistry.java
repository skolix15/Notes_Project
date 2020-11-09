import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class CentralRegistry {
	
	private static ArrayList<Customer> customers = new ArrayList<Customer>();
	
	/*Taking the saved information from the base (.txt file) */
	
	public static void getInformationFromBase() {
		
		/*Opening the .txt file-base*/
		
		ArrayList<String[]> names = new ArrayList<String[]>();

		try {
			
			File file = new File("C:\\Users\\UsersName\\Documents\\Base\\All Customers.txt");

			BufferedReader br = new BufferedReader(new FileReader(file));

			String readLine = "";

			while ((readLine = br.readLine()) != null) {
				
				
				String textArray[] = readLine.split(" ");
				names.add(textArray);
				
				
			}
			
			br.close();
			
		} catch (IOException e) {
			
			System.err.println("IOException: " + e.getMessage());
		}
		
		/*Taking the information from the .txt file and edit them in order to create
		 *the customers. */
		
		for(int i=0;i<names.size();i++) {
			
			String nameOfCustomer = names.get(i)[0] + " " + names.get(i)[1];
			
			try {

				String directoryPath ="C:\\Users\\UsersName\\Documents\\Base\\";
				String fileName = nameOfCustomer + ".txt";
				String fullPath = directoryPath + fileName;
			
				File file = new File(fullPath);
			
				BufferedReader br = new BufferedReader(new FileReader(file));

				String readLine = "";
				String notesText = "";

				while ((readLine = br.readLine()) != null) {
					notesText += readLine;
					notesText += "\n";
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				
				String lastModified = sdf.format(file.lastModified());
					
				Customer customer = new Customer(names.get(i)[1],names.get(i)[0],notesText,lastModified);
				customers.add(customer);
				
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				}
			
		}
		
		/*Sort the list of customers according to the fuction "compareTo" that has been created in "Customer" class*/
		
		Collections.sort(customers);
		
	}
	
	/*Add a customer in the list of all customers and sort the list.
	 *The same time the code inform the base-.txt file with the new information*/

	public static void addCustomer(Customer customer) {
		
		
		customers.add(customer);
		Collections.sort(customers);
		customer.writeCustomerInAFile();
		CentralRegistry.informBase();
		
	}
	
	/*Delete a customer from the list of all customers and sort the list.
	 *The same time the code inform the base-.txt file with the new information*/
	
	public static void deleteCustomer(String fullNameOfCustomer) {

		for(int i=0;i<customers.size();i++) {
			
			if(customers.get(i).getFullName().equals(fullNameOfCustomer)) {
				customers.remove(i);
				
				String directoryPath = "C:\\Users\\UsersName\\Documents\\Base\\";
				String fileName = fullNameOfCustomer + ".txt";
				String fullPath = directoryPath + fileName;

				File file = new File(fullPath);
				file.delete();
				}
			
		}
		
		Collections.sort(customers);
		
		CentralRegistry.informBase();
		
	}
	
	/*Function that is searching a customer in the customers's list with the name of the customer*/
	
	public static Customer searchForCustomer(String fullNameOfCustomer) {
		
		Customer customer = null;
		
		for(int i=0;i<customers.size();i++) 
			if(customers.get(i).getFullName().equals(fullNameOfCustomer))
				customer =  customers.get(i);
			
		return customer;

	}
	
	/*Getter for the list of all customers*/
	
	public static ArrayList<Customer> getAllListOfCustomers(){
		
		return customers;
	}
	
	/*Function that inform base-.txt file with the new infomation.
	 *More specifically customers list is being written in the .txt file */
	
	public static void informBase() {
		
		try{
			String fileName = "C:\\Users\\UsersName\\Documents\\Base\\All Customers.txt";

		    String allCustomersNames = "";

		    for(int i=0;i<customers.size();i++) {
		    	
		    	allCustomersNames += customers.get(i).getFullName() + "\n";
		    	
		    	FileWriter fw = new FileWriter(fileName,false);   //the true will append the new data
		    	fw.write(allCustomersNames);					  //appends the string to the file
			    fw.close();
			    }
		    }
			
			catch(IOException ioe)
			
			{
			    System.err.println("IOException: " + ioe.getMessage());
			}
			
		}
	
	/*Function that prints the full names of all the customers that exist in the "customers" list*/

	public static void printAllCustomerList() {
		
		System.out.println(customers.size());
		for(int i=0;i<customers.size();i++) 
			System.out.println(customers.get(i).getFullName());
	}
	
}

