package com.test.commentsapp.toolchain;

import androidx.annotation.NonNull;

import java.util.Objects;

@SuppressWarnings({"unused"})
public class Triple<F, S, T> {
    public final F first;
    public final S second;
    public final T third;

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(first, triple.first) &&
                Objects.equals(second, triple.second) &&
                Objects.equals(third, triple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @NonNull
    @Override
    public String toString() {
        return "Triple{" +
                "mFirst=" + first +
                ", mSecond=" + second +
                ", mThird=" + third +
                '}';
    }
}
