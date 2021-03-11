package com.arsh.twitter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arsh.twitter.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
}
