package com.shoply.application.port;
import com.shoply.domain.model.User;
public interface TokenIssuer { String issue(User user); }
