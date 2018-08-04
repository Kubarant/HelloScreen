package com.hello.screen.model;

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
}
