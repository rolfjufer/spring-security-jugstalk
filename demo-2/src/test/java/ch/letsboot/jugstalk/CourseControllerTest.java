package ch.letsboot.jugstalk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String getCourseExpected = "{\"id\":1,\"title\":\"Container & Kubernetes Security\",\"description\":\"In this course, participants will learn the fundamentals of security in Kubernetes and Container environments, and how to protect their applications and infrastructures from potential threats.\",\"city\":\"Zurich\",\"startDate\":\"14.05.2024\",\"endDate\":\"15.05.2024\",\"durationInDays\":2,\"price\":\"CHF 1’800.00\",\"trainerId\":2,\"trainerName\":\"Jonas Felix\",\"trainerEmail\":\"jonas.felix@letsboot.ch\"}";

    private String postCourse = "{\"id\":11,\"title\":\"Container & Kubernetes Security\",\"description\":\"In this course, participants will learn the fundamentals of security in Kubernetes and Container environments, and how to protect their applications and infrastructures from potential threats.\",\"city\":\"Zurich\",\"startDate\":\"14.05.2024\",\"endDate\":\"15.05.2024\",\"durationInDays\":2,\"price\":800.00,\"trainerId\":2}";

    private String postCourseExpected = "{\"id\":11,\"title\":\"Container & Kubernetes Security\",\"description\":\"In this course, participants will learn the fundamentals of security in Kubernetes and Container environments, and how to protect their applications and infrastructures from potential threats.\",\"city\":\"Zurich\",\"startDate\":\"14.05.2024\",\"endDate\":\"15.05.2024\",\"durationInDays\":2,\"price\":\"CHF 800.00\",\"trainerId\":2,\"trainerName\":\"Jonas Felix\",\"trainerEmail\":\"jonas.felix@letsboot.ch\"}";

    @Test
    public void loginWitUserNamePasswordThenAuthenticated() throws Exception {
        mockMvc.perform(formLogin("/login").user("admin").password("password"))
                .andExpect(authenticated().withUsername("admin"));
    }

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
    public void getCoursesAuthenticatedWithUserAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/courses/1")
                .with(user("admin")))
                .andExpect(content().json(getCourseExpected))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void getCoursesWithUserDetailsAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/courses/1"))
                .andExpect(content().json(getCourseExpected))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void getCoursesAnonymous() throws Exception {
        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCourseWithGivenId() throws Exception {
        mockMvc.perform(get("/api/v1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Container & Kubernetes Security"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addCourseWithRoleAdmin() throws Exception {
        mockMvc.perform(post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postCourse)
                .with(csrf()))  // Enable CSRF protection for the login request
                .andExpect(content().json(postCourseExpected))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteCourseWithRoleAdmin() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postCourse)
                .with(csrf()))  // Enable CSRF protection for the login request
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void formLoginWithUserAdmin() throws Exception {
        // Simulate form login (with CSRF protection)
        mockMvc.perform(post("/login")
             .with(csrf()))  // Enable CSRF protection for the login request
                .andExpect(status().isFound());
    }

    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("admin")
                .password("password");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("admin"));

       /* mockMvc.perform(logout())
                .andExpect(status().isFound());*/

    }

}




