package com.shoply.application.dto;

public record AdminDashboardDto(long productCount, long categoryCount, long customerCount, long orderCount, long lowInventoryCount) {}
