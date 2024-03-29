package com.GeeksForGeeks.Demo.Serivces;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GeeksForGeeks.Demo.Entities.UserInfo;
import com.GeeksForGeeks.Demo.Payloads.UserInfoDetails;
import com.GeeksForGeeks.Demo.Repositary.UserInfoRepositary;

@Service
public class UserInfoService implements UserDetailsService {
	@Autowired
	private UserInfoRepositary userInfoRepositary;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userDetail = userInfoRepositary.findByName(username);

		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not Found !!"));

	}
	
	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userInfoRepositary.save(userInfo);
		return "User Added Successfully !!";
	   
	}

}
