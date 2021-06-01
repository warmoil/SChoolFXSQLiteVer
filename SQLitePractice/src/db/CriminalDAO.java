package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CriminalDAO extends DAOBase{
	
	
	public CriminalDAO() {
		
	}
	
	public int getCrimNum(String crimId) {
		String sql = "select cNum from criminal where cId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				return rs.getInt(1); 
			}
			System.out.println("조회내용 없음 ");
			return -1; 
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			  if (conn != null) {
				    try {
				      conn.close(); 
				    } catch (Exception e) {
				     
				    }
				  }
				}
		return -1;
	}
	public void insertCriminal(String crimId) {
		String sql = "insert into  criminal  values(?, 1)";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			int insertNum = pstmt.executeUpdate(); 
			if(insertNum >0) {
				System.out.println("criminal에 "+crimId+":입력 성공");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			  if (conn != null) {
				    try {
				      conn.close(); 
				    } catch (Exception e) {
				     
				    }
				  }
				}
	}
	public void setCrimNum(String crimId) {
		String sql = "select cId from report where cId =?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			rs = pstmt.executeQuery(); 
			
			int rowCount = 0;
			while(rs.next()) {
				rowCount++;
			}
			System.out.println(rowCount+"로우count");
				
					sql = "update criminal set cNum =? where cId =?";
					try {
						pstmt = conn.prepareStatement(sql); 
						pstmt.setInt(1, rowCount);
						pstmt.setString(2, crimId);
						int count = pstmt.executeUpdate(); 
						System.out.println(count+":카운트");
					}catch(Exception e2) {
						e2.printStackTrace();
					}	finally {
						  if (conn != null) {
							    try {
							      conn.close(); 
							    } catch (Exception e) {
							     
							    }
							  }
							}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			  if (conn != null) {
				    try {
				      conn.close(); 
				    } catch (Exception e) {
				     
				    }
				  }
				}
		
	}
	
}
