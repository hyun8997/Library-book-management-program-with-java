package book_manager;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class AddBook extends JFrame{
	public AddBook(){
		super("도서 추가");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,1000,80);
		setLayout(new FlowLayout());
		
		//JPanel 호출
		AddBookPanel jp = new AddBookPanel();
		add(jp);
		
		
		setVisible(true);
	}//생성자 end
	
	public static void main(String[] args) {
		new AddBook();
	}
	
	
	
}

class AddBookPanel extends JPanel{
	private JButton jbtn;
	private JTextField jtf1;
	private JTextField jtf2;
	private JTextField jtf3;
	
	private JTextField msg;
	
	private JLabel jl1,jl2,jl3;
	private int check = 0; //중복 확인용
	
	public AddBookPanel(){
		jbtn = new JButton("추가");
		jbtn.setBounds(50,50,100,50);
		jtf1 =  new JTextField(20);
		jtf2 =  new JTextField(20);
		jtf3 =  new JTextField(20);
		
		jl1 = new JLabel("도서명");
		jl2 = new JLabel("저자명");
		jl3 = new JLabel("출간일자(yy/mm/dd)");
		
		msg =  new JTextField(20);
		
		add(jl1); add(jtf1);	
		add(jl2); add(jtf2);	
		add(jl3); add(jtf3);	
		add(jbtn);

		setVisible(true);
		
		jbtn.addActionListener(new ActionListener() {	//입력을 받겠다. 옵션 창을 띄워서
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				if(obj==jbtn) {

					String bname = jtf1.getText();
					String author = jtf2.getText();
					String bdate = jtf3.getText();
					
					check = checkDuplicate(bname, author);
					
					if(check == 0) {
						int result = JOptionPane.showConfirmDialog(null, "도서 추가 하시겠습니까?", "확인", JOptionPane.YES_NO_CANCEL_OPTION);	//
						
						if(result==0) {
							insertBooks(bname, author, bdate);
							//insertBooking(bname, author, bdate);		//예약관련은 따로하는게 나을것 같아서 주석처리함
							System.out.println("없던책");
						}else if(result==2) {
							System.exit(0);
						}
						
					}else if(check == 1) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 도서입니다.", "메시지창", JOptionPane.ERROR_MESSAGE);
					}
					check=0;
					
				}
				
			}
		});//버튼 액션

	}//생성자 end
	
	private int checkDuplicate(String bname, String author) {		//중복을 확인하는 메소드
		int check = 0;
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select bname, author from books ");
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		try {			//도서명 저자명 중복을 확인해서 에러창을 띄우게 하는 상수 설정
			pstmt2 = conn.prepareStatement(sb2.toString());

			rs2 = pstmt2.executeQuery();
			
			//도서명, 저자명 중복 확인
			while(rs2.next()) {
				String bnameCheck = rs2.getString(1);
				String authorCheck = rs2.getString(2);
				
				if(bname.equals(bnameCheck) && author.equals(authorCheck)) {
					
					check=1;
					
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
		
		
		return check;
	}
	
	
	private void insertBooks(String bname, String author, String bdate) {	//도서 관리 테이블에 도서 추가
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("insert into books ");
		sb.append("values (books_seq.NEXTVAL, ?, ?, ?) ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, bname);
			pstmt.setNString(2, author);
			pstmt.setNString(3, bdate);
			
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
	
//	private void insertBooking(String bname, String author, String bdate) {		//대여 관리 테이블에 도서항목 추가
//		Connection conn = MakeConnection.getConnection();	//db 연결
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append("insert into booking ");
//		sb.append("values (books_seq.NEXTVAL, null, null) ");
//		
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			pstmt = conn.prepareStatement(sb.toString());
//			rs = pstmt.executeQuery();
//			
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}//try~catch end
//		finally {
//			try {
//				rs.close();
//				pstmt.close();
//				conn.close();
//			}catch (SQLException e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//	}
	
	
	
	
}
