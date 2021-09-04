package com.thisara.utils.audit;

import java.time.OffsetDateTime;

import com.thisara.domain.Audit;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class RecordAuditor {

	private static final int defaultStatus = 1;
	
	public static Audit createAuditFields(Audit audit, String userId) {
		
		audit.setCreatedBy(userId);
		audit.setCreatedAt(OffsetDateTime.now());
		audit.setUpdatedAt(OffsetDateTime.now());
		audit.setUpdatedBy(userId);
		audit.setStatus(defaultStatus);
		
		return audit;
	}
}
