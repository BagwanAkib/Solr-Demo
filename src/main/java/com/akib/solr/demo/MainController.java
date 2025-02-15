package com.akib.solr.demo;

import java.io.IOException;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akib.solr.demo.model.SolrDocumentObject;

@RestController
public class MainController {

	@GetMapping("/test")
	public String index() {
		UpdateResponse updateResponse = null;
		try {
			final SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", UUID.randomUUID().toString());
			doc.addField("name", "Amazon2 Kindle Paperwhite");

			updateResponse = SolrUtil.solrServer.add("searchCore", doc);
			SolrUtil.solrServer.commit("searchCore");

		} catch (SolrServerException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return updateResponse.toString();
	}

	@PostMapping("/create")
	public String createSolrObject(Model model, @RequestParam("name") String name,
			@RequestParam("content") String content) {
		UpdateResponse updateResponse = null;
		try {
			final SolrInputDocument doc = new SolrInputDocument();
			doc.addField("SearchObject",
					new SolrDocumentObject(UUID.randomUUID().toString(), name, content).toString());
			updateResponse = SolrUtil.solrServer.add("searchCore", doc);
			System.out.println(updateResponse.toString());
			SolrUtil.solrServer.commit("searchCore");
			if (updateResponse.getStatus() == 0) {
				return "<a href='/'> Object Created get back to Create page</a>";
			}

		} catch (SolrServerException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		return updateResponse.toString();
	}

	@GetMapping("/list")
	public String listSolrObjects(@RequestParam("q") String query) {
		try {
			return SolrUtil.getListOfSearch(query).toString();
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
	}
	

	@GetMapping("/delete")
	public String objectDelet(@RequestParam("id") String id) {
		try {
			System.out.println("Delete: id: | "+id);
			UpdateResponse response =SolrUtil.solrServerCore.deleteById(id);
			SolrUtil.solrServerCore.commit();
			if(response.getStatus() ==0)
				return "Deleted!<br>"
						+ "<a href='/uilist'>UI List</a>";
			else
				return response.getException().getMessage();
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
	}
}
