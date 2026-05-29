package com.substring.helpdesk.repository;

import com.substring.helpdesk.dto.request.UserRequestDTO;
import com.substring.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
