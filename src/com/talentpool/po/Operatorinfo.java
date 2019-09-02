package com.talentpool.po;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the operatorinfo database table.
 * 
 */
@Entity
@NamedQuery(name="Operatorinfo.findAll", query="SELECT o FROM Operatorinfo o")
public class Operatorinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String area;

	private String contact;

	private String department;

	private String name;

	private String password;

	private String status;

	private String username;

	
	public Operatorinfo(int id, String area, String contact, String department, String name, String password,
			String status, String username) {
		super();
		this.id = id;
		this.area = area;
		this.contact = contact;
		this.department = department;
		this.name = name;
		this.password = password;
		this.status = status;
		this.username = username;
	}

	public Operatorinfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}