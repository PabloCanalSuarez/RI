package uo.ri.business.cashier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.InvoiceGatewayImpl;
import uo.ri.persistence.impl.PaymentMeansGatewayImpl;

public class PayOffInvoice {

	private Long invoiceNumber;
	private Connection con;

	private InvoiceGatewayImpl invGat = PersistenceFactory.getInvoiceGateway();
	private PaymentMeansGatewayImpl payGat = PersistenceFactory
			.getPaymentMeansGatewayImpl();

	public PayOffInvoice(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public PayOffInvoice() {
		super();
	}

	public InvoiceDto findInvoice() throws BusinessException {
		InvoiceDto dto = null;

		try {
			con = Jdbc.createThreadConnection();

			dto = invGat.findInvoice(invoiceNumber);

			if (dto == null) {
				throw new BusinessException(
						"The invoice " + invoiceNumber + "could not be found");
			}

			return dto;

		} catch (SQLException e) {
			throw new BusinessException(e);
		}
	}

	public List<PaymentMeanDto> findPayMethods(Long idInvoice)
			throws BusinessException {
		List<PaymentMeanDto> list = new ArrayList<>();
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			list = payGat.findPayMethods(idInvoice);

			if (list.isEmpty()) {
				throw new BusinessException(
						"Thera are no payment methods for invoice "
								+ idInvoice);
			}

			return list;

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
			}
			;
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(con);
		}
	}

}
