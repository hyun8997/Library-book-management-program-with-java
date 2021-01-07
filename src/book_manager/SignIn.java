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
import javax.swing.JTextField;

public class SignIn extends JFrame implements ActionListener {
	private JButton jbtn;
	private JTextField jtf1;
	private JTextField jtf2;
	private JTextField jtf3;
	private JTextField jtf4;
	private JLabel jl1,jl2,jl3,jl4;
	
	
	public SignIn() {
		// TODO Auto-generated constructor stub
		super("회원 가입창");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,250,380);
		setLayout(null);
		
		jbtn = new JButton("회원 가입");
		jbtn.setBounds(70,270,100,30);
		jtf1 =  new JTextField(20);
		jtf2 =  new JTextField(20);
		jtf3 =  new JTextField(20);
		jtf4 =  new JTextField(20);
		
		jtf1.setBounds(100,30,100,30);
		jtf2.setBounds(100,90,100,30);
		jtf3.setBounds(100,150,100,30);
		jtf4.setBounds(100,210,100,30);
		
		jl1 = new JLabel("ID");
		jl2 = new JLabel("PW");
		jl3 = new JLabel("회원명");
		jl4 = new JLabel("전화번호");
		
		jl1.setBounds(30,30,60,30);
		jl2.setBounds(30,90,60,30);
		jl3.setBounds(30,150,60,30);
		jl4.setBounds(30,210,60,30);
		
		add(jl1); add(jtf1);	
		add(jl2); add(jtf2);	
		add(jl3); add(jtf3);	
		add(jl4); add(jtf4);	
		add(jbtn);
		
		jbtn.addActionListener(this);

		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SignIn();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		int check=0;
		
		if(obj==jbtn) {

			String id = jtf1.getText();
			String pw = jtf2.getText();
			String uname = jtf3.getText();
			String phone = jtf4.getText();

			check = checkDuplicateId(id);	//id중복 검사
			
			if(check==0) {
				insertUsers(id, pw, uname, phone);
				dispose();
				new label4();
			}else if(check == 1) {
				new label3();
			}
			
			check=0;
			
		}//버튼 액션
		
		
	}
	
	private int checkDuplicateId(String id) {	//id 중복 검사
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		int check = 0;
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select * from users ");
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		try {
			pstmt2 = conn.prepareStatement(sb2.toString());

			rs2 = pstmt2.executeQuery();
			
			//id 중복 확인
			while(rs2.next()) {
				String uid = rs2.getString(2);
				
				if(id.equals(uid)) {
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
	}//id 중복 검사 end
	
	private void insertUsers(String id, String pw, String uname, String phone) {	//회원가입
		Connection conn = MakeConnection.getConnection();	//db 연결
		
		StringBuffer sb = new StringBuffer();
		sb.append("insert into users ");
		sb.append("values (users_seq.NEXTVAL, ?, ?, ?, ?, 0) ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setNString(1, id);
			pstmt.setNString(2, pw);
			pstmt.setNString(3, uname);
			pstmt.setNString(4, phone);
			
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

	}//회원가입 end
	
	
	
}

class label3 extends JFrame{	//id 중복 메세지
	private JLabel jl;
	
	public label3(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,700,150,80);
		setLayout(null);
		
		jl = new JLabel("  ID가 이미 존재합니다.");
		jl.setBounds(0, 0, 150, 50);
		add(jl);
		setVisible(true);
	}
}

class label4 extends JFrame{	//가입 완료 메세지
	private JLabel jl;
	
	public label4(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,700,150,80);
		setLayout(null);
		
		jl = new JLabel("  회원 가입 완료");
		jl.setBounds(0, 0, 150, 50);
		add(jl);
		setVisible(true);
	}
}
