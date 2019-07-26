package interfaces;

import classesTest2.InvalidQuestion;

public interface InterfaceFoo {
    String HELLO_WORLD = "Hello World";
    String questionTopics = "Are there any new topics?";
    String greet();
    String question(String question);
    String questionStrictly(String question) throws InvalidQuestion;
}
