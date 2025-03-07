package com.project_1.ecp_v1.user;

import java.time.LocalDate;

public record UserCreationDTO(
        Integer id,
        String firstname,
        String lastname,
        String email,
        LocalDate dateOfBirth,
        UserPositions position,
        Boolean isHired
) {
}
