package ru.smole.notify;

public interface NotificationProvider {

    Notify notify(Notify.Type type, String name, int duration, String... msg);

    void remove(Notify notify);
}
