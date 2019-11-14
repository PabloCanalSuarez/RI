package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.Conf;
import uo.ri.persistence.WorkOrderGateway;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	private Connection con;

	@Override
	public void linkWorkorderInvoice(long invoiceId, List<Long> workOrderIDS) {
		PreparedStatement pst = null;
		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_WORKORDER_INVOICE_ASSOC"));

			for (Long breakdownId : workOrderIDS) {
				pst.setLong(1, invoiceId);
				pst.setLong(2, breakdownId);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public BreakdownDto testRepairs(List<Long> workOrderIDS, Long id) throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		BreakdownDto dto = new BreakdownDto();
		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_CHECK_WORKORDER_STATUS"));
			pst.setLong(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				dto.id = id;
				dto.status = rs.getString("STATUS");
			}

			return dto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void updateWorkOrderStatus(List<Long> breakdownIds, String status) {

		PreparedStatement pst = null;
		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_WORKORDER_STATUS"));

			for (Long breakdownId : breakdownIds) {
				pst.setString(1, status);
				pst.setLong(2, breakdownId);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void updateWorkorderTotal(Long workOrderID, double total) {
		PreparedStatement pst = null;

		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_WORKORDER_AMOUNT"));
			pst.setDouble(1, total);
			pst.setLong(2, workOrderID);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public double checkTotalParts(Long workOrderID) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_PARTS_TOTAL"));
			pst.setLong(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				return 0.0; // There is no part replaced in this breakdown
			}

			return rs.getDouble(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public double checkTotalLabor(Long workOrderID) throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_LABOR_TOTAL"));
			pst.setLong(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				throw new BusinessException(
						"Workorder does not exist or it can not be charged");
			}

			return rs.getDouble(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public List<BreakdownDto> getUnchargedWorkorders(Long id)
			throws BusinessException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<BreakdownDto> list = new ArrayList<>();

		try {
			con = Jdbc.getCurrentConnection();
			st = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_UNCHARGED_WORKORDERS"));
			st.setLong(1, id);
			rs = st.executeQuery();

			BreakdownDto dto;
			while (rs.next()) {
				dto = new BreakdownDto();
				dto.id = rs.getLong("id");
				dto.date = rs.getDate("date");
				dto.status = rs.getString("status");
				dto.total = rs.getDouble("amount");
				dto.description = rs.getString("description");
				list.add(dto);
			}

			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, st);
		}
	}

}
