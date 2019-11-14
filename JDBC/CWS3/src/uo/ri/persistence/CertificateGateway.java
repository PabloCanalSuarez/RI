package uo.ri.persistence;

import uo.ri.business.dto.CertificateDto;

public interface CertificateGateway {
	
	public void addCertificate(CertificateDto dto);

	public CertificateDto findCertificate(CertificateDto dto);

}
