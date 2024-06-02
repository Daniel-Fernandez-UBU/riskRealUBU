package tfg.daniel.riskreal.riskrealApp.model;

import jakarta.persistence.*;

/**
 * Class User.
 * 
 * Entity class related with the table "Users" in the database.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
@Entity
@Table(name = "Users")
public class User {

    /** The email. */
    @Id
    @Column(name = "email", length = 100)
    private String email;

    /** The password. */
    @Column(name = "password", length = 100)
    private String password;

    /** The company. */
    @Column(name = "company", length = 50)
    private int company;

    /** The rol. */
    @Column(name = "rol", length = 50)
    private String rol;

    /** The firstname. */
    @Column(name = "firstname", length = 100)
    private String firstname;

    /** The lastname. */
    @Column(name = "lastname", length = 100)
    private String lastname;

    /** The gender. */
    @Column(name = "gender", length = 50)
    private String gender;

    /** The age. */
    @Column(name = "age", length = 50)
    private String age;

    /** The status. */
    @Column(name = "status")
    private int status;

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public int getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(int company) {
		this.company = company;
	}

	/**
	 * Gets the rol.
	 *
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Sets the rol.
	 *
	 * @param rol the new rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", company=" + company + ", rol=" + rol
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", gender=" + gender + ", age=" + age
				+ ", status=" + status + "]";
	}

	
    
}

