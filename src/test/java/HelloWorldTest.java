import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import interfaces.InterfaceFoo;

/*
Es wird ein Interface (interfaceFoo) gemocked
Es wird festgelegt was zurückgegeben werden soll wenn die greet() Methode des Interfaces aufgerufen wird
Ausgabe
Vergleich
*/

public class HelloWorldTest {
    @Test
    public void testGreet() {
        InterfaceFoo interfaceFoo = mock(InterfaceFoo.class);
        when(interfaceFoo.greet()).thenReturn(interfaceFoo.HELLO_WORLD);
        System.out.println("AUSGABE: " + interfaceFoo.greet());
        assertEquals(interfaceFoo.greet(), interfaceFoo.HELLO_WORLD);
    }
}
