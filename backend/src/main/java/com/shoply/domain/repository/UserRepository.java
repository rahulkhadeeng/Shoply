package com.shoply.domain.repository;

import com.shoply.domain.model.User;
import java.util.Optional;

public interface UserRepository { Optional<User> findByEmail(String email); User save(User user); }
