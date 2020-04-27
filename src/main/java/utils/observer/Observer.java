package utils.observer;


import utils.event.Event;

public interface Observer<E extends Event> {
    void update(E e);
}