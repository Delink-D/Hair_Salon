import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;

public class ClientsTest {

	@Before
	public void setUp() {
		DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "delink", "0000");
	}

	@After 
	public void clearAll() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "DELETE FROM clients *;";
			con.createQuery(sql).executeUpdate();
		}
	}

	@Test
	public void getCode_theSaveReturnsCode_Code() {
		Clients testClients = new Clients("Name", "Name",1);
		assertEquals(1,testClients.getStylist());
	}

	@Test
	public void getCode_theSaveReturnsLname_Name2() {
		Clients testClients = new Clients("Name", "Name2",1);
		assertEquals("Name2",testClients.getName2());
	}

	@Test
	public void equal_checkIfReturnssame() {
		Clients testClients1 = new Clients("Name", "Name",1);
		Clients testClients2 = new Clients("Name", "Name",2);
		assertTrue(testClients1.equals(testClients2));
	}

	@Test
	public void getClients_returnsAllInstancesOfClients_true() {
		Clients testClients1 = new Clients("Name", "Name", 1);
		testClients1.save();
		Clients testClients2 = new Clients("Name", "Name", 2);
		testClients2.save();
		assertTrue(Clients.getClients().get(0).equals(testClients1));
		assertTrue(Clients.getClients().get(1).equals(testClients2));
	}

	@Test
		public void save_assignsIdToClients_ID(){
			Clients testClients = new Clients("Name", "Name",1); 
			testClients.save();
			Clients savedClients = Clients.getClients().get(0);
			assertEquals(testClients.getId(),savedClients.getId());
		}

	@Test 
	public void find_returnsClientsWithSameId_secondClients() {
		Clients testClients1 = new Clients("Name", "Name",1);
		testClients1.save();
		Clients testClients2 = new Clients("Name", "Name",1);
		testClients2.save();
		assertEquals(Clients.find(testClients2.getId()), testClients2);
	}
}