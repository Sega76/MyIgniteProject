package stepik.generics;

import java.util.Objects;

public class Pair <T, X> {
    private final T first;
    private final X second;

    public static <T,X> Pair<T, X> of(T t, X x) {
        return new Pair<>(t, x);
    }

    public Pair(T first, X second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public X getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
