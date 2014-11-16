package Controller;

public class Test {
	public static void main (String args[]) throws Exception
	{
			MultiTermDomProcessor mtd = new MultiTermDomProcessor("aktuell.xml");
			MTF mtf = mtd.process();
			// free enough heap space
			mtd = null;
			System.gc();
			
			ExcelPrettyPrinter epp = new ExcelPrettyPrinter("aktuell.xls");
			epp.mkSheet(mtf);
			epp.close();
	}
}
