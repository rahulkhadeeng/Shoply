package com.shoply.application.service;
import com.shoply.application.dto.AdminCustomerDto;
import com.shoply.infrastructure.persistence.repository.SpringDataUserRepository;
import java.util.List;
public class AdminCustomerService { private final SpringDataUserRepository users; public AdminCustomerService(SpringDataUserRepository users){this.users=users;} public List<AdminCustomerDto> all(){return users.findAll().stream().map(u->new AdminCustomerDto(u.getId(),u.getEmail(),u.getRole())).toList();} }
