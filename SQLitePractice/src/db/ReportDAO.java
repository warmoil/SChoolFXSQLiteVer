package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO extends DAOBase{



	//private CriminalDAO cDao = new CriminalDAO();
	
	public ReportDAO() {
		
	}
	public String getContent(int index) {
		String sql = "select content from report where  num = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			String content = rs.getString("content");
			return content;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return null;
	}
	
	public ReportData[] getAllReportData() {
		
		String sql = "select * from report ";
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
				DAOBase base2 = new DAOBase();
				Connection conn2 = base2.conn;
				PreparedStatement pstmt2 = null; 
				ResultSet rs2 = null; 
				pstmt2 = conn2.prepareStatement(sql);
				rs2 = pstmt2.executeQuery();
				ReportData[] datas = new ReportData[getRowNum];
				int i=0;
				while(rs2.next()) {
					datas[i] = new ReportData(rs2.getString("reporter"),rs2.getString("cId"), rs2.getString("title"), rs2.getInt("num"),rs2.getString("reason"),false);
					i++;
				}
				return datas;
			}
		}catch(Exception e) {
			System.out.println("ReportData실패");
			e.printStackTrace();
		} finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return null;
	}
	
	public MyReportData[] getMyReportInfo(String userId) {
		String sql = "select * from report where reporter = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			int rowNum = 0;
			while(rs.next()) {
				rowNum++;
			}
			if(rowNum>0) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();
				MyReportData[] datas = new MyReportData[rowNum];
				int i=0;
				while(rs.next()) {
					datas[i] = new MyReportData(rs.getString("cId"), rs.getString("title"), rs.getInt("num"),rs.getString("reason"),false);
					i++;
				}
				return datas;
			}
		}catch(Exception e) {
			System.out.println("getReporterInfo실패");
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return null;
	}
	public Report[] getReportTitle(String crimId) {
		String sql = "select title,num from report where cId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, crimId);
			rs = pstmt.executeQuery();
			int getRowNum = 0;
			while(rs.next()) {
				getRowNum++;
			}
			if(getRowNum>0) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, crimId);
				rs = pstmt.executeQuery();
			Report[] titles = new Report[getRowNum];
			System.out.println(getRowNum);
			int i =0;
			Report report;
			while(rs.next()) {
				report = new Report();
				report.setReport(rs.getString("title"), rs.getInt("num"));
				titles[i] = report;
				i++;
			}
			return titles;
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
	public int getReportedNum(String cId) {
		String sql = "select title from report where cId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,cId);
			rs = pstmt.executeQuery();
			int reportedNum = 0;
			while(rs.next()) {
				reportedNum++;
			}
			return reportedNum;
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return 0;
	}
	//위아래는 완전히 같은 코드지만 위에는 신고당한 횟수 아래는 신고한 횟수임 
	//
	public int getReportingNum(String userId) {
		
		String sql = "select title from report where reporter = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userId);
			rs = pstmt.executeQuery();
			int rowCnt = 0;
			while(rs.next()) {
				rowCnt++;
			}
			
			return rowCnt;
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return 0;
	}
	public int deleteReporting(int[] cNum) {
		String sql = "delete from report where num =?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		int[] nums = cNum;
		for(int i = 0 ; i<nums.length; i++) {
			System.out.print(nums[i]+",");
		}
		if(nums.length <=0) {
			return -1 ;
		}
		try {
			pstmt = conn.prepareStatement(sql); 
			if(nums.length > 1) {
			for(int i=0; i <nums.length; i++) {
				pstmt.setInt(1, nums[i]);
				pstmt.addBatch();
				}
			}else if(nums.length == 1) {
				pstmt.setInt(1, nums[0]);
				int  result = pstmt.executeUpdate();
				return result;
			}
			pstmt.executeBatch();
			//conn.commit();
			
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
	public int doReporting(String crimId, String reporterId , String reason , String title) {
		String sql =  "insert into report values(?, ?, ?, ?,?)";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			System.out.println(crimId+"\n"+reporterId+"\n"+reason+"\n"+title);		
			pstmt.setString(1, crimId); 
			pstmt.setString(2, reporterId); 
			pstmt.setString(3, reason); 
			pstmt.setString(4, title); 
			pstmt.setString(5, null);
			pstmt.executeUpdate();
			int reported = isreported(crimId);
			if(reported == -1) {
				
				String sql2 = "insert into criminal values(?,?)";
				PreparedStatement pstmt2 = null; 
				DAOBase base2 = new DAOBase();
				Connection conn2 = base2.conn;
				try {
					System.out.println("실행?");		
					pstmt2 = conn2.prepareStatement(sql2);
					pstmt2.setString(1, crimId);
					pstmt2.setInt(2, 1);
					pstmt2.executeUpdate();
					 
				}catch(Exception e2) {
					System.out.println("isReported실패 ");
					e2.printStackTrace();
				}finally{
					 if ( pstmt2 !=null) try {pstmt2.close();}catch(Exception e) {}
				     if ( conn2 != null) try {conn2.close();} catch(Exception e) {}
				     if ( rs != null ) try{rs.close();}catch(Exception e){}
				     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
				     if ( conn != null ) try{conn.close();}catch(Exception e){}
				   
				 }
			}else {
				System.out.println("있던아이디이니까 추가할게 ");
			}
			base = new DAOBase();
			DAOBase base2 = new DAOBase();
			Connection conn2 = base2.conn;
			pstmt = null; 
			pstmt = conn2.prepareStatement(sql);
		    return 1; 
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		     if ( rs != null ) try{rs.close();}catch(Exception e){}
		     if ( pstmt != null ) try{pstmt.close();}catch(Exception e){}
		     if ( conn != null ) try{conn.close();}catch(Exception e){}

		 }
		return -1;
	}
	//이건 잘못 만든 코드 crim쪽으로 옮겨야함 
	public int isreported(String  cId) {
		String sql = "select cId from  criminal where cId = ?";
		DAOBase base = new DAOBase();
		Connection conn = base.conn;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, cId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				if(rs.getString(1).equals(cId)) {
					return 1; 
				}else
					return -1; 
			}
			return -1; 
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
