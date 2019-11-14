package uo.ri.ui.administrator.training.certificates;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.servicesLayer.CertificateService;
import uo.ri.common.factories.ServicesFactory;

public class GenerateCertificatesAction implements Action {

	@Override
	public void execute() throws Exception {

		Console.println("Generating certificates...");

		CertificateService cs = ServicesFactory.getCertificateService();
		int qty = cs.generateCertificates();

		Console.println(qty + " certificates generated");
	}

}
