package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.VehicleTypeGateway;

public class VehicleTypeGatewayImpl implements VehicleTypeGateway {

	private Connection con;
	
	@Override
	public VehicleTypeDto findVehicleType(Long id) {
		VehicleTypeDto dto = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_VEHICLETYPE_BY_ID"));
			pst.setLong(1, id);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				dto = new VehicleTypeDto();
				dto.id = rs.getLong("id");
				dto.name = rs.getString("name");
			}
			
			return dto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
