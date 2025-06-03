package com.nettoya.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Constructor estándar con mensaje personalizado
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructor detallado con nombre de recurso, campo y valor
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
