import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class mbuformValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection con;
    private CallableStatement call;
    String empid, proID;
	PrintWriter pw;
	public mbuformValidator() {
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
		empid = request.getParameter("EmployeeID");
		proID = request.getParameter("ProjectIDStatus");
		pw = response.getWriter();
		pw.println("<h1> Project ID : " + proID + " </h1>");
		try{
			call = con.prepareCall("{ call updateProjectStatus(?,?)}");
			call.setString(1, empid);
			call.setString(2, proID);
			call.executeUpdate();
			pw.println("EMployee GET the PROJECT");
		}catch(SQLException e){}finally{
			try {
				call.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
