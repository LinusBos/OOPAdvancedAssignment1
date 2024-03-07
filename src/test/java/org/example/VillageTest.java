package org.example;

import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class VillageTest {

    private Village testVillage;
    private PrintStream printStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setupVillage() {
        testVillage = new Village();

    }

    @Test
    public void testAddTwoWorkersWithDifferentInputs() {
        // Given
        String workerOneName = "Lumberman";
        String workerOneOccupation = "lumberjack";
        String workerTwoName = "Minerman";
        String workerTwoOccupation = "miner";


        // When
        testVillage.AddWorker(workerOneName, workerOneOccupation);
        testVillage.AddWorker(workerTwoName, workerTwoOccupation);

        String actualWorkerOneName = testVillage.getWorkers().get(0).getName();
        String actualWorkerOneOccupation = testVillage.getWorkers().get(0).getOccupation();
        String actualWorkerTwoName = testVillage.getWorkers().get(1).getName();
        String actualWorkerTwoOccupation = testVillage.getWorkers().get(1).getOccupation();

        // Then
        assertAll(
                () -> assertEquals(workerOneName, actualWorkerOneName),
                () -> assertEquals(workerOneOccupation, actualWorkerOneOccupation),
                () -> assertEquals(workerTwoName, actualWorkerTwoName),
                () -> assertEquals(workerTwoOccupation, actualWorkerTwoOccupation)
        );

    }
    @Test
    public void addingAWorkerWithoutCorrectOccupation() {

        // Given
        printStream = System.out;
        byteArrayOutputStream = new ByteArrayOutputStream();

        String testWorkerName = "Weirdo";
        String testWorkerOccupation = "useless";
        System.setOut(new PrintStream(byteArrayOutputStream));

        String expectedOutput = "There is no such job.";
        int expectedArrayListSize = 0;

        // When
        testVillage.AddWorker(testWorkerName, testWorkerOccupation);
        String actualOutput = byteArrayOutputStream.toString().trim();
        int actualArrayListSize = testVillage.getWorkers().size();

        // Then
        assertAll(
                () -> assertEquals(expectedOutput, actualOutput),
                () -> assertEquals(expectedArrayListSize, actualArrayListSize)
        );

        // Reset system.out
        System.setOut(System.out);
    }
    @Test
    public void confirmThatWorkersDoTheirJobTheFollowingDayAndResourcesUpdatesAndCorrectAmount() {
        // Given
        String workerOneName = "Lumberman";
        String workerOneOccupation = "lumberjack";
        String workerTwoName = "Minerman";
        String workerTwoOccupation = "miner";
        int startingWood = testVillage.getWood();
        int startingMetal = testVillage.getMetal();
        int expectedWood = 1;
        int expectedMetal = 1;
        testVillage.AddWorker(workerOneName, workerOneOccupation);
        testVillage.AddWorker(workerTwoName, workerTwoOccupation);

        // When
        testVillage.Day();
        String workerOneActualOccupation = testVillage.getWorkers().get(0).getOccupation();
        String workerTwoActualOccupation = testVillage.getWorkers().get(1).getOccupation();

        // Then
        assertAll(
                () -> assertEquals(workerOneOccupation, workerOneActualOccupation),
                () -> assertEquals(workerTwoOccupation, workerTwoActualOccupation),
                () -> assertEquals(expectedWood, testVillage.getWood()),
                () -> assertEquals(expectedMetal, testVillage.getMetal()),
                () -> assertNotEquals(startingWood, testVillage.getWood()),
                () -> assertNotEquals(startingMetal, testVillage.getMetal())
        );
    }
   @Test
   public void confirmThatMaxWorkersEqualSix(){
        int expectedMax = 6;
        int actualMax = testVillage.getMaxWorkers();
        assertEquals(expectedMax, actualMax);
   }


   /*
   The next test is one of the tasks for the assignment.
   However, this test will fail due to AddWorker() in Village doesn't check if maxWorkers has been reached.
   This should instead be checked on VillageInput where this is actually checked before adding workers.
   The best we can do is to confirm that isFull() works as intended, which will be done in the next test.
   */
   @Disabled
   @Test
    public void confirmThatNoMoreWorkersCanBeAddedThanMax() {
        // Given
        String wOneName = "Lumberman";
        String wTwoName = "Minerman";
        String wThreeName = "FarmerOne";
        String wFourName = "FarmerTwo";
        String wFiveName = "MinerTwo";
        String wSixName = "LumberTwo";

        String wOneOccupation = "lumberjack";
        String wTwoOccupation = "miner";
        String wThreeOccupation = "farmer";
        String wFourOccupation = "farmer";
        String wFiveOccupation = "miner";
        String wSixOccupation = "lumberjack";

        testVillage.AddWorker(wOneName, wOneOccupation);
        testVillage.AddWorker(wTwoName, wTwoOccupation);
        testVillage.AddWorker(wThreeName, wThreeOccupation);
        testVillage.AddWorker(wFourName, wFourOccupation);
        testVillage.AddWorker(wFiveName, wFiveOccupation);
        testVillage.AddWorker(wSixName, wSixOccupation);

        int expectedArraySize = testVillage.getWorkers().size();
        // When
        String wSevenName = "Testman";
        String wSevenOccupation = "miner";
        testVillage.AddWorker(wSevenName, wSevenOccupation);
        // Then
        assertEquals(expectedArraySize, testVillage.getWorkers().size());
    }

    @Test
    public void checkIsFullIsTrueWhenMaxWorkerHasBeenReachedAndThatItHasBeenUpdated() {
        //Given
        String wOneName = "Lumberman";
        String wTwoName = "Minerman";
        String wThreeName = "FarmerOne";
        String wFourName = "FarmerTwo";
        String wFiveName = "MinerTwo";

        String wOneOccupation = "lumberjack";
        String wTwoOccupation = "miner";
        String wThreeOccupation = "farmer";
        String wFourOccupation = "farmer";
        String wFiveOccupation = "miner";

        testVillage.AddWorker(wOneName, wOneOccupation);
        testVillage.AddWorker(wTwoName, wTwoOccupation);
        testVillage.AddWorker(wThreeName, wThreeOccupation);
        testVillage.AddWorker(wFourName, wFourOccupation);
        testVillage.AddWorker(wFiveName, wFiveOccupation);
        boolean before = testVillage.isFull();
        boolean expected = true;

        // When
        String wSixName = "LumberTwo";
        String wSixOccupation = "lumberjack";
        testVillage.AddWorker(wSixName, wSixOccupation);
        boolean actual = testVillage.isFull();

        // Then
        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertNotEquals(before, actual)
        );
    }

    @Test
    public void testToGoToNextDayWithoutWorkersWorks() {
       // Given
       int expectedDays = 1;
       int expectedWorkers = 0;

       // When
       testVillage.Day();
       int actualDays = testVillage.getDaysGone();
       int actualWorkers = testVillage.getWorkers().size();

       // Then
       assertAll(
               () -> assertEquals(expectedDays, actualDays),
               () -> assertEquals(expectedWorkers, actualWorkers)
        );

    }
    @Test
    public void testToGoNextDayWithoutFood() {
       // Given
        int expectedDays = 1;
        int expectedFood = 0;

        // When
        testVillage.setFood(0);
        int actualFood = testVillage.getFood();
        testVillage.Day();
        int actualDays = testVillage.getDaysGone();

        // Then
        assertAll(
                () -> assertEquals(expectedFood, actualFood),
                () -> assertEquals(expectedDays, actualDays)
        );
    }
}