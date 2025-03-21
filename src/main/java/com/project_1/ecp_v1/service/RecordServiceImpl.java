package com.project_1.ecp_v1.service;

import com.project_1.ecp_v1.dto.RecordDTO;
import com.project_1.ecp_v1.mapper.RecordMapper;
import com.project_1.ecp_v1.model.Record;
import com.project_1.ecp_v1.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        return recordRepository.findById(id)
                .map(recordMapper::toDto);
    }

    @Override
    public List<RecordDTO> getRecordsDtoByUserId(Integer userId) {
        return recordRepository.findAll().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .map(recordMapper::toDto)
                .sorted(Comparator.comparing(RecordDTO::start))
                .toList();
    }

    @Override
    public boolean deleteRecord(Integer id) {
        if(!recordRepository.existsById(id)){
            return false;
        } else {
            recordRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public RecordDTO addRecord(RecordDTO recordDto) {
        Record record = recordMapper.toEntity(recordDto);

        recordRepository.save(record);
        return recordMapper.toDto(record);
    }

    @Override
    public Optional<RecordDTO> partiallyUpdateRecord(Integer id, RecordDTO recordDto) {
        return recordRepository.findById(id)
                .map(existingRecord -> {
                    if (recordDto.start() != null) existingRecord.setStart(recordDto.start());
                    if (recordDto.end() != null) existingRecord.setEnd(recordDto.end());
                    if (recordDto.project() != null) existingRecord.setProject(recordDto.project());
                    if (recordDto.body() != null) existingRecord.setBody(recordDto.body());
                    if (recordDto.place() != null) existingRecord.setPlace(recordDto.place());
                    if (recordDto.recordType() != null) existingRecord.setRecordType(recordDto.recordType());

                    return recordRepository.save(existingRecord);
                })
                .map(recordMapper::toDto);
    }

//    public boolean setRecordsNotEditable(Integer userId, Integer month){}
}
