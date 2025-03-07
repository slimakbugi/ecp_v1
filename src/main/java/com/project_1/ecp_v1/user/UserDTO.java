package com.project_1.ecp_v1.user;

import java.time.LocalDate;

public record UserDTO(
        Integer id,
        String firstname,
        String lastname,
        String email
) {
}
