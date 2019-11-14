package uo.ri.persistence;

import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;

public interface PaymentMeansGateway {

	public List<PaymentMeanDto> findPayMethods(Long idInvoice);

}
