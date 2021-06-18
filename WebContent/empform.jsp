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
	PreparedStatement st, st1, pst;
	Statement st2;
	ResultSet rs, rs1, rs2;
	String fname, lname, dob, add, gender, contact, email;
	String skill1, skill2, skill3;
	String userID;
	String proStatus, SystemID, RequiredSoft;
	HttpSession session;
	String sqlCOmmant;%>

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
			session = request.getSession(true);
			userID = (String) session.getAttribute("username");
			st = con.prepareStatement("select * from EmployeeDetails where empid = ?");
			st.setString(1, userID);
			rs = st.executeQuery();
			rs.next();
			fname = rs.getString(2);
			lname = rs.getString(3);
			dob = rs.getString(4);
			add = rs.getString(5);
			gender = rs.getString(6);
			contact = rs.getString(7);
			email = rs.getString(8);
			st1 = con
					.prepareStatement("select * from table(select SkillSET_info from EmployeeDetails where EMPID = ?)");
			st1.setString(1, userID);
			rs1 = st1.executeQuery();
			rs1.next();
			skill1 = rs1.getString(1);
			skill2 = rs1.getString(2);
			skill3 = rs1.getString(3);
			
			//pst = con.prepareStatement("select proid,sysid from onproject where EMPID =?");
			pst = con.prepareStatement("select * from onproject where EMPID =?");
			
			pst.setString(1, userID);
			
			rs1 = pst.executeQuery();
			// 			sqlCOmmant = "select proid,sysid from onproject where EMPID = '" + userID + "'";
			// 			st2 = con.createStatement();
			// 			rs2 = st2.executeQuery(sqlCOmmant);
			if (rs1.next()) {
				
				proStatus = rs1.getString(1);
				SystemID = rs1.getString(2);
			} else {
				proStatus = "Nothing";
				SystemID = "Nothing";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	<h1>Employee Details:</h1>

	<form id="form1" name="form1">
		<p>
			Employee ID :
			<%=userID%></p>
		<input type="hidden" name="EmployeeID" value=<%=userID%>>

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
			Project Status :
			<%=proStatus%>
		</p>
		<p>
			System ID :
			<%=SystemID%>
		</p>

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