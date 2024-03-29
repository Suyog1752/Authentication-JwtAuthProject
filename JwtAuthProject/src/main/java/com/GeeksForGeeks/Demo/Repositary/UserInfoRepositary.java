package com.GeeksForGeeks.Demo.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeeksForGeeks.Demo.Entities.UserInfo;

@Repository
public interface UserInfoRepositary extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo>  findByName(String username);

}
