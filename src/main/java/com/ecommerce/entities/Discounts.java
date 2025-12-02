package com.ecommerce.entities;

import java.time.LocalDateTime;
import com.ecommerce.enums.DiscountTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "discounts")
public class Discounts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int discountId;

	@Enumerated(EnumType.STRING)
	private DiscountTypes discountType;

	@NotNull(message = "Discount Value is required")
	private int discountValue;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}



	public String getDiscountType() {
		return discountType != null ? discountType.name() : null;
	}
	
	public DiscountTypes getDiscountTypeEnum()
	{
		return discountType;
	}

	public void setDiscountType(DiscountTypes discountType) {
		this.discountType = discountType;
	}

	public int getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Discounts(int discountId,
			DiscountTypes discountType, @NotNull(message = "Discount Value is required") int discountValue,
			LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.discountId = discountId;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Discounts() {
		super();
		// TODO Auto-generated constructor stub
	}

}
