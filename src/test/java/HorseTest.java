import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void nameNullException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nameNullExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "  ", "\n\n", "\t\t"})
    void nameBlankException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "  ", "\n\n", "\t\t"})
    void nameBlankExceptionMessage(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void speedNegativeException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Fire", -1, 1));
    }

    @Test
    public void speedNegativeExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Fire", -1, 1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void distanceNegativeException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Fire", 1, -1));
    }

    @Test
    public void distanceNegativeExceptionMessage(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Fire", 1, -1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Fire", 1, 1);

        Field name = horse.getClass().getDeclaredField("name");
        name.setAccessible(true);
        String value = (String) name.get(horse);
        assertEquals("Fire", value);
    }

    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Fire", 1, 1);

        Field speed = horse.getClass().getDeclaredField("speed");
        speed.setAccessible(true);
        Double value = (Double) speed.get(horse);
        assertEquals(1, value);
    }

    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Fire", 1, 1);

        Field distance = horse.getClass().getDeclaredField("distance");
        distance.setAccessible(true);
        Double value = (Double) distance.get(horse);
        assertEquals(1, value);
    }

    @Test
    public void getZeroDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Fire", 1);

        Field distance = horse.getClass().getDeclaredField("distance");
        distance.setAccessible(true);
        Double value = (Double) distance.get(horse);
        assertEquals(0, value);
    }

    @Test
    public void moveGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Fire", 1, 1).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.2, 0.3, 0.4, 0.5, 0.6})
    public void moveSetDistance(double result) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Fire", 25, 150);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(result);

            horse.move();
            assertEquals((150 + 25*result), horse.getDistance());
        }

    }
}
