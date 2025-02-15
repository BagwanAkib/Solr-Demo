package com.akib.solr.demo.model;

import org.apache.solr.client.solrj.beans.Field;

public class SolrDocumentObject {

	@Field
	public String id;
	@Field
	public String name;
	@Field
	public String content;

	public SolrDocumentObject() {
	}

	public SolrDocumentObject(String id, String name, String content) {
		this.id = id;
		this.name = name;
		this.content = content;
	}

	@Override
	public String toString() {
		return "SolrDocumentObject[ id=" + id + ", name=" + name + ", content=" + content + " ]";
	}	
}
