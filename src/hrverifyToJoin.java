import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class hrverifyToJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private CallableStatement st;
	private PreparedStatement st1;
	HttpSession session;
	private ResultSet rs;
	String userID;
	
    public hrverifyToJoin() {
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
		userID = request.getParameter("RegistreID1");
		PrintWriter pw = response.getWriter();
		String DofJ;
		if(answer.equals("Verify"))
		{
			try {
				pw.println("<h1>USER ID IS : " + userID + "</h1>");
				st1 = con.prepareStatement("select to_char(datetojoin,'DD-Mon-YYYY') from waittojoin where regid = ?");
				st1.setString(1, userID);
				rs = st1.executeQuery();
				rs.next();
				DofJ = rs.getString(1);
				pw.println("<h1>Date of Joining is : " + DofJ + " </h1>");
				st = con.prepareCall("{ call InsertEmployeeDetails(?,?,?)}");
				st.setString(1, userID);
				st.setString(2,DofJ);
				st.setString(3, "Buffer");
				st.executeUpdate();
				st.close();
				pw.println("<script>alert('Successful');</script>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			CallableStatement call;
			try{
				call = con.prepareCall("{ call DeleteRegisratedRecord(?)}");
				call.setString(1, userID);
				call.executeUpdate();
				call.close();
				pw.println("<h1>U Reject to JOIN</h1>");
			}catch(SQLException e){}
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
