package com.talentpool.po;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the logininfo database table.
 * 
 */
@Entity
@NamedQuery(name="Logininfo.findAll", query="SELECT l FROM Logininfo l")
public class Logininfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String password;

	private String role;

	private String username;

	
	public Logininfo(Integer id, String password, String role, String username) {
		super();
		this.id = id;
		this.password = password;
		this.role = role;
		this.username = username;
	}

	public Logininfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}