package com.ecom.exceptions;

import com.ecom.entities.master_entity.MasSerialPK;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;

    MasSerialPK fiendPKId;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, MasSerialPK fiendPKId) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fiendPKId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fiendPKId = fiendPKId;
    }

}
