package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "SPAREPART_ID",
		"INTERVENTION_ID" }) }, name = "TSubstitutions")
public class Substitution extends BaseEntity {
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Intervention intervention;
	private int quantity;

	Substitution() {
	}

	public Substitution(SparePart sparePart, Intervention intervention) {
		Argument.isNotNull(sparePart);
		Argument.isNotNull(intervention);

		Associations.Sustitute.link(sparePart, this, intervention);

	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		this(sparePart, intervention);

		Argument.isTrue(quantity > 0);

		this.quantity = quantity;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	/* package */ void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public double getQuantity() {
		return sparePart.getPrice() * quantity;
	}

	/* package */ void _setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	/* package */ void _setSpareParts(SparePart sp) {
		this.sparePart = sp;
	}

//	/* package */ Set<Intervention> _getInterventions() {
//		return interventions;
//	}
//
//	public Set<Intervention> getInterventions() {
//		return new HashSet<>(interventions);
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((intervention == null) ? 0 : intervention.hashCode());
		result = prime * result
				+ ((sparePart == null) ? 0 : sparePart.hashCode());
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
		Substitution other = (Substitution) obj;
		if (intervention == null) {
			if (other.intervention != null)
				return false;
		} else if (!intervention.equals(other.intervention))
			return false;
		if (sparePart == null) {
			if (other.sparePart != null)
				return false;
		} else if (!sparePart.equals(other.sparePart))
			return false;
		return true;
	}

	public double getImporte() {
		return quantity * sparePart.getPrice();
	}

}
