<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@page import='java.sql.*'%>
	<%@page import='javax.servlet.*'%>
	<%!Connection con;
	PreparedStatement st, st1;
	Statement st2;
	ResultSet rs, rs1, rs2;
	String fname, lname, dob, add, gender, contact, email;
	String skill1, skill2, skill3;
	String userID;%>

	<%
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@10.13.44.22:1521:xe", "hr", "hr");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	<%
		try {

			userID = request.getParameter("newprodID");
			st = con.prepareStatement("select empid from EmployeeDetails where pstatus= ?");
			st.setString(1, userID);
			rs = st.executeQuery();
			fname = rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	<h1>Employee in Project :</h1>

	<form id="form1" name="form1" method="post" >
		Employee ID :
		<%while(rs.next()) {%>
		<p><%=rs.getString(1)%></p>
		<%} %>
		

</form>

	<%
		try {
			rs.close();
			rs1.close();
			rs2.close();
			st.close();
			st1.close();
			st2.close();
			con.close();
		} catch (Exception e) {
		}
	%>




</body>
</html>