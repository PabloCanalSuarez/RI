package uo.ri.cws.application.service.training.crud.command;

import java.sql.Date;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class UpdateCourse implements Command<Void> {

	private CourseDto dto;
	private CourseRepository repo = Factory.repository.forCourse();

	public UpdateCourse(CourseDto dto) {
		this.dto = dto;
	}
	
	@Override
	public Void execute() throws BusinessException {
		checkDto(dto);
		
		Optional<Course> oc = repo.findById(dto.id);
		BusinessCheck.exists(oc,
				"The course with id " + dto.id + " does not exist");
		
		Course c = oc.get();
		BusinessCheck.hasVersion(c, dto.version);

		c.setDescription(dto.description);
		c.setName(dto.name);
		c.setHours(dto.hours);
		c.setStartDate(new Date(dto.startDate.getTime()));
		c.setEndDate(new Date(dto.endDate.getTime()));
		return null;
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

}
