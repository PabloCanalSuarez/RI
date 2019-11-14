package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TVouchers")
public class Voucher extends PaymentMean {
	@Column(unique = true)
	private String code;
	private double available;
	private String description;

	/**
	 * Augments the accumulated and decrements the available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		if (this.available < amount)
			throw new IllegalStateException();

		this.accumulated += amount;
		this.available -= amount;
	}

	Voucher() {
	}

	public Voucher(String code, double available) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public Voucher(String code, double available, String description) {
		this(code, available);
		Argument.isNotNull(available);
		Argument.isNotEmpty(description);

		this.available = available;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available
				+ ", description=" + description + ", toString()="
				+ super.toString() + "]";
	}

	public double getDisponible() {
		return available;
	}

	public void setDescripcion(String desc) {
		this.description = desc;
	}

}
