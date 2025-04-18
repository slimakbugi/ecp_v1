package com.project_1.ecp_v1.service;

import com.project_1.ecp_v1.dto.RecordDTO;

import java.util.List;
import java.util.Optional;

public interface RecordService {
    Optional<RecordDTO> getRecordDtoById(Integer id);
    List<RecordDTO> getRecordsDtoByUserId(Integer userId);
    boolean deleteRecord(Integer id);
    RecordDTO addRecord(RecordDTO record);
    Optional<RecordDTO> partiallyUpdateRecord(Integer id, RecordDTO record);
    void setRecordsEditable(Integer userId, Integer month, boolean isEditable);
    void isTimeOutsideRange(RecordDTO record);
    void isRecordOrUserNull(RecordDTO record);
    void isStartBeforeEnd(RecordDTO record);
}
