package com.example.waterSports.utils;

import java.io.ByteArrayOutputStream;

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

public class POSPrint {

	public static void main(String[] args) {
//		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
//		PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
//		patts.add(Sides.DUPLEX);
//
//		PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
//		if (ps.length == 0) {
//			throw new IllegalStateException("No Printer found");
//		}
//
//		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//		PrintService myService = null;
//		for (PrintService printService : printServices) {
//			System.out.println(printService.getName());
//			if (printService.getName().equals("POS-58-Series")) {
//				myService = printService;
//				break;
//			}
//		}
//
//		if (myService == null) {
//			throw new IllegalStateException("Printer not found");
//		}
//
//		ByteArrayOutputStream expected = new ByteArrayOutputStream();
//
//		expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
//
//		expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight3());
//		expected.writeBytes("123456789123456789123456789123456789\n".getBytes());
//
//		expected.writeBytes(POS.POSPrinter.CharSize.Normal());
//		expected.writeBytes("123456789123456789123456789123456789\n".getBytes());
//
//		expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
//		expected.writeBytes("123456789123456789123456789123456789\n".getBytes());
//
//		expected.writeBytes(POS.POSPrinter.CutPage());
//
//		System.out.println("---------------");
//		System.out.println(expected.toString());
//
//		DocPrintJob job = myService.createPrintJob();
//		Doc doc = new SimpleDoc(expected.toByteArray(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
//		try {
//			job.print(doc, new HashPrintRequestAttributeSet());
//		} catch (PrintException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
