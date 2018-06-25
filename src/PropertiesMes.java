import java.io.IOException;
import java.util.Properties;

/**
 * ���������������������Ϣ
 *
 */
public class PropertiesMes {
	
	static Properties prop = new Properties();
		static {
		    try {
				prop.load(PropertiesMes.class.getClassLoader().getResourceAsStream("config/tanks.properties"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	 private PropertiesMes() {};
		
	 public static String getProperties(String key) {	   
		return prop.getProperty(key);
		
	}

}
