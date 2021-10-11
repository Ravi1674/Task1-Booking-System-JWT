package com.bookingsystem.api.entities;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	private String userName;
	private String userEmail;
	private String userPassword;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> role;

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", role="
				+ role + ", getUserName()=" + getUserName() + ", getUserEmail()=" + getUserEmail()
				+ ", getUserPassword()=" + getUserPassword() + ", getRole()=" + getRole() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
}
