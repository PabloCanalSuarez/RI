package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.InvoiceGateway;

public class InvoiceGatewayImpl implements InvoiceGateway {

	private Connection con = null;


	@Override
	public Long createInvoiceFor(long numberInvoice, Date dateInvoice,
			double vat, double total) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_INSERT_INVOICE"));
			pst.setLong(1, numberInvoice);
			pst.setDate(2, new Date(dateInvoice.getTime()));
			pst.setDouble(3, vat);
			pst.setDouble(4, total);
			pst.setString(5, "NOT_YET_PAID");

			pst.executeUpdate();

			return getGeneratedKey(numberInvoice);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public long getGeneratedKey(Long numberInvoice) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_RETRIEVE_GENERATED_KEY"));
			pst.setLong(1, numberInvoice);
			rs = pst.executeQuery();
			rs.next();

			return rs.getLong(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public InvoiceDto findInvoice(Long numeroFactura) {
		InvoiceDto dto = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_GET_INVOICE"));
			pst.setLong(1, numeroFactura);
			rs = pst.executeQuery();

			if(rs.next()) {
				dto = new InvoiceDto();
				dto.id = rs.getLong("ID");
				dto.date = rs.getDate("DATE");
				dto.number = numeroFactura;
				dto.vat = rs.getDouble("VAT");
				dto.total = rs.getDouble("AMOUNT") * (1 + dto.vat / 100);
				dto.status = rs.getString("STATUS");
			}

			return dto;

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

	@Override
	public Long generateInvoiceNumber() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = Jdbc.getCurrentConnection();
			pst = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_LAST_INVOICE_NUMBER"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
