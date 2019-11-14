package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.PaymentMeanDtoType;
import uo.ri.conf.Conf;
import uo.ri.persistence.PaymentMeansGateway;

public class PaymentMeansGatewayImpl implements PaymentMeansGateway {

	private Connection con;

	@Override
	public List<PaymentMeanDto> findPayMethods(Long idInvoice) {
		List<PaymentMeanDto> list = new ArrayList<>();
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			PreparedStatement ps1 = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_GET_CLIENT_ID_FROM_INVOICE_ID"));
			ps1.setLong(1, idInvoice);
			ResultSet rs1 = ps1.executeQuery();

			Long idClient = null;
			while (rs1.next()) {
				idClient = rs1.getLong("ID");
			}

			PreparedStatement ps = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_GET_PAYMENTMETHOD"));
			ps.setLong(1, idClient);
			ResultSet rs = ps.executeQuery();

			PaymentMeanDtoType dto = null;
			while (rs.next()) {
				dto = new PaymentMeanDtoType();

				dto.type = rs.getString("DTYPE");
				;
				dto.id = rs.getLong("ID");
				dto.accumulated = rs.getDouble("ACCUMULATED");
				dto.clientId = rs.getLong("CLIENT_ID");

				list.add(dto);
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
