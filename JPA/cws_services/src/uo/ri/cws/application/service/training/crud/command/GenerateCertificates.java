package uo.ri.cws.application.service.training.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class GenerateCertificates implements Command<Integer> {

	private EnrollmentRepository repoEnrollment = Factory.repository
			.forEnrollment();
	private VehicleTypeRepository repoVehicleType = Factory.repository
			.forVehicleType();
	private CertificateRepository repoCertificate = Factory.repository
			.forCertificate();

	@Override
	public Integer execute() throws BusinessException {
		int countCertificate = 0;
		List<Mechanic> listMechanics = repoEnrollment.findMechanicEnrolled();
		List<VehicleType> listDedicated = repoVehicleType
				.findVehicleTypeDedicated();

		for (Mechanic m : listMechanics) {
			for (VehicleType v : listDedicated) {
				if (checkCertificateIsOK(m, v)) {

					if (!checkCertificateExists(m.getId(), v.getId())) {
						createCertificate(m, v);
						countCertificate++;
					}
				}
			}
		}
		return countCertificate;
	}

	/**
	 * Creates and adds the certificate corresponding to a mechanic and
	 * vehicletype to the repository.
	 * 
	 * @param m - Mechanic to be certified.
	 * @param v - VehicleType to be certified.
	 */
	private void createCertificate(Mechanic m, VehicleType v) {
		Certificate certificate = new Certificate(m, v);
		repoCertificate.add(certificate);
	}

	/**
	 * Checks if the certificate is ok to be done. If the total attedended hours
	 * is greater or equal to the minimum training hours of the vehicle type it
	 * returns true. False otherwise.
	 * 
	 * @param m - Mechanic to be certified.
	 * @param v - VehicleType to be certified.
	 * @return True if the total attedended hours is greater or equal to the
	 *         minimum training hours of the vehicle type. False otherwise.
	 */
	private boolean checkCertificateIsOK(Mechanic m, VehicleType v) {

		Optional<Long> oi;
		oi = repoEnrollment.getEnrollmentTime(m.getId(), v.getId());

		if (oi.isPresent()) {
			int totalattendedhours = oi.get().intValue();
			int minhours = v.getMinTrainingHours();
			if (totalattendedhours >= minhours) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the certificate already exists in the database
	 * 
	 * @param id - Id of the certificate
	 * @return
	 */
	private boolean checkCertificateExists(String idMechanic,
			String idVehicleType) {
		Optional<Certificate> c = repoCertificate
				.findByMechanicAndVehicleTypeIds(idMechanic, idVehicleType);
		if (c.isPresent()) {
			return true;
		}
		return false;
	}
}
