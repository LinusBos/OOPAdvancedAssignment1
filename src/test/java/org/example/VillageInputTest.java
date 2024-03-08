package org.example;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.DatabaseConnection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

class VillageInputTest {
    DatabaseConnection databaseConnection;
    @BeforeEach
    public void setup() {
        this.databaseConnection = mock(DatabaseConnection.class);
    }
    @Test
    public void testSaveFunctionWithMocking() {
        // Given
        String input = "TestSave\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream sysInStream = System.in; // Backup
        System.setIn(in);

        Village testVillage = new Village();

        when(databaseConnection.SaveVillage(testVillage,"TestSave")).thenReturn(true);

        VillageInput testVillageInput = new VillageInput(testVillage,databaseConnection);

        // When
        testVillageInput.Save();

        // Then
        verify(databaseConnection).SaveVillage(testVillage, "TestSave");

        System.setIn(sysInStream);
    }
    @Test
    public void testSaveFunctionWithMockingWithOverrideSaved() {
        // Given
        ArrayList<String> savedTownsForTest = new ArrayList<>();
        savedTownsForTest.add("TestSave");
        savedTownsForTest.add("TestTown2");
        savedTownsForTest.add("TestTown3");
        savedTownsForTest.add("TestTown4");
        savedTownsForTest.add("TestTown5");

        String input = "TestSave\ny";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream sysInStream = System.in; // Backup
        System.setIn(in);

        Village testVillage = new Village();

        when(databaseConnection.GetTownNames()).thenReturn(savedTownsForTest);
        when(databaseConnection.SaveVillage(testVillage,"TestSave")).thenReturn(true);

        VillageInput testVillageInput = new VillageInput(testVillage,databaseConnection);

        // When
        testVillageInput.Save();

        // Then
        verify(databaseConnection).SaveVillage(testVillage, "TestSave");

        System.setIn(sysInStream);
    }


    @Test
    public void testLoadFunctionWithMocking() {
        //Given
        ArrayList<String> savedTownsForTest = new ArrayList<>();
        savedTownsForTest.add("TestTown1");
        savedTownsForTest.add("TestTown2");
        savedTownsForTest.add("TestTown3");
        savedTownsForTest.add("TestTown4");
        savedTownsForTest.add("TestTown5");

        String input = "TestTown1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream sysInStream = System.in; // Backup
        System.setIn(in);

        Village testVillage = new Village();
        when(databaseConnection.GetTownNames()).thenReturn(savedTownsForTest);
        when(databaseConnection.LoadVillage(anyString())).thenReturn(testVillage);
        VillageInput testVillageInput = new VillageInput(testVillage, databaseConnection);

        testVillageInput.Load();

        verify(databaseConnection).LoadVillage("TestTown1");

        System.setIn(sysInStream);
    }
    @Test
    public void testLoadFunctionWithMockingWithWrongInput() {
        //Given
        ArrayList<String> savedTownsForTest = new ArrayList<>();
        savedTownsForTest.add("TestTown1");
        savedTownsForTest.add("TestTown2");
        savedTownsForTest.add("TestTown3");
        savedTownsForTest.add("TestTown4");
        savedTownsForTest.add("TestTown5");

        String input = "TestTown\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream sysInStream = System.in; // Backup
        System.setIn(in);

        Village testVillage = new Village();
        when(databaseConnection.GetTownNames()).thenReturn(savedTownsForTest);
        when(databaseConnection.LoadVillage(anyString())).thenReturn(testVillage);
        VillageInput testVillageInput = new VillageInput(testVillage, databaseConnection);

        testVillageInput.Load();

        verify(databaseConnection, never()).LoadVillage(any());

        System.setIn(sysInStream);
    }

}