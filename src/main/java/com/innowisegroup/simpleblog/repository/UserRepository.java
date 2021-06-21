package com.innowisegroup.simpleblog.repository;

import com.innowisegroup.simpleblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findByLastnameOrderByName(String surname);
    List<User> findByOrderByLastnameDesc();
    List<User> findByOrderByRole();
}
