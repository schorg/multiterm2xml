package Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

@SuppressWarnings("deprecation")  // Because of deprecated CellRangeAddress

public class ExcelPrettyPrinter {
	
	enum Repr {
		DOTTED, // First line header
		THIN,   // Second line header
		BOLD,   // Terms
		STRIKE, // Locked Terms
		WRAP,   // Wrapped text entries: Definition, Source, Categories
		NORMAL  // All others
		}; 
		
	enum Color{WHITE, GREY, ONE, TWO, THREE, FOUR, FIVE, SIX}
	
	Map<String, Color> lang2color;
	Map<String, String> lang2language;
		
	private HSSFWorkbook wb;
	private HSSFSheet terminology;
	// private HSSFSheet userguide;
	
	private HSSFFont boldFont;
	private HSSFFont boldStrikeOutFont;
	private HSSFFont superScriptFont;
	
	private int widthUnit;
	
	private List<String> languageList;
	private int languageWidth;
	private FileOutputStream fileOut;

	
	public ExcelPrettyPrinter(String destFile) throws FileNotFoundException 
	{
		wb = new HSSFWorkbook();
		terminology = wb.createSheet("Terminology");
		// userguide = wb.createSheet("User guide");
		
		String[] langs = {"DE-DE", "EN-US", "FR-FR", "ES-ES", "IT-IT", "PT-BR"};
		languageList = Arrays.asList(langs);
		
		languageWidth = 6; // Number of fields per language
		fileOut = new FileOutputStream(destFile);
		
		lang2color = new HashMap<String, Color>();
		lang2color.put("DE-DE", Color.ONE);
		lang2color.put("EN-US", Color.TWO);
		lang2color.put("FR-FR", Color.THREE);
		lang2color.put("ES-ES", Color.FOUR);
		lang2color.put("IT-IT", Color.FIVE);
		lang2color.put("PT-BR", Color.SIX);
		
		lang2language = new HashMap<String, String>();
		lang2language.put("DE-DE", "German");
		lang2language.put("EN-US", "English");
		lang2language.put("FR-FR", "French");
		lang2language.put("ES-ES", "Spanish");
		lang2language.put("IT-IT", "Italian");
		lang2language.put("PT-BR", "Portuguese");
		
    	boldFont = wb.createFont();
    	boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	
    	boldStrikeOutFont = wb.createFont();
    	boldStrikeOutFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	boldStrikeOutFont.setStrikeout(true);
    	
    	superScriptFont = wb.createFont();
    	superScriptFont.setTypeOffset(HSSFFont.SS_SUPER);
    	
    	widthUnit = terminology.getColumnWidth(0) / 10;
    	
    	mkStyles();
	}
	
	private Map<Color, Map<Repr, HSSFCellStyle>> styles;
	
	private HSSFCellStyle mkStyle(Color color, Repr repr) {
		HSSFCellStyle cs = wb.createCellStyle();

		short c = 0;
		switch (color) {
		case WHITE: c = HSSFColor.WHITE.index; break;
		case GREY: c = HSSFColor.GREY_25_PERCENT.index; break;
		case ONE: c = HSSFColor.LIGHT_ORANGE.index; break;
		case TWO: c = HSSFColor.LIME.index; break;
		case THREE: c = HSSFColor.GOLD.index; break;
		case FOUR: c = HSSFColor.LAVENDER.index; break;
		case FIVE: c = HSSFColor.LIGHT_YELLOW.index; break;
		case SIX: c = HSSFColor.LIGHT_CORNFLOWER_BLUE.index; break;

		}
		
		cs.setFillForegroundColor(c);	
    	cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	cs.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
    	cs.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
		
		switch (repr){
		case NORMAL: break;
		case DOTTED: 
			cs.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
			cs.setFont(boldFont);
			break;
		case BOLD: cs.setFont(boldFont); break;
		case STRIKE: cs.setFont(boldStrikeOutFont); break;
		case WRAP: cs.setWrapText(true); break;
		case THIN: cs.setBorderBottom(HSSFCellStyle.BORDER_THIN); break;
		}
		
		return cs;		
	}
	
	private void mkStyles() {
		styles = new HashMap<Color, Map<Repr, HSSFCellStyle>>();
		for (Color c : Color.values()) {
			Map<Repr, HSSFCellStyle> rs = new HashMap<Repr, HSSFCellStyle>();
			styles.put(c, rs);
			for (Repr r : Repr.values()) {
				rs.put(r, mkStyle(c, r));
			}
		}
	}
	
	private int mkConceptHeader(HSSFRow row, int col)
	{ 
		HSSFCell app = row.createCell(col++);
		HSSFCell cat = row.createCell(col++);
		HSSFCellStyle style = styles.get(Color.GREY).get(Repr.DOTTED);
		app.setCellValue("HBMTerm");
		app.setCellStyle(style);
		cat.setCellStyle(style);
        terminology.addMergedRegion(new CellRangeAddress(0, 0, col-2, col-1));
		return col;
	}
	
