

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class hrSendDOJ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement pt;
    public hrSendDOJ() {
        super();
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@10.13.44.22:1521:xe", "hr", "hr");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e){
			e.printStackTrace();
		}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String doj = request.getParameter("dateOfJoining");
	String userID = request.getParameter("regID");
	try {
		pt = con.prepareStatement("update waittojoin set datetojoin = ? where regid = ?");
		pt.setString(1, doj);
		pt.setString(2, userID);
		int i = pt.executeUpdate();
		PrintWriter pw = response.getWriter();
		if(i != 0)
			pw.println("<H1>SUCCESSFUL</h1>");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	this.doGet(request, response);
	}

}
