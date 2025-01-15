package com.ternak.sapi.controller;

import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.payload.*;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.model.User;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.HewanService;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.service.PkbService;
import com.ternak.sapi.service.UserService;
import com.ternak.sapi.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserService userService = new UserService();

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getRole().equalsIgnoreCase("1") ? "ROLE_ADMINISTRATOR" : currentUser.getRole().equalsIgnoreCase("2") ? "ROLE_PETUGAS" : "ROLE_PETERNAK","", "");
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) throws IOException {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) throws IOException {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) throws IOException {
        User user = userRepository.findByUsername(username);
        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());
        return userProfile;
    }

    @GetMapping("/users")
    public PagedResponse<User> getUsers(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) throws IOException {
        return userService.getAllUser(page, size);
    }

    @GetMapping("/users/not-used-account")
    public PagedResponse<User> getUserNotUses(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) throws IOException {
        return userService.getUserNotUsedAccount(page, size);
    }

    @PostMapping ("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) throws IOException {
        try {
            User user = userService.createUser(userRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{userId}")
                    .buildAndExpand(user.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "User Created Successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error while inserting data"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }

    }

    @PostMapping ("/users/bulk")
    public ResponseEntity<?> createUserBulk(@RequestBody List<UserRequest> userRequest) throws IOException {
        try {
            userService.createBulkUser(userRequest);
            return ResponseEntity.ok(new ApiResponse(true, "Bulk User Created Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected error occurred."));
        }

    }

//    @PostMapping ("/users/petugasBulk")
//    public ResponseEntity<?> createUserPetugas(@Valid @RequestBody UserRequest userRequest) throws IOException {
//        try {
//            User user = userService.createUserPetugasBulk(userRequest);
//
//            URI location = ServletUriComponentsBuilder
//                    .fromCurrentRequest().path("/{userId}")
//                    .buildAndExpand(user.getId()).toUri();
//
//            return ResponseEntity.created(location)
//                    .body(new ApiResponse(true, "User Created Successfully"));
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest()
//                    .body(new ApiResponse(false, e.getMessage()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest()
//                    .body(new ApiResponse(false, "Error while inserting data"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(false, "An unexpected error occurred."));
//        }
//    }
//
//    @PostMapping ("/users/peternakBulk")
//    public ResponseEntity<?> createUserPeternak(@Valid @RequestBody UserRequest userRequest) throws IOException {
//        try {
//            User user = userService.createUserPeternakBulk(userRequest);
//
//            URI location = ServletUriComponentsBuilder
//                    .fromCurrentRequest().path("/{userId}")
//                    .buildAndExpand(user.getId()).toUri();
//
//            return ResponseEntity.created(location)
//                    .body(new ApiResponse(true, "User Created Successfully"));
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest()
//                    .body(new ApiResponse(false, e.getMessage()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest()
//                    .body(new ApiResponse(false, "Error while inserting data"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(false, "An unexpected error occurred."));
//        }
//    }
}
