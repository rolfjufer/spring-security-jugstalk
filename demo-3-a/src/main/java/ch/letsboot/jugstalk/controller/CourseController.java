package ch.letsboot.jugstalk.controller;

import ch.letsboot.jugstalk.dto.CourseRequest;
import ch.letsboot.jugstalk.dto.CourseResponse;
import ch.letsboot.jugstalk.exception.CourseAlreadyExistsException;
import ch.letsboot.jugstalk.exception.CourseNotFoundException;
import ch.letsboot.jugstalk.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Courses", description = "Course management APIs")
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Retrieve all Courses", description = "Get all Courses.")
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getCourses() {
        return ResponseEntity.ok(courseService.viewCourseList());
    }

    @Operation(summary = "Retrieve a Course by Id", description = "Get a Course object by specifying its id.")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) throws CourseNotFoundException {
        return ResponseEntity.ok(courseService.viewCourseDetails(id));
    }

    @Operation(summary = "Create a new Course", description = "Create a new Course.")
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest courseRequest)
            throws CourseAlreadyExistsException, MethodArgumentNotValidException {
        CourseResponse courseResponse = courseService.addCourse(courseRequest);
        return ResponseEntity.ok(courseResponse);
    }

    @Operation(summary = "Update Course with given id", description = "Update Course with given id.")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest courseRequest)
            throws CourseNotFoundException {
        CourseResponse courseResponse = courseService.editCourse(id, courseRequest);
        return ResponseEntity.ok(courseResponse);
    }

    @Operation(summary = "Delete Course with given id", description = "Delete Course with given id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) throws CourseNotFoundException {
        courseService.removeCourse(id);
        return ResponseEntity.ok().build();
    }

}
