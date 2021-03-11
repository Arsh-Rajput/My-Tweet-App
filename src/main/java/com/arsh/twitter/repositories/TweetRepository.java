package com.arsh.twitter.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arsh.twitter.models.Tweet;
@Repository
public interface TweetRepository extends JpaRepository<Tweet,Integer> {

}
