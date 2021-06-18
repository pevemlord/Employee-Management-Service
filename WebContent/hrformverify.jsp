<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
	<%@page import='java.sql.*'%>
	<%@page import='javax.servlet.*'%>
	<%!Connection con;
	PreparedStatement st, st1, st2;
	ResultSet rs, rs1, rs2;
	String fname, lname, dob, add, gender, contact, email;
	String skill1, skill2, skill3;
	String userID;
	String dateToJoin;
	HttpSession session;
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
		
			userID = request.getParameter("AccepteeID");
			st = con.prepareStatement("select * from Registrations where regid = ?");
			st.setString(1, userID);
			rs = st.executeQuery();
			rs.next();
			fname = rs.getString(3);
			lname = rs.getString(4);
			dob = rs.getString(5);
			add = rs.getString(6);
			gender = rs.getString(7);
			contact = rs.getString(8);
			email = rs.getString(9);
			st1 = con
					.prepareStatement("select * from table(select Skills_info from Registrations where RegID = ?)");
			st1.setString(1, userID);
			rs = st.executeQuery();
			rs1 = st1.executeQuery();
			rs1.next();
			skill1 = rs1.getString(1);
			skill2 = rs1.getString(2);
			skill3 = rs1.getString(3);
			st2 = con.prepareStatement("select to_char(datetojoin,'DD-Mon-YYYY') from waittojoin where regid = ?");
			st2.setString(1, userID);
			rs2 = st2.executeQuery();
			rs2.next();
			dateToJoin = rs2.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	<h1>Registree Details:</h1>
	
	<form id="form1" name="form1" method="post" action="hrverifyToJoin">
	<p>
		User ID :
		<%=userID%></p>
		<input type="hidden" name="RegistreID1" value=<%=userID %>>
	
	<p>
		First Name :
		<%=fname%></p>
	<p>
		Last Name :
		<%=lname%></p>
	<p>
		Date of Birth :
		<%=dob%></p>
	<p>
		Address :
		<%=add%></p>
	<p>
		Gender :
		<%=gender%></p>
	<p>
		Contact No. :
		<%=contact%></p>
	<p>
		Email ID :
		<%=email%></p>
	<p>
		Skills Are :
		<%=skill1%>&nbsp;<%=skill2%>&nbsp;<%=skill3%>&nbsp;
	</p>
	<p>
		Date of Joining : 
		<%=dateToJoin %>
	</p>
		<input type="submit" name="Answer" id="acceptButton"
			value="Verify" /> <input type="submit" name="Answer"
			id="rejectButton" value="Reject" />
	</form>

	<%
		try {
			rs.close();
			rs1.close();
			st.close();
			st1.close();
			con.close();
		} catch (Exception e) {
		}
	%>

</body>
</html>