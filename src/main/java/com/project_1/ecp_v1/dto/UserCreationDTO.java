package com.project_1.ecp_v1.dto;

import java.time.LocalDate;

public record UserCreationDTO(
        String firstname,
        String lastname,
        String email,
        LocalDate dateOfBirth,
        String position,
        LocalDate employmentDate
) {}
