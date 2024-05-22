package tfg.daniel.riskreal.riskrealApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Profiles")
public class Profile {

    @Id
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "profile", length = 70)
    private String profile;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;

	public String getEmail() {
		return email;
	}

	public void setUsername(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Profile [email=" + email + ", profile=" + profile + "]";
	}
    
	
    
}