import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

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
    assertEquals(Monster.all().get(0), myMonster);
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

  @Test
  public void sleep_increasesMonsterSleepLevel_true() {
    Monster myMonster = new Monster("Cookiemonster", 1);
    int currentSleepLevel = myMonster.getSleepLevel();
    myMonster.sleep();
    assertEquals(myMonster.getSleepLevel(), currentSleepLevel + 1);
  }

  @Test
  public void play_increasesMonsterPlayLevel_true() {
    Monster myMonster = new Monster("Cookiemonster", 1);
    int currentPlayLevel = myMonster.getPlayLevel();
    myMonster.play();
    assertEquals(myMonster.getPlayLevel(), currentPlayLevel + 1);
  }

  @Test
  public void feed_increasesMonsterFoodLevel_true() {
    Monster myMonster = new Monster("Cookiemonster", 1);
    int currentFoodLevel = myMonster.getFoodLevel();
    myMonster.feed();
    assertEquals(myMonster.getFoodLevel(), currentFoodLevel + 1);
  }

  // Establishes exception (food)
  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
      testMonster.feed();
    }
  }

  @Test
  public void monster_foodLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
      try {
        testMonster.feed();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL);
  }


    // Establishes exception (play)
  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
      testMonster.play();
    }
  }
    // Stops the loop when exception is reached/thrown
  @Test
  public void monster_playLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
      try {
        testMonster.play();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testMonster.getPlayLevel() <= Monster.MAX_PLAY_LEVEL);
  }

@Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
      testMonster.sleep();
    }
  }

  @Test
  public void monster_sleepLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
      try {
        testMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testMonster.getSleepLevel() <= Monster.MAX_SLEEP_LEVEL);
  }

  @Test
  public void find_returnsMonsterWithGivenId_monster() {
    Monster myMonster1 = new Monster("Cookiemonster", 1);
    myMonster1.save();
    assertEquals(myMonster1, Monster.find(myMonster1.getId()));
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    Timestamp savedMonsterBirthday = Monster.find(testMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    testMonster.sleep();
    Timestamp savedMonsterLastSlept = Monster.find(testMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    testMonster.feed();
    Timestamp savedMonsterLastAte = Monster.find(testMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    testMonster.play();
    Timestamp savedMonsterLastPlayed = Monster.find(testMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastPlayed));
  }

}
