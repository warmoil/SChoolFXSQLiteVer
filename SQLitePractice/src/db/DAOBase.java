package db;

import java.sql.Connection;
import java.sql.DriverManager;
import org.sqlite.SQLiteConfig;
public class DAOBase {
	public  Connection conn; 
	public DAOBase() {
	
		try {
			Class.forName("org.sqlite.JDBC");
			String currentDir = System.getProperty("user.dir");
			System.out.println(currentDir);
			conn = DriverManager.getConnection("jdbc:sqlite:"+currentDir+"\\thecheat.db");  //만약 프로젝트로 만들거면 thecheat.db도 같은 폴더에 넣고 시작 빌드시엔 이코드
			//conn = DriverManager.getConnection("jdbc:sqlite:"+currentDir+"\\lib\\thecheat.db");
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
