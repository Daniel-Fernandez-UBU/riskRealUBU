package tfg.daniel.riskreal.riskrealApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "company", length = 100)
    private String company;

    @Column(name = "rol", length = 50)
    private String rol;

    @Column(name = "firstname", length = 100)
    private String firstname;

    @Column(name = "lastname", length = 100)
    private String lastname;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "age", length = 50)
    private String age;

    @Column(name = "status")
    private boolean status;

}

