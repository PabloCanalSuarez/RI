package uo.ri.persistence;

import java.sql.Date;

import uo.ri.business.dto.InvoiceDto;

public interface InvoiceGateway {

	public InvoiceDto findInvoice(Long numeroFactura);

	public Long createInvoiceFor(long numberInvoice, Date dateInvoice,
			double vat, double total);

	public long getGeneratedKey(Long numberInvoice);

	public Long generateInvoiceNumber();
}
