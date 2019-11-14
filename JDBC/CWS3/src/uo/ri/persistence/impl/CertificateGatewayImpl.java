package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.CourseForRow;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.CertificateGateway;

public class CertificateGatewayImpl implements CertificateGateway {

	private Connection con;

	@Override
	public void addCertificate(CertificateDto dto) {
		PreparedStatement pst = null;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_INSERT_CERTIFICATE"));
			pst.setLong(1, dto.mechanic.id);
			pst.setLong(2, dto.vehicleType.id);
			pst.setDate(3, new Date(dto.obtainedAt.getTime()));
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public CertificateDto findCertificate(CertificateDto dto) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		CertificateDto cert = null;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_GET_CERTIFICATE"));
			pst.setLong(1, dto.mechanic.id);
			pst.setLong(2, dto.vehicleType.id);
			
			rs = pst.executeQuery();
			
			if(rs.next()) {
				cert = new CertificateDto();
				cert.mechanic = dto.mechanic;
				cert.vehicleType = dto.vehicleType;
				cert.obtainedAt = dto.obtainedAt;
			}
			
			return cert;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	public List<CertificateDto> findCertificatedByVehicleType() {
		List<CertificateDto> list = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_CERTIFICATE_BY_VEHICLETYPE"));
			rs = pst.executeQuery();
			
			CertificateDto dto = null;
			while(rs.next()) {
				dto = new CertificateDto();
				dto.id = rs.getLong("id");
				MechanicDto mechanic = new MechanicDto();
				mechanic.id = rs.getLong("mechanic_id");
				dto.mechanic = mechanic;
				VehicleTypeDto vehtype = new VehicleTypeDto();
				vehtype.id = rs.getLong("vehicletype_id");
				dto.vehicleType = vehtype;
				dto.obtainedAt = rs.getDate("date");
				
				list.add(dto);
			}
			
			return list;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	public List<CourseForRow> findCoursesWithAssistants() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<CourseForRow> list = new ArrayList<>();
		try {
			con = Jdbc.getCurrentConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(Conf.getInstance()
					.getProperty("SQL_LIST_COURSENAME_AND_ASSISTANTS"));
			rs = pst.executeQuery();
			
			CourseForRow dto = null;
			while(rs.next()) {
				dto = new CourseForRow();
				dto.name = rs.getString("name");
				dto.numberAssistants = rs.getLong("Number_Assistants");
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
