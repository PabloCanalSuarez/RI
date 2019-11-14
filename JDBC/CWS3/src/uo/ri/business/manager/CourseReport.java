package uo.ri.business.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.CourseForRow;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.CertificateGatewayImpl;
import uo.ri.persistence.impl.MechanicGatewayImpl;
import uo.ri.persistence.impl.VehicleTypeGatewayImpl;

public class CourseReport {

	private CertificateGatewayImpl ceGat = PersistenceFactory
			.getCertificateGateway();
	private MechanicGatewayImpl meGat = PersistenceFactory.getMechanicGateway();
	private VehicleTypeGatewayImpl veGat = PersistenceFactory.getVehicleTypeGateway();
	
	public List<CertificateDto> findCertificatedByVehicleType()
			throws BusinessException {
		List<CertificateDto> list = null;
		try {
			Connection con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);
			list = ceGat.findCertificatedByVehicleType();
			
			for(CertificateDto dto : list) {
				dto.mechanic = (MechanicDto) meGat.findById(dto.mechanic.id);
				dto.vehicleType = veGat.findVehicleType(dto.vehicleType.id);
			}
			
			return list;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<CourseForRow> findCoursesWithAssistants() {
		List<CourseForRow> list = new ArrayList<>();
		try {
			Connection con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);
			list = ceGat.findCoursesWithAssistants();
			
			return list;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
