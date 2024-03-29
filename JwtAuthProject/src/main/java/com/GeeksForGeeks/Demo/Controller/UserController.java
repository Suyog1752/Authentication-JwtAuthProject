package com.GeeksForGeeks.Demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GeeksForGeeks.Demo.Entities.UserInfo;
import com.GeeksForGeeks.Demo.Payloads.AuthRequest;
import com.GeeksForGeeks.Demo.Serivces.JwtService;
import com.GeeksForGeeks.Demo.Serivces.UserInfoService;

@RestController
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	
    @GetMapping("/welcome")
	public String welocme() {
	 return "Welcome this endpoint is not secure";
	}
    
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
    	return userInfoService.addUser(userInfo);
    }
    
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
    	return"welcome to user Profile";
    }
    
    
    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public String adminProfile() {
    	return "Welcome to admin profile";
    }
    
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest  ){
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
    	if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		}else {
			throw new UsernameNotFoundException("invalid user request !!");
		}
    }
}