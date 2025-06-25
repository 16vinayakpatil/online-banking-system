package com.axis.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.axis.entity.Transaction;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class TransactionPDFExporter {
	
	private List<Transaction> listTransaction;

	public TransactionPDFExporter(List<Transaction> listTransaction) {
		this.listTransaction = listTransaction;
	}
	
	private void writeTableHeader(PdfPTable table) {
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font =FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("transactionid",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("amount",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("datetime",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("description",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("transactiontype",font));
		table.addCell(cell);
		
//		cell.setPhrase(new Phrase("accid",font));
//		table.addCell(cell);
		
	}
	private void writeTableData(PdfPTable table) {
		
		for(Transaction transaction : listTransaction) {
			table.addCell(String.valueOf(transaction.getTransactionid()));
			table.addCell(String.valueOf(transaction.getAmount()));
			table.addCell(String.valueOf(transaction.getDatetime()));
			table.addCell(transaction.getDescription());
			table.addCell(transaction.getTransactiontype());
			//table.addCell(String.valueOf(transaction.account));
			
		}
		
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font font =FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.BLUE);
		font.setSize(18);
		
		document.add(new Paragraph("List of all Transactions",font));
		
		PdfPTable table =new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		
		writeTableHeader(table);
		writeTableData(table);
		
		document.add(table);
		document.close();
		
	}
	
	
	

}
