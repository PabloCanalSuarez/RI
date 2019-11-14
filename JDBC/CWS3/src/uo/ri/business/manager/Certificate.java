package uo.ri.business.manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.BusinessException;
import uo.ri.common.factories.PersistenceFactory;
import uo.ri.persistence.impl.CertificateGatewayImpl;
import uo.ri.persistence.impl.DedicationGatewayImpl;
import uo.ri.persistence.impl.EnrollmentGatewayImpl;

public class Certificate {

	private DedicationGatewayImpl veGat = PersistenceFactory
			.getDedicationGateway();
	private CertificateGatewayImpl ceGat = PersistenceFactory
			.getCertificateGateway();
	private EnrollmentGatewayImpl enGat = PersistenceFactory
			.getEnrollmentGateway();
	
	private Connection con;

	public int generateCertificates() throws BusinessException {
		int countCertficate = 0;
		List<MechanicDto> listMechanics;
		List<VehicleTypeDto> listDedicated;

		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			listMechanics = enGat.findMechanicEnrolled();
			listDedicated = veGat.findVehicleTypeDedicated();

			for (MechanicDto m : listMechanics) {
				for (VehicleTypeDto v : listDedicated) {
					if (checkCertificate(m, v)) {
						CertificateDto dto = new CertificateDto();
						dto.mechanic = m;
						dto.vehicleType = v;
						dto.obtainedAt = new Date(System.currentTimeMillis());
						
						if (ceGat.findCertificate(dto) == null) {
							countCertficate++;
							ceGat.addCertificate(dto);
							con.commit();
						}
					}
				}
			}

			con.commit();

			return countCertficate;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean checkCertificate(MechanicDto m, VehicleTypeDto v)
			throws BusinessException {
		int totalattendedhours;
		try {
			con = Jdbc.createThreadConnection();
			con.setAutoCommit(false);

			totalattendedhours = enGat.getEnrollmentTime(m.id, v.id);
			if (totalattendedhours >= v.minTrainigHours) {
				return true;
			}
			con.commit();
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
