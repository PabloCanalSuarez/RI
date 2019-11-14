package uo.ri.persistence;

import java.util.List;

public interface Gateway {

	public void add(Object o);

	public void delete(Long id);

	public void update(Object o);

	public List<Object> findAll();

	public Object findById(Long id);
}
