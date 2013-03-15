package com.db.gm.riskengine.tesla.client;

public enum AuditFields {

    AuditId				("AUDIT_ID")
    ,AuditAt			("AUDIT_AT")
    ,AuditBy			("AUDIT_BY");	
	
    String fieldName;

	private AuditFields(String fieldName) {
		this.fieldName = fieldName;
	}

}
