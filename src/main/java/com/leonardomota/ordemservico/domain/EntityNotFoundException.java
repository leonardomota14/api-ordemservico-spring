package com.leonardomota.ordemservico.domain;

import com.leonardomota.ordemservico.domain.exception.DomainException;

public class EntityNotFoundException extends DomainException {
	
private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(String message) {
		
		super(message);
		
	}
}