	private int mkLanguageHeader(HSSFRow row, String langId, int col) 
	{
		Color c = lang2color.get(langId);
		HSSFCellStyle style = styles.get(c).get(Repr.DOTTED);

		HSSFCell cell = row.createCell(col++);
		cell.setCellValue(lang2language.get(langId));
		cell.setCellStyle(style);
		for (int i=1; i<languageWidth; i++) {
			cell = row.createCell(col++);
			cell.setCellStyle(style);
		}
        terminology.addMergedRegion(new CellRangeAddress(0, 0, col-languageWidth, col-1));
        return col;
	}
	
	private void mkLanguageHeaders(int row) 
	{
		int col = 0;
		HSSFRow r = terminology.createRow(row);
		col = mkConceptHeader(r, col);
		for (String langId : languageList) {
			col = mkLanguageHeader(r, langId, col);
		}
	}
	
	private int mkConceptHeading(HSSFRow row, int col){       
		terminology.setColumnWidth(col, 21 * widthUnit);
		HSSFCell app = row.createCell(col++);
        terminology.setColumnWidth(col, 17 * widthUnit);
		HSSFCell cat = row.createCell(col++);
		HSSFCellStyle style = styles.get(Color.GREY).get(Repr.THIN);
		app.setCellValue("Application");
		app.setCellStyle(style);
		cat.setCellValue("Categories");
		cat.setCellStyle(style);	
		return col;
	}
	
	private int mkLanguageHeading(HSSFRow row, String langId, int col) {
		Color lc = lang2color.get(langId);
		
        terminology.setColumnWidth(col, 27 * widthUnit);
		HSSFCell trm = row.createCell(col++);
        terminology.setColumnWidth(col, 35 * widthUnit);
		HSSFCell def = row.createCell(col++);
        terminology.setColumnWidth(col, 12 * widthUnit);
		HSSFCell src = row.createCell(col++);
        terminology.setColumnWidth(col, 12 * widthUnit);
		HSSFCell stat = row.createCell(col++);
        terminology.setColumnWidth(col, 12 * widthUnit);
		HSSFCell lr = row.createCell(col++);
        terminology.setColumnWidth(col, 22 * widthUnit);
		HSSFCell fn = row.createCell(col++);

		trm.setCellValue("Term");
		trm.setCellStyle(styles.get(lc).get(Repr.THIN));
		def.setCellValue("Definition");
		def.setCellStyle(styles.get(lc).get(Repr.THIN));	
		src.setCellValue("Source");
		src.setCellStyle(styles.get(lc).get(Repr.THIN));	
		stat.setCellValue("Status");
		stat.setCellStyle(styles.get(lc).get(Repr.THIN));	
		lr.setCellValue("Lock");
		lr.setCellStyle(styles.get(lc).get(Repr.THIN));	
		fn.setCellValue("Footnote");
		fn.setCellStyle(styles.get(lc).get(Repr.THIN));	
		return col;		
	}
	
	private void mkHeading(int row){
		int col = 0;
		HSSFRow r = terminology.createRow(row);
		col = mkConceptHeading(r, col);
		for (String langId : languageList) {
			col = mkLanguageHeading(r, langId, col);
		}
	}
	
	private int mkConceptFirstLine(HSSFRow row, Concept concept, int conceptIdx, int col) {
		Color c = (conceptIdx%2 == 1) ? Color.GREY : Color.WHITE;

		HSSFCell app = row.createCell(col++);
		HSSFCell cat = row.createCell(col++);
		
		app.setCellValue(concept.getSubjectArea());
		app.setCellStyle(styles.get(c).get(Repr.NORMAL));
		cat.setCellValue(new HSSFRichTextString(concept.getCategories()));
		cat.setCellStyle(styles.get(c).get(Repr.WRAP));	
		return col;
	}
	
	private int mkLanguageFirstLine(HSSFRow row, String langId, Concept concept, int conceptIdx, int col) {
		Color lc = lang2color.get(langId);
		Color c = (conceptIdx%2 == 1) ? lc : Color.WHITE;
		
		HSSFCell trm = row.createCell(col++);
        HSSFCell def = row.createCell(col++);
        HSSFCell src = row.createCell(col++);
		HSSFCell stat = row.createCell(col++);
		HSSFCell lr = row.createCell(col++);
		HSSFCell fn = row.createCell(col++);
		
		Language language = concept.getLanguages().get(langId);

		if (language != null) {
			trm.setCellValue(" ");
			def.setCellValue(language.getDefinition());	
			src.setCellValue(language.getSource());
			stat.setCellValue(" ");
			lr.setCellValue(" ");
			fn.setCellValue(" ");
    	}						
    	     	
    	trm.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		def.setCellStyle(styles.get(c).get(Repr.WRAP));	
		src.setCellStyle(styles.get(c).get(Repr.WRAP));	
		stat.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		lr.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		fn.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		
		return col;
	}
	
