package edu.udel.tpic.server.soap;

import edu.udel.tpic.server.soap.jaxws.SayGoodbye;
import edu.udel.tpic.server.soap.jaxws.SayGoodbyeResponse;
import edu.udel.tpic.server.soap.jaxws.SayHello;
import edu.udel.tpic.server.soap.jaxws.SayHelloResponse;

;
public class GreeterAdapter {
  private Greeter greeter = new Greeter();

  public SayHelloResponse sayHello(SayHello request){
    String name = request.getArg0();
    String responseGreeting = greeter.sayHello(name);
    SayHelloResponse response = new SayHelloResponse();
    response.setReturn(responseGreeting);
    return response;
  }

  public SayGoodbyeResponse sayGoodbye(SayGoodbye request){
    String name = request.getArg0();
    String responseGreeting = greeter.sayGoodbye(name);
    SayGoodbyeResponse response = new SayGoodbyeResponse();
    response.setReturn(responseGreeting);
    return response;
  }
}
