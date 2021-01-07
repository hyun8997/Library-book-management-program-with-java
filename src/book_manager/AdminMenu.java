package book_manager;

/*
	[관리자 모드]
	-	도서 추가 
	-	삭제
	-	수정 
	-	도서 연체자 관리
			기본 14일
			
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class AdminMenu extends JFrame implements ActionListener{
	
	
	private JButton jbtn1, jbtn2,jbtn3, jbtn4,jbtn5;
	
	public AdminMenu() {
		// TODO Auto-generated constructor stub
		super("Admin Menu");
		setBounds(1000, 300, 300, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		jbtn1 = new JButton("도서 추가");		//도서 추가
		jbtn2 = new JButton("도서 삭제");		//도서 삭제
		jbtn3 = new JButton("도서 수정");		//도서 수정
		jbtn4 = new JButton("연체자 관리");		//연체자 관리
		jbtn5 = new JButton("도서 반납");		//도서 반납
		
		jbtn1.setBounds(50,50,200,50);
		jbtn2.setBounds(50,125,200,50);
		jbtn3.setBounds(50,200,200,50);
		jbtn4.setBounds(50,275,200,50);
		jbtn5.setBounds(50,350,200,50);
		
		add(jbtn1); add(jbtn2); add(jbtn3); add(jbtn4); add(jbtn5); 
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);
		jbtn3.addActionListener(this);
		jbtn4.addActionListener(this);
		jbtn5.addActionListener(this);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new AdminMenu();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj==jbtn1) {	//도서 추가
			new AddBook();
			//dispose();
		}else if(obj==jbtn2) {	//도서 삭제
			new DeleteBook();
			//dispose();
		}else if(obj==jbtn3) {	//도서 수정
			new UpdateBook();
			//dispose();
		}else if(obj==jbtn4) {	//연체자 관리
			new CheckBanUser();
			//dispose();
		}else if(obj==jbtn5) {
			new ReturnBook();
		}

		
	}


	
}
