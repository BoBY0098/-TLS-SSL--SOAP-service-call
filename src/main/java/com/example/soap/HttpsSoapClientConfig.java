package com.example.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

@Configuration
public class HttpsSoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // You can generate classes from the WSDL later
        // For now we can use a simple approach or generate them
        marshaller.setContextPath("com.example.soap.generated");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) throws Exception {

        // 1. Load the empty TrustStore
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        trustStore.load(new ClassPathResource("truststore/empty-truststore.p12").getInputStream(),"changeit".toCharArray());

        // 2. Create TrustManager from the empty TrustStore
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        // 3. Create HTTPS Message Sender that uses ONLY this TrustStore
        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setTrustManagers(tmf.getTrustManagers());

        // 4. Create WebServiceTemplate
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri("https://apps.learnwebservices.com/services/hello");
        template.setMessageSender(messageSender);
        return template;
    }
}
