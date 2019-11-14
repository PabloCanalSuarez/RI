package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "MECHANIC_ID",
		"VEHICLETYPE_ID" }) }, name = "TCertificates")
public class Certificate extends BaseEntity {

	private Date date;

	@ManyToOne
	private VehicleType vehicleType;
	@ManyToOne
	private Mechanic mechanic;

	Certificate() {
	}

	public Certificate(Mechanic mechanic, VehicleType vehicleType) {
		date = new Date();
		Argument.isNotNull(mechanic);
		Argument.isNotNull(vehicleType);

		Associations.Certify.link(mechanic, this, vehicleType);
	}

	void _setVehicleType(VehicleType vt) {
		this.vehicleType = vt;
	}

	void _setMechanic(Mechanic m) {
		this.mechanic = m;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((mechanic == null) ? 0 : mechanic.hashCode());
		result = prime * result
				+ ((vehicleType == null) ? 0 : vehicleType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Certificate other = (Certificate) obj;
		if (mechanic == null) {
			if (other.mechanic != null)
				return false;
		} else if (!mechanic.equals(other.mechanic))
			return false;
		if (vehicleType == null) {
			if (other.vehicleType != null)
				return false;
		} else if (!vehicleType.equals(other.vehicleType))
			return false;
		return true;
	}

}
