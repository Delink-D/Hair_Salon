import java.util.*;
import org.sql2o.*;

public class Stylist {
	private int id;
	private String fname;
	private String lname;
	private String user_code;

	public Stylist(String name1, String name2, String code) {
		this.fname 		= name1;
		this.lname 		= name2;
		this.user_code 	= code;
	}

	// save our new stylist to database
	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO stylist(fname,lname,user_code)VALUES(:name1,:name2,:code)";
			this.id = (int) con.createQuery(sql,true)
			.addParameter("name1", this.fname)
			.addParameter("name2", this.lname)
			.addParameter("code", this.user_code)
			.executeUpdate()
			.getKey();
		}
	}

	public static List<Stylist > getStylists() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM stylist";
			return con.createQuery(sql).executeAndFetch(Stylist.class);
		}
	}

	public int getId() {
		return id;
	}

	public String getName1() {
		return fname;
	}

	public String getName2() {
		return lname;
	}

	public String getCode() {
		return user_code;
	}

	public static Stylist find(int id) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM stylist WHERE id = :id";
			Stylist stylist = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Stylist.class);
			return stylist;
		}
	}

	// overide the equals method with our own
	@Override
	public boolean equals(Object otherStylist) {
		if (!(otherStylist instanceof Stylist)) {
			return false;
		}else {
			Stylist newStylist = (Stylist) otherStylist;
			return this.getCode().equals(newStylist.getCode()) && this.getId() == newStylist.getId();
		}
	}
}