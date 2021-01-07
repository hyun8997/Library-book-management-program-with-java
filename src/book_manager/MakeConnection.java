package book_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//생성 패턴, 구조 패턴, 행위 패턴 => 디자인 패턴

//그 중 생성패턴의 싱글턴 패턴 사용 : 한 클래스에 한 객체만 존재하도록 제한
//singleTone pattern
public class MakeConnection {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";	//final=>상수 처리
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "scott";
	private static final String PASSWORD = "tiger";
	
	static Connection conn = null;
	
	private static MakeConnection mc;
	
	public static MakeConnection getInstance() {
		if(mc == null) mc = new MakeConnection();
		return mc;
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	
}
