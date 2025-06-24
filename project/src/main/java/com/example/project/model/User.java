package com.example.project.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users") // PostgreSQL上で "users" テーブルに対応
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private boolean admin;

    private boolean delete;

    // ゲッターとセッター
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }   
    public void setName(String name) {
        this.name = name;
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

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDelete() {
        return delete;
    }       
    public void setDelete(boolean delete) {
        this.delete = delete;
    }


}
