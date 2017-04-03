import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void monster_instantiatesWithHalfFullPlayLevel_true(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(myMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullSleepLevel_true(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(myMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullFoodLevel_true(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(myMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void getName_returnsCorrectName_CookieMonster(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals("Cookiemonster", myMonster.getName());
  }

  @Test
  public void getPersonId_returnsCorrectPersonId_1(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(1, myMonster.getPersonId());
  }

  @Test
  public void getPlayLevel_returnsCorrectPlayLevel_int(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(Monster.MAX_PLAY_LEVEL / 2, myMonster.getPlayLevel());
  }

  @Test
  public void getSleepLevel_returnsCorrectSleepLevel_int(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(Monster.MAX_SLEEP_LEVEL / 2, myMonster.getSleepLevel());
  }

  @Test
  public void getFoodLevel_returnsCorrectFoodLevel_int() {
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertEquals(Monster.MAX_FOOD_LEVEL / 2, myMonster.getFoodLevel());
  }

  @Test
  public void save_savesMonsterToDB_true() {
    Monster myMonster = new Monster("Cookiemonster", 1);
    myMonster.save();
    assertTrue(Monster.all().get(0).equals(myMonster));
  }

  @Test
  public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    assertTrue(myMonster.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels_true() {
    Monster myMonster = new Monster("Cookiemonster", 1);
    myMonster.depleteLevels();
    assertEquals(myMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(myMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(myMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2) - 1);
  }

  @Test
  public void isAlive_recognizesMonsterIsDeadWhenLevelsReachMinimum_false(){
    Monster myMonster = new Monster("Cookiemonster", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= Monster.MAX_FOOD_LEVEL; i++){
      myMonster.depleteLevels();
    }
    assertFalse(myMonster.isAlive());
  }
}
