package com.nee.demo.edu.spring.orm.demo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_user")
@ToString
public class User {

    private int id;
    private String name;
    private String password;
}
