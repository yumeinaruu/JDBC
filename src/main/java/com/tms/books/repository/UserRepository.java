package com.tms.books.repository;

import com.tms.JPA.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> { //ентити и тип айдишки оттуда

}
