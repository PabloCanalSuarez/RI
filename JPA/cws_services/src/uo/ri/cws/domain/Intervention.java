package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "WORKORDER_ID",
		"MECHANIC_ID", "DATE" }) }, name = "TInterventions")
public class Intervention extends BaseEntity {
	@ManyToOne
	private WorkOrder workOrder;
	@ManyToOne
	private Mechanic mechanic;

	private Date date;
	private double minutes;

	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitution = new HashSet<>();

	Intervention() {
	}

	public Intervention(WorkOrder workOrder, Mechanic mechanic, Date date) {
		this.date = new Date(date.getTime());
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(workOrder, mechanic, new Date());

		Argument.isTrue(minutes >= 0);
		this.minutes = minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Date getDate() {
		return date;
	}

	public int getMinutes() {
		return (int) minutes;
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitution);
	}

	/* package */ Set<Substitution> _getSubstitution() {
		return substitution;
	}

	/* package */ void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	/* package */ void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public double getAmount() {
		double pricePerHour = workOrder.getVehicle().getVehicleType()
				.getPricePerHour();
		double p1 = this.minutes / 60;
		double price = pricePerHour * p1;

		for (Substitution s : substitution) {
			price += s.getQuantity();
		}
		return price;
	}

}
