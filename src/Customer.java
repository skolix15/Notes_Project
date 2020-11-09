import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Customer implements Comparable< Customer >{
	
	private String firstName;
	private String lastName;
	private String notes;
	private String lastModified;
	
	public Customer(String aFirstName, String aLastName, String someNotes,String lastModified) {
		
		this.firstName = aFirstName;
		this.lastName = aLastName;
		this.notes = someNotes;	
		this.lastModified = lastModified;
	}
	/*Getters and Setters */
	
	public String getFirstName() {
		
		return firstName;
	}
	
	public String getSecondName() {
		
		return lastName;
	}
	

	public String getFullName() {
		
		return (lastName + " " + firstName);
	}
	
	public void setNotes(String someNotes) {
		
		notes = someNotes;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		lastModified = formatter.format(date);
	}
	
	public String getLastModified() {
		
		return lastModified;
	}
	
	
	public String getNotes() {
		
		return notes;
	}
	
	/* Getter for Customer's Information*/
	
	public String getAllInformationForCustomer() {
		
		String text = "";
		
		text += (lastName + " " + firstName) + "\n";
		text += notes;
		
		return text;
		
	}
	
	/*Function that saves a customer in a .txt file which is used like a base*/
	
	public void writeCustomerInAFile() {
		
		try
		{
			String directoryPath = "C:\\Users\\UsersName\\Documents\\Base\\";
		    String textToBeWritten = "";
		    String fileName = (lastName + " " + firstName ) + ".txt";
		    String fullPath = directoryPath + fileName;

		    textToBeWritten = notes;
		         
		    FileWriter fw = new FileWriter(fullPath,false);   //the true will append the new data
		    fw.write(textToBeWritten);						  //appends the string to the file
		    fw.close();
		    
		}
		
		catch(IOException ioe)
		
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	/*Function that overwrites tha usual "compareTo" function*/
	
	public int compareTo(Customer otherCustomer) {
		
        return this.getFullName().compareTo(otherCustomer.getFullName());
       
    }
	
}
