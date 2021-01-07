package book_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserBookList extends JFrame {
	JPanel panel;
	
	
	//JTable
	Object ob[][] = new Object[0][3];
	DefaultTableModel model;
	JTable table;
	JScrollPane js;
	String str[] = {"도서명", "저자명", "출판일"};//컬럼명
	
	public UserBookList() {
		// TODO Auto-generated constructor stub
		super("Book List");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		
		
		//JTable 가운데 배치
		model = new DefaultTableModel(ob, str);
		table =  new JTable(model);
		js = new JScrollPane(table);
		this.add("Center", js);
		setBounds(250,250,300,300);
		

		Connection conn = MakeConnection.getConnection();	//db 연결
		StringBuffer sb = new StringBuffer();
		sb.append("select bname,author,bdate from books ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String bname = rs.getString(1);		//도서명 받기
				String author = rs.getString(2);	//저자명 받기
				String bdate = rs.getString(3);	//출판일 받기

				Object [] bookData = {bname, author, bdate};
				model.addRow(bookData);
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
		
		
		setVisible(true);

	}
	
	public static void main(String[] args) {
		new UserBookList();
	}

}
