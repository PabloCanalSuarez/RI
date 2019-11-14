package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class VehicleTypeJpaRepository extends BaseJpaRepository<VehicleType>
		implements VehicleTypeRepository {

	@Override
	public List<VehicleType> findVehicleTypeDedicated() {
		return Jpa.getManager()
				.createNamedQuery("VehicleType.findVehicleTypesDedicated",
						VehicleType.class)
				.getResultList();
	}

}
