package com.example.ecommerce.models;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;
import javax.jms.*;

@Component
public class MessageSender {

     //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
     //default broker URL is : tcp://localhost:61616"

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

     //Queue Name.You can create any/many queue names as per your requirement.
    private static String queueName = "MESSAGE_QUEUE";

    public void sendMessage(String msg) throws JMSException {

        System.out.println("url = " + url);

        //Getting JMS connection from the JMS server and starting it
        System.out.println("JMS connection ready.");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

         //Creating a non transactional session to send/receive JMS message.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

         //The queue will be created automatically on the server.
        Destination destination = session.createQueue(queueName);

         //Destination represents here our queue 'MESSAGE_QUEUE' on the JMS server.
         //MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(msg);
        producer.send(message);

        System.out.println("Message sent Successfully to the Queue");

        //Close the connection
        connection.close();
    }

}
