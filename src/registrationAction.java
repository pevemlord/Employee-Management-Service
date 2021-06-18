

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class registrationAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private ResultSet rs;
	private CallableStatement st;

	public registrationAction() {
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
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String dOfB = request.getParameter("dateOfBirth");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String contact = request.getParameter("contactNo");
		String emailID = request.getParameter("emailID");
		String skills[] = request.getParameterValues("Skills");
		PrintWriter pw = response.getWriter();
		pw.println("First Name : " + fname);
		pw.println("Last Name : " + lname);
		pw.println("Date of Birth : " + dOfB);
		pw.println("Address : " + address);
		pw.println("gender : " + gender);
		pw.println("Contact No. : " + contact);
		pw.println("Email ID : " + emailID);
		pw.println("<h1>skills : " + skills[0] + " </h1>" );
		for(String s : skills){
			pw.println(s);
		}
		int count = skills.length;
		if(count == 3){
			try {
				st = con.prepareCall("{call InsertEmployeeRecord(?,?,?,?,?,?,?,?,?,?)}");
				st.setString(1, fname);
				st.setString(2, lname);
				st.setString(3, dOfB);
				st.setString(4, address);
				st.setString(5, gender);
				st.setString(6, contact);
				st.setString(7, emailID);
				st.setString(8, skills[0]);
				st.setString(9, skills[1]);
				st.setString(10, skills[2]);
				pw.println("<h1>Successful</h1>");
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(count == 1 ){
			try {
				st = con.prepareCall("{call InsertEmployeeRecord(?,?,?,?,?,?,?,?,?,?)}");
				st.setString(1, fname);
				st.setString(2, lname);
				st.setString(3, dOfB);
				st.setString(4, address);
				st.setString(5, gender);
				st.setString(6, contact);
				st.setString(7, emailID);
				st.setString(8, skills[0]);
				st.setString(9, "null");
				st.setString(10, "null");
				pw.println("<h1>Successful</h1>");
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(count == 2){
			try {
				st = con.prepareCall("{call InsertEmployeeRecord(?,?,?,?,?,?,?,?,?,?)}");
				st.setString(1, fname);
				st.setString(2, lname);
				st.setString(3, dOfB);
				st.setString(4, address);
				st.setString(5, gender);
				st.setString(6, contact);
				st.setString(7, emailID);
				st.setString(8, skills[0]);
				st.setString(9, skills[1]);
				st.setString(10, null);
				pw.println("<h1>Successful</h1>");
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			pw.println("<h1>Select atmost 3 skills<\\h1>");
		}

		try {
			
			st.close();
			con.close();
			//rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//this.doPost(request,response);
		RequestDispatcher rd = request.getRequestDispatcher("reg2.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);		
	}

}
