package com.project_1.ecp_v1.mapper;

import com.project_1.ecp_v1.dto.RecordDTO;
import com.project_1.ecp_v1.model.Record;
import com.project_1.ecp_v1.repository.RecordRepository;
import com.project_1.ecp_v1.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    UserMapper userMapper;
    UserRepository userRepository;
    RecordRepository recordRepository;

//    Constructor
    public RecordMapper(UserMapper userMapper, UserRepository userRepository, RecordRepository recordRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
    }

//    From DTO to entity mapper

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
                userRepository.findById(recordDTO.user().id())
                        .orElseThrow(() -> new RuntimeException("User not found!"))
                );
    }

//    public Record existingToEntity(RecordDTO recordDTO){
//         Optional<Record> record = recordRepository.findById(recordDTO.id());
//        );
//    }

//    From entity to DTO mapper
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
                userMapper.toDto(record.getUser()),
                record.getIsEditable()
        );
    }
}
