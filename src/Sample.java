

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//import connection.MyConnection;
public class Sample {
	Connection con;
	Statement stmt;
	ResultSet rs;

	public Sample() {
		try {

			/*** Connecting to Oracle with Type-4 Driver */

			// Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@10.13.44.22:1521:xe", "hr",
					"hr");

			// con=MyConnection.getConnection();//using different class

			System.out.println("Connection established");

			stmt = con.createStatement();

			rs = stmt.executeQuery("select * from userlist");
			System.out.println("statement created");

			while (rs.next()) {
				System.out.println("username : " + rs.getString(1));
				System.out.println("password: " + rs.getString(2));
			}

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) {
		new Sample();
	}

}// -----p--------------end of class------------------//