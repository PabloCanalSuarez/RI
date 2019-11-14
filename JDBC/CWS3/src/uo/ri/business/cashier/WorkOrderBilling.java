package uo.ri.business.cashier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.InvoiceGatewayImpl;
import uo.ri.persistence.impl.WorkOrderGatewayImpl;

public class WorkOrderBilling {

	private Connection connection = null;
	private List<Long> workOrderIds = null;

	private InvoiceGatewayImpl invGat = PersistenceFactory.getInvoiceGateway();
	private WorkOrderGatewayImpl woGat = PersistenceFactory
			.getWorkOrderGateway();

	public WorkOrderBilling(List<Long> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	public WorkOrderBilling() {
		super();
	}

	public InvoiceDto execute() throws BusinessException {

		try {
			connection = Jdbc.createThreadConnection();
			connection.setAutoCommit(false);

			testRepairs(workOrderIds);

			long numberInvoice = generateInvoiceNumber();
			Date dateInvoice = Dates.today();
			double amount = calculateTotalInvoice(workOrderIds); // not vat
																	// included
			double vat = vatPercentage(amount, dateInvoice);
			double total = amount * (1 + vat / 100); // vat included
			total = Round.twoCents(total);

			long idInvoice = createInvoice(numberInvoice, dateInvoice, vat,
					total);
			linkWorkorderInvoice(idInvoice, workOrderIds);
			updateWorkOrderStatus(workOrderIds, "INVOICED");

			InvoiceDto inv = new InvoiceDto();
			inv.id = idInvoice;
			inv.date = dateInvoice;
			inv.number = numberInvoice;
			inv.total = total;
			inv.vat = vat;

			connection.commit();

			return inv;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}
			;
			throw new RuntimeException(e);
		} catch (BusinessException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}
			;
			throw e;
		} finally {
			Jdbc.close(connection);
		}
	}

	public double getAmount() throws BusinessException {
		return calculateTotalInvoice(workOrderIds);
	}

	private void testRepairs(List<Long> workOrderIDS)
			throws SQLException, BusinessException {
		for (Long workOrderID : workOrderIDS) {
			BreakdownDto wo = woGat.testRepairs(workOrderIDS, workOrderID);

			if (wo.status == null) {
				throw new BusinessException(
						"Workorder " + workOrderID + " doesn't exist");
			}

			String status = wo.status;
			if (!"FINISHED".equalsIgnoreCase(status)) {
				throw new BusinessException("Workorder " + workOrderID
						+ " is not finished yet");
			}

		}
	}

	private void updateWorkOrderStatus(List<Long> breakdownIds, String status)
			throws SQLException {
		woGat.updateWorkOrderStatus(breakdownIds, status);
	}

	private void linkWorkorderInvoice(long invoiceId, List<Long> workOrderIDS)
			throws SQLException {
		woGat.linkWorkorderInvoice(invoiceId, workOrderIDS);
	}

	private long createInvoice(long numberInvoice, Date dateInvoice, double vat,
			double total) throws SQLException {
		long id = invGat.createInvoiceFor(numberInvoice,
				new java.sql.Date(dateInvoice.getTime()), vat, total);
		return id;
	}

	private Long generateInvoiceNumber() {
		try {
			connection = Jdbc.createThreadConnection();
			connection.setAutoCommit(false);

			return invGat.generateInvoiceNumber();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private double vatPercentage(double totalInvoice, Date dateInvoice) {
		return Dates.fromString("1/7/2012").before(dateInvoice) ? 21.0 : 18.0;
	}

	protected double calculateTotalInvoice(List<Long> workOrderIDS)
			throws BusinessException {
		double totalInvoice = 0.0;
		for (Long workOrderID : workOrderIDS) {
			double laborTotal = checkTotalLabor(workOrderID);
			double sparesTotal = checkTotalParts(workOrderID);
			double workTotal = laborTotal + sparesTotal;

			updateWorkorderTotal(workOrderID, workTotal);

			totalInvoice += workTotal;
		}
		return totalInvoice;
	}

	private void updateWorkorderTotal(Long workOrderID, double total) {
		try {
			connection = Jdbc.createThreadConnection();
			connection.setAutoCommit(false);

			woGat.updateWorkorderTotal(workOrderID, total);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private double checkTotalParts(Long workOrderID) {
		try {
			connection = Jdbc.createThreadConnection();
			connection.setAutoCommit(false);

			return woGat.checkTotalParts(workOrderID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private double checkTotalLabor(Long workOrderID) throws BusinessException {
		try {
			connection = Jdbc.createThreadConnection();
			connection.setAutoCommit(false);

			return woGat.checkTotalLabor(workOrderID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean checkInvoicePaid(InvoiceDto inv) {
		if (!inv.status.equalsIgnoreCase("PAID")) {
			return false;
		} else {
			return true;
		}
	}

}
