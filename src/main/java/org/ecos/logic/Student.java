package org.ecos.logic;

@SuppressWarnings("unused")
public class Student {
    private int id;
    private String name;
    private String lastname;
    private int height;
    private int classroom;

    public Student(int id, String name, String lastname, int height, int classroom) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.height = height;
        this.classroom = classroom;
    }

    public Student(String name, String lastname, int height, int classroom) {
        this.name = name;
        this.lastname = lastname;
        this.height = height;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public int getHeight() {
        return height;
    }

    public int getClassroom() {
        return classroom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", height=" + height +
                ", classroom=" + classroom +
                '}';
    }
}
