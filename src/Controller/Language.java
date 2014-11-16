package Controller;

import java.util.ArrayList;
import java.util.List;


public class Language {
	public Language() {
		this.terms = new ArrayList<Term>();
	}
	String language;
	String definition; // "Definition"
	String source;     // "Source"
	List<Term> terms;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		// error is export: too many newlines (two instead of one for cr lf)
		this.definition = definition.replace("\n\n", "\n").trim();
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source.trim();
	}
	public List<Term> getTerms() {
		return terms;
	}

}
