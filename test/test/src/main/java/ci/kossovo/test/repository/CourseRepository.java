package ci.kossovo.test.repository;

import ci.kossovo.test.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}
