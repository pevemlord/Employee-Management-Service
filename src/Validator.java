import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class Validator extends HttpServlet {
	String useridt;
	String password;
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	Integer count = 0;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		useridt = request.getParameter("user");
		password = request.getParameter("pass");
		HttpSession session = request.getSession();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@10.13.44.22:1521:xe", "hr", "hr");
			System.out.println("AFter");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ps = con.prepareStatement("select * from userList where userID=? and pass=?");
			ps.setString(1, useridt);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("data matched");
				pw.println("welcome" + useridt);
				if (useridt.substring(0, 2).equals("HR")) {
					session.setAttribute("username", useridt);
					RequestDispatcher rd = request
							.getRequestDispatcher("HR.html");
					rd.forward(request,response);
				} else if (useridt.substring(0, 2).equals("MB")) {
					session.setAttribute("username", useridt);
					RequestDispatcher rd = request
							.getRequestDispatcher("mbu.html");
					rd.forward(request,response);
				} else if (useridt.substring(0, 2).equals("RE")) {
					session.setAttribute("username", useridt);
					RequestDispatcher rd = request
							.getRequestDispatcher("regVerfiyform.jsp");
					rd.forward(request,response);
				} else if (useridt.substring(0, 2).equals("EM")) {
					session.setAttribute("username", useridt);
					RequestDispatcher rd = request
							.getRequestDispatcher("empform.jsp");
					rd.forward(request,response);
				}

			} else {
				System.out.println("Sorry username or password error");
				pw.println("Sorry username or password error");
				RequestDispatcher rd = request
						.getRequestDispatcher("login2.html");
				rd.forward(request, response);
			}
		}

		catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			this.doGet(request, response);
	}
}
