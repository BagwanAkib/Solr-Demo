### **Summary of Solr Operations in the Project**

This project integrates various Solr operations to enable efficient search and data management capabilities. Below is an overview of the key Solr-related functionalities implemented:

---
### SCREENSHOT

### Create page
#### **1. Adding Documents to Solr**
- A form is provided in the UI for users to create Solr objects.
- The form includes:
  - A `name` field (textbox) for the document's title.
  - A `content` field (textarea) for the document's content.
- Upon submission, the form sends a request to the Solr server to add the document.

![create](https://github.com/user-attachments/assets/d5766d3f-03e5-4cb2-b378-8b5b9e2dd840)

```java
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
```

### Created message
![created](https://github.com/user-attachments/assets/1a27523a-cb26-4be6-8082-f659088977ec)

---

### List and Search window

#### **2. Search Query Implementation**
- A search form enables users to query Solr with specific rules:
  - **Exact Search**: Enclose terms in double quotes (e.g., `"abc"`).
  - **AND Operation**: Use `+` to combine terms (e.g., `"abc" + "def"`).
  - **OR Operation**: Use `-` to combine terms (e.g., `"abc" - "def"`).
- Backend logic ensures the query is formatted according to the rules:
  - `+` is replaced with `AND`.
  - `-` is replaced with `OR`.
  - Plain text is wrapped in double quotes.
 ** Handling Double Quotes in Queries**
- When a query is enclosed in double quotes, the backend ensures it remains an exact search term.
- For queries with multiple terms enclosed in quotes, additional filter queries (`fq`) are generated to refine the search.
**Query Operators**
- **Default Query Operator**:
  - The `SolrQuery` object is configured to use `AND` as the default operator.
- **Dynamic Query Logic**:
  - If a query contains `+`, the operator is set to `AND`.
  - If a query contains `-`, the operator is set to `OR`.

![list](https://github.com/user-attachments/assets/bb6fbdee-95c5-422f-ae89-be64cff3cf19)
```java
    SolrQuery q = new SolrQuery();
		q.set("q", "*:*");
		q.addFilterQuery(SolrUtil.processQuery(query));
  try {
			QueryResponse queryResponse = SolrUtil.solrServer.query(SolrUtil.coreName, q);
			return queryResponse.getResults();
		} catch (SolrServerException e) {
			throw new Exception("SolrServerException :" + e.getMessage());
		} catch (IOException e) {
			throw new Exception("IOException :" + e.getMessage());
		}
```

---

### Delete Window alert
**3. Deleting Documents from Solr**
- Each document in the list is displayed with a delete button.
- The delete button sends a request to the Solr server using the endpoint `/delete?id=<document_id>`.
- This allows users to remove specific documents by their unique ID.

![delete-promt](https://github.com/user-attachments/assets/dfa511b2-638c-4f58-8fda-cb2cb2fa5764)

```java

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

```

### Once Deleted
![deleted-by-id](https://github.com/user-attachments/assets/f9a26812-d24b-456c-8af7-50cf40e7f93d)

---

### Remaining objects:
![remaining](https://github.com/user-attachments/assets/6ec2fcfb-5ef8-4838-b2ca-10941a199a20)

### **Usage Scenarios**
1. **Create Documents**: Users can add new documents to the Solr index using the form.
2. **Search**: Query Solr with advanced rules (AND, OR, exact match).
3. **Delete Documents**: Remove unwanted entries from the Solr index directly from the UI.
4. **Advanced Query Handling**: Backend logic ensures proper query formatting for Solr compatibility.
