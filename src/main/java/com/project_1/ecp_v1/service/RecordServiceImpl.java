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

//    Public methods
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

        // !!!!!!!!!!!!! dorobić odpowiedź
        isTimeOutsideRange(record);

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

//    Private methods
private void isTimeOutsideRange(Record record) {

    recordRepository.findAll().stream()
            .filter(r -> r.getUser().getId().equals(record.getUser().getId()))
            .filter(r -> r.getStart().getYear() == record.getStart().getYear())
            .filter(r -> r.getStart().getMonth() == record.getStart().getMonth())
            .filter(r -> r.getStart().getDayOfMonth() == record.getStart().getDayOfMonth())
            .forEach(r -> {
                if(record.getStart().isAfter(r.getStart()) && record.getStart().isBefore(r.getEnd())){
                    throw new RuntimeException("The start time is overlapping with existing records!");
                } else if (record.getEnd().isAfter(r.getStart()) && record.getEnd().isBefore(r.getEnd())) {
                    throw new RuntimeException("The end time is overlapping with existing records!");
                }
            });
}

}
