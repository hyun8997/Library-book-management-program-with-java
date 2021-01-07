package book_manager;

//도서 검색 및 예약 기능임.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UserSearch extends JFrame implements ActionListener {
	
	private JTextField jtf;
	private JButton jbtn1, jbtn2, jbtn3;
	private JLabel jl;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox2;
	private String unum = "0";
	private int check = 1;	//1은 예약되어있는것, 0은 안된것
	
	public UserSearch() {
		// TODO Auto-generated constructor stub
		super("도서 검색 및 예약");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,870,205);
		setLayout(null);
		
		jl = new JLabel("도서명 및 저자명으로 검색");
		jl.setBounds(30,0, 380,80);
		
		jtf = new JTextField(20);
		jtf.setBounds(30, 50, 350, 40);
		
		jbtn1 = new JButton("도서명 검색");
		jbtn1.setBounds(30, 100, 150, 40);
		
		jbtn2 = new JButton("저자명 검색");
		jbtn2.setBounds(230, 100, 150, 40);
		
		jbtn3 = new JButton("도서 예약");
		jbtn3.setBounds(720, 50, 100, 40);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(400, 50, 300, 40);
		
		comboBox2 = new JComboBox<String>();
		comboBox2.setBounds(400, 90, 300, 40);
		
		add(comboBox); add(comboBox2);
		add(jbtn3);
		
		add(jl); add(jtf); add(jbtn1); add(jbtn2);
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);
		jbtn3.addActionListener(this);
		
		setVisible(true);
		
		this.unum = Login.getUnum();	//회원 번호 받기
	}
	
	public static void main(String[] args) {
		new UserSearch();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj==jbtn1) {	//도서명 검색
			String data = jtf.getText();
			
			Connection conn = MakeConnection.getConnection();	//db 연결
			
			StringBuffer sb = new StringBuffer();
			sb.append("select bname,author from books ");
			sb.append("where bname = ? ");
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			comboBox.removeAllItems();	//검색전 콤보박스 초기화
			comboBox2.removeAllItems();	//검색전 콤보박스 초기화
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setNString(1, data);
				rs = pstmt.executeQuery();
				
				
				while(rs.next()) {
					String bname = rs.getString(1);		//도서명 받기
					String author = rs.getString(2);	//저자명 받기
					if(bname.equals(data)) {
						comboBox.addItem(bname);
						comboBox2.addItem(author);
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
		}//도서명 검색 end
		
		
		else if(obj==jbtn2) {	//저자명 검색
			String data = jtf.getText();
			
			Connection conn = MakeConnection.getConnection();	//db 연결
			
			StringBuffer sb = new StringBuffer();
			sb.append("select bname, author from books ");
			sb.append("where author = ? ");
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			comboBox.removeAllItems();	//검색전 콤보박스 초기화
			comboBox2.removeAllItems();	//검색전 콤보박스 초기화
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setNString(1, data);
				rs = pstmt.executeQuery();

				while(rs.next()) {
					String bname = rs.getString(1);		//도서명 받기
					String author = rs.getString(2);	//저자명 받기
					if(author.equals(data)) {
						comboBox.addItem(bname);
						comboBox2.addItem(author);
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
			
		}//저자명 검색 end

		else if(obj==jbtn3) {	//도서 예약 기능
			String bnum = "0";
			String bnameBook = comboBox.getSelectedItem().toString();
			String authorBook = comboBox2.getSelectedItem().toString();
			
			
			bnum = findBook(bnameBook,authorBook);
			
			check = checkBook(bnum);
			
			if(check == 0) {
				bookBook(bnum, unum);
				
				new okBook();
			}else if(check == 1) {
				new noBook();
			}
			
		}//도서 예약 end
		
		
		
	}
	
	private String findBook(String bname, String author) {
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		String bnum = "0";
		StringBuffer sb2 = new StringBuffer();	//책 번호 찾는 sql문
		sb2.append("select bnum, bname, author from books ");
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		try {
			pstmt2 = conn.prepareStatement(sb2.toString());
			
			rs2 = pstmt2.executeQuery();
			
			while(rs2.next()) {
				String bnumFound = rs2.getString(1);
				String bnameFound = rs2.getString(2);
				String authorFound = rs2.getString(3);
				
				if(bname.equals(bnameFound) && author.equals(authorFound)) {
					bnum = bnumFound;
				}
				
				
			}
		}catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}//try~catch end
		finally {
			try {
				rs2.close();
				pstmt2.close();
				conn.close();
			}catch (SQLException e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}
		
		
		return bnum;
	}
	
	private void bookBook(String bnum, String unum) {
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();	//예약관리 테이블 insert
		sb.append("insert into booking ");
		sb.append("values (?, ?, sysdate) ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());	//오늘자로 도서 예약
			pstmt.setNString(1, bnum);
			pstmt.setNString(2, unum);
			rs = pstmt.executeQuery();

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
		
		
		
	}
	
	private int checkBook(String bnum) {
		int check=1;
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();	//예약관리 테이블 insert
		sb.append("select count(*) from booking ");
		sb.append("where bnum = ? ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());	//오늘자로 도서 예약
			pstmt.setNString(1, bnum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String checkEx = rs.getString(1);
				
				if(checkEx.equals("0") ) {
					check=0;
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
		
		
		return check;
	}

	
}

class okBook extends JFrame{
	private JLabel jl;
	
	public okBook(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,400,200,80);
		setLayout(null);
		
		jl = new JLabel("  도서가 예약되었습니다.");
		jl.setBounds(0, 0, 200, 50);
		add(jl);
		setVisible(true);
	}
}
class noBook extends JFrame{
	private JLabel jl;
	
	public noBook(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,400,200,80);
		setLayout(null);
		
		jl = new JLabel("  도서가 이미 예약되어있습니다.");
		jl.setBounds(0, 0, 200, 50);
		add(jl);
		setVisible(true);
	}
}

