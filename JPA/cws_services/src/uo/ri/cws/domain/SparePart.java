package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TSpareParts")
public class SparePart extends BaseEntity {
	@Column(unique = true)
	private String code;
	private String description;
	private double price;

	@OneToMany(mappedBy = "sparePart")
	private Set<Substitution> substitution = new HashSet<>();

	SparePart() {
	}

	public SparePart(String code) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public SparePart(String code, String description, double price) {
		this(code);

		Argument.isNotEmpty(description);
		Argument.isNotNull(price);

		this.description = description;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Set<Substitution> getSustituciones() {
		return new HashSet<>(substitution);
	}

	/* package */ Set<Substitution> _getSustituciones() {
		return substitution;
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
		SparePart other = (SparePart) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description
				+ ", price=" + price + "]";
	}

}
