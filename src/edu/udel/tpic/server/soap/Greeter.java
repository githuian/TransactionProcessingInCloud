package edu.udel.tpic.server.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Greeter {
  @WebMethod
  public String sayHello(String name){
    return "Hello, " + name+ "!";
  }
  @WebMethod
  public String sayGoodbye(String name){
    return "Goodbye, " + name + "!";
  }
}