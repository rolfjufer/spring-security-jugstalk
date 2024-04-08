package ch.letsboot.jugstalk.repository;

import ch.letsboot.jugstalk.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);
    boolean existsByTitle(String title);

}
