import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		/* Creating or opening  the .txt file which is used like a base for the customers*/
		
		File directory = new File("C:\\Users\\UsersName\\Documents\\Base");
		if (!directory.exists())
			directory.mkdir();
		
		

		File f = new File("C:\\Users\\UsersName\\Documents\\Base\\All Customers.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/* Creating or opening the .txt file which is used like a base for the password*/
		
		File directory2 = new File("C:\\Users\\UsersName\\Documents\\Base\\Password");
		if(!directory2.exists())
			directory2.mkdir();
		
		File f2 = new File("C:\\Users\\UsersName\\Documents\\Base\\Password\\Password.txt");
				if(!f.exists())
					try {
						f2.createNewFile();
					} catch(IOException e) {
						e.printStackTrace();
					}
		
		/*Getting all the information from tha base*/

		CentralRegistry.getInformationFromBase();
		
		/*Opening the first frame in order the user to enter the password*/
		
		new IdentityFrame();
	}
}


