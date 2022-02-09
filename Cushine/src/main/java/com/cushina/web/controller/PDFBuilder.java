package com.cushina.web.controller;

import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cushina.web.bean.HotelBean;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 */
public class PDFBuilder extends AbstractITextPdfView {

	@SuppressWarnings("unchecked")
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String reportType = (String) model.get("reportType");
		System.out.println("getting in pdfg");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ reportType + ".pdf" + "\"");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		LinkedHashSet<HotelBean> listOfBusiness = (LinkedHashSet<HotelBean>) model
				.get("listOfAPIs");

		if (reportType != null) {
			if (reportType.equalsIgnoreCase("Address")) {
				donloadPDF(document, listOfBusiness);

			}
		}

	}

	private void donloadPDF(Document document,
			LinkedHashSet<HotelBean> listOfBusiness) throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("Hotel Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Address#", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("PhoneNumber", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("webSiteURL", font));
		table.addCell(cell);
		for (HotelBean accuracydto : listOfBusiness) {
			table.addCell(accuracydto.getHotelName());
			table.addCell(String.valueOf(accuracydto.getHotelAddress()));
			table.addCell(String.valueOf(accuracydto.getPhoneNumber()));
			table.addCell(String.valueOf(accuracydto.getEmail()));
			table.addCell(String.valueOf(accuracydto.getWebSiteURL()));
		}
		document.add(table);
	}

}
