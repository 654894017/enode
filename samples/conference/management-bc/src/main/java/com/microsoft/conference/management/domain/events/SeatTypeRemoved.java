package com.microsoft.conference.management.domain.events;

import org.enodeframework.eventing.DomainEvent;

public class SeatTypeRemoved extends DomainEvent<String> {
    public String SeatTypeId;

    public SeatTypeRemoved() {
    }

    public SeatTypeRemoved(String seatTypeId) {
        SeatTypeId = seatTypeId;
    }
}
