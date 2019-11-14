package uo.ri.cws.application.service.training.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class DeleteCourse implements Command<Void> {
	
	private String id;
	private CourseRepository repo = Factory.repository.forCourse();
	
	public DeleteCourse(String id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {

		Optional<Course> oc = repo.findById(id);
		BusinessCheck.exists(oc,
				"The course with id " + id + "does not exist.");

		Course c = oc.get();
		repo.remove(c);
		
		return null;
	}

}
