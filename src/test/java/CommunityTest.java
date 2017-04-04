import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class CommunityTest {

  @Rule
  public DatabaseRule databse = new DatabaseRule();

  @Test
  public void community_instantiatesCorrectly_true() {
    Community testCommunity = new Community("Water Enthusiasts", "Lovers of all things water monsters!");
    assertTrue(testCommunity instanceof Community);
  }

  @Test
  public void getName_communityInstantiatesWithCorrectName_String() {
    Community testCommunity = new Community("Water Enthusiasts", "Lovers of all things water monsters!");
    assertEquals("Water Enthusiasts", testCommunity.getName());
  }

  @Test
  public void getDescription_communityInstantiatesWithDescription_String() {
    Community testCommunity = new Community("Water Enthusiasts", "Lovers of all things water monsters!");
    assertEquals("Lovers of all things water monsters!", testCommunity.getDescription());
  }

  @Test
  public void equals_returnsTrueIfNameAndDescriptionAreSame_true() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    Community anotherCommunity = new Community("Fire Enthusiasts", "Flame on!");
    assertTrue(testCommunity.equals(anotherCommunity));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Community() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    assertEquals(true, Community.all().get(0).equals(testCommunity));
  }

  @Test
  public void all_returnsAllInstancesOfCommunity_true(){
    Community testCommunity1 = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity1.save();
    Community testCommunity2 = new Community("Water Enthusiasts", "Water on!");
    testCommunity2.save();
    assertTrue(Community.all().get(0).equals(testCommunity1));
    assertTrue(Community.all().get(1).equals(testCommunity2));
  }

  @Test
  public void addPerson_addsPersonToCommunity_true() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Joe Smith", "test@gmail.com");
    testPerson.save();
    testCommunity.addPerson(testPerson);
    Person savedPerson = testCommunity.getPersons().get(0);
    assertTrue(testPerson.equals(savedPerson));
  }

  @Test
  public void getPersons_returnsAllPersons_List() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson1 = new Person("Joe Smith", "test@gmail.com");
    testPerson1.save();
    testCommunity.addPerson(testPerson1);
    Person testPerson2 = new Person("Bob Brown", "testing@gmail.com");
    testPerson2.save();
    testCommunity.addPerson(testPerson2);
    List<Person> people = testCommunity.getPersons();
    assertEquals(people.size(), 2);
  }

  @Test
  public void delete_deletesCommunity_true() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    testCommunity.delete();
    assertEquals(0, Community.all().size());
  }

  @Test
  public void delete_deletesAllPersonsAndCommunitiesAssociations() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson1 = new Person("Joe Smith", "test@gmail.com");
    testPerson1.save();
    testCommunity.addPerson(testPerson1);
    Person testPerson2 = new Person("Bob Brown", "testing@gmail.com");
    testPerson2.save();
    testCommunity.addPerson(testPerson2);
    testCommunity.delete();
    List<Person> people = testCommunity.getPersons();
    assertEquals(0, people.size());
  }

}
