package com.uday.vijayadairy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.uday.vijayadairy.dto.Loginrequest;
import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.service.Userservice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class Usercontroller {

    @Autowired
    Userservice userservice;
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Loginrequest clientuser) {

        String email = clientuser.getEmail();
        String password = clientuser.getPassword();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "productStatus", false,
                            "message", "Email or password cannot be empty"
                    ));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {

            String token = userservice.login(user);

            return ResponseEntity.ok(Map.of(
            		"email",user.getEmail(),
                    "productStatus", true,
                    "token", token
            ));

        } catch (RuntimeException e) {
        	System.out.println(e.getMessage()+" "+e);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "productStatus", false,
                            "message", e.getMessage()
                    ));
        }
    }
    
    
    
    
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@Valid @RequestBody User user, HttpSession session) {

        if (user.getEmail() == null || user.getPassword() == null || user.getConfirmpassword() == null) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "productStatus", HttpStatus.BAD_REQUEST.value(),
                            "message", "Email, password and confirm password should not be empty"));
        }

        if (!user.getEmail().contains("@")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid email format"));
        }

        String name = user.getEmail().substring(0, user.getEmail().indexOf("@"));
        user.setName(name);

        String message = userservice.checkuser(user);

        if (message.equals("User Already exists")
                || message.equals("Passwords incorrect")
                || message.equals("Password should not be empty")
                || message.equals("Invalid user data")) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", message));
        }

        session.setAttribute("user", user);

        String otp = userservice.sendotp(user);
        session.setAttribute("otp", otp);

        return ResponseEntity.ok(
                Map.of(
                        "productStatus", true,
                        "message", "otp sent successfully"
                )
        );
    }

    @PostMapping("/verifyotp")
    @ResponseBody
    public ResponseEntity<?> verifyOtp(@RequestParam String otp, HttpSession session) {

        String generatedOtp = (String) session.getAttribute("otp");
        User user = (User) session.getAttribute("user");

        if (generatedOtp == null || user == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "productStatus", false,
                            "message", "Session expired. Please register again"
                    ));
        }

        if (generatedOtp.equals(otp)) {

            int indx = user.getEmail().indexOf("@");
            String name = user.getEmail().substring(0, indx);
            user.setName(name);

            userservice.registerUser(user);

            session.removeAttribute("otp");
            session.removeAttribute("user");

            return ResponseEntity.ok(
                    Map.of(
                            "productStatus", true,
                            "message", "OTP verified successfully"
                    )
            );
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "productStatus", false,
                        "message", "Invalid OTP"
                ));
    }

    @GetMapping("/all")
    @ResponseBody
    public List<User> getUsers() {
        return userservice.getallusers();
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotpassword(@RequestParam String email, HttpSession session) {

        if (email == null || email.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Email should not be empty"));
        }

        User user = new User();
        user.setEmail(email);

        String otp = userservice.sendotp(user);

        session.setAttribute("forgotpasswordotp", otp);
        session.setAttribute("email", email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "message", "forgot password otp has been sent to the user",
                        "email", email
                ));
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetpassword(@RequestParam String otp,
                                           @RequestParam String password,
                                           @RequestParam String confirmpassword,
                                           HttpSession session) {

        String generatedotp = (String) session.getAttribute("forgotpasswordotp");
        String toemail = (String) session.getAttribute("email");

        if (generatedotp == null || toemail == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Session expired. Please request OTP again"));
        }

        if (!otp.equals(generatedotp)) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid OTP"));
        }

        if (!password.equals(confirmpassword)) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Password and confirm password do not match"));
        }

        User user = new User();
        user.setEmail(toemail);
        user.setPassword(password);
        user.setConfirmpassword(confirmpassword);

        boolean result = userservice.updatepassword(user);

        session.invalidate();//it will remove all the data in the session
        if (result) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "message", "successfully updated the password",
                            "email", toemail
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Unable to update password"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userservice.getUserByemail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "name", user.getName(),
                        "mobile", user.getMobilenumber(),
                        "email", user.getEmail()
                )
        );
    }
}