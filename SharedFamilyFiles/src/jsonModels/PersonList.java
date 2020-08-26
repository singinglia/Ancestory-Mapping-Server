package jsonModels;


import model.Person;

import java.util.List;

public class PersonList {
    List<Person> data;

    public PersonList(List<Person> data) {
        this.data = data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public List<Person> getData() {
        return data;
    }
}
