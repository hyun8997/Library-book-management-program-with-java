package book_manager;

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

public class UpdateBook extends JFrame {
	private JButton jbtn1,jbtn2,jbtn3;
	private JTextField jtf1;	//도서명
	private JTextField jtf2;	//저자명
	private JTextField jtf3;	//수정할 내용

	private JLabel jl1,jl2,jl3;
	
	public UpdateBook() {
		// TODO Auto-generated constructor stub
		super("도서 수정");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(1150,500,480,320);
		setLayout(null);
		
		jbtn1 = new JButton("도서명 수정");
		jbtn1.setBounds(50,210,110,30);
		jbtn2 = new JButton("저자명 수정");
		jbtn2.setBounds(170,210,110,30);
		jbtn3 = new JButton("출간일 수정");
		jbtn3.setBounds(290,210,110,30);

		jl1 = new JLabel("도서명 입력");
		jl2 = new JLabel("저자명 입력");
		jl3 = new JLabel("수정할 내용 입력(출간일은 yy/mm/dd)");
		
		jtf1 =  new JTextField(20);
		jtf2 =  new JTextField(20);
		jtf3 =  new JTextField(20);
		
		
		jtf1.setBounds(50, 50, 350, 30);
		jtf2.setBounds(50, 110, 350, 30);
		jtf3.setBounds(50, 170, 350, 30);
		
		jl1.setBounds(50, 20, 110, 30);
		jl2.setBounds(50, 80, 110, 30);
		jl3.setBounds(50, 140,300, 30);
		
		
		add(jtf1);	add(jbtn1);
		add(jtf2);	add(jbtn2);
		add(jtf3);	add(jbtn3);
		add(jl1); add(jl2); add(jl3);

		
		UpdateBookPanel jp = new UpdateBookPanel(jbtn1,jbtn2,jbtn3,jtf1,jtf2,jtf3);
		add(jp);

		setVisible(true);
	}
	
	public static void main(String[] args) {
		new UpdateBook();
	}

}

class UpdateBookPanel extends JPanel{
	
	public UpdateBookPanel(JButton jbtn1, JButton jbtn2, JButton jbtn3,
							JTextField jtf1, JTextField jtf2,JTextField jtf3) {
		
		jbtn1.addActionListener(new ActionListener() {	//입력을 받겠다. 옵션 창을 띄워서
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				if(obj==jbtn1) {

					String bname = jtf1.getText();
					String author = jtf2.getText();
					String udata = jtf3.getText();

					Connection conn = MakeConnection.getConnection();	//db 연결
					
					StringBuffer sb = new StringBuffer();
					sb.append("update books ");
					sb.append("set bname = ? ");
					sb.append("where bname = ? and author = ?");
					
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					int result = JOptionPane.showConfirmDialog(null, "도서명을 변경하시겠습니까?", "확인", JOptionPane.YES_NO_CANCEL_OPTION);	//
					
					if(result==0) {
						try {
							pstmt = conn.prepareStatement(sb.toString());
							pstmt.setNString(1, udata);
							pstmt.setNString(2, bname);
							pstmt.setNString(3, author);
							
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
					}else if(result==2) {
						System.exit(0);
					}
					
				}
				
			}
		});//버튼 액션
		
		
		jbtn2.addActionListener(new ActionListener() {	//입력을 받겠다. 옵션 창을 띄워서
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				if(obj==jbtn2) {

					String bname = jtf1.getText();
					String author = jtf2.getText();
					String udata = jtf3.getText();

					Connection conn = MakeConnection.getConnection();	//db 연결
					
					StringBuffer sb = new StringBuffer();
					sb.append("update books ");
					sb.append("set author = ? ");
					sb.append("where bname = ? and author = ?");
					
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					int result = JOptionPane.showConfirmDialog(null, "저자명을 변경하시겠습니까?", "확인", JOptionPane.YES_NO_CANCEL_OPTION);	//
					
					if(result==0) {
						try {
							pstmt = conn.prepareStatement(sb.toString());
							pstmt.setNString(1, udata);
							pstmt.setNString(2, bname);
							pstmt.setNString(3, author);
							
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
					}else if(result==2) {
						System.exit(0);
					}
					
				}
				
			}
		});//버튼 액션
		
		jbtn3.addActionListener(new ActionListener() {	//입력을 받겠다. 옵션 창을 띄워서
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				if(obj==jbtn3) {

					String bname = jtf1.getText();
					String author = jtf2.getText();
					String udata = jtf3.getText();

					Connection conn = MakeConnection.getConnection();	//db 연결
					
					StringBuffer sb = new StringBuffer();
					sb.append("update books ");
					sb.append("set bdate = ? ");
					sb.append("where bname = ? and author = ?");
					
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					int result = JOptionPane.showConfirmDialog(null, "출간일을 변경하시겠습니까?", "확인", JOptionPane.YES_NO_CANCEL_OPTION);	//
					
					if(result==0) {
						try {
							pstmt = conn.prepareStatement(sb.toString());
							pstmt.setNString(1, udata);
							pstmt.setNString(2, bname);
							pstmt.setNString(3, author);
							
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
					}else if(result==2) {
						System.exit(0);
					}
					
				}
				
			}
		});//버튼 액션
		
		
	}
}
















