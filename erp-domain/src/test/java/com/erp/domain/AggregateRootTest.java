package com.erp.domain;

import com.erp.domain.common.AggregateRoot;
import com.erp.domain.common.DomainEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregateRootTest {

    private record TestEvent(String name) implements DomainEvent {}

    private static class TestAggregate extends AggregateRoot<String> {
        void publish(DomainEvent event) {
            registerEvent(event);
        }
    }

    // -------------------------------------------------------------------------
    // pullDomainEvents
    // -------------------------------------------------------------------------

    @Test
    void pullDomainEvents_shouldReturnEmptyList_whenNoEventsRegistered() {
        TestAggregate aggregate = new TestAggregate();

        List<DomainEvent> events = aggregate.pullDomainEvents();

        assertTrue(events.isEmpty());
    }

    @Test
    void pullDomainEvents_shouldReturnRegisteredEvents() {
        TestAggregate aggregate = new TestAggregate();
        TestEvent event = new TestEvent("created");

        aggregate.publish(event);
        List<DomainEvent> events = aggregate.pullDomainEvents();

        assertEquals(1, events.size());
        assertSame(event, events.get(0));
    }

    @Test
    void pullDomainEvents_shouldClearEventsAfterPull() {
        TestAggregate aggregate = new TestAggregate();
        aggregate.publish(new TestEvent("first"));

        aggregate.pullDomainEvents();
        List<DomainEvent> secondPull = aggregate.pullDomainEvents();

        assertTrue(secondPull.isEmpty());
    }

    @Test
    void pullDomainEvents_shouldReturnAllRegisteredEventsInOrder() {
        TestAggregate aggregate = new TestAggregate();
        TestEvent first = new TestEvent("first");
        TestEvent second = new TestEvent("second");

        aggregate.publish(first);
        aggregate.publish(second);

        List<DomainEvent> events = aggregate.pullDomainEvents();

        assertEquals(2, events.size());
        assertSame(first, events.get(0));
        assertSame(second, events.get(1));
    }

    @Test
    void pullDomainEvents_shouldReturnUnmodifiableList() {
        TestAggregate aggregate = new TestAggregate();
        aggregate.publish(new TestEvent("event"));

        List<DomainEvent> events = aggregate.pullDomainEvents();

        assertThrows(UnsupportedOperationException.class, () -> events.add(new TestEvent("extra")));
    }
}
