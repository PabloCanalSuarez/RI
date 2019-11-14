package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.EnrollmentGateway;

public class EnrollmentGatewayImpl implements EnrollmentGateway {

	private Connection con;
	
	@Override
	public int getEnrollmentTime(Long idMechanic, Long idVehicleType) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int result = -1;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);
			
			pst = con.prepareStatement(Conf.getInstance().getProperty("SQL_CHECK_CERTIFICATE"));
			pst.setLong(1, idVehicleType);
			pst.setLong(2, idMechanic);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("hoursattendedtotype");
			}

			return result;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}
	
	@Override
	public List<MechanicDto> findMechanicEnrolled() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MechanicDto> list = new ArrayList<>();
		MechanicDto dto = null;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);
			
			pst = con.prepareStatement(Conf.getInstance().getProperty("SQL_GET_MECHANIC_ENROLLED"));
			rs = pst.executeQuery();
			
			while(rs.next()) {
				dto = new MechanicDto();
				dto.id = rs.getLong("mechanic_id");
				list.add(dto);
			}

			return list;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
