package uo.ri.persistence;

import uo.ri.common.BusinessException;

public interface ClientGateway {

	public Long getClientID(String dni) throws BusinessException;

}
