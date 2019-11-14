package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private InvoiceRepository invoiceRepo = Factory.repository.forInvoice();
	private WorkOrderRepository workorderRepo = Factory.repository
			.forWorkOrder();

	public CreateInvoiceFor(List<String> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		checkList(workOrderIds);

		List<WorkOrder> list = new ArrayList<>();
		for (String id : workOrderIds) {
			list.add(workorderRepo.findById(id).get());
		}

		Invoice i = new Invoice(invoiceRepo.getNextInvoiceNumber(), list);
		invoiceRepo.add(i);

		return DtoAssembler.toDto(i);
	}

	private void checkList(List<String> list) throws BusinessException {
		BusinessCheck.isFalse(list.isEmpty(),
				"List of workorders ids can't be empty");
		BusinessCheck.isNotNull(list);
		for (String l : list) {
			BusinessCheck.isNotEmpty(l, "Id is empty");
		}
	}

}
