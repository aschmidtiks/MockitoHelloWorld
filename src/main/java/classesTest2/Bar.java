package classesTest2;

import interfaces.InterfaceFoo;

public class Bar {
    public String greet(InterfaceFoo interfaceFoo) {
        System.out.println("Bar invokes InterfaceFoo.greet");
        return interfaceFoo.greet();
    }

    public String question(InterfaceFoo interfaceFoo, String question) {
        verifyInterfaceFooConnection(interfaceFoo);
        if (InterfaceFoo.questionTopics.equals(question)) {
            return interfaceFoo.question(question);
        }
        return "invalid Question";
    }

    public String questionStrictly(InterfaceFoo interfaceFoo, String question) throws InvalidQuestion {
        verifyInterfaceFooConnection(interfaceFoo);
        String answer = interfaceFoo.questionStrictly(question);
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
