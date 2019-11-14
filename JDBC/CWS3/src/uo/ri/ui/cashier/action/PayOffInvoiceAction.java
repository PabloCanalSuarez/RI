package uo.ri.ui.cashier.action;

import java.util.Date;
import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.PaymentMeanDtoType;
import uo.ri.business.servicesLayer.implementation.InvoiceServiceImpl;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.ServicesFactory;

public class PayOffInvoiceAction implements Action {

	/**
	 * Algorithm:
	 * 
	 * - Ask user invoice number - Retrieve invoice info - Display invoice info
	 * 
	 * - Check is unpaid (status <> 'PAID') - List payment methods accepted for
	 * the customer
	 * 
	 * - Ask user to type amount to charge in each payment method Check that sum
	 * of amounts equals invoice amount - Record payments in the DDBB - Increase
	 * total for each payment method - Decrease money available in coupon if any
	 * has been redeemed - Finally, mark invoice as paid
	 * 
	 */
	@Override
	public void execute() throws BusinessException {
		Long invNumber = Console.readLong("Type invoice number: ");

		InvoiceServiceImpl invoiceServ = (InvoiceServiceImpl) ServicesFactory
				.getInvoiceCrudService();
		InvoiceDto inv = invoiceServ.findInvoice(invNumber);

		displayInvoice(inv.number, inv.date, inv.vat, inv.total);

		if (invoiceServ.checkInvoicePaid(inv) == true) {
			Console.printf("-- Invoice is already paid --\n");
		} else {
			// List payment methods for the customer
			List<PaymentMeanDto> list = invoiceServ
					.findPayMethodsForInvoice(inv.id);
			displayPaymentMethods(list);
		}
	}

	private void displayPaymentMethods(List<PaymentMeanDto> list) {
		for (PaymentMeanDto dto : list) {
			PaymentMeanDtoType dto2 = (PaymentMeanDtoType) dto;
			Console.printf("Payments accepted: \n");
			Console.printf("\t %s", dto2.type);
		}
	}

	private void displayInvoice(long numberInvoice, Date dateInvoice,
			double vat, double totalConIva) {
		Console.printf("Invoice number: %d\n", numberInvoice);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
		Console.printf("\tVAT: %.1f %% \n", vat);
		Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
	}

}
