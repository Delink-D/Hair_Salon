import java.util.*;
import org.sql2o.*;

public class Login {
	private int id;
	private String fname;
	private String lname;
	private static String email;
	private static String password;
	private static boolean login;

	public Login(String email, String pword) {
		this.email 		= email;
		this.password 	= pword;
		login = false;
	}

	public static String getEmail() {
		return email;
	}

	public static String getPword() {
		return password;
	}

	public static boolean login() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM admin WHERE email = :email";

			Login login = con.createQuery(sql).addParameter("email", email).executeAndFetchFirst(Login.class);

			return email.equals(login.getEmail()) && password.equals(login.getPword());
		}
	}

	// overide the equals method with our own
	// @Override
	// public boolean equals(Object otherClient) {
	// 	if (!(otherClient instanceof Clients)) {
	// 		return false;
	// 	}else {
	// 		Clients newClient = (Clients) otherClient;
	// 		return this.getName2().equals(newClient.getName2()) && this.getId() == newClient.getId();
	// 	}
	// }
}