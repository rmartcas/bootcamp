// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Core dto to implement search filters of other application Dtos
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public abstract class Page<E extends Core<?>> implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current page number.
	 */
	private int currentPage;
	/**
	 * Number of records by page.
	 */
	private int size;
	/**
	 * The list of properties used to sort results.
	 */
	private List<PageOrder> pageOrder;
	/**
	 * Total number of pages.
	 */
	private int totalPages;
	/**
	 * Total number of records.
	 */
	private int totalRecords;
	/**
	 * Pagination prefix for sql wrap.
	 */
	@JsonIgnore
	private transient String prefix;
	/**
	 * Pagination suffix for sql wrap.
	 */
	@JsonIgnore
	private transient String suffix;
	/**
	 * Obtained records from search.
	 */
	private List<E> records = new ArrayList<>();
	/**
	 * The dto for which this filter is used.
	 */
	private E filters;

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof Page)) return false;
		final Page<?> other = (Page<?>) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		if (this.getCurrentPage() != other.getCurrentPage()) return false;
		if (this.getSize() != other.getSize()) return false;
		if (this.getTotalPages() != other.getTotalPages()) return false;
		if (this.getTotalRecords() != other.getTotalRecords()) return false;
		final java.lang.Object this$pageOrder = this.getPageOrder();
		final java.lang.Object other$pageOrder = other.getPageOrder();
		if (this$pageOrder == null ? other$pageOrder != null : !this$pageOrder.equals(other$pageOrder)) return false;
		final java.lang.Object this$records = this.getRecords();
		final java.lang.Object other$records = other.getRecords();
		if (this$records == null ? other$records != null : !this$records.equals(other$records)) return false;
		final java.lang.Object this$filters = this.getFilters();
		final java.lang.Object other$filters = other.getFilters();
		if (this$filters == null ? other$filters != null : !this$filters.equals(other$filters)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof Page;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + this.getCurrentPage();
		result = result * PRIME + this.getSize();
		result = result * PRIME + this.getTotalPages();
		result = result * PRIME + this.getTotalRecords();
		final java.lang.Object $pageOrder = this.getPageOrder();
		result = result * PRIME + ($pageOrder == null ? 43 : $pageOrder.hashCode());
		final java.lang.Object $records = this.getRecords();
		result = result * PRIME + ($records == null ? 43 : $records.hashCode());
		final java.lang.Object $filters = this.getFilters();
		result = result * PRIME + ($filters == null ? 43 : $filters.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public Page() {
	}

	/**
	 * Current page number.
	 * @return {@link Integer} the currentPage
	 */
	@java.lang.SuppressWarnings("all")
	public int getCurrentPage() {
		return this.currentPage;
	}

	/**
	 * Number of records by page.
	 * @return {@link Integer} the size
	 */
	@java.lang.SuppressWarnings("all")
	public int getSize() {
		return this.size;
	}

	/**
	 * The list of properties used to sort results.
	 * @return {@link List} of {@link PageOrder} the pageOrder
	 */
	@java.lang.SuppressWarnings("all")
	public List<PageOrder> getPageOrder() {
		return this.pageOrder;
	}

	/**
	 * Total number of pages.
	 * @return {@link Integer} the totalPages
	 */
	@java.lang.SuppressWarnings("all")
	public int getTotalPages() {
		return this.totalPages;
	}

	/**
	 * Total number of records.
	 * @return {@link Integer} the totalRecords
	 */
	@java.lang.SuppressWarnings("all")
	public int getTotalRecords() {
		return this.totalRecords;
	}

	/**
	 * Pagination prefix for sql wrap.
	 * @return {@link String} the prefix
	 */
	@java.lang.SuppressWarnings("all")
	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * Pagination suffix for sql wrap.
	 * @return {@link String} the suffix
	 */
	@java.lang.SuppressWarnings("all")
	public String getSuffix() {
		return this.suffix;
	}

	/**
	 * Obtained records from search.
	 * @return {@link List} the records
	 */
	@java.lang.SuppressWarnings("all")
	public List<E> getRecords() {
		return this.records;
	}

	/**
	 * The dto for which this filter is used.
	 * @return the filters
	 */
	@java.lang.SuppressWarnings("all")
	public E getFilters() {
		return this.filters;
	}

	/**
	 * Current page number.
	 * @param currentPage {@link Integer} The currentPage
	 */
	@java.lang.SuppressWarnings("all")
	public void setCurrentPage(final int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * Number of records by page.
	 * @param size {@link Integer} The size
	 */
	@java.lang.SuppressWarnings("all")
	public void setSize(final int size) {
		this.size = size;
	}

	/**
	 * The list of properties used to sort results.
	 * @param pageOrder {@link List} The pageOrder
	 */
	@java.lang.SuppressWarnings("all")
	public void setPageOrder(final List<PageOrder> pageOrder) {
		this.pageOrder = pageOrder;
	}

	/**
	 * Total number of pages.
	 * @param totalPages {@link Integer} The totalPages
	 */
	@java.lang.SuppressWarnings("all")
	public void setTotalPages(final int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Total number of records.
	 * @param totalRecords {@link Integer} The totalRecords
	 */
	@java.lang.SuppressWarnings("all")
	public void setTotalRecords(final int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * Pagination prefix for sql wrap.
	 * @param prefix {@link String} The prefix
	 */
	@JsonIgnore
	@java.lang.SuppressWarnings("all")
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Pagination suffix for sql wrap.
	 * @param suffix {@link String} The suffix
	 */
	@JsonIgnore
	@java.lang.SuppressWarnings("all")
	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	/**
	 * Obtained records from search.
	 * @param records {@link List} The records
	 */
	@java.lang.SuppressWarnings("all")
	public void setRecords(final List<E> records) {
		this.records = records;
	}

	/**
	 * The dto for which this filter is used.
	 * @param filters The filters
	 */
	@java.lang.SuppressWarnings("all")
	public void setFilters(final E filters) {
		this.filters = filters;
	}
}