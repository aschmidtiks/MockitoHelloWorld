//import classesTest1.Bar;
import classesTest2.Bar;
import classesTest2.FooNotAvailable;
import classesTest2.InvalidQuestion;
import interfaces.InterfaceFoo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.argThat;

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
    private final static ValidQuestions VALID_QUESTIONS = new ValidQuestions();

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
    public void fooNotAvailable() {
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
        bar.question(interfaceFoo, InterfaceFoo.ANY_NEW_TOPICS);
        System.out.println("Verify that Foo has been asked the question");
        verify(interfaceFoo, times(1)).question(InterfaceFoo.ANY_NEW_TOPICS);
    }

    @Test
    public void filterInvalidQuestions() {
        Bar bar = new Bar();
        String invalidQuestion = "Invalid questions";
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

    @Test
    public void throwExceptionIfAnyInvalidQuestion() throws InvalidQuestion {
        assertThrows(InvalidQuestion.class, () -> {
            Bar bar = new Bar();
            String invalidQuestion = "Invalid question";
            when(interfaceFoo.questionStrictly((String) (argThat(new InValidQuestions())))).thenThrow(new InvalidQuestion());
            bar.questionStrictly(interfaceFoo, invalidQuestion);
        });
    }

    @Test
    public void getTodaysTopicPrice() throws InvalidQuestion {
        Bar bar = new Bar();
        when(interfaceFoo.questionStrictly((String) (argThat(new ValidQuestions())))).thenAnswer(new FooAnswers());
        when(interfaceFoo.getPrice(InterfaceFoo.TOPIC_MOCKITO)).thenReturn(20);

        String answer = bar.questionStrictly(interfaceFoo, InterfaceFoo.ANY_NEW_TOPICS);
        System.out.println("Answer is: " + answer);
        assertEquals(answer, "Topic is Mockito, price is 20");
        verify(interfaceFoo, times(1)).questionStrictly(InterfaceFoo.WHAT_IS_TODAYS_TOPIC);
        verify(interfaceFoo, times(1)).getPrice(InterfaceFoo.TOPIC_MOCKITO);
        verify(interfaceFoo, times(1)).bye();
    }

    @Test
    public void noNewTopic() throws InvalidQuestion {
        Bar bar = new Bar();
        when(interfaceFoo.questionStrictly(InterfaceFoo.ANY_NEW_TOPICS)).thenReturn(InterfaceFoo.NO_NEW_TOPIC);

        String answer = bar.questionStrictly(interfaceFoo, InterfaceFoo.ANY_NEW_TOPICS);
        System.out.println("Answer is: " + answer);
        assertEquals(answer, InterfaceFoo.NO_NEW_TOPIC);
        verify(interfaceFoo, times(1)).bye();
        verify(interfaceFoo, never()).questionStrictly(InterfaceFoo.WHAT_IS_TODAYS_TOPIC);
        verify(interfaceFoo, never()).getPrice(InterfaceFoo.TOPIC_MOCKITO);
    }

    private static class InValidQuestions implements ArgumentMatcher {
        @Override
        public boolean matches(Object argument) {
            return !VALID_QUESTIONS.matches(argument);
        }
    }

    private static class ValidQuestions implements ArgumentMatcher {

        @Override
        public boolean matches(Object argument) {
            return argument.equals(InterfaceFoo.ANY_NEW_TOPICS) || argument.equals(InterfaceFoo.WHAT_IS_TODAYS_TOPIC);
        }
    }

    private static class FooAnswers implements Answer {
        public String answer(InvocationOnMock invocation) throws Throwable {
            String arg = (String) invocation.getArguments()[0];
            if (InterfaceFoo.ANY_NEW_TOPICS.equals(arg)) {
                return InterfaceFoo.YES_NEW_TOPICS_AVAILABLE;
            } else if (InterfaceFoo.WHAT_IS_TODAYS_TOPIC.equals(arg)) {
                return InterfaceFoo.TOPIC_MOCKITO;
            } else {
                throw new InvalidQuestion();
            }
        }
    }
}