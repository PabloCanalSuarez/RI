package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class EnrollmentJpaRepository extends BaseJpaRepository<Enrollment>
implements EnrollmentRepository {

	@Override
	public List<Mechanic> findMechanicEnrolled() {
		return Jpa.getManager()
				.createNamedQuery("Enrollment.findMechanicsEnrolled",
						Mechanic.class)
				.getResultList();
	}

	@Override
	public Optional<Long> getEnrollmentTime(String idMechanic, 
			String idVehicleType) {
		return Jpa.getManager()
				.createNamedQuery("Enrollment.getEnrollmentTime", Long.class)
				.setParameter(1, idMechanic)
				.setParameter(2, idVehicleType)
				.getResultStream()
				.findFirst();
	}
}
