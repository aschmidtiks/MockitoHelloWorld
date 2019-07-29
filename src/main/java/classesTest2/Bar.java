package classesTest2;

import interfaces.InterfaceFoo;

public class Bar {
    public String greet(InterfaceFoo interfaceFoo) {
        System.out.println("Bar invokes InterfaceFoo.greet");
        return interfaceFoo.greet();
    }

    public String question(InterfaceFoo interfaceFoo, String question) {
        verifyInterfaceFooConnection(interfaceFoo);
        if (InterfaceFoo.ANY_NEW_TOPICS.equals(question)) {
            return interfaceFoo.question(question);
        }
        return "invalid Question";
    }

    public String questionStrictly(InterfaceFoo interfaceFoo, String question) throws InvalidQuestion {
        verifyInterfaceFooConnection(interfaceFoo);
        String answer = interfaceFoo.questionStrictly(question);
        switch (answer) {
            case InterfaceFoo.NO_NEW_TOPIC:
                System.out.println("No");
                System.out.println("Let's quit now");
                interfaceFoo.bye();
                break;
            case InterfaceFoo.YES_NEW_TOPICS_AVAILABLE:
                System.out.println("Yes");
                System.out.println(InterfaceFoo.WHAT_IS_TODAYS_TOPIC);
                answer = interfaceFoo.questionStrictly(InterfaceFoo.WHAT_IS_TODAYS_TOPIC);
                System.out.println("Topic name is " + answer);
                System.out.println("What is the price");
                int price = interfaceFoo.getPrice(answer);
                System.out.println("Price is " + price);
                System.out.println("Let's quit now");
                interfaceFoo.bye();
                answer = "Topic is " + answer + ", price is " + price;
                break;
            default:
                System.out.println("Answer is " + answer);
                break;
        }
        return answer;
    }

    public void verifyInterfaceFooConnection(InterfaceFoo interfaceFoo) {
        System.out.println("Is Foo available?");
        String response = interfaceFoo.greet();
        if (!InterfaceFoo.HELLO_WORLD.equals(response)) {
            System.out.println("No");
            throw new FooNotAvailable();
        }
        System.out.println("Yes");
    }
}
