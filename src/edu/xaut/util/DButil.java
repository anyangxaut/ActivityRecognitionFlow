package edu.xaut.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 与数据库建立连接
 * @author anyang
 *
 */
public class DButil {

		//连接数据库
		
    public Connection openConnection() {
			
			Properties prop = new Properties();//Properties类的作用是利用文件（后缀名为properties）存储一组键值对
			String driver = null;
			String url = null;
			String username = null;
			String password = null;

			try {
				
				prop.load(this.getClass().getClassLoader().getResourceAsStream(
						"DBConfig.properties"));

				driver = prop.getProperty("driver");
				url = prop.getProperty("url");
				username = prop.getProperty("username");
				password = prop.getProperty("password");
				
				Class.forName(driver);
				
				return DriverManager.getConnection(url, username, password);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}

			return null;
		}
    
    //在本地数据库查询城市代码
    public Connection openConnectionlocal() {
		
		Properties prop = new Properties();//Properties类的作用是利用文件（后缀名为properties）存储一组键值对
		String driver = null;
		String url = null;
		String username = null;
		String password = null;

		try {
			
			prop.load(this.getClass().getClassLoader().getResourceAsStream(
					"DBConfig.properties"));

			driver = prop.getProperty("driverlocal");
			url = prop.getProperty("urllocal");
			username = prop.getProperty("usernamelocal");
			password = prop.getProperty("passwordlocal");
			
			Class.forName(driver);
			
			return DriverManager.getConnection(url, username, password);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return null;
	}

    
    //在本地数据库查询气调库信息
    public Connection openConnectionqtk() {
		
		Properties prop = new Properties();//Properties类的作用是利用文件（后缀名为properties）存储一组键值对
		String driver = null;
		String url = null;
		String username = null;
		String password = null;

		try {
			
			prop.load(this.getClass().getClassLoader().getResourceAsStream(
					"DBConfig.properties"));

			driver = prop.getProperty("driverqtk");
			url = prop.getProperty("urlqtk");
			username = prop.getProperty("usernameqtk");
			password = prop.getProperty("passwordqtk");
			
			Class.forName(driver);
			
			return DriverManager.getConnection(url, username, password);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return null;
	}

}

	

