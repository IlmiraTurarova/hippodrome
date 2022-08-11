import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void horsesNullException(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void horsesNullExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void horsesEmptyException(){
        List<Horse> horses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    public void horsesEmptyExceptionMessage(){
        List<Horse> horses = new ArrayList<>();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("name " + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse: horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner() {
        List<Horse> horses = new ArrayList<>();
        Horse horse1 = new Horse("name 1", 10, 10);
        Horse horse2 = new Horse("name 2", 10, 100);
        Horse horse3 = new Horse("name 3", 10, 20);
        Horse horse4 = new Horse("name 4", 10, 15);
        Horse horse5 = new Horse("name 5", 10, 50);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4, horse5));

        assertEquals(horse2, hippodrome.getWinner());
    }
}
