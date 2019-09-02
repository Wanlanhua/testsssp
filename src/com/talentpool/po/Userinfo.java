package com.talentpool.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the userinfo database table.
 * 
 */
@Entity
@NamedQuery(name="Userinfo.findAll", query="SELECT u FROM Userinfo u")
public class Userinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Transient 
	private int age;
	private String area;

	private String city;

	private String contact;

	private String degree;

	private String gzd;

	private String head;

	private String idcard;

	private String idcardphotof;

	private String idcardphotoz;

	private String jobphoto;

	@Temporal(TemporalType.DATE)
	private Date joindate;

	private String name;

	private String password;

	private String province;

	private String resume;

	private String sex;

	private String speciality;

	private String status;

	private String username;

	private String workplant;


	public Userinfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGzd() {
		return this.gzd;
	}

	public void setGzd(String gzd) {
		this.gzd = gzd;
	}

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdcardphotof() {
		return this.idcardphotof;
	}

	public void setIdcardphotof(String idcardphotof) {
		this.idcardphotof = idcardphotof;
	}

	public String getIdcardphotoz() {
		return this.idcardphotoz;
	}

	public void setIdcardphotoz(String idcardphotoz) {
		this.idcardphotoz = idcardphotoz;
	}

	public String getJobphoto() {
		return this.jobphoto;
	}

	public void setJobphoto(String jobphoto) {
		this.jobphoto = jobphoto;
	}

	public Date getJoindate() {
		return this.joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
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

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
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

	public String getWorkplant() {
		return this.workplant;
	}

	public void setWorkplant(String workplant) {
		this.workplant = workplant;
	}

}