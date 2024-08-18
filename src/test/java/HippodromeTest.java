import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    List<Horse> horses;

    @BeforeEach
    void setHorses() {
        horses = new ArrayList<>();
    }

    @Test
    void HippodromeConstructor_CheckingParameter_ListOfHorses_IsNullTest() {
        Throwable thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        assertEquals("Horses cannot be null.", thrown.getMessage());
    }

    @Test
    void HippodromeConstructor_CheckingParameter_ListOfHorses_IsEmptyTest() {
        Throwable thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );
        assertEquals("Horses cannot be empty.", thrown.getMessage());
    }

    @Test
    void Hippodrome_CheckIfReturnUnmodifiedListOfHorses_getHorsesTest() {
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i), i));
        }
        List<Horse> unmodifiedHorses = new Hippodrome(horses).getHorses();
        Horse testHorse = new Horse("test", 1);

        //добавил проверку на выбрасывание исключения в случае попытки изменения unmodifiableList
        assertThrows(
                UnsupportedOperationException.class,
                () -> unmodifiedHorses.add(3,testHorse)
        );
        assertEquals(horses, unmodifiedHorses);
    }

    @Test
    void Hippodrome_IfEveryHorsesFromListInitMove_MoveTest() {
        List<Horse> horsesMock = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            horsesMock.add(mock(Horse.class));

        new Hippodrome(horsesMock).move();

        for (Horse horse : horsesMock)
            Mockito.verify(horse).move();
    }

    @Test
    void Hippodrome_getWinnerTest() {
        horses = List.of(
                new Horse("Bucephalus", 2.4),
                new Horse("Ace of Spades", 2.5),
                new Horse("Zephyr", 4.5)
        );
        Hippodrome hippodrome = new Hippodrome(horses);
        for (int i = 0; i < 5; i++) {
            hippodrome.move();
        }
        assertEquals("Zephyr", hippodrome.getHorses().get(2).getName());
    }
}