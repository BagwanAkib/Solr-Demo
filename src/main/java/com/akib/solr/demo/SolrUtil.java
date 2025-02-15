package com.akib.solr.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.SSLConfig;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.akib.solr.demo.model.SolrResultModel;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrUtil {
	static final String solrUrl = "http://localhost:8983/solr";
	static final String core = "/searchCore";
	static final String coreName = "searchCore";
	static final String objectName = "SearchObject";
	public static final SolrClient solrServer = getSolr();
	public static final SolrClient solrServerCore = getSolrCore();
	// public static final Http2SolrClient solrServer2 = getSolrHTTP2();

	/*
	 * This method is deprecated after Solr 9.8
	 */
	public SolrClient getSolrCloud() {
		final List<String> solrUrls = new ArrayList<String>();
		solrUrls.add(solrUrl);
		return new CloudSolrClient.Builder(solrUrls).build();
	}

	public static SolrClient getSolr() {
		return new HttpSolrClient.Builder(solrUrl).build();
	}

	public static SolrClient getSolrCore() {
		return new HttpSolrClient.Builder(solrUrl + core).build();
	}

	/*
	 * Not implemented! facing some issue for listeners
	 */
	public static Http2SolrClient getSolrHTTP2() {
		Builder object = new Http2SolrClient.Builder(solrUrl);
		object.withSSLConfig(new SSLConfig(false, false, core, core, core, core));
		Http2SolrClient client = object.build();
		return client;
	}

	public static String processQuery(String query) {
		List<String> fqList = new ArrayList<>();
		if (query.contains("\"")) {
			// Handle double-quoted strings
			String[] parts = null;
			if (query.contains("-"))
				parts = query.split("\\-"); // Split by '-'
			else
				parts = query.split("\\+"); // Split by '+'
			for (String part : parts) {
				// Remove the double quotes and wrap the content in '*'
				String processed = part.replaceAll("\"", ""); // Remove quotes
				fqList.add(objectName + ":*" + processed + "*");
			}
		} else {
			// Handle single word
			fqList.add(objectName + ":" + query + "");
		}
		// Combine all fq clauses into a single string
		return String.join(" ", fqList);
	}

	public static SolrDocumentList getListOfSearch(String query) throws Exception {

		if (query == null || query.isBlank() || query.isEmpty())
			query = "*";
		String qOp = "AND";
		System.out.println("\n string q       : " + query);
		System.out.println("\n processQuery q : " + SolrUtil.processQuery(query));
		SolrQuery q = new SolrQuery();
		q.set("q", "*:*");
		q.addFilterQuery(SolrUtil.processQuery(query));

		if (query.contains("-")) {
			qOp = "OR";
		} else if (query.contains("+")) {
			qOp = "AND";
		}

		q.set("q.op", qOp);
		try {
			QueryResponse queryResponse = SolrUtil.solrServer.query(SolrUtil.coreName, q);
			return queryResponse.getResults();
		} catch (SolrServerException e) {
			throw new Exception("SolrServerException :" + e.getMessage());
		} catch (IOException e) {
			throw new Exception("IOException :" + e.getMessage());
		}
	}

	public static List<SolrResultModel> getConverted(SolrDocumentList list) {
		List<SolrResultModel> result = new ArrayList<SolrResultModel>();
		for (SolrDocument item : list) {

			result.add(new SolrResultModel(item.getFieldValue("id").toString(),
					item.getFieldValue(objectName).toString()));
		}
		return result;
	}
	
	public static  List<SolrResultModel> makeQueryResult(String query) throws Exception{
		return getConverted(getListOfSearch(query));
	}
}
