package com.substring.helpdesk.utils.validator;

import com.substring.helpdesk.dto.request.AIRequestDTO;
import com.substring.helpdesk.exception.custom.AIServiceValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AIRequestValidator {

    public void validate(AIRequestDTO requestDTO){
        if (requestDTO==null){
            throw new AIServiceValidatorException("Please tell me how can help you..");
        }
        if (requestDTO.getQuery()==null || requestDTO.getQuery().isBlank()){
            throw new AIServiceValidatorException(
                    "Query cannot be empty"
            );
        }
    }
}
