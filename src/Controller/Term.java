package Controller; 

public class Term {
	private String term;  
	private String status;     // "Status"
	private String lockReason; // "Lock Reason"
	private String footNote1;  // "Footnote 1"
	private String footNote2;  // "Footnote 2"
	
	public Term() {
		this.term = "";
		this.status = "";
		this.lockReason = "";
		this.footNote1 = "";
		this.footNote2 = "";
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status; 
	}
	public String getLockReason() {
		return lockReason;
	}
	public void setLockReason(String lockReason) {
		this.lockReason = lockReason.replace(" term", ""); // eliminate redundant words
		this.lockReason = lockReason.replace("e Benennung", "");
	}
	private String getFootNote1() {
		return footNote1;
	}
	public void setFootNote1(String footNote1) {
		this.footNote1 = footNote1.trim();
	}
	private String getFootNote2() {
		return footNote2;
	}
	public String getFootNotes() {
		String fn1 = getFootNote1();
		if (!"".equals(fn1)) 
			return fn1.concat(" ").concat(getFootNote2()).trim();
		else
			return fn1;
	}

	public void setFootNote2(String footNote2) {
		this.footNote2 = footNote2.trim();
	}

}
