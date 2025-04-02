package com.project_1.ecp_v1.service;

import com.project_1.ecp_v1.dto.RecordDTO;
import com.project_1.ecp_v1.mapper.RecordMapper;
import com.project_1.ecp_v1.model.Record;
import com.project_1.ecp_v1.repository.RecordRepository;
import org.springframework.http.ResponseEntity;
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

//    Main methods
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

        Record saved = recordRepository.save(record);

        return recordMapper.toDto(saved);
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


//    Auxiliary methods
    @Override
    public void isTimeOutsideRange(RecordDTO record) {

        int result = 0;

        List<Record> list = recordRepository.findAll().stream()
                .filter(r -> r.getUser().getId().equals(record.user().id()))
                .filter(r -> r.getStart().getYear() == record.start().getYear())
                .filter(r -> r.getStart().getMonth() == record.start().getMonth())
                .filter(r -> r.getStart().getDayOfMonth() == record.start().getDayOfMonth())
                .toList();

        for(Record r : list){
            if (record.start().isEqual(r.getStart())
                    || (record.start().isAfter(r.getStart())
                    && record.start().isBefore(r.getEnd()))) {
                result++;
            } else if (record.end().isEqual(r.getStart())
                    || (record.end().isAfter(r.getStart())
                    && record.end().isBefore(r.getEnd()))) {
                result++;
            }
        }

        if (result > 0){
            ResponseEntity.badRequest().body("The start/end time is overlapping with existing records!");
        }
    }

    @Override
    public void setRecordsEditable(Integer userId, Integer month, boolean isEditable){
        recordRepository.findAll().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .filter(r -> r.getStart().getMonthValue() == month)
                .forEach(r -> {
                    r.setIsEditable(isEditable);
                    recordRepository.save(r);
                });
    }

    @Override
    public void isRecordOrUserNull(RecordDTO record) {
        if(record == null || record.user() == null){
            ResponseEntity.badRequest().body("Record/user is null!");
        }
    }

    @Override
    public void isStartBeforeEnd(RecordDTO record) {
        if(record.start().isAfter(record.end())){
            ResponseEntity.badRequest().body("The start time is equal or after the end time!");
        }
    }

}
