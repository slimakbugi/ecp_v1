package com.project_1.ecp_v1.service;

import com.project_1.ecp_v1.dto.RecordDTO;
import com.project_1.ecp_v1.mapper.RecordMapper;
import com.project_1.ecp_v1.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService{

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;

//    Constructor
    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
    }

//    Methods
    @Override
    public Optional<RecordDTO> getRecordDtoById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<RecordDTO> getRecordsDtoByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public boolean deleteRecord(Integer id) {
        return false;
    }

    @Override
    public RecordDTO addRecord(RecordDTO record) {
        return null;
    }

    @Override
    public Optional<RecordDTO> partiallyUpdateRecord(Integer id, RecordDTO record) {
        return Optional.empty();
    }
}
