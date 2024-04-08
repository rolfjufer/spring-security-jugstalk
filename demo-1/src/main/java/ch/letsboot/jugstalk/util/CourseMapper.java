package ch.letsboot.jugstalk.util;

import ch.letsboot.jugstalk.dto.CourseRequest;
import ch.letsboot.jugstalk.dto.CourseResponse;
import ch.letsboot.jugstalk.model.Course;
import ch.letsboot.jugstalk.model.Trainer;
import org.modelmapper.ModelMapper;

public class CourseMapper {

    public CourseResponse toCourseResponse(Course course) {
        ModelMapper modelMapper = new ModelMapper();
        CourseResponse courseResponse = modelMapper.map(course, CourseResponse.class);
        Trainer trainer = course.getTrainer();
        if (trainer != null) {
            courseResponse.setTrainerId(trainer.getId());
            // Set any other trainer information you need
            courseResponse.setTrainerName(trainer.getName());
            courseResponse.setTrainerEmail(trainer.getEmail());
        }
        return courseResponse;
    }

    public CourseRequest toCourseRequest(Course course) {
        ModelMapper modelMapper = new ModelMapper();
        CourseRequest courseRequest = modelMapper.map(course, CourseRequest.class);
        Trainer trainer = course.getTrainer();
        if (trainer != null) {
            courseRequest.setTrainerId(trainer.getId());
        }
        return courseRequest;
    }



    public Course toCourse(CourseRequest courseRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(courseRequest, Course.class);
    }
}