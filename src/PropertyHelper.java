import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyHelper extends Properties{
	private static PropertyHelper instance = null;
	
	private PropertyHelper() {	
	}
	public static PropertyHelper getInstance() {
		if (instance == null) {
			try {
				instance = new PropertyHelper();
							
				FileInputStream stream =new FileInputStream("C:\\th_workspace\\BitcoinReader\\src\\import.properties");
				
				instance.load(stream);
					
				stream.close(); 
			}
									
			    catch (FileNotFoundException a) {
					a.printStackTrace();
				}
			    catch (IOException e) {
				e.printStackTrace();
				return null;
			    }					
		}
		return instance;
	}
	
}
