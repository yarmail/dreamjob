package ru.job4j.dreamjob.model;

import java.util.Objects;

/**
 * В этом уроке мы добавим в форму создания
 * вакансии поле ввода типа список.
 * Часто в приложениях используется справочные данные:
 * города, реквизиты банков и т.д.
 * В нашем приложении мы добавим города.
 */
public class City {
    private int id;
    private String name;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}