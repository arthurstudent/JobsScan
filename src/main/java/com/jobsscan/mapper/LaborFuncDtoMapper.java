package com.jobsscan.mapper;

import com.jobsscan.dto.LaborFunctionDto;
import com.jobsscan.utils.GeneratorUtils;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class LaborFuncDtoMapper {
    private static final String COMA = ",";

    public Set<LaborFunctionDto> toLaborDto(String value) {
        return StringUtils.isBlank(value) ? new HashSet<>() : splitLabels(value);
    }

    public <T, U> U generalLaborFuncDtoMapper(@NonNull T mapFrom, @NonNull Class<U> className) {
        return (U) new ModelMapper().map(mapFrom, className);
    }

    public Set<LaborFunctionDto> splitLabels(String value) {
        return Arrays.stream(value.split(COMA))
                .map(splitLabel -> new LaborFunctionDto(GeneratorUtils.generateId(), splitLabel))
                .collect(Collectors.toSet());
    }
}
