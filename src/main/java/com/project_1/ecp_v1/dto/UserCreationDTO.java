package com.project_1.ecp_v1.dto;

import com.project_1.ecp_v1.enums.UserPositions;

import java.time.LocalDate;

public record UserCreationDTO(
        String firstname,
        String lastname,
        String email,
        LocalDate dateOfBirth,
        UserPositions position,
        LocalDate employmentDate
) {
}
