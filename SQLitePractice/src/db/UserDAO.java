package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO extends DAOBase{

	//private User user = new User();
	public UserDAO() {
	
	}
	
	public int login(String userId, String userPw) {
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		String sql = "select userPw from user where userID = ?";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				if(rs.getString(1).equals(userPw)) {
					return 1; 
				}else
					return 0; 
			}
			return -1; 
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return -2; 
	}
	
	public int join(User user) {
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		  String sql = "insert into user values(?, ?, ?, ?, ?)";
		  try {
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, user.getUserId());
		    pstmt.setString(2, user.getUserPw());
		    pstmt.setString(3, user.getUserNickName());
		    pstmt.setString(4, user.getUserAsk());
		    pstmt.setString(5, user.getUserAnswer());
		    return pstmt.executeUpdate();
		  }catch (Exception e) {
		 	e.printStackTrace();
		  }finally {
			     if ( rs != null ) try{rs.close();}catch(Exception e){}
			     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
			     if ( conn != null ) try{conn.close();}catch(Exception e){}

			 }
		  return -1;
		}
	
	public String getUserNickName(String userId) {
		String sql = "select nickname from user where userId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {		
				String nick = rs.getString("nickname");				
					return nick;
				}
			System.out.println("틀림");
			return null;
		}catch(Exception e) {
			System.out.println("실패");
			e.printStackTrace();
			return null;
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
	}

	public User getUserInfo(String userId) {
		String sql = "select * from user where userId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		User user = new User();
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {		
				System.out.println("rs.next()는 됐음");
				String nick = rs.getString("nickname");				
				String id = rs.getString("userId");
				String pw = rs.getString("userPw");
				String ask = rs.getString("ask");
				String answer = rs.getString("answer");
				user.setUserInfo(id, pw, nick, ask, answer);
				return user;
				}
			System.out.println("getUserInfo실패");
			
		}catch(Exception e) {
			System.out.println("getUserInfo실패");
			e.printStackTrace();
			
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return null;
		
	}
	
	public String getUserNickName(User user) {
		String sql = "select nickname from user where userId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		System.out.println("겟닉네임 메소드 실행");
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, user.getUserId()); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {		
				String nick = rs.getString("nickname");				
					return nick;
				}
			System.out.println("틀림");
			return null;
		}catch(Exception e) {
			System.out.println("getUserNickName실패");
			e.printStackTrace();
			return null;
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
	}
	public boolean isUserUpdate(User user) {
		String sql  = "update user set userPw = ? , nickname = ? , ask = ? , answer = ? where userId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserPw());
			pstmt.setString(2, user.getUserNickName());
			pstmt.setString(3, user.getUserAsk());
			pstmt.setString(4, user.getUserAnswer());
			pstmt.setString(5, user.getUserId());
			int rsNum = pstmt.executeUpdate();
			if(rsNum ==1) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return false;
	}
	public boolean isJoined(String  userId) {
		String sql = "select userId from user where userId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				if(rs.getString(1).equals(userId)) {
					return true; 
				}else
					return false; 
			}
			return false; 
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		
	}
	
	public UserData[] getAllUserData() {
		String sql = "select * from user";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int getRowNum = 0;
			while(rs.next()) {
				getRowNum++;
			}
			if(getRowNum>0) {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				UserData[] datas = new UserData[getRowNum];
				int i =0;
				while(rs.next()) {
					datas[i] = new UserData(rs.getString("userId"), rs.getString("userPw"), rs.getString("nickName"), rs.getString("ask"), rs.getString("answer") , false);
					i++;
				}
				return datas;
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return null;
	}
	public int deleteUser(String[] userId) {
		String sql = "delete from user where userId =?";
		String[] users = userId;
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		for(int i = 0 ; i<users.length; i++) {
			System.out.print(users[i]+",");
		}  //잘 작동하나? 
		if(users.length <=0) {
			return -1 ;
		}
		try {
			pstmt = conn.prepareStatement(sql); 
			if(users.length > 1) {
			for(int i=0; i <users.length; i++) {
				pstmt.setString(1, users[i]);
				pstmt.addBatch();
				}
			}else if(users.length == 1) {
				pstmt.setString(1, users[0]);
				int  result = pstmt.executeUpdate();
				return result;
			}
			pstmt.executeBatch();
			conn.commit();
			
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		
	
	}
}
