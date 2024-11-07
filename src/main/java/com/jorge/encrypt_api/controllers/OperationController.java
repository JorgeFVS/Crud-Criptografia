package com.jorge.encrypt_api.controllers;

import com.jorge.encrypt_api.domain.operation.Operation;
import com.jorge.encrypt_api.dto.OperationDTO;
import com.jorge.encrypt_api.dto.OperationResponseDTO;
import com.jorge.encrypt_api.services.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    final private OperationService service;

    public OperationController(OperationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Operation> create(@RequestBody OperationDTO operationDTO, UriComponentsBuilder uriBuilder) {
        Operation newOperation = this.service.create(operationDTO);
        var uri = uriBuilder.path("/api/operation/{id}").buildAndExpand(newOperation.getId()).toUri();
        return ResponseEntity.created(uri).body(newOperation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationResponseDTO> read(@PathVariable Long id) {
        OperationResponseDTO operation = this.service.read(id);
        return ResponseEntity.ok(operation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operation> update(@PathVariable Long id, @RequestBody OperationDTO operationDTO) {
        Operation updateOperation = this.service.update(id, operationDTO);
        return ResponseEntity.ok(updateOperation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
