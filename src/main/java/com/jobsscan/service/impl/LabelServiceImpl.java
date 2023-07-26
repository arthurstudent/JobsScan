package com.jobsscan.service.impl;

import com.jobsscan.domain.LabelEntity;
import com.jobsscan.repository.LabelRepository;
import com.jobsscan.service.LabelService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public LabelEntity saveIfNotExist(LabelEntity labelEntity) {
        Optional<LabelEntity> optionalLabel = Optional.ofNullable(labelRepository.getLabel(labelEntity.getLaborFunction()));

        if (optionalLabel.isEmpty()) {
            return labelRepository.save(labelEntity);
        }
        return labelEntity;
    }
}
