package uo.ri.business.servicesLayer.implementation;

import uo.ri.business.manager.Certificate;
import uo.ri.business.servicesLayer.CertificateService;
import uo.ri.common.BusinessException;

public class CertificateServiceImpl implements CertificateService {

	@Override
	public int generateCertificates() throws BusinessException {
		return new Certificate().generateCertificates();
	}
}
