package uo.ri.cws.application.service.training.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class RegisterNewCourse implements Command<CourseDto> {

	private CourseDto dto;
	private CourseRepository repo = Factory.repository.forCourse();

	public RegisterNewCourse(CourseDto dto) {
		this.dto = dto;
	}

	@Override
	public CourseDto execute() throws BusinessException {
		checkDto(dto);
		checkDoesNotExist(dto.code);
		
		Course c = new Course(dto.code, dto.name, dto.description,
				dto.startDate, dto.endDate, dto.hours);
		repo.add(c);
		
		dto.id = c.getId();
		return dto;
	}

	private void checkDto(CourseDto c) throws BusinessException {
		BusinessCheck.isNotEmpty(c.code, "Emtpy code");
		BusinessCheck.isNotEmpty(c.name, "Emtpy name");
		BusinessCheck.isNotEmpty(c.description, "Emtpy description");
		
		BusinessCheck.isNotNull(c.startDate, "Null startDate");
		BusinessCheck.isNotNull(c.endDate, "Null endDate");
		BusinessCheck.isNotNull(c.hours, "Null hours");
		
		BusinessCheck.isTrue(c.startDate.getTime() > 0);
		BusinessCheck.isTrue(c.endDate.getTime() > 0);
		BusinessCheck.isTrue(c.hours > 0);
	}

	private void checkDoesNotExist(String code) throws BusinessException {
		Optional<Course> oc = repo.findByCode(code);
		BusinessCheck.isFalse(oc.isPresent(), "The course already exists");
	}

}
