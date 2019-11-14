package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.Conf;
import uo.ri.persistence.ClientGateway;

public class ClientGatewayImpl implements ClientGateway {

	private Connection con;

	@Override
	public Long getClientID(String dni) throws BusinessException {
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			// Get id corresponding to the dni
			PreparedStatement ps = con.prepareStatement(
					Conf.getInstance().getProperty("SQL_GET_CLIENT_ID"));
			ps.setLong(1, Long.parseLong(dni));
			ResultSet stID = ps.executeQuery();

			return stID.getLong("ID");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
