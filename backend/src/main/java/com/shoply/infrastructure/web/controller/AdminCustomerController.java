package com.shoply.infrastructure.web.controller;
import com.shoply.application.dto.AdminCustomerDto; import com.shoply.application.service.AdminCustomerService; import java.util.List; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin/customers") public class AdminCustomerController {private final AdminCustomerService customers;public AdminCustomerController(AdminCustomerService customers){this.customers=customers;}@GetMapping public List<AdminCustomerDto> all(){return customers.all();}}
