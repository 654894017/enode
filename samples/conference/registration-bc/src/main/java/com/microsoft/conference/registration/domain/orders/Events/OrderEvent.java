package com.microsoft.conference.registration.domain.orders.Events;

import org.enodeframework.eventing.DomainEvent;

public abstract class OrderEvent extends DomainEvent<String> {
    public String ConferenceId;

    public OrderEvent() {
    }

    public OrderEvent(String conferenceId) {
        ConferenceId = conferenceId;
    }
}
