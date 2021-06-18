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
	<%!Connection con;
	PreparedStatement st;
	Statement stmt;
	ResultSet rs;
	int count = 1;
	%>


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
			stmt = con.createStatement();
			rs=stmt.executeQuery("select RegID from waittojoin where Regstatus='Accept'");
			
		}catch (Exception e) {
				e.printStackTrace();
		} 
	%>

<form action="hrformverify.jsp" method="get">
	Accepted : <select name="AccepteeID">
	<%while(rs.next()) {%>
	<option value=<%=rs.getString(count)%> name="AccepteeID"> <%=rs.getString(count) %></option>
	<%} %>
	</select>
	<input type="submit" name="VerifyButton">	 
	</form>
<%
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
		}
	%>


</body>
</html>