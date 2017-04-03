import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Date;
// import java.util.Date;
import java.util.ArrayList;

public class Monster {
  private String name;
  private int personId;
  private int id;
  private Timestamp birthday;
  private Timestamp lastSlept;
  private Timestamp lastAte;
  private Timestamp lastPlayed;
  private int foodLevel;
  private int sleepLevel;
  private int playLevel;

  public static final int MAX_FOOD_LEVEL = 3;
  public static final int MAX_SLEEP_LEVEL = 8;
  public static final int MAX_PLAY_LEVEL = 12;
  public static final int MIN_ALL_LEVELS = 0;

  public Monster(String name, int personId) {
    this.name = name;
    this.personId = personId;
    this.playLevel = MAX_PLAY_LEVEL / 2;
    this.sleepLevel = MAX_SLEEP_LEVEL / 2;
    this.foodLevel = MAX_FOOD_LEVEL / 2;

  }

  public String getName(){
    return name;
  }

  public int getPersonId() {
    return personId;
  }

  public int getPlayLevel() {
    return playLevel;
  }

  public int getSleepLevel() {
    return sleepLevel;
  }

  public int getFoodLevel() {
    return foodLevel;
  }

  public Timestamp getLastAte(){
    return lastAte;
  }

  public Timestamp getLastSlept(){
    return lastSlept;
  }

  public Timestamp getLastPlayed(){
    return lastPlayed;
  }

  public Timestamp getBirthday() {
    return birthday;
  }

  public int getId(){
    return id;
  }

  public void save(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO monsters (name, personId, birthday) VALUES (:name, :personId, now());";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("personId", this.personId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Monster> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM monsters;";
      return con.createQuery(sql)
        .executeAndFetch(Monster.class);
    }
  }

  @Override
  public boolean equals(Object otherMonster) {
    if (!(otherMonster instanceof Monster)) {
      return false;
    } else {
      Monster newMonster = (Monster) otherMonster;
      return this.getName().equals(newMonster.getName()) &&
             this.getPersonId() == newMonster.getPersonId() &&
             this.getId() == newMonster.getId();
    }
  }

  public static Monster find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM monsters WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Monster.class);
    }
  }

  public boolean isAlive() {
    if(foodLevel <= MIN_ALL_LEVELS || playLevel <= MIN_ALL_LEVELS || sleepLevel <= MIN_ALL_LEVELS) {
      return false;
    }
    return true;
  }

  public void depleteLevels() {
    playLevel--;
    foodLevel--;
    sleepLevel--;
  }

  public void sleep(){
    if (sleepLevel >= MAX_SLEEP_LEVEL){
      throw new UnsupportedOperationException("You cannot make your monster sleep anymore!");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE monsters SET lastslept = now() WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    sleepLevel++;
  }

  public void play(){
    if (playLevel >= MAX_PLAY_LEVEL){
      throw new UnsupportedOperationException("You cannot play with monster anymore!");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE monsters SET lastplayed = now() WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    playLevel++;
  }

  public void feed(){
    if (foodLevel >= MAX_FOOD_LEVEL){
      throw new UnsupportedOperationException("You cannot feed your monster anymore!");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE monsters SET lastate = now() WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    foodLevel++;
  }

}
