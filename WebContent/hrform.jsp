<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
	<%@page import='java.sql.*'%>
	<%@page import='javax.servlet.*' %>
	<%!Connection con;
	PreparedStatement st, st1;
	ResultSet rs, rs1;
	String fname, lname, dob, add, gender, contact, email;
	String skill1, skill2, skill3;
	String userID;
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
			userID = request.getParameter("RegistreeID");
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
			st1 = con.prepareStatement("select * from table(select Skills_info from Registrations where RegID = ?)");
			st1.setString(1, userID);
			rs = st.executeQuery();
			rs1 = st1.executeQuery();
			rs1.next();
			skill1 = rs1.getString(1);
			skill2 = rs1.getString(2);
			skill3 = rs1.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	<h1>Registree Details:</h1>
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
		<form id="form1" name="form1" method="post" action="hrSendDOJ">
			Date of Joining : <input type="text" name="dateOfJoining" id="dateOfJoining" placeholder="DD-MON-YEAR" />
			<input type="hidden" name="regID" value=<%=userID %>></input>
			<input type="submit" name="submitButton" id="submitButton"
				value="Submit" /> <input type="reset" name="resetButton"
				id="resetButton" value="Reset" />
		</form>
	</p>
	<p>&nbsp;</p>
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
