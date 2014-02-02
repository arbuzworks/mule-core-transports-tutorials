package org.mule.example.rmi;
        
public class HelloImpl implements Hello {
        
    public HelloImpl() {}

    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
        
}