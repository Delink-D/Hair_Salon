import java.util.*;
import org.sql2o.*;

public class Clients {
	private int id;
	private String fname;
	private String lname;
	private static int stylist;

	public Clients(String name1, String name2, int stylist) {
		this.fname 		= name1;
		this.lname 		= name2;
		this.stylist 	= stylist;
	}

	// save our new stylist to database
	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO clients(fname,lname,stylist)VALUES(:name1,:name2,:stylist)";
			this.id = (int) con.createQuery(sql,true)
			.addParameter("name1", this.fname)
			.addParameter("name2", this.lname)
			.addParameter("stylist", this.stylist)
			.executeUpdate()
			.getKey();
		}
	}

	public static List<Clients> getClients() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM clients";
			return con.createQuery(sql).executeAndFetch(Clients.class);
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

	public static int getStylist() {
		return stylist;
	}

	public static Clients find(int id) {
		try(Connection con = DB.sql2o.open()){
			String sql = "SELECT * FROM clients WHERE id = :id";
			Clients client = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Clients.class);
			return client;
		}
	}

	// overide the equals method with our own
	@Override
	public boolean equals(Object otherClient) {
		if (!(otherClient instanceof Clients)) {
			return false;
		}else {
			Clients newClient = (Clients) otherClient;
			return this.getName2().equals(newClient.getName2()) && this.getId() == newClient.getId();
		}
	}
}