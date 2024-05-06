package ch.letsboot.jugstalk.service;

import ch.letsboot.jugstalk.dto.CourseRequest;
import ch.letsboot.jugstalk.dto.CourseResponse;
import ch.letsboot.jugstalk.exception.CourseAlreadyExistsException;
import ch.letsboot.jugstalk.exception.CourseNotFoundException;
import ch.letsboot.jugstalk.model.Course;
import ch.letsboot.jugstalk.repository.CourseRepository;
import ch.letsboot.jugstalk.util.CourseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper = new CourseMapper();

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> viewCourseList() {
        List<Course> courses = courseRepository.findAll();
              return courses.stream()
                .map(course -> courseMapper.toCourseResponse(course))
                .collect(Collectors.toList());
    }


    public CourseResponse viewCourseDetails(Long id) throws CourseNotFoundException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        return courseMapper.toCourseResponse(course);
    }

    public CourseResponse addCourse(@Valid CourseRequest courseRequest)
            throws CourseAlreadyExistsException {
        Optional<Course> course = courseRepository.findById(courseRequest.getId());

        if (course.isPresent()) {
            throw new CourseAlreadyExistsException(courseRequest.getId());
        }

        Course mappedCourse = courseMapper.toCourse(courseRequest);
        Course savedCourse = courseRepository.save(mappedCourse);
        return courseMapper.toCourseResponse(savedCourse);
    }

    public void removeCourse(Long id) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    public CourseResponse editCourse(Long id, CourseRequest courseRequest) throws CourseNotFoundException {
        CourseResponse courseResponse = courseRepository.findById(id)
                .map(existingCourse -> {
                    Course courseToUpdate = courseMapper.toCourse(courseRequest);
                    courseToUpdate.setId(existingCourse.getId());  // Set the existing ISBN
                    Course updatedCourse = courseRepository.save(courseToUpdate);
                    return courseMapper.toCourseResponse(updatedCourse);
                })
                .orElseThrow(() -> new CourseNotFoundException(id));
        return courseResponse;
    }

}
