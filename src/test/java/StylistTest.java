import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;

public class StylistTest {

	@Before
	public void setUp() {
		DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "delink", "0000");
	}

	@After 
	public void clearAll() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "DELETE FROM stylist *;";
			con.createQuery(sql).executeUpdate();
		}
	}

	@Test
	public void getCode_theSaveReturnsCode_Code() {
		Stylist testStylist = new Stylist("Name", "Name","Code"); 
		assertEquals("Code",testStylist.getCode());
	}

	@Test
	public void getCode_theSaveReturnsLname_Name2() {
		Stylist testStylist = new Stylist("Name", "Name2","Code");
		assertEquals("Name2",testStylist.getName2());
	}

	@Test
	public void equal_checkIfReturnssame() {
		Stylist testStylist1 = new Stylist("Name", "Name","Code");
		Stylist testStylist2 = new Stylist("Name", "Name","Code");
		assertTrue(testStylist1.equals(testStylist2));
	}

	@Test
	public void getStylists_returnsAllInstancesOfStylists_true() {
		Stylist testStylist1 = new Stylist("Name", "Name","Code");
		testStylist1.save();
		Stylist testStylist2 = new Stylist("Name", "Name","Code");
		testStylist2.save();
		assertTrue(Stylist.getStylists().get(0).equals(testStylist1));
		assertTrue(Stylist.getStylists().get(1).equals(testStylist2));
	}

	@Test
	public void save_assignsIdToStylists_ID(){
		Stylist testStylist = new Stylist("Name", "Name","Code"); 
		testStylist.save();
		Stylist savedStylist = Stylist.getStylists().get(0);
		assertEquals(testStylist.getId(),savedStylist.getId());
	}

	@Test 
	public void find_returnsStylistWithSameId_secondStylist() {
		Stylist testStylist1 = new Stylist("Name", "Name","Code");
		testStylist1.save();
		Stylist testStylist2 = new Stylist("Name", "Name","Code");
		testStylist2.save();
		assertEquals(Stylist.find(testStylist2.getId()), testStylist2);
	}

	@Test 
	public void update_updatesStylistDEtails_true() {
		Stylist testStylist = new Stylist("Name", "Name","code");
		testStylist.save();
		testStylist.update("Name", "myUpdate");
		assertEquals("myUpdate", Stylist.find(testStylist.getId()).getName2());
	}

	@Test 
	public void delete_deletesStylist_true() {
		Stylist testStylist = new Stylist("Name", "Name","code");
		testStylist.save();
		int stylistid = testStylist.getId();
		testStylist.delete();
		assertEquals(null, Stylist.find(stylistid));
	}
}