package com.jorge.encrypt_api.services;

import com.jorge.encrypt_api.domain.operation.Operation;
import com.jorge.encrypt_api.domain.operation.exceptions.OperationNotFoundException;
import com.jorge.encrypt_api.dto.OperationDTO;
import com.jorge.encrypt_api.dto.OperationResponseDTO;
import com.jorge.encrypt_api.repositories.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OperationService {

    private OperationRepository repository;
    private EncryptService encryptService;

    public OperationService(OperationRepository repository, EncryptService encryptService) {
        this.repository = repository;
        this.encryptService = encryptService;
    }

    public Operation create(OperationDTO operationDTO) {
        Operation operation = new Operation();
        String userDocumentHashed = this.encryptService.encryptData(operationDTO.userDocument());
        String creditCardHashed = this.encryptService.encryptData(operationDTO.creditCardToken());

        operation.setCreditCardToken(creditCardHashed);
        operation.setUserDocument(userDocumentHashed);
        operation.setValue(operationDTO.operationValue());

        this.repository.save(operation);
        return operation;
    }

    public OperationResponseDTO read(Long id) throws OperationNotFoundException {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));

        String userDocumentHashed = this.encryptService.decryptData(operation.getUserDocument());
        String creditCardHashed = this.encryptService.decryptData(operation.getCreditCardToken());

        OperationResponseDTO dto = new OperationResponseDTO(operation.getId(), userDocumentHashed, creditCardHashed, operation.getValue());

        return dto;
    }

    @Transactional
    public Operation update(Long id, OperationDTO data) {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));

        if (!data.creditCardToken().isEmpty()) {
            operation.setCreditCardToken(this.encryptService.encryptData(data.creditCardToken()));
        }
        if (!data.userDocument().isEmpty()) {
            operation.setUserDocument(this.encryptService.encryptData(data.userDocument()));
        }
        if(data.operationValue() != null) {
            operation.setValue(data.operationValue());
        }

        return operation;
    }

    public void delete(Long id) {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        repository.delete(operation);
    }
}
