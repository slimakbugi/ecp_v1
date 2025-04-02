package com.project_1.ecp_v1.dto;

public record UserDTO(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String position
) {}
