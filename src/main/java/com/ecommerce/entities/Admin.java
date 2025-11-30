package com.ecommerce.entities;

import com.ecommerce.enums.AdminActivityStatus;
import com.ecommerce.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "admin")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int adminId;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Roles role = Roles.ADMIN;

	@NotBlank(message = "First Name is Required")
	private String firstName;

	@NotBlank(message = "Last Name is Required")
	private String lastName;

	@NotBlank(message = "Phone Number is Required")
	private String phoneNumber;

	@NotBlank(message = "Email is Required")
	private String email;

	@NotBlank(message = "Address is Required")
	private String address;

	@NotBlank(message = "City is Required")
	private String city;

	@NotBlank(message = "District is Required")
	private String district;

	@NotBlank(message = "Postal Code is Required")
	private String postalCode;

	@NotBlank(message = "State is Required")
	private String state;

	@NotBlank(message = "Country is Required")
	private String country;

	@NotBlank(message = "Admin Name is Required")
	private String adminName;

	@NotBlank(message = "Password is Required")
	private String password;

	@Column(name = "adminStatus")
	@Enumerated(EnumType.STRING)
	private AdminActivityStatus adminActivityStatus = AdminActivityStatus.ACTIVE;

	public String getAdminActivityStatus() {
		return adminActivityStatus != null ? adminActivityStatus.name() : null;
	}

	public void setAdminActivityStatus(AdminActivityStatus adminActivityStatus) {
		this.adminActivityStatus = adminActivityStatus;
	}

	public String getUserRole() {
		return role != null ? role.name() : null;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Admin(int adminId, Roles role, @NotBlank(message = "First Name is Required") String firstName,
			@NotBlank(message = "Last Name is Required") String lastName,
			@NotBlank(message = "Phone Number is Required") String phoneNumber,
			@NotBlank(message = "Email is Required") String email,
			@NotBlank(message = "Address is Required") String address,
			@NotBlank(message = "City is Required") String city,
			@NotBlank(message = "District is Required") String district,
			@NotBlank(message = "Postal Code is Required") String postalCode,
			@NotBlank(message = "State is Required") String state,
			@NotBlank(message = "Country is Required") String country,
			@NotBlank(message = "Admin Name is Required") String adminName,
			@NotBlank(message = "Password is Required") String password) {
		super();
		this.adminId = adminId;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.city = city;
		this.district = district;
		this.postalCode = postalCode;
		this.state = state;
		this.country = country;
		this.adminName = adminName;
		this.password = password;
	}

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

}
