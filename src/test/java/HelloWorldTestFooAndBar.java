import classesTest1.Bar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import interfaces.InterfaceFoo;

/*
@Beforeeach
Es wird ein Interface gemocked
Der Rückgabe werd für die Methode greet() des interfaces wird festgelegt

Erster Test:
Ob die greet Funktion des interfaces Foo den richtigen wert (HELLO_WORLD) ausgibt

Zweiter Test:
Ob das Bar Objekt den richtige Wert (HELLO_WORLD) durch übergabe des Interfaces ausgibt
*/

public class HelloWorldTestFooAndBar {
    private InterfaceFoo interfaceFoo;

    @BeforeEach
    public void setupMock() {
        interfaceFoo = mock(InterfaceFoo.class);
        when(interfaceFoo.greet()).thenReturn(interfaceFoo.HELLO_WORLD);
    }

    @Test
    public void fooGreets() {
        System.out.println("Foo greets: " + interfaceFoo.greet());
        assertEquals(interfaceFoo.HELLO_WORLD, interfaceFoo.greet());
    }

    @Test
    public void barGreets() {
        Bar bar = new Bar();
        assertEquals(interfaceFoo.HELLO_WORLD, bar.greet(interfaceFoo));
    }
}
