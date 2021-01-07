package book_manager;

/*
 * 유저가 누구인지 선택하는 창
 * 
 * 선택한 유저에 따라 로그인을 진행하도록 하게 할 것
 * 
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu extends JFrame implements ActionListener {

	private JButton jbtn1, jbtn2;
	
	public MainMenu() {
		// TODO Auto-generated constructor stub
		super("Choose User Type");
		setBounds(1000, 300, 300, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		jbtn1 = new JButton("Admin");			//에드민 로그인 버튼
		jbtn2 = new JButton("Public User");		//일반 유저 로그인 버튼
		
		jbtn1.setBounds(50,50,200,50);
		jbtn2.setBounds(50,125,200,50);
		
		add(jbtn1); add(jbtn2); 
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainMenu();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object obj = arg0.getSource();
		
		if(obj==jbtn1) {
			new AdminLogin();
			dispose();
		}else if(obj==jbtn2) {
			new Login();
			dispose();
		}
		
		
		
	}
	
}
