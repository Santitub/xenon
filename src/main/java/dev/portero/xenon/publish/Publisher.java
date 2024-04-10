package dev.portero.xenon.publish;

public interface Publisher {

    void subscribe(Object subscriber);

    void publish(PublishEvent publishEvent);

}
