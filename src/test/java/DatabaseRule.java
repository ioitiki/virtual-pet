import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/virtual_pet_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deletePersons = "DELETE FROM persons *;";
      String deleteMonsters= "DELETE FROM monsters *;";
      con.createQuery(deletePersons).executeUpdate();
      con.createQuery(deleteMonsters).executeUpdate();
    }
  }

}
