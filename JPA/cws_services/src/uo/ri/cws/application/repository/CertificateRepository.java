package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Certificate;

public interface CertificateRepository extends Repository<Certificate> {

	Optional<Certificate> findByMechanicAndVehicleTypeIds(String idMechanic,
			String idVehicleType);

	/**
	 * Returns a list with all the certificates
	 * @return a list with all the certificates
	 */
	List<Certificate> findAll();


}
