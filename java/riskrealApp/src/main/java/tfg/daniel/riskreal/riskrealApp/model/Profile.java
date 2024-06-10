package tfg.daniel.riskreal.riskrealApp.model;

import jakarta.persistence.*;

/**
 * Class Profile.
 * 
 * Entity class related with the table "Profiles" in the database.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
@Entity
@Table(name = "Profiles")
public class Profile {

    /** The email. */
    @Id
    @Column(name = "email", length = 100)
    private String email;

    /** The profile. */
    @Column(name = "profile", length = 70)
    private String profile;

    /** The user. 
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user; */

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the username.
	 *
	 * @param email the new username
	 */
	public void setUsername(String email) {
		this.email = email;
	}

	/**
	 * Gets the profile.
	 *
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * Sets the profile.
	 *
	 * @param profile the new profile
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Profile [email=" + email + ", profile=" + profile + "]";
	}
    
	
    
}