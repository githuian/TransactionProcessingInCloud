package edu.udel.tpic.server.soap;

import java.util.Iterator;
import javax.xml.bind.JAXB;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SAAJResult;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import edu.udel.tpic.server.soap.jaxws.SayGoodbye;
import edu.udel.tpic.server.soap.jaxws.SayHello;


public class GreeterSOAPHandler {

  private static final String NAMESPACE_URI = "http://soap.server.tpic.udel.edu/";
  private static final QName SAY_HELLO_QNAME = new QName(NAMESPACE_URI, "sayHello");
  private static final QName SAY_GOODBYE_QNAME = new QName(NAMESPACE_URI, "sayGoodbye");

  private MessageFactory messageFactory;
  private GreeterAdapter greeterAdapter;

  public GreeterSOAPHandler() throws SOAPException {
    messageFactory = MessageFactory.newInstance();
    greeterAdapter = new GreeterAdapter();
  }

  public SOAPMessage handleSOAPRequest(SOAPMessage request) throws SOAPException {
    SOAPBody soapBody = request.getSOAPBody();
    Iterator iterator = soapBody.getChildElements();
    Object responsePojo = null;
    while (iterator.hasNext()) {
      Object next = iterator.next();
      if (next instanceof SOAPElement) {
        SOAPElement soapElement = (SOAPElement) next;
        QName qname = soapElement.getElementQName();
          if (SAY_HELLO_QNAME.equals(qname)) {
            responsePojo = handleSayHelloRequest(soapElement);
            break;
          } else if (SAY_GOODBYE_QNAME.equals(qname)) {
            responsePojo = handleSayGoodbyeRequest(soapElement);
            break;
          }
      }
    }
    SOAPMessage soapResponse = messageFactory.createMessage();
    soapBody = soapResponse.getSOAPBody();
    if (responsePojo != null) {
      JAXB.marshal(responsePojo, new SAAJResult(soapBody));
    } else {
      SOAPFault fault = soapBody.addFault();
      fault.setFaultString("Unrecognized SOAP request.");
    }
    return soapResponse;
  }

  private Object handleSayHelloRequest(SOAPElement soapElement) {
    SayHello sayHelloRequest = JAXB.unmarshal(new DOMSource(soapElement), SayHello.class);
    return greeterAdapter.sayHello(sayHelloRequest);
  }

  private Object handleSayGoodbyeRequest(SOAPElement soapElement) {
    SayGoodbye sayGoodbyeRequest =
        JAXB.unmarshal(new DOMSource(soapElement), SayGoodbye.class);
    return greeterAdapter.sayGoodbye(sayGoodbyeRequest);
  }
}
