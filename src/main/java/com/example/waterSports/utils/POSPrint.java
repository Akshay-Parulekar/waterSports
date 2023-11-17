package com.example.waterSports.utils;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.io.ByteArrayOutputStream;

public class POSPrint {

	public static void main(String[] args) {
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
		PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
		patts.add(Sides.DUPLEX);

		PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
		if (ps.length == 0) {
			throw new IllegalStateException("No Printer found");
		}

		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		PrintService myService = null;
		for (PrintService printService : printServices) {
			System.out.println(printService.getName());
			if (printService.getName().equals("POS-80-Series")) {
				myService = printService;
				break;
			}
		}

		if (myService == null) {
			throw new IllegalStateException("Printer not found");
		}

		ByteArrayOutputStream expected = new ByteArrayOutputStream();

		expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));

		expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight3());
		expected.writeBytes("Shop Title\n".getBytes());

		expected.writeBytes(POS.POSPrinter.CharSize.Normal());
		expected.writeBytes("Address Line 1\n".getBytes());
		expected.writeBytes("Address Line 2\n".getBytes());
		expected.writeBytes("Address Line 3\n".getBytes());
		expected.writeBytes("Phone No. 900000000\n".getBytes());

		expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Left));

		expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
		expected.writeBytes("Item                     Price  Qty   Total\n".getBytes());
		expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));

		expected.writeBytes(("-".repeat(44) + "\n").getBytes());
		expected.writeBytes("Item 1                     $10    2     $20\n".getBytes());
		expected.writeBytes("Item 2                     $15    3     $45\n".getBytes());
		expected.writeBytes("Item 3                     $15    3     $45\n".getBytes());
		expected.writeBytes(("-".repeat(44) + "\n").getBytes());

		expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
		expected.writeBytes("Total : $110\n".getBytes());
		expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));

		expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
		expected.writeBytes("Thank you for coming\n".getBytes());

		expected.writeBytes(POS.POSPrinter.BarCode.SetBarcodeHeightInDots(300));
		expected.writeBytes(POS.POSPrinter.BarCode.SetBarWidth(POS.BarWidth.Thinnest));

		expected.writeBytes(POS.POSPrinter.FontSelect.FontA());
		expected.writeBytes(POS.POSPrinter.BarCode.PrintBarcode(POS.BarcodeType.ITF, "0123456789"));
		expected.writeBytes("ITF 0123445\n".getBytes());

		expected.writeBytes(POS.POSPrinter.BarCode.Code39("CR123445"));
		expected.writeBytes("Code 39 CR123445\n".getBytes());

		expected.writeBytes(POS.POSPrinter.QrCode.Print("www.techsapphire.in"));
		expected.writeBytes("www.techsapphire.in\n\n\n\n".getBytes());

		expected.writeBytes(POS.POSPrinter.CutPage());

		DocPrintJob job = myService.createPrintJob();
		Doc doc = new SimpleDoc(expected.toByteArray(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
		try {
			job.print(doc, new HashPrintRequestAttributeSet());
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

