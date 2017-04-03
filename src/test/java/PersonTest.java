import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void person_instantiatesCorrectly_true() {
    Person testPerson = new Person("Joe Smith", "test@gmail.com");
    assertTrue(testPerson instanceof Person);
  }

  @Test
  public void getName_returnsCorrectName_JoeSmith() {
    Person testPerson = new Person("Joe Smith", "test@gmail.com");
    assertEquals("Joe Smith", testPerson.getName());
  }

  @Test
  public void getEmail_returnsCorrectEmail_String() {
    Person testPerson = new Person("Joe Smith", "test@gmail.com");
    assertEquals("test@gmail.com", testPerson.getEmail());
  }

  @Test
  public void save_savesPersonToDB_true() {
    Person testPerson = new Person("Joe Smith", "test@gmail.com");
    testPerson.save();
    assertEquals("Joe Smith", Person.all().get(0).getName());
    assertEquals("test@gmail.com", Person.all().get(0).getEmail());
  }

  @Test
  public void all_returnsAllPersonsInDB_true() {
    Person testPerson1 = new Person("Fargo", "test@gmail.com");
    testPerson1.save();
    Person testPerson2 = new Person("True Grit", "test@gmail.com");
    testPerson2.save();
    assertTrue(Person.all().get(0).equals(testPerson1));
    assertTrue(Person.all().get(1).equals(testPerson2));
  }

  @Test
  public void equals_comparesPersonNameAndEmailToVerifyEqual_true() {
    Person testPerson1 = new Person("John Doe", "test@gmail.com");
    Person testPerson2 = new Person("John Doe", "test@gmail.com");
    assertTrue(testPerson1.equals(testPerson2));
  }

  @Test
  public void getId_assignsIdToPerson_true() {
    Person testPerson = new Person("John Doe", "test@gmail.com");
    testPerson.save();
    assertTrue(testPerson.getId() == Person.all().get(0).getId());
  }

  @Test
  public void find_returnsPersonGivenId_Person() {
    Person testPerson = new Person("John Doe", "test@gmail.com");
    testPerson.save();
    int testPersonId = testPerson.getId();
    assertTrue(Person.find(testPersonId).equals(testPerson));
  }

  @Test
  public void update_updatesPersonWithGivenName_true() {
    Person testPerson = new Person("John Doe", "test@gmail.com");
    testPerson.save();
    testPerson.updatePerson("Greg Blah", "test@gmail.com");
    assertEquals(testPerson.getName(), "Greg Blah");
    assertEquals(testPerson.getEmail(), "test@gmail.com");
    assertEquals(Person.find(testPerson.getId()).getName(), "Greg Blah");
    assertEquals(Person.find(testPerson.getId()).getEmail(), "test@gmail.com");

  }
}
