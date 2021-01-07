package book_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CheckBanUser extends JFrame {
	Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();	//현재시간 비교용
	
	JPanel panel;
	//JTable
	Object ob[][] = new Object[0][3];
	DefaultTableModel model;
	JTable table;
	JScrollPane js;
	String str[] = {"회원명", "전화번호", "연체 일 수"};//컬럼명
	
	int overDue=0;
	
	public CheckBanUser() {
		// TODO Auto-generated constructor stub
		
		super("Overdue Users");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		
		
		//JTable 가운데 배치
		model = new DefaultTableModel(ob, str);
		table =  new JTable(model);
		js = new JScrollPane(table);
		this.add("Center", js);
		setBounds(250,250,300,300);
		

		Connection conn = MakeConnection.getConnection();	//db 연결
		StringBuffer sb = new StringBuffer();
		sb.append("select b1.uname, b1.phone, b2.booked  ");
		sb.append("from users b1 join booking b2 ");
		sb.append("on  b1.unum = b2.unum ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String uname = rs.getString(1);		//도서명 받기
				String phone = rs.getString(2);		//저자명 받기
				String booked = rs.getString(3);	//대여 일자 받기
				
				String bookedDate = getBookedDate(booked);	//반환일 구하기
				overDue = getOverDue(bookedDate);	//남은 일 수
				
				if(overDue>0) {
					Object [] bookData = {uname, phone, String.valueOf(overDue)};
					model.addRow(bookData);
				}
			}
		}catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}//try~catch end
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch (SQLException e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new CheckBanUser();
	}
	
	
	
	private String getBookedDate(String booked) {	//반환일 구하는 함수
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date to = null;
		try {
			to = fm.parse(booked);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		cal1.setTime(to);
		cal1.add(Calendar.DATE, 14);
		
		String bookedDate = fm.format(cal1.getTime());
		return bookedDate;
	}
	
	private int getOverDue(String bookedDate) {	//반환일 남은 일 수 구하는 함수
		String overDue;
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date reqDate = null;
		try {
			reqDate = fm.parse(bookedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long reqDateTime = reqDate.getTime();
		
		Date curDate = cal2.getTime();
		long curDateTime=curDate.getTime();
		
		long min = (curDateTime - reqDateTime) / 60000;
		
		long day = min/60/24;
		
//		cal2.setTime(to);
//		cal2.add(Calendar.DATE, 14);
		
		//String bookedDate = fm.format(cal2.getTime());
		return (int)day;
	}
}
