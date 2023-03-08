package com.example.learnpassvariablewithintent;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Person implements Serializable {
    private String name;
    private String surname;
    private Calendar birthDate;

    public Person(String name,String surname,Calendar birthDate){
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }
    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
}
