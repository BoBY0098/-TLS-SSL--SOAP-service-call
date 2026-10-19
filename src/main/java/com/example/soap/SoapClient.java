package com.example.soap;

import com.example.soap.generated.HelloRequest;
import com.example.soap.generated.HelloResponse;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.namespace.QName;

@Service
@RequiredArgsConstructor
public class SoapClient {

    private final WebServiceTemplate webServiceTemplate;

    public String sayHello() {
        HelloRequest request = new HelloRequest();
        request.setName("Amir Hossein");

        // Create JAXBElement
        QName qName = new QName("http://learnwebservices.com/services/hello", "HelloRequest");
        JAXBElement<HelloRequest> jaxbElement = new JAXBElement<>(qName, HelloRequest.class, request);

        Object response = webServiceTemplate.marshalSendAndReceive(jaxbElement);

        // 4. Handle the response
        if (response instanceof JAXBElement) {
            response = ((JAXBElement<?>) response).getValue();
        }
        HelloResponse helloResponse = (HelloResponse) response;
        return helloResponse.getMessage();
    }
}
