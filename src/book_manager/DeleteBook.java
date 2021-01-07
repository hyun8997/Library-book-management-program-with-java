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

//import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class DeleteBook extends JFrame {

	public DeleteBook() {
		// TODO Auto-generated constructor stub
		super("도서 삭제");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,700,80);
		setLayout(new FlowLayout());
		
		DeleteBookPanel jp = new DeleteBookPanel();
		add(jp);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new DeleteBook();
	}

}

class DeleteBookPanel extends JPanel{
	
	private JButton jbtn;
	private JTextField jtf1;
	private JTextField jtf2;
	private JLabel jl1,jl2;
	private int check = 0; 	//도서가 존재하는지 여부 확인용
	
	
	public DeleteBookPanel() {
		// TODO Auto-generated constructor stub
		jbtn = new JButton("삭제");
		jbtn.setBounds(50,50,100,50);
		jtf1 =  new JTextField(20);
		jtf2 =  new JTextField(20);
		
		jl1 = new JLabel("도서명");
		jl2 = new JLabel("저자명");
		
		add(jl1); add(jtf1);	
		add(jl2); add(jtf2);	
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

					
					
					check=checkBookEx(bname,author);
					
					if(check==0) {
						int result = JOptionPane.showConfirmDialog(null, "도서 삭제 하시겠습니까?", "확인", JOptionPane.YES_NO_CANCEL_OPTION);
						if(result==0) {
							deleteBooks(bname, author);
							deleteBooking(bname, author);
							
						}else if(result==2) {
							System.exit(0);
						}
					
					}else if(check==1) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 도서입니다.", "메시지창", JOptionPane.ERROR_MESSAGE);
					}
					check=0;
				}
				
				
				
			}
		});//버튼 액션
		
		
		
		
	}
	
	private int checkBookEx(String bname, String author) {		//삭제할 도서가 존재하는지 찾기, check==1 --> 존재 X
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		int check = 0;
		
		StringBuffer sb2 = new StringBuffer();		
		sb2.append("select count(*) from books ");
		sb2.append("where bname = ? and author = ? ");
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		try {
			pstmt2 = conn.prepareStatement(sb2.toString());
			pstmt2.setNString(1, bname);
			pstmt2.setNString(2, author);
			
			rs2 = pstmt2.executeQuery();
			
			while(rs2.next()) {				//삭제할 책을 가지고 있는지 확인
				String haveBook = rs2.getString(1);
				
				if(haveBook.equals("0")) {
					check=1;
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//try~catch end
		finally {
			try {
				rs2.close();
				pstmt2.close();
				conn.close();
			}catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
		return check;
	}
	
	private void deleteBooks(String bname, String author) {	//books table에서 삭제
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from books ");
		sb.append("where bname = ? and author = ? ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, bname);
			pstmt.setNString(2, author);
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
	
	private void deleteBooking(String bname, String author) {	//booking table에서도 삭제
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from booking ");
		sb.append("where bnum = (select bnum from books ");
		sb.append("				where bname = ? and author = ?) ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, bname);
			pstmt.setNString(2, author);
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






