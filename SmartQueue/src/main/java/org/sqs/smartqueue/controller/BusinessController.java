package org.sqs.smartqueue.controller;

import lombok.RequiredArgsConstructor;
import org.sqs.smartqueue.data.dto.BusinessDto;
import org.sqs.smartqueue.messages.Message;
import org.sqs.smartqueue.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping("/{name}")
    public ResponseEntity<BusinessDto> findByName(@PathVariable String name) {
        BusinessDto business = businessService.findByName(name);
        if (business == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(business);
    }

    @PostMapping
    public ResponseEntity<BusinessDto> create(@RequestBody BusinessDto businessDto) {
        return ResponseEntity.ok(businessService.create(businessDto));
    }
    @PostMapping("/add/{id}")
    public ResponseEntity<BusinessDto> add(@PathVariable Long id, @RequestBody LocalDateTime dateTime) {
        return ResponseEntity.ok(businessService.addAvailableHours(id, dateTime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessDto> update(@PathVariable Long id, @RequestBody BusinessDto businessDto) {
        return ResponseEntity.ok(businessService.update(id, businessDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String result = businessService.delete(id);
        if (result.equals(Message.BUSINESS_NOT_FOUND)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}