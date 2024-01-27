package com.example.waterSports.utils;

import com.example.waterSports.modal.OrderDetailsWaterSport;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Helper
{
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static String[] arrayActivity = {"Jet Ski Ride", "Banana Ride", "Seating Bumper", "Sleeping Bumper"};
    public static String[] arrayMonth = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    public static int particularsWidth = 24;
    public static int rateWidth = 8;
    public static int quantityWidth = 7;
    public static int amountWidth = 9;
    public static int maxChars = 48;

    public static int PrintBill(String title, String header, String footer, String address, String contact, String printerName, Long billNo, String customerName, String activities, Integer nPerson, Double rate, String date, List<OrderDetailsWaterSport> listOrderDet)
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

        expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight2());
        expected.writeBytes((title + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes((wrapTextOld(address, 32).toString() + "\n").getBytes());
        expected.writeBytes(("Phone : " + contact + "\n").getBytes());

        expected.writeBytes(("-".repeat(maxChars) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Left));
        int middleSpace = maxChars - ("BillNo : " + billNo).length() - ("Date : " + date).length();
        String strBillNoDate = String.format("%s%" + middleSpace + "s%s", "BillNo : " + billNo, "", "Date : " + date + "\n");

        expected.writeBytes((strBillNoDate).getBytes());
        expected.writeBytes(("Customer : " + customerName + "\n").getBytes());
        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));

        expected.writeBytes(("-".repeat(maxChars) + "\n").getBytes());

        String columnHeads = String.format("%-" + particularsWidth + "s%"+ rateWidth +"s%" + quantityWidth + "s%" + amountWidth + "s", "Activities", "Rate", "Qty", "Amount");
        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
        expected.writeBytes((columnHeads + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));

        if(activities == null) // Watersports
        {
            for (OrderDetailsWaterSport orderDet:listOrderDet)
            {
                String row = generateReceiptRow(Helper.arrayActivity[orderDet.getIdActivity()-1], orderDet.getRate(), orderDet.getPersons(), (orderDet.getRate() * orderDet.getPersons()));
                expected.writeBytes((row).getBytes());
            }
        }
        else // Parasailing
        {
            String row = generateReceiptRow(activities, rate, nPerson, (rate * nPerson));
            expected.writeBytes((row).getBytes());
        }

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
        expected.writeBytes(("-".repeat(maxChars) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
        double grandTotal = 0;

        if(activities == null) // Water Sports
        {
            grandTotal = calculateTotal(listOrderDet);
        }
        else // Parasailing
        {
            grandTotal = rate * nPerson;
        }

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
        expected.writeBytes(("Grand Total Rs " + (int)grandTotal + " only" + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
        expected.writeBytes((footer + "\n\n").getBytes());

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

        System.out.println(expected.toString().replace("\u0001", "").replace("\u001B","").replace("!", ""));

        // Preview output on Console
//        System.out.println("------------------");
//        System.out.println(expected);

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
//        return 1;
    }

    public static String generateReceiptRow(String particulars, double rate, int quantity, double amount) {
        String[] wrappedParticulars = wrapWords(particulars, particularsWidth);

        StringBuilder formattedParticulars = new StringBuilder();

        for (int i = 0; i < wrappedParticulars.length; i++) {
            formattedParticulars.append(String.format("%-" + particularsWidth + "s", wrappedParticulars[i]));

            // Add quantity and amount on the last line of particulars
            if (i == wrappedParticulars.length - 1) {
                formattedParticulars.append(String.format("%" + (rateWidth + quantity + amountWidth) + "s",
                        String.format("%" + rateWidth + "s", (int)rate) +
                                String.format("%" + quantityWidth + "s", quantity) +
                                String.format("%" + amountWidth + "s", (int)amount)
                ));
            }

            formattedParticulars.append("\n");
        }

        return formattedParticulars.toString();
    }

    public static String[] wrapWords(String text, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : text.split("\\s+")) {
            if (currentLine.length() + word.length() <= width) {
                if (currentLine.length() > 0) {
                    currentLine.append(" "); // Add space between words
                }
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines.toArray(new String[0]);
    }

    public static String wrapTextOld(String text, int lineLength) {
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

    public static double calculateTotal(List<OrderDetailsWaterSport> listOrderDet)
    {
        double sum = 0;
        for (OrderDetailsWaterSport orderDet:listOrderDet)
        {
            sum = sum + (orderDet.getRate() * orderDet.getPersons());
        }
        return sum;
    }
}
