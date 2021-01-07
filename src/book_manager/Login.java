package book_manager;

//일반 회원 로그인
//회원가입 가능

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	private JButton jbtn1, jbtn2;
	private JLabel jlb1, jlb2;
	private JTextField jtf1;
	private JPasswordField jpf1;
	private int check = 0;
	private static String unum;
	
	public Login() {
		// TODO Auto-generated constructor stub
		super("login");
		setBounds(1100, 300, 400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		jbtn1 = new JButton("Login");
		jbtn2 = new JButton("Sign Up");
		
		jbtn1.setBounds(80,200,80,40);
		jbtn2.setBounds(200,200,80,40);
		
		add(jbtn1); add(jbtn2); 
		
		
		jlb1 = new JLabel("I D");
		jlb2 = new JLabel("P W");
		
		jlb1.setBounds(30,60,70,40);
		jlb2.setBounds(30,120,70,40);
		
		add(jlb1); add(jlb2);
		
		
		jtf1 = new JTextField(30);
		jpf1 = new JPasswordField(30);
		
		jtf1.setBounds(100, 60, 150, 40);
		jpf1.setBounds(100, 130, 150, 40);

		add(jtf1); add(jpf1); 
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);

		setVisible(true);
	}//생성자 end
	
	public static void main(String[] args) {
		new Login();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		
		if(obj==jbtn1) {
			//System.out.println("jbtn1 click");
			//로그인 기능 수행
			String id = jtf1.getText();
			@SuppressWarnings("deprecation")
			String pw = jpf1.getText();
			
			//dbms에 접근해서 사용자가 입력한 id와 pw값이 존재하는지 확인(비교)
			Connection conn = MakeConnection.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("select * from users ");
			sb.append("where id = ? and pw = ?");
			
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
			
			StringBuffer sb2 = new StringBuffer();
			sb2.append("select * from users ");
			
			PreparedStatement pstmt2 = null;
			ResultSet rs2 = null;
			
			try {
				pstmt2 = conn.prepareStatement(sb2.toString());

				rs2 = pstmt2.executeQuery();
				
				//id, pw 확인
				while(rs2.next()) {
					
					String uid = rs2.getString(2);
					String upw = rs2.getString(3);
					
					if(id.compareTo(uid)==0 && pw.compareTo(upw)!=0) {	//비밀번호 틀림
						check=1;
					} else if(id.compareTo(uid)!=0 && pw.compareTo(upw)==0) {	//아이디 존재x
						check=2;
					} else if(id.compareTo(uid)==0 && pw.compareTo(upw)==0) {	//id pw 확인됨
						check=3;
						unum=rs2.getString(1);
					}
				}
			}catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} finally {
				try {
					rs2.close();
					pstmt2.close();
					conn.close();
				} catch (SQLException e3) {
					// TODO: handle exception
					e3.printStackTrace();
				}
			}

			if(check == 1) {
				new label1();
			} else if(check == 2) {
				new label2();
			} else if(check == 3) {
				new UserMenu();
				dispose();
			}
			
		}else if(obj == jbtn2) {
			//회원가입창으로 이동(호출)
			new SignIn();
		}
		
	}
	
	static String getUnum() {
		return unum;
	}

}

class label1 extends JFrame{
	private JLabel jl;
	
	public label1(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,400,200,80);
		setLayout(null);
		
		jl = new JLabel("  PW가 일치하지 않습니다.");
		jl.setBounds(0, 0, 200, 50);
		add(jl);
		setVisible(true);
	}
}

class label2 extends JFrame{
	private JLabel jl;
	
	public label2(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,400,200,80);
		setLayout(null);
		
		jl = new JLabel("  ID가 존재하지 않습니다.");
		jl.setBounds(0, 0, 200, 50);
		add(jl);
		setVisible(true);
	}
}
