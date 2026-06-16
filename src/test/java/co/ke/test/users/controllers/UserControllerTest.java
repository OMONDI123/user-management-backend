package co.ke.test.users.controllers;

// Core Mockito Static Imports
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Spring MockMvc Static Request/Response Matchers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean; // Standard for Spring Boot 3.x
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.ke.test.users.enums.Roles;
import co.ke.test.users.enums.Status;
import co.ke.test.users.models.AddressModel;
import co.ke.test.users.models.UserModel;
import co.ke.test.users.response.UserResponse;
import co.ke.test.users.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc 
@DisplayName("UserController Integration Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean 
    private UserService userService;

    private UserModel sampleModel;
    private UserResponse sampleResponse;

    @BeforeEach
    void setUp() {
        AddressModel address = new AddressModel();
        address.setStreet("123 Main Street");
        address.setCity("Nairobi");
        address.setState("Nairobi County");
        address.setPostalCode("00100");
        address.setCountry("Kenya");
        address.setPrimary(true);

        sampleModel = new UserModel();
        sampleModel.setEmail("alice@example.com");
        sampleModel.setFirstName("Alice");
        sampleModel.setLastName("Wanjiru");
        sampleModel.setRole(Roles.ADMIN);
        sampleModel.setUserAddresses(List.of(address));

        sampleResponse = new UserResponse();
        sampleResponse.setEmail("alice@example.com");
        sampleResponse.setFirstName("Alice");
        sampleResponse.setLastName("Wanjiru");
        sampleResponse.setRole(Roles.ADMIN);
        sampleResponse.setStatus(Status.ACTIVE);
    }

  
    @Nested
    @DisplayName("POST /users")
    class CreateOrUpdateUser {

        @Test
        @DisplayName("returns 200 and the saved user when body is valid")
        void create_validBody_returns200() throws Exception {
            when(userService.createOrUpdateUser(any(UserModel.class)))
                    .thenReturn(ResponseEntity.ok(sampleResponse));

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleModel)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.email").value("alice@example.com"))
                    .andExpect(jsonPath("$.firstName").value("Alice"))
                    .andExpect(jsonPath("$.lastName").value("Wanjiru"))
                    .andExpect(jsonPath("$.role").value("ADMIN"))
                    .andExpect(jsonPath("$.status").value("ACTIVE"));

            verify(userService, times(1)).createOrUpdateUser(any(UserModel.class));
        }

        @Test
        @DisplayName("returns 201 when service signals resource was created")
        void create_serviceReturns201_propagated() throws Exception {
            when(userService.createOrUpdateUser(any(UserModel.class)))
                    .thenReturn(ResponseEntity.status(201).body(sampleResponse));

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleModel)))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("returns 415 when Content-Type is not JSON")
        void create_wrongContentType_returns415() throws Exception {
            mockMvc.perform(post("/users")
                            .contentType(MediaType.TEXT_PLAIN)
                            .content("not json"))
                    .andExpect(status().isUnsupportedMediaType());

            verifyNoInteractions(userService);
        }

        @Test
        @DisplayName("delegates the full model to the service exactly once")
        void create_delegatesToServiceOnce() throws Exception {
            when(userService.createOrUpdateUser(any(UserModel.class)))
                    .thenReturn(ResponseEntity.ok(sampleResponse));

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleModel)))
                    .andExpect(status().isOk());

            verify(userService, times(1)).createOrUpdateUser(any(UserModel.class));
        }

        @Test
        @DisplayName("update: returns 200 with updated first name")
        void update_firstNameChanged_reflected() throws Exception {
            sampleModel.setFirstName("Alicia");
            sampleResponse.setFirstName("Alicia");

            when(userService.createOrUpdateUser(any(UserModel.class)))
                    .thenReturn(ResponseEntity.ok(sampleResponse));

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleModel)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("Alicia"));
        }
    }

  
    @Nested
    @DisplayName("GET /users")
    class GetUsers {

        @Test
        @DisplayName("returns a paginated result for a valid status")
        void getUsers_validStatus_returnsPage() throws Exception {
            Page<UserResponse> page = new PageImpl<>(List.of(sampleResponse));
            when(userService.getUsersByStatus(eq(Status.ACTIVE), eq(0), eq(10), isNull()))
                    .thenReturn(page);

            mockMvc.perform(get("/users").param("status", "ACTIVE"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(1))
                    .andExpect(jsonPath("$.content[0].email").value("alice@example.com"))
                    .andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.number").value(0));
        }

        @Test
        @DisplayName("applies default page=0 and size=10 when params are absent")
        void getUsers_defaultPagination_passedToService() throws Exception {
            when(userService.getUsersByStatus(any(), anyInt(), anyInt(), any()))
                    .thenReturn(Page.empty());

            mockMvc.perform(get("/users").param("status", "ACTIVE"))
                    .andExpect(status().isOk());

            verify(userService).getUsersByStatus(Status.ACTIVE, 0, 10, null);
        }

        @Test
        @DisplayName("forwards custom page, size and searchTerm to the service")
        void getUsers_customPaginationAndSearch() throws Exception {
            Page<UserResponse> page = new PageImpl<>(List.of(sampleResponse));
            when(userService.getUsersByStatus(eq(Status.ACTIVE), eq(1), eq(20), eq("Wanjiru")))
                    .thenReturn(page);

            mockMvc.perform(get("/users")
                            .param("status", "ACTIVE")
                            .param("page", "1")
                            .param("size", "20")
                            .param("search", "Wanjiru"))
                    .andExpect(status().isOk());
        }
    }
}
