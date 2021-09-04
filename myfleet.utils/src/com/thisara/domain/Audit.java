package com.thisara.domain;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@MappedSuperclass
public class Audit {

	@Column(name = "record_created_by")
	private String createdBy;
	
	@Column(name = "record_updated_by")
	private String updatedBy;
	
	@Column(name = "record_created_at")
	private OffsetDateTime createdAt;
	
	@Column(name = "record_updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "record_status")
	private Integer status;
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
