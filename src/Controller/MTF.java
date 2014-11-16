package Controller;

import java.util.ArrayList;
import java.util.List;

public class MTF {
	public MTF() {
		this.concepts = new ArrayList<Concept>();
	}

	private List<Concept> concepts;
	
	public List<Concept> getConcepts() {
		return concepts;
	}

}
