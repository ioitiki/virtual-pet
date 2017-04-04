import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class FireMonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void FireMonster_instantiatesWithHalfFullPlayLevel_true(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(myFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void FireMonster_instantiatesWithHalfFullSleepLevel_true(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(myFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void FireMonster_instantiatesWithHalfFullFoodLevel_true(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(myFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void getName_returnsCorrectName_CookieFireMonster(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals("CookieFireMonster", myFireMonster.getName());
  }

  @Test
  public void getPersonId_returnsCorrectPersonId_1(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(1, myFireMonster.getPersonId());
  }

  @Test
  public void getPlayLevel_returnsCorrectPlayLevel_int(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(FireMonster.MAX_PLAY_LEVEL / 2, myFireMonster.getPlayLevel());
  }

  @Test
  public void getSleepLevel_returnsCorrectSleepLevel_int(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(FireMonster.MAX_SLEEP_LEVEL / 2, myFireMonster.getSleepLevel());
  }

  @Test
  public void getFoodLevel_returnsCorrectFoodLevel_int() {
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertEquals(FireMonster.MAX_FOOD_LEVEL / 2, myFireMonster.getFoodLevel());
  }

  @Test
  public void save_savesFireMonsterToDB_true() {
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    myFireMonster.save();
    assertEquals(FireMonster.all().get(0), myFireMonster);
  }

  @Test
  public void isAlive_confirmsFireMonsterIsAliveIfAllLevelsAboveMinimum_true(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    assertTrue(myFireMonster.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels_true() {
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    myFireMonster.depleteLevels();
    assertEquals(myFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(myFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(myFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2) - 1);
  }

  @Test
  public void isAlive_recognizesFireMonsterIsDeadWhenLevelsReachMinimum_false(){
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= FireMonster.MAX_FOOD_LEVEL; i++){
      myFireMonster.depleteLevels();
    }
    assertFalse(myFireMonster.isAlive());
  }

  @Test
  public void sleep_increasesFireMonsterSleepLevel_true() {
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    int currentSleepLevel = myFireMonster.getSleepLevel();
    myFireMonster.sleep();
    assertEquals(myFireMonster.getSleepLevel(), currentSleepLevel + 1);
  }

  @Test
  public void play_increasesFireMonsterPlayLevel_true() {
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    int currentPlayLevel = myFireMonster.getPlayLevel();
    myFireMonster.play();
    assertEquals(myFireMonster.getPlayLevel(), currentPlayLevel + 1);
  }

  @Test
  public void feed_increasesFireMonsterFoodLevel_true() {
    FireMonster myFireMonster = new FireMonster("CookieFireMonster", 1);
    int currentFoodLevel = myFireMonster.getFoodLevel();
    myFireMonster.feed();
    assertEquals(myFireMonster.getFoodLevel(), currentFoodLevel + 1);
  }

  // Establishes exception (food)
  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FOOD_LEVEL); i++){
      testFireMonster.feed();
    }
  }

  @Test
  public void FireMonster_foodLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FOOD_LEVEL); i++){
      try {
        testFireMonster.feed();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testFireMonster.getFoodLevel() <= FireMonster.MAX_FOOD_LEVEL);
  }


    // Establishes exception (play)
  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_PLAY_LEVEL); i++){
      testFireMonster.play();
    }
  }
    // Stops the loop when exception is reached/thrown
  @Test
  public void FireMonster_playLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_PLAY_LEVEL); i++){
      try {
        testFireMonster.play();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testFireMonster.getPlayLevel() <= FireMonster.MAX_PLAY_LEVEL);
  }

@Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_SLEEP_LEVEL); i++){
      testFireMonster.sleep();
    }
  }

  @Test
  public void FireMonster_sleepLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_SLEEP_LEVEL); i++){
      try {
        testFireMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testFireMonster.getSleepLevel() <= FireMonster.MAX_SLEEP_LEVEL);
  }

  @Test
  public void find_returnsFireMonsterWithGivenId_FireMonster() {
    FireMonster myFireMonster1 = new FireMonster("CookieFireMonster", 1);
    myFireMonster1.save();
    assertEquals(myFireMonster1, FireMonster.find(myFireMonster1.getId()));
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    Timestamp savedFireMonsterBirthday = FireMonster.find(testFireMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedFireMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    testFireMonster.sleep();
    Timestamp savedFireMonsterLastSlept = FireMonster.find(testFireMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    testFireMonster.feed();
    Timestamp savedFireMonsterLastAte = FireMonster.find(testFireMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    testFireMonster.play();
    Timestamp savedFireMonsterLastPlayed = FireMonster.find(testFireMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastPlayed));
  }

  @Test
  public void timer_executesDepleteLevelsMethod() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    int firstPlayLevel = testFireMonster.getPlayLevel();
    testFireMonster.startTimer();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testFireMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterFireMonsterDies() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.startTimer();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException exception){}
    assertFalse(testFireMonster.isAlive());
    assertTrue(testFireMonster.getFoodLevel() >= 0);
  }

  @Test
  public void fireMonster_instantiatesWithHalfFullFireLevel(){
    FireMonster testFireMonster = new FireMonster("Smokey", 1);
    assertEquals(testFireMonster.getFireLevel(), (FireMonster.MAX_FIRE_LEVEL / 2));
  }

  @Test
  public void kindling_increasesFireMonsterFireLevel(){
    FireMonster testFireMonster = new FireMonster("Smokey", 1);
    testFireMonster.kindling();
    assertTrue(testFireMonster.getFireLevel() > (FireMonster.MAX_FIRE_LEVEL / 2));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void kindling_throwsExceptionIfFireLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Smokey", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FIRE_LEVEL); i++){
      testFireMonster.kindling();
    }
  }

  @Test
  public void depleteLevels_reducesAllLevels(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.depleteLevels();
    assertEquals(testFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(testFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(testFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2) - 1);
    assertEquals(testFireMonster.getFireLevel(), (FireMonster.MAX_FIRE_LEVEL / 2) - 1);
  }

  @Test
  public void kindling_recordsTimeLastKinglingInDatabase(){
    FireMonster testFireMonster = new FireMonster("Charizard", 1);
    testFireMonster.save();
    testFireMonster.kindling();
    Timestamp savedFireMonsterLastKindling = FireMonster.find(testFireMonster.getId()).getLastKindling();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastKindling));
  }
}
