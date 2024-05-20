package tfg.daniel.riskreal.riskrealApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Profiles")
public class Profile {

    @Id
    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "profile", length = 70)
    private String profile;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;
}