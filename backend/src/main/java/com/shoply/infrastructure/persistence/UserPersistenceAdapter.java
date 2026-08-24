package com.shoply.infrastructure.persistence;

import com.shoply.domain.model.User;
import com.shoply.domain.repository.UserRepository;
import com.shoply.infrastructure.persistence.entity.UserEntity;
import com.shoply.infrastructure.persistence.repository.SpringDataRoleRepository;
import com.shoply.infrastructure.persistence.repository.SpringDataUserRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceAdapter implements UserRepository {
  private final SpringDataUserRepository users;
  private final SpringDataRoleRepository roles;
  public UserPersistenceAdapter(SpringDataUserRepository users, SpringDataRoleRepository roles) { this.users=users; this.roles=roles; }
  public Optional<User> findByEmail(String email) { return users.findByEmail(email).map(this::domain); }
  public User save(User user) {
    var role = roles.findByName(user.role()).orElseThrow(() -> new IllegalStateException("Required role is missing"));
    return domain(users.save(new UserEntity(user.id(), user.email(), user.passwordHash(), role)));
  }
  private User domain(UserEntity entity) { return new User(entity.getId(), entity.getEmail(), entity.getPasswordHash(), entity.getRole()); }
}
