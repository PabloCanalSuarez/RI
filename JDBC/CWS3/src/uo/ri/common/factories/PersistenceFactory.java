package uo.ri.common.factories;

import uo.ri.persistence.impl.CertificateGatewayImpl;
import uo.ri.persistence.impl.ClientGatewayImpl;
import uo.ri.persistence.impl.CourseGatewayImpl;
import uo.ri.persistence.impl.DedicationGatewayImpl;
import uo.ri.persistence.impl.EnrollmentGatewayImpl;
import uo.ri.persistence.impl.InvoiceGatewayImpl;
import uo.ri.persistence.impl.MechanicGatewayImpl;
import uo.ri.persistence.impl.PaymentMeansGatewayImpl;
import uo.ri.persistence.impl.VehicleTypeGatewayImpl;
import uo.ri.persistence.impl.WorkOrderGatewayImpl;

public class PersistenceFactory {

	public static MechanicGatewayImpl getMechanicGateway() {
		return new MechanicGatewayImpl();
	}

	public static InvoiceGatewayImpl getInvoiceGateway() {
		return new InvoiceGatewayImpl();
	}

	public static WorkOrderGatewayImpl getWorkOrderGateway() {
		return new WorkOrderGatewayImpl();
	}

	public static PaymentMeansGatewayImpl getPaymentMeansGatewayImpl() {
		return new PaymentMeansGatewayImpl();
	}

	public static ClientGatewayImpl getClientGatewayImpl() {
		return new ClientGatewayImpl();
	}

	public static CourseGatewayImpl getCourseGatewayImpl() {
		return new CourseGatewayImpl();
	}

	public static DedicationGatewayImpl getDedicationGateway() {
		return new DedicationGatewayImpl();
	}

	public static CertificateGatewayImpl getCertificateGateway() {
		return new CertificateGatewayImpl();
	}

	public static EnrollmentGatewayImpl getEnrollmentGateway() {
		return new EnrollmentGatewayImpl();
	}

	public static VehicleTypeGatewayImpl getVehicleTypeGateway() {
		return new VehicleTypeGatewayImpl();
	}
}
