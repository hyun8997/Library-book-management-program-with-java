package book_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UserCheckReturn extends JFrame {
	private JTextField jtf1,jtf2,jtf3;
	private JLabel jl1,jl2,jl3;
	private String unum = "0";
	Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();	//현재시간 비교용
	
	int overDue=0;
	
	
	
	public UserCheckReturn() {
		// TODO Auto-generated constructor stub
		super("도서 반납일 및 연체료 확인");
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,430,300);
		setLayout(null);
		
		this.unum = Login.getUnum();	//회원 번호 받기
		
		jl1 = new JLabel("빌린 도서");
		jl1.setBounds(30,0, 100,40);
		
		jl2 = new JLabel("반환일");
		jl2.setBounds(30,80, 100,40);
		
		jl3 = new JLabel("연체료");
		jl3.setBounds(30,160, 100,40);
		

		Connection conn = MakeConnection.getConnection();	//db 연결
		StringBuffer sb = new StringBuffer();
//		sb.append("select bname,author,bdate from books ");
//		sb.append("where bnum = (select bnum from booking ");
//		sb.append("				where unum = ? ) ");
		
		sb.append("select b1.bname, b1.author, b2.booked  ");
		sb.append("from books b1 join booking b2 ");
		sb.append("on  b1.bnum = b2.bnum ");
		sb.append("where b2.unum = ? ");
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(unum==null) {
			unum="1";
		}
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, unum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String bname = rs.getString(1);		//도서명 받기
				String author = rs.getString(2);	//저자명 받기
				String booked = rs.getString(3);	//대여 일자 받기

				jtf1 = new JTextField(bname.concat(" by ").concat(author));
				jtf1.setBounds(30, 40, 350, 40);
				
				String bookedDate = getBookedDate(booked);	//반환일 구하기
				
				jtf2 = new JTextField(bookedDate);
				jtf2.setBounds(30, 120, 350, 40);
		
				overDue = getOverDue(bookedDate);	//남은 일 수
				String fine = "0";		//연체료
				
				if(overDue>=7) {
					int gap = (overDue - 7)*500;
					fine = String.valueOf(gap);
				}else {
					fine = "0";
				}
				
				jtf3 = new JTextField(fine);
				jtf3.setBounds(30, 200, 350, 40);
				
				add(jtf1);add(jtf2);add(jtf3);
				
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
		
		add(jl1); 
		add(jl2); 
		add(jl3); 
		
		
		
		setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) {
		new UserCheckReturn();
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

