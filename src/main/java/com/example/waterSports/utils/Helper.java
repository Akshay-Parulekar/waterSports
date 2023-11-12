package com.example.waterSports.utils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class Helper
{
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static String[] arrayActivity = {"Jet Ski Ride", "Banana Ride", "Seating Bumper", "Sleeping Bumper"};
    public static String[] arrayMonth = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    public static int PrintBill(String title, String header, String footer, String address, String contact, String printerName, Long billNo, String customerName, String activities, Integer nPerson, Double rate, String date)
    {
        // Setup Printer
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
        patts.add(Sides.DUPLEX);

        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
        if (ps.length == 0) {
            // No Printer Found
            return 0;
        }

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService myService = null;
        for (PrintService printService : printServices) {
            System.out.println(printService.getName());
            if (printService.getName().equals(printerName)) {
                myService = printService;
                break;
            }
        }

        if (myService == null) {
            // No Printer Found
            return 0;
        }

        // Preparing the content
        ByteArrayOutputStream expected = new ByteArrayOutputStream();

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes((header.replaceAll("/","\n") + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight3());
        expected.writeBytes((title + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes((wordWrap(address, 32) + "\n").getBytes());
        expected.writeBytes(("Phone : " + contact + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Left));

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
        expected.writeBytes(("BillNo : " + billNo + ", Date : " + date + "\n").getBytes());
        expected.writeBytes(("Customer : " + customerName + "\n").getBytes());
        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));

        expected.writeBytes(("-".repeat(32) + "\n").getBytes());
        expected.writeBytes((wordWrap("Activities : " + activities, 32) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
        expected.writeBytes(("Total = " + decimalFormat.format(rate) + " X " + nPerson + " = " + decimalFormat.format(rate * nPerson) + "\n").getBytes());

        expected.writeBytes(("-".repeat(32) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
        expected.writeBytes((footer + "\n\n\n\n").getBytes());

        // QR Code, Bar Code

//        expected.writeBytes(POS.POSPrinter.BarCode.SetBarcodeHeightInDots(300));
//        expected.writeBytes(POS.POSPrinter.BarCode.SetBarWidth(POS.BarWidth.Thinnest));
//
//        expected.writeBytes(POS.POSPrinter.FontSelect.FontA());
//        expected.writeBytes(POS.POSPrinter.BarCode.PrintBarcode(POS.BarcodeType.ITF, "0123456789"));
//        expected.writeBytes("ITF 0123445\n".getBytes());
//
//        expected.writeBytes(POS.POSPrinter.BarCode.Code39("CR123445"));
//        expected.writeBytes("Code 39 CR123445\n".getBytes());
//
//        expected.writeBytes(POS.POSPrinter.QrCode.Print("www.techsapphire.in"));
//        expected.writeBytes("www.techsapphire.in\n\n\n\n".getBytes());

        // Cutting the page
        expected.writeBytes(POS.POSPrinter.CutPage());

        // Printing the content to paper
        DocPrintJob job = myService.createPrintJob();
        Doc doc = new SimpleDoc(expected.toByteArray(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
        try {
            job.print(doc, new HashPrintRequestAttributeSet());
            return 1;
        } catch (PrintException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    //    return 1;
    }
    

    public static String wordWrap(String text, int lineLength) {
        StringBuilder wrappedText = new StringBuilder();
        int currentLineLength = 0;

        String[] words = text.split(" ");
        for (String word : words) {
            if (currentLineLength + word.length() + 1 <= lineLength) {
                if (currentLineLength > 0) {
                    wrappedText.append(" ");
                    currentLineLength++;
                }
                wrappedText.append(word);
                currentLineLength += word.length();
            } else {
                wrappedText.append("\n").append(word);
                currentLineLength = word.length();
            }
        }

        return wrappedText.toString();
    }
}
