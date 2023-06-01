package com.example.pp3.dto;

import java.util.List;

public class UserDTO {

    private int id;
    private String name;
    private byte age;
    private String email;
    private String password;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirtDate(String birtDate) {
        this.birthDate = birtDate;
    }

    private String birthDate;

    private List<String> roles;

    public UserDTO() {
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

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserDTO(int id, String name, byte age, String email, String password, List<String> roles) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO(int id, String name, String birthDate, String email, String password, List<String> roles) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
