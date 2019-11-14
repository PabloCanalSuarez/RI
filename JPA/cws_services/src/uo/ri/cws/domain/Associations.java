package uo.ri.cws.domain;

public class Associations {

	public static class Own {

		public static void link(Client client, Vehicle vehicle) {
			// WE ALWAYS PUT THE ONE SIDE
			vehicle._setClient(client);
			client._getVehicles().add(vehicle);

		}

		public static void unlink(Client client, Vehicle vehicle) {
			client._getVehicles().remove(vehicle);
			vehicle._setClient(null);
		}

	}

	public static class Classify {
		public static void link(VehicleType vehicleType, Vehicle vehicle) {
			// WE ALWAYS PUT THE ONE SIDE
			vehicle._setVehicleType(vehicleType);
			vehicleType._getVehicles().add(vehicle);

		}

		public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
			vehicleType._getVehicles().remove(vehicle);
			vehicle._setVehicleType(null);
		}

	}

	public static class Pay {

		public static void link(PaymentMean pm, Client client) {
			pm._setClient(client);
			client._getPaymentMeans().add(pm);
		}

		public static void unlink(Client client, PaymentMean pm) {
			client._getPaymentMeans().remove(pm);
			pm._setClient(null);
		}

	}

	public static class Order {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
			workOrder._setVehicle(vehicle);
			vehicle._getWorkOrders().add(workOrder);

		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
			vehicle._getWorkOrders().remove(workOrder);
			workOrder._setVehicle(null);

		}

	}

	public static class ToInvoice {
		public static void link(Invoice invoice, WorkOrder workOrder) {
			workOrder._setInvoice(invoice);
			invoice._getWorkOrders().add(workOrder);
		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) {
			invoice._getWorkOrders().remove(workOrder);
			workOrder._setInvoice(null);

		}
	}

	public static class Charges {
		public static void link(Invoice invoice, Charge charge,
				PaymentMean paymentMean) {
			charge._setInvoice(invoice);
			charge._setPaymentMean(paymentMean);

			invoice._getCharges().add(charge);
			paymentMean._getCharges().add(charge);

		}

		public static void unlink(Charge charge) {
			charge.getInvoice()._getCharges().remove(charge);
			charge.getPaymentMean()._getCharges().remove(charge);

			charge._setInvoice(null);
			charge._setPaymentMean(null);
		}

	}

	public static class Assign {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
			workOrder._setMechanic(mechanic);
			mechanic._getAssigned().add(workOrder);

		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
			mechanic._getAssigned().remove(workOrder);
			workOrder._setMechanic(null);
		}

	}

	public static class Intervene {

		public static void link(WorkOrder w, Intervention i, Mechanic m) {
			i._setMechanic(m);
			i._setWorkOrder(w);

			m._getInterventions().add(i);
			w._getInterventions().add(i);
		}

		// DIFFERENT
		public static void unlink(Intervention i) {
			i.getMechanic()._getInterventions().remove(i);
			i.getWorkOrder()._getInterventions().remove(i);

			i._setMechanic(null);
			i._setWorkOrder(null);

		}

	}

	public static class Sustitute {

		public static void link(SparePart sp, Substitution s, Intervention i) {
			s._setIntervention(i);
			s._setSpareParts(sp);

			i._getSubstitution().add(s);
			sp._getSustituciones().add(s);

		}

		public static void unlink(Substitution s) {
			s.getIntervention()._getSubstitution().remove(s);
			s.getSparePart()._getSustituciones().remove(s);

			s._setIntervention(null);
			s._setSpareParts(null);
		}

	}

	public static class Certify {

		public static void link(Mechanic m, Certificate c, VehicleType vt) {
			c._setMechanic(m);
			c._setVehicleType(vt);

			m._getCertificates().add(c);
			vt._getCertificates().add(c);
		}

		public static void unlink(Certificate c) {
			c.getMechanic()._getCertificates().remove(c);
			c.getVehicleType()._getCertificates().remove(c);

			c._setMechanic(null);
			c._setVehicleType(null);
		}
	}

	public static class Enroll {

		public static void link(Mechanic m, Enrollment e, Course c) {
			e._setMechanic(m);
			e._setCourse(c);

			m._getEnrollments().add(e);
			c._getEnrollments().add(e);
		}

		public static void unlink(Enrollment e) {
			e.getMechanic()._getEnrollments().remove(e);
			e.getCourse()._getEnrollments().remove(e);

			e._setMechanic(null);
			e._setCourse(null);
		}
	}

	public static class Dedicate {
		public static void link(VehicleType vt, Dedication d, Course c) {
			d._setVehicleType(vt);
			d._setCourse(c);

			c._getDedications().add(d);
			vt._getDedications().add(d);

		}

		public static void unlink(Dedication d) {
			d.getCourse()._getDedications().remove(d);
			d.getVehicleType()._getDedications().remove(d);

			d._setVehicleType(null);
			d._setCourse(null);
		}
	}
}
