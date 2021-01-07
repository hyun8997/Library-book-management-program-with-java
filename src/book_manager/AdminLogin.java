package book_manager;

//어드민 계정 로그인, 딱 하나로 정해져있다.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AdminLogin extends JFrame implements ActionListener{
	JButton jbtn1;
	JLabel jlb1, jlb2;
	JTextField jtf1;
	JPasswordField jpf1;
	
	private String aid = "goott";
	private String apw = "java1234";
	
	public AdminLogin() {
		// TODO Auto-generated constructor stub
		super("Admin login");
		setBounds(1100, 300, 400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		jbtn1 = new JButton("Login");
		
		
		jbtn1.setBounds(80,200,80,40);
		
		
		add(jbtn1); 
		
		
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

		setVisible(true);
	}//생성자 end
	
	public static void main(String[] args) {
		new AdminLogin();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj==jbtn1) {
			//System.out.println("jbtn1 click");
			//로그인 기능 수행
			String tid = jtf1.getText();
			String tpw = jpf1.getText();
			
			if(tid.equals(aid)&&tpw.equals(apw)) {
				new AdminMenu();
				dispose();
			}else {
				new nope();
				dispose();
			}
			
			
		}
		
	}
	
	
	
	
	
	
}
class nope extends JFrame{
	private JLabel jl;
	
	public nope(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1200,400,200,80);
		setLayout(null);
		
		jl = new JLabel("  존재하지 않는 Admin");
		jl.setBounds(0, 0, 200, 50);
		add(jl);
		setVisible(true);
	}
}
