package com.shoply.infrastructure.web.controller;

import com.shoply.application.dto.AdminDashboardDto;
import com.shoply.application.service.AdminDashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {
  private final AdminDashboardService dashboard;
  public AdminDashboardController(AdminDashboardService dashboard){this.dashboard=dashboard;}
  @GetMapping public AdminDashboardDto summary(){return dashboard.summary();}
}
