package com.example.waterSports.utils;

import com.example.waterSports.modal.OrderDetailsWaterSport;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Helper
{
//    public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static String[] arrayActivity = {"All Rides", "Jet Ski Ride", "Banana Ride", "Seating Bumper", "Sleeping Bumper"};
    public static String[] arrayMonth = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    public static int particularsWidth = 25;
    public static int quantityWidth = 7;
    public static int maxChars = 32;

    public static int PrintBill(String title, String header, String footer, String address, String contact, String printerName, Long billNo, String referee, String owner, String customerName, String activities, Integer nPerson, Double rate, String date, List<OrderDetailsWaterSport> listOrderDet, Integer receiptWidth)
    {
        if(receiptWidth == 58)
        {
            particularsWidth = 25;
            quantityWidth = 7;
            maxChars = 32;
        }
        else if(receiptWidth == 80)
        {
            particularsWidth = 41;
            quantityWidth = 7;
            maxChars = 48;
        }

        System.out.println("maxChars = " + maxChars);

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
        for (PrintService printService:printServices) {
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

        if(activities != null) // Parasailing
        {
            expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
            expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight4());
            expected.writeBytes(("|".repeat(maxChars) + "\n").getBytes());
        }

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
        expected.writeBytes((header.replaceAll("/","\n") + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight2());
        expected.writeBytes((title + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes((wrapTextOld(address, maxChars).toString() + "\n").getBytes());

//        expected.writeBytes(("Phone:" + contact + "\n").getBytes());


        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes(("-".repeat(maxChars) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
        expected.writeBytes(POS.POSPrinter.CharSize.DoubleHeight2());
        expected.writeBytes((customerName + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.CharSize.Normal());
        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Left));
        int middleSpace = maxChars - ("BillNo:" + billNo).length() - ("Date:" + date).length();
        String strBillNoDate = String.format("%s%" + middleSpace + "s%s", "BillNo:" + billNo, "", "Date:" + date + "\n");
        expected.writeBytes((strBillNoDate).getBytes());

        middleSpace = maxChars - ("Own:" + owner).length() - ("Ref:" + referee).length();
        String strOwnRef = String.format("%s%" + middleSpace + "s%s", "Own:" + owner, "", "Ref:" + referee + "\n");
        expected.writeBytes((strOwnRef).getBytes());

        expected.writeBytes(("-".repeat(maxChars) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.Bold));
        String columnHeads = String.format("%-" + particularsWidth + "s%" + quantityWidth + "s", "Activities", "Qty");
        expected.writeBytes((columnHeads + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));

        if(activities == null) // Watersports
        {
            for (OrderDetailsWaterSport orderDet:listOrderDet)
            {
                String activity = Helper.arrayActivity[orderDet.getIdActivity()-1];

                String row = generateReceiptRow(orderDet.isBigRound() ? activity + "(Big Round)":activity, orderDet.getPersons());
                expected.writeBytes((row).getBytes());
            }
        }
        else // Parasailing
        {
            String row = generateReceiptRow(activities, nPerson);
            expected.writeBytes((row).getBytes());
        }

        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
        expected.writeBytes(("-".repeat(maxChars) + "\n").getBytes());

        expected.writeBytes(POS.POSPrinter.Justification(POS.Justifications.Center));
        expected.writeBytes(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
        expected.writeBytes((footer + "\n\n").getBytes());

        if(maxChars == 32)
        {
            expected.writeBytes(("\n\n").getBytes());
        }

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

    public static String generateReceiptRow(String particulars, int quantity) {
        String[] wrappedParticulars = wrapWords(particulars, particularsWidth);

        StringBuilder formattedParticulars = new StringBuilder();

        for (int i = 0; i < wrappedParticulars.length; i++) {
            formattedParticulars.append(String.format("%-" + particularsWidth + "s", wrappedParticulars[i]));

            // Add quantity on the last line of particulars
            if (i == wrappedParticulars.length - 1) {
                formattedParticulars.append(String.format("%" + (quantity) + "s",
                                String.format("%" + quantityWidth + "s", quantity)
                ));
            }

            formattedParticulars.append("\n");
        }

        return formattedParticulars.toString();
    }

    public static String[] wrapWords(String text, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word:text.split("\\s+")) {
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
        for (String word:words) {
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
