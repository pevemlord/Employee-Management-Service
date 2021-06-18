<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
<%@page import='java.sql.*'  %>
<%! Connection con;
CallableStatement st;
ResultSet rs;
String user;
String pass;
%>
<% try {
	Class.forName("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection(
			"jdbc:oracle:thin:@10.13.44.22:1521:xe", "hr", "hr");			
} catch (ClassNotFoundException e) {
	e.printStackTrace();
}catch (SQLException e){
	e.printStackTrace();
}
 %>
<%
try {
			st = con.prepareCall("{call getLastRegistreeInfo(?,?)}");
			st.registerOutParameter(1,java.sql.Types.VARCHAR);
			st.registerOutParameter(2,java.sql.Types.VARCHAR);
			st.executeUpdate();
			user = st.getString(1);
			pass = st.getString(2);
			} catch (SQLException e) {
				e.printStackTrace();
			}%> 
 

<p>Your Registration IS :<%=user %></p>
<p>Your Password : <%=pass %></p>
</body>
</html>
