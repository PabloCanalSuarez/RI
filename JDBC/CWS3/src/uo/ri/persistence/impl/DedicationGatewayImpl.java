package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.DedicationGateway;

public class DedicationGatewayImpl implements DedicationGateway {

	private Connection con;

	@Override
	public List<VehicleTypeDto> findVehicleTypeDedicated() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<VehicleTypeDto> list = new ArrayList<>();
		VehicleTypeDto dto = null;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_GET_VEHICLETYPE_DEDICATED"));
			rs = pst.executeQuery();

			while (rs.next()) {
				dto = new VehicleTypeDto();
				dto.id = rs.getLong("vehicletype_id");
				dto.minTrainigHours = rs.getInt("mintraininghours");
				list.add(dto);
			}

			return list;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
