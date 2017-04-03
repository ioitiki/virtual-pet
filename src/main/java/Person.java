import org.sql2o.*;
import java.util.List;
import java.util.List;

public class Person {
  private String name;
  private int id;

  public Person(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO persons (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
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

  public void updatePerson(String name) {
    this.name = name;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE persons SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
 }

}
