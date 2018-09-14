package stepik.functions.other;

public interface Sendable<T> {
    String getFrom();
    String getTo();
    T  getContent();
}