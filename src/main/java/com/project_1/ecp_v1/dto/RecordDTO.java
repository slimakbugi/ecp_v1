package com.project_1.ecp_v1.dto;

import java.time.LocalDateTime;

public record RecordDTO(
        Integer id,
        LocalDateTime start,
        LocalDateTime end,
        String project,
        String body,
        String place,
        String recordType,
        Long workTime,
        UserDTO user,
        boolean isEditable
) {}
