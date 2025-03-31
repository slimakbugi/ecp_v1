package com.project_1.ecp_v1.controller;

import com.project_1.ecp_v1.dto.RecordDTO;
import com.project_1.ecp_v1.service.RecordServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
public class RecordController {
    private final RecordServiceImpl recordService;

    public RecordController(RecordServiceImpl recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<RecordDTO> getRecord(@PathVariable Integer id){
        Optional<RecordDTO> record = recordService.getRecordDtoById(id);

        return record
                .map(r -> ResponseEntity.ok().body(r))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecordDTO>> getUsersRecords(@PathVariable Integer userId){
        List<RecordDTO> usersRecords = recordService.getRecordsDtoByUserId(userId);

        if(usersRecords.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usersRecords);
        }
    }

    @PostMapping()
    public ResponseEntity<?> addRecord(@RequestBody RecordDTO record){
//        Checking if the record / user is null
        isRecordOrUserNull(record);

//        Checking if the start is before end
        isStartBeforeEnd(record);

//        Checking if the start/end time are not overlapping with existing records
        isTimeOutsideRange(record);

//        Adding record to base
        RecordDTO addedRecord;

        try {
            addedRecord = recordService.addRecord(record);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("User not found!");

        }

//        Preparing URI header
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/single/{id}")
                .buildAndExpand(addedRecord.id())
                .toUri();

        return ResponseEntity.created(uri).body(addedRecord);
    }

    @PatchMapping("/single/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable Integer id, @RequestBody RecordDTO record){
        
//        Checking if the record / user is null
        isRecordOrUserNull(record);

//        Checking if the start is before end
        isStartBeforeEnd(record);

//        Checking if the start/end time are not overlapping with existing records
        isTimeOutsideRange(record);

        return recordService.partiallyUpdateRecord(id, record)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/single/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Integer id){
        boolean isRecordDeleted = recordService.deleteRecord(id);

        if(isRecordDeleted){
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    Auxiliary methods
    private static void isRecordOrUserNull(RecordDTO record) {
        if(record == null || record.user() == null){
            ResponseEntity.badRequest().body("Record/user is null!");
        }
    }

    private void isTimeOutsideRange(RecordDTO record) {
        int timeOutsideRange = recordService.isTimeOutsideRange(record);

        if (timeOutsideRange > 0){
            ResponseEntity.badRequest().body("The start/end time is overlapping with existing records!");
        }
    }

    private static void isStartBeforeEnd(RecordDTO record) {
        if(record.start().isAfter(record.end())){
            ResponseEntity.badRequest().body("The start time is equal or after the end time!");
        }
    }


}
