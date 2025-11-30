package com.ecommerce.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ecommerce.enums.MemberShipStatus;
import com.ecommerce.enums.OtpStatus;
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
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Roles role = Roles.USER;

	@NotBlank(message = "First Name is Required")
	private String firstName;

	@NotBlank(message = "Last Name is Required")
	private String lastName;

	@NotBlank(message = "Phone Number is Required")
	private String phoneNumber;

	@NotBlank(message = "Email is Required")
	private String email;

	@NotBlank(message = "Door Number is Required")
	private String doorNumber;

	@NotBlank(message = "Street is Required")
	private String street;

	@NotBlank(message = "Village is Required")
	private String village;

	@NotBlank(message = "Land Mark is Required")
	private String landMark;

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

	@NotBlank(message = "User Name is Required")
	private String userName;

	@NotBlank(message = "Password is Required")
	private String password;

	@Column(name = "membershipstatus")
	@Enumerated(EnumType.STRING)
	private MemberShipStatus memberShipStatus = MemberShipStatus.NORMAL;

	@Column(name = "otpNumber")
	private Long otpNumber = 0L;

	@Column(name = "otpStatus")
	@Enumerated(EnumType.STRING)
	private OtpStatus otpStatus = OtpStatus.GENERATE;

	private LocalDate registeredDate;

	@Column(name = "otp_expiry")
	private LocalDateTime otpExpiry;

	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public String getOtpStatus() {
		return otpStatus != null ? otpStatus.name() : null;
	}

	public String getUserRole() {
		return role != null ? role.name() : null;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MemberShipStatus getMemberShipStatus() {
		return memberShipStatus;
	}

	public void setMemberShipStatus(MemberShipStatus memberShipStatus) {
		this.memberShipStatus = memberShipStatus;
	}

	public long getOtpNumber() {
		return otpNumber;
	}

	public void setOtpNumber(long otpNumber) {
		this.otpNumber = otpNumber;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public void setOtpStatus(OtpStatus otpStatus) {
		this.otpStatus = otpStatus;
	}

	public Users(int userId, Roles role, @NotBlank(message = "First Name is Required") String firstName,
			@NotBlank(message = "Last Name is Required") String lastName,
			@NotBlank(message = "Phone Number is Required") String phoneNumber,
			@NotBlank(message = "Email is Required") String email,
			@NotBlank(message = "Door Number is Required") String doorNumber,
			@NotBlank(message = "Street is Required") String street,
			@NotBlank(message = "Village is Required") String village,
			@NotBlank(message = "Land Mark is Required") String landMark,
			@NotBlank(message = "City is Required") String city,
			@NotBlank(message = "District is Required") String district,
			@NotBlank(message = "Postal Code is Required") String postalCode,
			@NotBlank(message = "State is Required") String state,
			@NotBlank(message = "Country is Required") String country,
			@NotBlank(message = "User Name is Required") String userName,
			@NotBlank(message = "Password is Required") String password, MemberShipStatus memberShipStatus,
			long otpNumber, OtpStatus otpStatus, LocalDate registeredDate) {
		super();
		this.userId = userId;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.doorNumber = doorNumber;
		this.street = street;
		this.village = village;
		this.landMark = landMark;
		this.city = city;
		this.district = district;
		this.postalCode = postalCode;
		this.state = state;
		this.country = country;
		this.userName = userName;
		this.password = password;
		this.memberShipStatus = memberShipStatus;
		this.otpNumber = otpNumber;
		this.otpStatus = otpStatus;
		this.registeredDate = registeredDate;
	}

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

}
