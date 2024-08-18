import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class HorseTest {
    public Horse horse;

    // конструктор------------------------------------------------------
    @Test
    void HorseConstructor_CheckingFirstParameter_Name_IsNullTest() {
        Throwable thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 2, 0)
        );
        assertEquals("Name cannot be null.", thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("argsForTest")
    void HorseConstructor_CheckingFirstParameter_Name_IsBlankTest(String name) {
        Throwable thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 2, 0)
        );
        assertEquals("Name cannot be blank.", thrown.getMessage());
    }

    static Stream<String> argsForTest() {
        return Stream.of("\t", "\r", "\n", "\f", " ");
    }

    @Test
    void HorseConstructor_CheckingSecondParameter_Speed_IsNegativeNumberTest() {
        Throwable thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Pony", -1, 0)
        );
        assertEquals("Speed cannot be negative.", thrown.getMessage());
    }

    @Test
    void HorseConstructor_CheckingThirdParameter_Distance_IsNegativeNumberTest() {
        Throwable thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Pony", 2, -1)
        );
        assertEquals("Distance cannot be negative.", thrown.getMessage());
    }

    @BeforeEach
    public void initClass() {
        horse = new Horse("Test", 2,1);
    }

    //метод getName-------------------------------------------------------
    @Test
    void HorseName_CheckIfReturnFirstParameter_getNameTest() {
        assertEquals("Test", horse.getName());
    }

    //метод getSpeed------------------------------------------------------
    @Test
    void HorseSpeed_CheckIfReturnSecondParameter_getSpeedTest() {
        assertEquals(2, horse.getSpeed());
    }

    //метод getDistance---------------------------------------------------
    @Test
    void HorseDistance_CheckIfReturnThirdParameter_getDistanceTest() {
        assertEquals(1, horse.getDistance());
        horse = new Horse("Test", 2, 3);
        assertEquals(3, horse.getDistance());
    }

    //метод move----------------------------------------------------------
    @Test
    void HorseMove_CheckIfGetRandomDoubleWasCalled_MoveTest() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2,0.9));
        }
    }
    @ParameterizedTest
    @CsvSource ({
            "0.3, 1.6",
            "0.4, 1.8",
            "0.5, 2.0",
            "0.6, 2.2",
            "0.7, 2.4",
            "0.8, 2.6"
    })
    void HorseMove_CheckIfDistanceCountsAccordingFormula_MoveTest(double randomNumber, double distance) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(randomNumber);
            horse.move();
            assertEquals(distance, horse.getDistance());
        }
    }
}