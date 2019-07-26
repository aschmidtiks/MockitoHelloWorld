//import classesTest1.Bar;
import classesTest2.Bar;
import classesTest2.FooNotAvailable;
import classesTest2.InvalidQuestion;
import interfaces.InterfaceFoo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
@Beforeeach
Es wird ein Interface gemocked
Der Rückgabe wert für die Methode greet() des interfaces wird festgelegt

Erster Test:
Ob die greet Funktion des interfaces Foo den richtigen wert (HELLO_WORLD) ausgibt

Zweiter Test:
Ob das Bar Objekt den richtige Wert (HELLO_WORLD) durch übergabe des Interfaces ausgibt
*/

public class HelloWorldTestWithQuestion {
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

    @Test
    public void fooNotAvailable() throws Exception {
        assertThrows(FooNotAvailable.class, () -> {
            Bar bar = new Bar();
            System.out.println("Foo is down so will not respond");
            when(interfaceFoo.greet()).thenReturn(null);
            System.out.println("Bar sends a question to Foo but since Foo is not available will throw FooNotAvailable");
            bar.question(interfaceFoo, "Hello Foo");
        });
    }

    @Test
    public void barQuestionsFoo() {
        Bar bar = new Bar();
        System.out.println("Bar asks Foo 'Any new topics?', it should get a response");
        bar.question(interfaceFoo, InterfaceFoo.questionTopics);
        System.out.println("Verify that Foo has been asked the question");
        verify(interfaceFoo, times(1)).question(InterfaceFoo.questionTopics);
    }

    @Test
    public void filterInvalidQuestions() {
        Bar bar = new Bar();
        String invalidQuestion = "Invalid questions";
        //bar.question(interfaceFoo, invalidQuestion);
        System.out.println(bar.question(interfaceFoo, invalidQuestion));
        System.out.println("Verify that question was never requested because of the invalid input");
        verify(interfaceFoo, never()).question(invalidQuestion);
    }

    @Test
    public void throwExceptionIfInvalidQuestion() throws InvalidQuestion {
        assertThrows(InvalidQuestion.class, () -> {
            Bar bar = new Bar();
            String invalidQuestion = "Invalid question";
            when(interfaceFoo.questionStrictly("Invalid question")).thenThrow(new InvalidQuestion());
            bar.questionStrictly(interfaceFoo, invalidQuestion);
        });
    }


}
