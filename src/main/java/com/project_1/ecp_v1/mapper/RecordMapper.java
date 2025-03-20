package com.project_1.ecp_v1.mapper;

import com.project_1.ecp_v1.dto.RecordDTO;
import com.project_1.ecp_v1.model.Record;
import com.project_1.ecp_v1.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    UserMapper userMapper;
    UserRepository userRepository;

    public RecordMapper(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Record toEntity(RecordDTO recordDTO){
        if(recordDTO == null){
            return null;
        }
        return new Record(
                recordDTO.id(),
                recordDTO.start(),
                recordDTO.end(),
                recordDTO.project(),
                recordDTO.body(),
                recordDTO.place(),
                recordDTO.recordType(),
                userRepository.findById(recordDTO.userDTO().id())
                        .orElseThrow(() -> new RuntimeException("User not found!"))
                );
    }

    public RecordDTO toDto(Record record){
        if (record == null){
            return null;
        }
        return new RecordDTO(
                record.getId(),
                record.getStart(),
                record.getEnd(),
                record.getProject(),
                record.getBody(),
                record.getPlace(),
                record.getRecordType(),
                record.getWorkTime(),
                userMapper.toDto(record.getUser())
        );
    }
}
