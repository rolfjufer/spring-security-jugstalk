package ch.letsboot.jugstalk.jugstalk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String getCourseExpected = "{\"id\":1,\"title\":\"Container & Kubernetes Security\",\"description\":\"In this course, participants will learn the fundamentals of security in Kubernetes and Container environments, and how to protect their applications and infrastructures from potential threats.\",\"city\":\"Zurich\",\"startDate\":\"14.05.2024\",\"endDate\":\"15.05.2024\",\"durationInDays\":2,\"price\":\"CHF 1’800.00\",\"trainerId\":2,\"trainerName\":\"Jonas Felix\",\"trainerEmail\":\"jonas.felix@letsboot.ch\"}";

    private final String postCourse = "{\"id\":11,\"title\":\"Container & Kubernetes Security\",\"description\":\"In this course, participants will learn the fundamentals of security in Kubernetes and Container environments, and how to protect their applications and infrastructures from potential threats.\",\"city\":\"Zurich\",\"startDate\":\"14.05.2024\",\"endDate\":\"15.05.2024\",\"durationInDays\":2,\"price\":800.00,\"trainerId\":2}";

    private final String postCourseExpected = "{\"id\":11,\"title\":\"Container & Kubernetes Security\",\"description\":\"In this course, participants will learn the fundamentals of security in Kubernetes and Container environments, and how to protect their applications and infrastructures from potential threats.\",\"city\":\"Zurich\",\"startDate\":\"14.05.2024\",\"endDate\":\"15.05.2024\",\"durationInDays\":2,\"price\":\"CHF 800.00\",\"trainerId\":2,\"trainerName\":\"Jonas Felix\",\"trainerEmail\":\"jonas.felix@letsboot.ch\"}";



    @Test
    public void getCoursesUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getCoursesWithRoleAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/courses/1"))
              .andExpect(content().json(getCourseExpected))
              .andExpect(status().isOk());
    }


    @Test
    public void postCourseWithRoleAdmin() throws Exception {
        // Simulate form login (with CSRF protection)
        mockMvc.perform(post("/api/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(postCourse)
            .with(user("admin").roles("ADMIN"))
            .with(csrf())) // Enable CSRF protection for the login request
            .andExpect(content().json(postCourseExpected))
            .andExpect(status().isOk());
    }


    @Test
    void demoEndpointSuccessfulAuthenticationTest() throws Exception {
        mockMvc.perform(
                 delete("/api/v1/courses/1").with(jwt().authorities(() -> "ROLE_ADMIN")))
                .andExpect(status().isOk());
    }
}




