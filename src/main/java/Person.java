import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Person {
  private String name;
  private String email;
  private int id;

  public Person(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO persons (name, email) VALUES (:name, :email);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Person> all(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM persons;";
      return con.createQuery(sql)
        .executeAndFetch(Person.class);
    }
  }

  @Override
  public boolean equals(Object otherPerson) {
    if (!(otherPerson instanceof Person)) {
      return false;
    } else {
      Person newPerson = (Person) otherPerson;
      return this.getName().equals(newPerson.getName()) &&
             this.getEmail().equals(newPerson.getEmail()) &&
             this.getId() == newPerson.getId();
    }
  }

  public static Person find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM persons WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Person.class);
    }
  }

  public void updatePerson(String name, String email) {
    this.name = name;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE persons SET (name, email) = (:name, :email) WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("email", email)
        .addParameter("id", this.id)
        .executeUpdate();
    }
 }

 public List<Object> getMonsters() {
   List<Object> allMonsters = new ArrayList<Object>();

   try (Connection con = DB.sql2o.open()) {
     String sqlFire = "SELECT * FROM monsters WHERE personId = :id AND type = 'fire';";
     List<FireMonster> fireMonsters = con.createQuery(sqlFire)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(FireMonster.class);
    allMonsters.addAll(fireMonsters);

      String sqlWater = "SELECT * FROM monsters WHERE personId = :id AND type = 'water';";
      List<WaterMonster> waterMonsters = con.createQuery(sqlWater)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeAndFetch(WaterMonster.class);
      allMonsters.addAll(waterMonsters);
   }
   return allMonsters;
 }

}
