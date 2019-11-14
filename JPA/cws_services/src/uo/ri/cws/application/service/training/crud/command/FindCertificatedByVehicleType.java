package uo.ri.cws.application.service.training.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;

public class FindCertificatedByVehicleType
		implements Command<List<CertificateDto>> {
	
	private CertificateRepository repoCertificate = Factory.repository.forCertificate();

	@Override
	public List<CertificateDto> execute() throws BusinessException {
		List<Certificate> list = repoCertificate.findAll();	
		return DtoAssembler.toCertificateDtoList(list);
	}

}
