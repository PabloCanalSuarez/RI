package uo.ri.business.servicesLayer.implementation;

import java.util.List;
import java.util.Map;

import uo.ri.business.cashier.PayOffInvoice;
import uo.ri.business.cashier.UnchargedRepairsClient;
import uo.ri.business.cashier.WorkOrderBilling;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.servicesLayer.InvoiceService;
import uo.ri.common.BusinessException;

public class InvoiceServiceImpl implements InvoiceService {

	// IMPLEMENT
	@Override
	public InvoiceDto createInvoiceFor(List<Long> idsAveria)
			throws BusinessException {
		return new WorkOrderBilling(idsAveria).execute();
	}

	public double getInvoiceAmount(List<Long> workOrderIds)
			throws BusinessException {
		return new WorkOrderBilling(workOrderIds).getAmount();
	}

	@Override
	public InvoiceDto findInvoice(Long numeroFactura) throws BusinessException {
		return new PayOffInvoice(numeroFactura).findInvoice();
	}

	@Override
	public List<PaymentMeanDto> findPayMethodsForInvoice(Long idFactura)
			throws BusinessException {
		return new PayOffInvoice().findPayMethods(idFactura);
	}

	@Override
	public void settleInvoice(Long idFactura, Map<Long, Double> cargos)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

	// IMPLEMENT
	@Override
	public List<BreakdownDto> findWorkOrdersByClientDni(String dni)
			throws BusinessException {
		return new UnchargedRepairsClient(dni).execute();
	}

	public boolean checkInvoicePaid(InvoiceDto inv) {
		return new WorkOrderBilling().checkInvoicePaid(inv);
	}

}
