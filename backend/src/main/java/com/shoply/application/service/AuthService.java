package com.shoply.application.service;

import com.shoply.application.dto.AuthTokenDto;
import com.shoply.application.port.*;
import com.shoply.domain.model.User;
import com.shoply.domain.repository.UserRepository;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

public class AuthService {
  private final UserRepository users; private final PasswordHasher passwords; private final TokenIssuer tokens;
  public AuthService(UserRepository users, PasswordHasher passwords, TokenIssuer tokens){this.users=users;this.passwords=passwords;this.tokens=tokens;}
  public AuthTokenDto register(String email,String password){
    String normalized=email.trim().toLowerCase(Locale.ROOT);
    if(users.findByEmail(normalized).isPresent())
      throw new IllegalArgumentException("Email is already registered");
    
    String role = "CUSTOMER";
    String adminEmailsEnv = System.getenv("SHOPLY_ADMIN_EMAILS");
    if (adminEmailsEnv != null && !adminEmailsEnv.isEmpty()) {
      for (String adminEmail : adminEmailsEnv.split(",")) {
        if (adminEmail.trim().equalsIgnoreCase(normalized)) {
          role = "ADMIN";
          break;
        }
      }
    }
    
    return token(users.save(new User(UUID.randomUUID(),normalized,passwords.hash(password),role)));
  }
  public AuthTokenDto login(String email,String password){User user=users.findByEmail(email.trim().toLowerCase(Locale.ROOT)).orElseThrow(()->new NoSuchElementException("Invalid email or password"));if(!passwords.matches(password,user.passwordHash()))throw new NoSuchElementException("Invalid email or password");return token(user);}
  private AuthTokenDto token(User u){return new AuthTokenDto(tokens.issue(u),u.id(),u.email(),u.role());}
}
