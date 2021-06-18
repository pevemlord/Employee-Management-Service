

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class regRequestToJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement st;
	HttpSession session;
	String userID;
	public regRequestToJoin() {
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
		String answer = request.getParameter("Answer");
		session = request.getSession(true);
		userID = (String)session.getAttribute("username");
		PrintWriter pw = response.getWriter();
		if(answer.equals("Accept"))
		{
			try {
				st = con.prepareStatement("update waittojoin set regstatus = 'Accept' where regid = ?");
				st.setString(1, userID);
				st.executeUpdate();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println("<h1>U Accept to JOIN</h1>");
			pw.println("<script>alert(\"Your New ID will be EMP" + userID.substring(3)+ " and password is same \" );</script>");
		}
		else
		{
			CallableStatement call;
			try{
				call = con.prepareCall("{ call DeleteRegisratedRecord(?)}");
				call.setString(1, userID);
				call.executeUpdate();
				call.close();
			}catch(SQLException e){}
			pw.println("<h1>U Reject to JOIN</h1>");
		}
		try {
			con.close();
		} catch (Exception e) {
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
