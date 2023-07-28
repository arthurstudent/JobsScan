package com.jobsscan.validator;

import com.jobsscan.domain.VacancyEntity;
import com.jobsscan.exception.VacancyServiceException;
import com.jobsscan.utils.CustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class VacancyValidator {

    private final static String PLACE = "VacancyServiceImpl:";

    public void validateVacancyPresence(boolean check, String publicId, String message) {
        if (check) {
            throw new VacancyServiceException(message.formatted(publicId));
        }
    }

    public void validateVacancyList(List<VacancyEntity> vacancyEntityList, String message) {
        if (CustomUtils.collectionNullCheck(vacancyEntityList)) {
            log.warn(PLACE + message);
            throw new VacancyServiceException(message);
        }
    }
}
