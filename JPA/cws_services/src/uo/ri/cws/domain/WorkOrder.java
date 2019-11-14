package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "VEHICLE_ID",
		"DATE" }) }, name = "TWorkOrders")
public class WorkOrder extends BaseEntity {

	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	@Column(unique = true)
	private Date date;
	private String description;
	private double amount = 0.0;
	@Enumerated(value = EnumType.STRING)
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	// Accidental atribute
	@ManyToOne
	private Vehicle vehicle;
	@ManyToOne
	private Mechanic mechanic;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();
	@ManyToOne
	private Invoice invoice;

	WorkOrder() {
	}

	public WorkOrder(Vehicle vehicle, Date date) {
		Argument.isNotNull(date);
		Argument.isNotNull(vehicle);

		this.date = new Date(date.getTime());
		this.vehicle = vehicle;
		Associations.Order.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, new Date());

		Argument.isNotEmpty(description);
		this.description = description;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	/* package */ void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	/* package */ Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	/* package */ void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	/* package */ void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description
				+ ", amount=" + amount + ", status=" + status + ", vehicle="
				+ vehicle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		WorkOrder other = (WorkOrder) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or -
	 *                               The work order is not linked with the
	 *                               invoice
	 */
	public void markAsInvoiced() {
		if (this.status != WorkOrderStatus.FINISHED) {
			throw new IllegalStateException();
		}

		if (invoice == null) {
			throw new IllegalStateException();
		}

		this.status = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state, or - The work order is not linked
	 *                               with a mechanic
	 */
	public void markAsFinished() {
		if (this.status != WorkOrderStatus.ASSIGNED) {
			throw new IllegalStateException();
		}

		if (this.mechanic == null) {
			throw new IllegalStateException();
		}

		// Computes the amount;
		double amount = 0.0;
		for (Intervention intervention : interventions) {
			amount += intervention.getAmount();
		}
		this.amount = amount;
		this.status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or -
	 *                               The work order is still linked with the
	 *                               invoice
	 */
	public void markBackToFinished() {
		if (this.status != WorkOrderStatus.INVOICED) {
			throw new IllegalStateException();
		}

		// if (this.invoice != null) {//IS THIS OKAY?
		// throw new IllegalStateException();
		// }

		this.status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN status,
	 *                               or - The work order is already linked with
	 *                               another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		if (this.status != WorkOrderStatus.OPEN) {
			throw new IllegalStateException();
		}
		// throw if its already linked?

		Associations.Assign.link(mechanic, this);
		this.status = WorkOrderStatus.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               status
	 */
	public void desassign() {
		if (this.status != WorkOrderStatus.ASSIGNED) {
			throw new IllegalStateException();
		}

		Associations.Assign.unlink(mechanic, this);
		this.status = WorkOrderStatus.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be
	 * moved back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED
	 *                               status
	 */
	public void reopen() {
		if (this.status != WorkOrderStatus.FINISHED) {
			throw new IllegalStateException();
		}

		this.status = WorkOrderStatus.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

}
