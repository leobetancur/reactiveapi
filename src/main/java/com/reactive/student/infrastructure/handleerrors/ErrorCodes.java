package com.reactive.student.infrastructure.handleerrors;

import com.reactive.student.infrastructure.configuration.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorCodes {

    private ServerProperties serverProperties;
    private Map<String,String> mapErrors;
    private Map<String,String> listHttpStatus;

    @Autowired
    public ErrorCodes(ServerProperties serverProperties){
        this.serverProperties = serverProperties;
        mapErrors = new HashMap<>();
        listHttpStatus = new HashMap<>();
        extractListErrors();
    }

    private void extractListErrors(){
        serverProperties.getErrors().forEach((s, typeError) -> mapErrors.put(typeError.getCode(), typeError.getMessage()));
        serverProperties.getErrors().forEach((s, typeError) -> listHttpStatus.put(typeError.getCode(), typeError.getHttpStatus()));
    }

    public String getErrorMessage(String code){
        if(!mapErrors.containsKey(code)){
            return "Error doesn't coded";
        }
        return mapErrors.get(code);
    }

    public Integer getHttpStatusCode(String code){
        if(!listHttpStatus.containsKey(code)){
            return 500;
        }
        return Integer.parseInt(listHttpStatus.get(code));
    }

}
