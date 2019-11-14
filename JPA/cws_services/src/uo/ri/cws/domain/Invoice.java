package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;
import alb.util.date.Dates;

@Entity
@Table(name = "TInvoices")
public class Invoice extends BaseEntity {
	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	@Column(unique = true)
	private Long number;

	private Date date;
	private double amount;
	private double vat;

	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy = "invoice")
	private Set<Charge> charges = new HashSet<>();

	Invoice() {
	}

	public Invoice(Long number, Date date) {
		// check arguments (always), through IllegalArgumentException
		Argument.isNotNull(number);
		Argument.isNotNull(date);

		// store the number
		this.number = number;

		// store a copy of the date
		this.date = new Date(date.getTime());
	}

	public Invoice(Long number) {
		this(number, new Date());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, new Date(), workOrders);
	}

	public Invoice(Long number, Date date, List<WorkOrder> workOrders) {
		this(number, date);
		for (WorkOrder workOrder : workOrders) {
			addWorkOrder(workOrder);
		}
	}

	/**
	 * Computed amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		// vat = ...
		// amount = ...

		this.vat = Dates.fromString("1/7/2012").before(this.date) ? 21.0 : 18.0;

		double total = 0.0;
		for (WorkOrder wo : this.workOrders)
			total += wo.getAmount();

		total += (total * this.vat) / 100;

		// redondeo a 2 decimales
		this.amount = Math.round(total * 100) / 100.0;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if (this.status != InvoiceStatus.NOT_YET_PAID) {
			throw new IllegalStateException();
		}
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		this.computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (this.status != InvoiceStatus.NOT_YET_PAID) {
			throw new IllegalStateException();
		}
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		this.computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts
	 *                               paid with charges to payment means do not
	 *                               cover the total of the invoice
	 */
	public void settle() {
		// ?
		this.status = InvoiceStatus.PAID;
	}

	public Long getNumber() {
		return number;
	}

	public Date getDate() {// RETURN A COPY OF THE DATE; IS MUTABLE
		return new Date(date.getTime());
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	/* package */ Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	/* package */ Set<Charge> _getCharges() {
		return charges;
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
		Invoice other = (Invoice) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount="
				+ amount + ", vat=" + vat + ", status=" + status + "]";
	}

	public void setDate(Date now) {
		this.date = new Date(date.getTime());
	}

}
