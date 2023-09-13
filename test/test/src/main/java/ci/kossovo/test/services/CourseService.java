package ci.kossovo.test.services;

import ci.kossovo.test.entity.Course;
import ci.kossovo.test.repository.CourseRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CourseService {

  private CourseRepository courseRepository;

  public Course addNewCourse(Course course) {
    log.info("CourseService::addNewCourse method executed");
    return courseRepository.save(course);
  }

  public List<Course> getAllAvailableCourses() {
    log.info("CourseService::getAllAvailableCourses method executed");
    return courseRepository.findAll();
  }
}
