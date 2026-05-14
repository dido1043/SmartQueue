package org.ipt.smartqueue.controller;

import lombok.RequiredArgsConstructor;
import org.ipt.smartqueue.data.dto.BusinessDto;
import org.ipt.smartqueue.messages.Message;
import org.ipt.smartqueue.service.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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