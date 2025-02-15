package com.akib.solr.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.akib.solr.demo.model.SolrResultModel;

@Controller
public class UIController {

	@GetMapping("/")
	public String IndexPage() {
		return "main";
	}

	@GetMapping("/uilist")
	public String listPage(Model model, @RequestParam(name = "q", required = false) String query) {
		
		model.addAttribute("q", query == null ? "*" : query);
		try {
			List<SolrResultModel> result = SolrUtil.makeQueryResult(query);
			model.addAttribute("list", result);
		} catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("info", e.getMessage());
		}
		return "uilist";
	}
}
