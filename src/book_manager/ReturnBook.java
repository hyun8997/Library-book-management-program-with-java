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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReturnBook extends JFrame implements ActionListener{
	private JButton jbtn;
	private JTextField jtf1;
	private JTextField jtf2;
	private JLabel jl1,jl2;
	private int check = 0; 	//도서가 대여 정보가 존재하는지 여부 확인
	
	public ReturnBook() { 
		super("도서 반납");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,700,80);
		setLayout(new FlowLayout());
		
		jbtn = new JButton("반납");
		jbtn.setBounds(50,50,100,50);
		jtf1 =  new JTextField(20);
		jtf2 =  new JTextField(20);
		
		jl1 = new JLabel("도서명");
		jl2 = new JLabel("고객명");
		
		add(jl1); add(jtf1);	
		add(jl2); add(jtf2);	
		add(jbtn);
		jbtn.addActionListener(this);

		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ReturnBook();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj==jbtn) {

			String bname = jtf1.getText();
			String uname = jtf2.getText();
			
			System.out.println(bname);
			System.out.println(uname);
			
			
			check = checkBook(bname, uname);
			
			if(check == 0) {
				reBook(bname);
				dispose();
				new returnLabel();
			}else if(check == 1) {
				new noreturnLabel();
				
			}
			
			
		}
	}

	private int checkBook(String bname, String uname) {	//0은 빌린것, 1은 빌리지 않은것
		int check = 0;
		
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) ");
		sb.append("from booking ");
		sb.append("where bnum = ( select bnum from books where bname = ? ) and ");
		sb.append("unum = ( select unum from users where uname = ? ) ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, bname);
			pstmt.setNString(2, uname);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String count = rs.getString(1);
				
				if(count.equals("0")) {
					check=1;
				}
			}
			
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

		return check;
	}
	
	private void reBook(String bname) {
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from booking ");
		sb.append("where bnum = (select bnum from books where bname = ? ) ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, bname);
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


class returnLabel extends JFrame{	//id 중복 메세지
	private JLabel jl;
	
	public returnLabel(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,700,150,80);
		setLayout(null);
		
		jl = new JLabel("  도서 반납 완료");
		jl.setBounds(0, 0, 150, 50);
		add(jl);
		setVisible(true);
	}
}


class noreturnLabel extends JFrame{	//id 중복 메세지
	private JLabel jl;
	
	public noreturnLabel(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,700,150,80);
		setLayout(null);
		
		jl = new JLabel("  반납할 도서가 없습니다.");
		jl.setBounds(0, 0, 150, 50);
		add(jl);
		setVisible(true);
	}
}





