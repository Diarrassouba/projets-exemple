package ci.kossovo.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ci.kossovo.test.controller.CourseController;
import ci.kossovo.test.entity.Course;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseServiceApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  static Cont
  static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
    "postgres: latest"
  )
    .withUsername("ddrissa")
    .withPassword("dddd")
    .withDatabaseName("testCoursedb")
    .withExposedPorts(5433);

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @BeforeAll
  static void beforeAll() {
    postgresContainer.start();
  }

  @AfterAll
  static void afterAll() {
    postgresContainer.stop();
  }

  @Before
  public void setup() {
    this.mockMvc =
      MockMvcBuilders.standaloneSetup(CourseController.class).build();
  }

  @Test
  public void addNewCourseTest() throws Exception {
    //build request body
    Course course = Course
      .builder()
      .name("test-course")
      .price(100)
      .duration("0 month")
      .build();
    //call controller endpoints
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/courses")
          .contentType("application/json")
          .content(asJsonString(course))
          .accept("application/json")
      )
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  public void getAllTheCoursesTest() throws Exception {
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .get("/courses")
          .accept("application/json")
          .contentType("application/json")
      )
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1));
  }

  private String asJsonString(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
