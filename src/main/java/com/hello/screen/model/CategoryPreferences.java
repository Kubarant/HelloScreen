package com.hello.screen.model;

import java.util.Objects;

public class CategoryPreferences {

    private String label;
    private int value;

    public CategoryPreferences() {
        super();
    }

    public CategoryPreferences(String label, int value) {
        super();
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPreferences that = (CategoryPreferences) o;
        return value == that.value &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, value);
    }

    @Override
    public String toString() {
        return "CategoryPreferences{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}
