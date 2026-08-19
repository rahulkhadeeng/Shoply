package com.shoply.infrastructure.security;
import com.shoply.application.port.PasswordHasher; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service;
@Service public class BcryptPasswordHasher implements PasswordHasher {private final PasswordEncoder encoder;public BcryptPasswordHasher(PasswordEncoder encoder){this.encoder=encoder;}public String hash(String raw){return encoder.encode(raw);}public boolean matches(String raw,String hash){return encoder.matches(raw,hash);}}
