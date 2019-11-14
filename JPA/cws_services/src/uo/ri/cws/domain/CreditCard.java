package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;
import alb.util.date.Dates;

@Entity
@Table(name = "TCreditCards")
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	private String type;
	private Date validThru;

	CreditCard() {
	}

	public CreditCard(String number) {
		Argument.isNotEmpty(number);
		this.number = number;
	}

	public CreditCard(String number, String type, Date validThru) {
		this(number);

		Argument.isNotEmpty(type);
		Argument.isNotNull(validThru);

		this.type = type;
		this.validThru = validThru;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public Date getValidThru() {
		return validThru;
	}

	@Override
	public void pay(double importe) {
		if (this.validThru.before(Dates.today()))
			throw new IllegalStateException();

		this.accumulated += importe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		CreditCard other = (CreditCard) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type
				+ ", validThru=" + validThru + ", getAccumulated()="
				+ super.toString() + "]";
	}

}