	private int mkConceptFurtherLine(HSSFRow row, Concept concept, int conceptIdx, int col) {
		Color c = (conceptIdx%2 == 1) ? Color.GREY : Color.WHITE;

		HSSFCell app = row.createCell(col++);
		HSSFCell cat = row.createCell(col++);
		
		app.setCellStyle(styles.get(c).get(Repr.NORMAL));
		cat.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		return col;
	}
	
	private int mkLanguageFurtherLine(HSSFRow row, String langId, Concept concept, int conceptIdx, int termIdx, int col) {
		Color lc = lang2color.get(langId);
		Color c = (conceptIdx%2 == 1) ? lc : Color.WHITE;
		
		HSSFCell trm = row.createCell(col++);
        HSSFCell def = row.createCell(col++);
        HSSFCell src = row.createCell(col++);
		HSSFCell stat = row.createCell(col++);
		HSSFCell lr = row.createCell(col++);
		HSSFCell fn = row.createCell(col++);
		
		Boolean locked = false;
		Language language = concept.getLanguages().get(langId);
		Term term = null;
		if (language != null) {
			if (termIdx < language.getTerms().size()) {
				term = language.getTerms().get(termIdx);
			}
    		if (term != null) {
    			locked = !"".equals(term.getLockReason());
    			String t = term.getTerm();
    			String f = term.getFootNotes();
    			if (!"".equals(f)) { //add a * to term and footnote
    				HSSFRichTextString ts = new HSSFRichTextString(t+"*");
    				ts.applyFont(t.length(), t.length(), superScriptFont);
    				trm.setCellValue(ts);		
    				HSSFRichTextString fs = new HSSFRichTextString("*"+f);
    				ts.applyFont(0, 0, superScriptFont);
    				fn.setCellValue(fs);
    			} else {
	    			trm.setCellValue(t);    				
	    			fn.setCellValue(term.getFootNotes());
    			}
    			stat.setCellValue(term.getStatus());
    			lr.setCellValue(term.getLockReason());
    		} else {
    			trm.setCellValue(" "); // prevent overlapping footnote from left
    		}
    		
		}
		
    	trm.setCellStyle((locked) ? styles.get(c).get(Repr.STRIKE) : styles.get(c).get(Repr.BOLD));
    	def.setCellStyle(styles.get(c).get(Repr.WRAP));	
		src.setCellStyle(styles.get(c).get(Repr.WRAP));	
		stat.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		lr.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		fn.setCellStyle(styles.get(c).get(Repr.NORMAL));	
		
		return col;
	}
	
	private void mkFirstLine(Concept concept, int conceptIdx, int row) {
		int col = 0;
		HSSFRow r = terminology.createRow(row);
		col = mkConceptFirstLine(r, concept, conceptIdx, col);
		for (String langId : languageList) {
			col = mkLanguageFirstLine(r, langId, concept, conceptIdx, col);
		}
	}

	private void mkFurtherLine(Concept concept, int conceptIdx, int termIdx, int row) {
		int col = 0;
		HSSFRow r = terminology.createRow(row); 
		col = mkConceptFurtherLine(r, concept, conceptIdx, col);
		for (String langId : languageList) {
			col = mkLanguageFurtherLine(r, langId, concept, conceptIdx, termIdx, col);
		}
	}

	
	private void mkFinish() {
		terminology.createFreezePane( 2, 2, 2, 2 );
		int col = 2;
    	for (int i=0; i < languageList.size(); i++) {
    		terminology.groupColumn(col + 1, col + languageWidth - 1);
    		col = col + languageWidth;
    	}
	}
	
	public void mkSheet(MTF mtf){
		int row = 0;
		mkLanguageHeaders(row++);
		mkHeading(row++);
		for (int i=0; i<mtf.getConcepts().size(); i++) {
			Concept cpt = mtf.getConcepts().get(i);
			mkFirstLine(cpt, i, row++);
			for (int j=0; j < cpt.getMaxTermCount(); j++) {
				mkFurtherLine(cpt, i, j, row++);
			}
		}
		mkFinish();
	}
	
	public void close() throws IOException
	{	
		wb.write(fileOut);
		fileOut.close();
	}
	    
	public static void main (String args[]) throws IOException
	{
		ExcelPrettyPrinter mepp = new ExcelPrettyPrinter("mytest.xls");

		Concept concept = new Concept();
		concept.setCategories("bla\nblabla\nblablabla");
		
		mepp.mkLanguageHeaders(0);
		mepp.mkHeading(1);
		mepp.mkFirstLine(concept, 0, 2);
		
		mepp.mkFinish();
		mepp.close();
	}
}
