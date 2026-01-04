package com.team42.churninsight.domain;

import com.team42.churninsight.domain.enums.*;

public record CustomerData(
        String customerId,
        Integer age,
        Gender gender,
        IncomeBracket incomeBracket,
        MaritalStatus maritalStatus,
        EducationLevel educationLevel,
        Occupation occupation
        // agrega aquí solo lo que realmente mande el request
) {}

