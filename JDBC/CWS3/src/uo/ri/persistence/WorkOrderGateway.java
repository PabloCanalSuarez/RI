package uo.ri.persistence;

import java.util.List;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.common.BusinessException;

public interface WorkOrderGateway {

	public void linkWorkorderInvoice(long invoiceId, List<Long> workOrderIDS);

	BreakdownDto testRepairs(List<Long> workOrderIDS, Long workOrderId) throws BusinessException;

	void updateWorkOrderStatus(List<Long> breakdownIds, String status);

	void updateWorkorderTotal(Long workOrderID, double total);

	double checkTotalParts(Long workOrderID);

	double checkTotalLabor(Long workOrderID) throws BusinessException;

	List<BreakdownDto> getUnchargedWorkorders(Long id) throws BusinessException;

}
