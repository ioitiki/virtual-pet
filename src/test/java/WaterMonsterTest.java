import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class WaterMonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void waterMonster_instantiatesWithHalfFullWaterLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    assertEquals(testWaterMonster.getWaterLevel(), (WaterMonster.MAX_WATER_LEVEL / 2));
  }

  @Test
  public void WaterMonster_instantiatesWithHalfFullPlayLevel_true(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(myWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void WaterMonster_instantiatesWithHalfFullSleepLevel_true(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(myWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void WaterMonster_instantiatesWithHalfFullFoodLevel_true(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(myWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void getName_returnsCorrectName_CookieWaterMonster(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals("CookieWaterMonster", myWaterMonster.getName());
  }

  @Test
  public void getPersonId_returnsCorrectPersonId_1(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(1, myWaterMonster.getPersonId());
  }

  @Test
  public void getPlayLevel_returnsCorrectPlayLevel_int(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(WaterMonster.MAX_PLAY_LEVEL / 2, myWaterMonster.getPlayLevel());
  }

  @Test
  public void getSleepLevel_returnsCorrectSleepLevel_int(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(WaterMonster.MAX_SLEEP_LEVEL / 2, myWaterMonster.getSleepLevel());
  }

  @Test
  public void getFoodLevel_returnsCorrectFoodLevel_int() {
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertEquals(WaterMonster.MAX_FOOD_LEVEL / 2, myWaterMonster.getFoodLevel());
  }

  @Test
  public void save_savesWaterMonsterToDB_true() {
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    myWaterMonster.save();
    assertEquals(WaterMonster.all().get(0), myWaterMonster);
  }

  @Test
  public void isAlive_confirmsWaterMonsterIsAliveIfAllLevelsAboveMinimum_true(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    assertTrue(myWaterMonster.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    testWaterMonster.depleteLevels();
    assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getWaterLevel(), (WaterMonster.MAX_WATER_LEVEL / 2) - 1);
  }

  @Test
  public void isAlive_recognizesWaterMonsterIsDeadWhenLevelsReachMinimum_false(){
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= WaterMonster.MAX_FOOD_LEVEL; i++){
      myWaterMonster.depleteLevels();
    }
    assertFalse(myWaterMonster.isAlive());
  }

  @Test
  public void sleep_increasesWaterMonsterSleepLevel_true() {
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    int currentSleepLevel = myWaterMonster.getSleepLevel();
    myWaterMonster.sleep();
    assertEquals(myWaterMonster.getSleepLevel(), currentSleepLevel + 1);
  }

  @Test
  public void play_increasesWaterMonsterPlayLevel_true() {
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    int currentPlayLevel = myWaterMonster.getPlayLevel();
    myWaterMonster.play();
    assertEquals(myWaterMonster.getPlayLevel(), currentPlayLevel + 1);
  }

  @Test
  public void feed_increasesWaterMonsterFoodLevel_true() {
    WaterMonster myWaterMonster = new WaterMonster("CookieWaterMonster", 1);
    int currentFoodLevel = myWaterMonster.getFoodLevel();
    myWaterMonster.feed();
    assertEquals(myWaterMonster.getFoodLevel(), currentFoodLevel + 1);
  }

  // Establishes exception (food)
  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++){
      testWaterMonster.feed();
    }
  }

  @Test
  public void WaterMonster_foodLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++){
      try {
        testWaterMonster.feed();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testWaterMonster.getFoodLevel() <= WaterMonster.MAX_FOOD_LEVEL);
  }


    // Establishes exception (play)
  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++){
      testWaterMonster.play();
    }
  }
    // Stops the loop when exception is reached/thrown
  @Test
  public void WaterMonster_playLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++){
      try {
        testWaterMonster.play();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testWaterMonster.getPlayLevel() <= WaterMonster.MAX_PLAY_LEVEL);
  }

@Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++){
      testWaterMonster.sleep();
    }
  }

  @Test
  public void WaterMonster_sleepLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++){
      try {
        testWaterMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testWaterMonster.getSleepLevel() <= WaterMonster.MAX_SLEEP_LEVEL);
  }

  @Test
  public void find_returnsWaterMonsterWithGivenId_WaterMonster() {
    WaterMonster myWaterMonster1 = new WaterMonster("CookieWaterMonster", 1);
    myWaterMonster1.save();
    assertEquals(myWaterMonster1, WaterMonster.find(myWaterMonster1.getId()));
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    Timestamp savedWaterMonsterBirthday = WaterMonster.find(testWaterMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedWaterMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.sleep();
    Timestamp savedWaterMonsterLastSlept = WaterMonster.find(testWaterMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.feed();
    Timestamp savedWaterMonsterLastAte = WaterMonster.find(testWaterMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.play();
    Timestamp savedWaterMonsterLastPlayed = WaterMonster.find(testWaterMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastPlayed));
  }

  @Test
  public void timer_executesDepleteLevelsMethod() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    int firstPlayLevel = testWaterMonster.getPlayLevel();
    testWaterMonster.startTimer();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testWaterMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterWaterMonsterDies() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.startTimer();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException exception){}
    assertFalse(testWaterMonster.isAlive());
    assertTrue(testWaterMonster.getFoodLevel() >= 0);
  }

  @Test
  public void water_increasesWaterMonsterWaterLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    testWaterMonster.water();
    assertTrue(testWaterMonster.getWaterLevel() > (WaterMonster.MAX_WATER_LEVEL / 2));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void water_throwsExceptionIfWaterLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_WATER_LEVEL); i++){
      testWaterMonster.water();
    }
  }

  @Test
  public void water_recordsTimeLastWateredInDatabase(){
    WaterMonster testWaterMonster = new WaterMonster("Loch Ness", 1);
    testWaterMonster.save();
    testWaterMonster.water();
    Timestamp savedWaterMonsterLastWater = WaterMonster.find(testWaterMonster.getId()).getLastWater();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastWater));
  }



}
