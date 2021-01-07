package book_manager;

/*
	[사용자 모드]
	-	회원 정보 수정
	-	회원 탈퇴
	-	도서 검색 
	-	도서 예약 (예약 신청일로부터 14일)
	-	도서 반납일 확인
	-	연체 비용 확인(연체후부터 1일*500원)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserMenu extends JFrame implements ActionListener{
	
	private JButton jbtn1, jbtn2,jbtn3, jbtn4,jbtn5;
	private String unum = "0";
	
	
	public UserMenu() {
		// TODO Auto-generated constructor stub

		super("User Menu");
		setBounds(1000, 300, 370, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		jbtn1 = new JButton("도서 리스트");		
		jbtn2 = new JButton("도서 검색 및 예약");		
		jbtn3 = new JButton("도서 반납일 및 연체 비용 확인");	
		jbtn4 = new JButton("회원 탈퇴");		
		jbtn5 = new JButton("날짜 변경");		
		
		jbtn1.setBounds(50,50,250,50);
		jbtn2.setBounds(50,125,250,50);
		jbtn3.setBounds(50,200,250,50);
		jbtn4.setBounds(50,275,250,50);
		jbtn5.setBounds(50,400,250,50);
		
		add(jbtn1); add(jbtn2); add(jbtn3); add(jbtn4); add(jbtn5); 
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);
		jbtn3.addActionListener(this);
		jbtn4.addActionListener(this);
		jbtn5.addActionListener(this);
		
		
		setVisible(true);
		
		this.unum = Login.getUnum();	//회원 번호 받기
		
	}

	public static void main(String[] args) {
		new UserMenu();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj==jbtn1) {	//도서 검색
			new UserBookList();
		}else if(obj==jbtn2) {	//도서 예약
			new UserSearch();
		}else if(obj==jbtn3) {	//도서 반납일 확인 및 연체 비용 확인
			new UserCheckReturn();
		}else if(obj==jbtn4) {	//회원 탈퇴
			usersOut(unum);
			bookingOut(unum);
			dispose();
			new goodBye();
		}
		
		
		else if(obj==jbtn5) {		//  !!!이건 테스트용 버튼입니다. 날짜를 30일정도 전으로 돌려줍니다.!!!
			changeBook(unum);
			
		}
		
		
	}
	public void changeBook(String unum) {
		Connection conn = MakeConnection.getConnection();	//db 연결
		StringBuffer sb = new StringBuffer();
		sb.append("update booking ");
		sb.append("set booked = '20/11/23' ");
		sb.append("where unum = ? ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, unum);
			
			rs = pstmt.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//try~catch end
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
	public void usersOut(String unum) {			//탈퇴관련 users 테이블 정보 삭제
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from users ");
		sb.append("where unum = ? ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, unum);
			
			rs = pstmt.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//try~catch end
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	public void bookingOut(String unum) {		//탈퇴관련 booking 테이블 정보 삭제
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from booking ");
		sb.append("where unum = ? ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, unum);
			
			rs = pstmt.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//try~catch end
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
}

class goodBye extends JFrame{
	private JLabel jl;
	
	public goodBye(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,400,200,80);
		setLayout(null);
		
		jl = new JLabel("  정보를 삭제하였습니다.");
		jl.setBounds(0, 0, 200, 50);
		add(jl);
		setVisible(true);
	}
}
