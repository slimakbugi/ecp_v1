package com.project_1.ecp_v1.user;

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
