package com.charusat.pacelearn.Repository;

import com.charusat.pacelearn.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserEmail(String email);
//    boolean findByUserEmailExists(String email);
//    Optional<User> findOneByEmailIgnoreCase(String email);
}
