package classesTest1;

import interfaces.InterfaceFoo;

public class Bar {
    public String greet(InterfaceFoo interfaceFoo) {
        System.out.println("Bar invokes InterfaceFoo.greet");
        return interfaceFoo.greet();
    }
}
