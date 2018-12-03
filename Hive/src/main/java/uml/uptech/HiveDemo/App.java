package uml.uptech.HiveDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Hello world!
 *
 */
public class App 
{
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";  //org.apache.hive.jdbc.HiveDriver
	
	private static String hive_url = "jdbc:hive2://192.168.11.53:10000/default";
	
	private static String hive_username = "hive";
	
	private static String hive_pwd = "hive";
	
    public static void main( String[] args )
    {
          try {  
              Class.forName(driverName);  
              Connection con = null;  
              con = DriverManager.getConnection(hive_url, hive_username, hive_pwd);  
              Statement stmt = con.createStatement();  
              ResultSet res = null;  
              String sql = "select * from student";  
              System.out.println("Running: " + sql);  
              res = stmt.executeQuery(sql);  
              System.out.println("ok");  
              while (res.next()) {  
                  System.out.println(res.getString(1));  
              }  
              
              con.close();
          } catch (Exception e) {  
              e.printStackTrace();  
              System.out.println("error");  
          }  
    }
}
