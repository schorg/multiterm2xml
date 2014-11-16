package Controller;

import java.util.HashMap;
import java.util.Map;

public class Concept {
	
	public Concept() {
		this.languages = new HashMap<String, Language>();
	}
	
	private String subjectArea; // "Subject Area" number 
	private String categories;  // "Categories" |-separated strings aka tags
	private Map<String, Language> languages;
	
	public String getSubjectArea() {
		return subjectArea;
	}
	public void setSubjectArea(String subjectArea) {
		subjectArea = "0".equals(subjectArea) ? " " : subjectArea;
		this.subjectArea = subjectArea.trim();
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		categories = categories.trim();
		this.categories = categories.replace("| ", "\n");
	}
	public Map<String, Language> getLanguages() {
		return languages;
	}
	
	public int getMaxTermCount() {
		int max = 1;
		for (Map.Entry<String, Language> pair : languages.entrySet()) {
			int n = pair.getValue().getTerms().size();
			if (n > max) max = n;			
		}
		return max;
	}

}
