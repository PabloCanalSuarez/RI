package uo.ri.ui.cashier.action;

import java.util.Date;
import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.servicesLayer.implementation.InvoiceServiceImpl;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.ServicesFactory;

public class UnchargedRepairsClientAction implements Action {

	/**
	 * Process:
	 * 
	 * - Ask customer dni
	 * 
	 * - Display all uncharged breakdowns (status <> 'CHARGED'). For each
	 * breakdown, display id, date, status, amount and description
	 */
	@Override
	public void execute() throws BusinessException {
		String dni = Console.readString("Type DNI of the customer: ");

		InvoiceServiceImpl inv = (InvoiceServiceImpl) ServicesFactory
				.getInvoiceCrudService();
		List<BreakdownDto> list = inv.findWorkOrdersByClientDni(dni);

		for (BreakdownDto dto : list) {
			display((int) dto.id, dto.date, dto.status, dto.total,
					dto.description);
		}
	}

	private void display(int id, Date date, String status, double total,
			String description) {
		Console.printf("Breakdown id: %d\n", id);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", date);
		Console.printf("\tStatus: %s\n", status);
		Console.printf("\tAmount: %.2f €\n", total);
		Console.printf("\tDescription: %s\n", description);
	}

}
