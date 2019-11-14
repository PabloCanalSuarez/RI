package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.Invoice.InvoiceStatus;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "INVOICE_ID",
		"PAYMENTMEAN_ID" }) }, name = "TCharges")
public class Charge extends BaseEntity {
	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;
	private double amount;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean) {
		Associations.Charges.link(invoice, this, paymentMean);
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this(invoice, paymentMean);
		this.amount = amount;
		this.paymentMean.pay(amount);
		// store the amount
		// increment the paymentMean accumulated ( paymentMean.pay( -amount) )
		// link invoice, this and paymentMean
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		if (this.getInvoice().getStatus() == InvoiceStatus.PAID) {
			throw new IllegalStateException();
		}
		paymentMean.pay(amount);
		// Associations.Charges.unlink(invoice, this, paymentMean);
		// assert the invoice is not in PAID status
		// decrement the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlink invoice, this and paymentMean
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	/* package */ void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	/* package */ void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
