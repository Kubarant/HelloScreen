package com.hello.screen.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProfileRegistrationDTO {

    @NotNull(message = "Imie jest wymaganym polem")
    @Size(min = 3, max = 20, message = "Imie powinno zawierać od 2 do 30 znaków")
    private String name;
    @NotNull(message = "Preferencje są wymaganym polem")
    private List<CategoryPreferences> preferences;
    @NotNull(message = "Słowa kluczowe są wymaganym polem")
    private List<String> keywords;

    public ProfileRegistrationDTO(String name, List<CategoryPreferences> preferences, List<String> keywords) {
        super();
        this.name = name;
        this.preferences = preferences;
        this.keywords = keywords;
    }

    public ProfileRegistrationDTO() {
    }

    public String getName() {
        return name;
    }

    public List<CategoryPreferences> getPreferences() {
        return preferences;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreferences(List<CategoryPreferences> preferences) {
        this.preferences = preferences;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "ProfileRegistrationDTO [name=" + name + ", preferences=" + preferences + ", keywords=" + keywords + "]";
    }

}
