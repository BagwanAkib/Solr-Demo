package com.akib.solr.demo.model;

import java.io.Serializable;

public class SolrResultModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1039208301917343566L;
	public String SearchObject;
	public String id;

	public SolrResultModel(String id,String searchObject) {
		super();
		SearchObject = searchObject;
		this.id = id;
	}

	public SolrResultModel() {
	}

	public String getSearchObject() {
		return SearchObject;
	}

	public void setSearchObject(String searchObject) {
		SearchObject = searchObject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SolrResultModel [SearchObject=" + SearchObject + ", id=" + id + "]";
	}

}
