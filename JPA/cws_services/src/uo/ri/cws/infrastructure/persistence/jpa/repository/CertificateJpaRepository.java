package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class CertificateJpaRepository extends BaseJpaRepository<Certificate>
		implements CertificateRepository {

	@Override
	public Optional<Certificate> findByMechanicAndVehicleTypeIds(
			String idMechanic, String idVehicleType) {
		return Jpa.getManager()
				.createNamedQuery("Certificate.findByMechanicAndVehicleTypeIds",
						Certificate.class)
				.setParameter(1, idMechanic)
				.setParameter(2, idVehicleType)
				.getResultStream()
				.findFirst();
	}

}
