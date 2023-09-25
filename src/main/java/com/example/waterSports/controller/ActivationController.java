package com.example.waterSports.controller;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/activation/")
public class ActivationController
{
    Preferences pref = Preferences.userRoot().node(this.getClass().getName());

    public static String softwareName = "watersports_app";
    String bios, cpu, manuf, model, str, secKey = "Om Namo LakshmiNarayana";
    Process processBios = null, processCpu = null, processManuf = null, processModel = null;
    Scanner sc = null;
    String strOriginal, actCode, strEnc, strDec, serialKey, strExpDate, strActCode;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Cipher cipher = null;
    SecretKeySpec secretKey = null;
    byte[] key = null;
    Date expDate = null;
    LocalDate expLocalDate = null;
    long diff = 0;
    Calendar calendar = null;

    @GetMapping("/validate/")
    public ModelAndView validateProduct(RedirectAttributes redirectAttributes)
    {
        String resultPage = "productActivation";

        String serialKey = pref.get("serial_key", null);

        if(serialKey == null)
        {
            redirectAttributes.addFlashAttribute("msg", "Please activate the product");
        }
        else
        {
            resultPage = "redirect:/water/";

            strOriginal = decrypt(serialKey, secKey);

            strExpDate = strOriginal.substring(strOriginal.length()-10);

            diff = getDiff(strExpDate);

            if(diff < 1)
            {
                resultPage = "productActivation";
            }
            else
            {
                if(diff <= 30)
                {
                    if(diff == 1)
                    {
                        redirectAttributes.addFlashAttribute("msg", "Your License Key gonna expire on " + strExpDate + ".\nOnly " + diff + " day left. If you wish to continue using this software, please contact developer and purchase new License Key.");
                    }
                    else
                    {
                        redirectAttributes.addFlashAttribute("msg", "Your License Key gonna expire on " + strExpDate + ".\nOnly " + diff + " days left. If you wish to continue using this software, please contact developer and purchase new License Key.");
                    }
                }
            }
        }



        return new ModelAndView(resultPage);
    }

    @PostMapping("/activate/")
    public String activae(Model model, int monthCount, String activation_key, String serial_key, String strBtn)
    {
        String resultPage = "productActivation";

        if(strBtn.equalsIgnoreCase("generate"))
        {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, monthCount);
            strExpDate = sdf.format(calendar.getTime());

            strOriginal = getSystemInfo() + strExpDate;
            serialKey = encrypt(strOriginal, secKey);
            strActCode = encrypt(serialKey, secKey);

            model.addAttribute("activation_key", strActCode);
            model.addAttribute("month_count", monthCount);
        }
        else
        {
            if(serial_key.equals(serialKey))
            {
                pref.put("serial_key", serial_key);
                resultPage = "redirect:/water/";
            }
            else
            {
                int trials = pref.getInt("trials", 3);
                trials--;
                pref.putInt("trials", trials);

                if(trials < 1)
                {
                    resultPage = "redirect:/logout";
                    pref.remove("trials");
                }
                else
                {
                    if(trials == 1)
                    {
                        model.addAttribute("msg", "Invalid serial key. Only " + trials + " chance left");
                    }
                    else
                    {
                        model.addAttribute("msg", "Invalid serial key. Only " + trials + " chances left");
                    }

                    model.addAttribute("month_count", monthCount);
                    model.addAttribute("activation_key", activation_key);
                }

            }
        }

        return resultPage;
    }

    public String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String getSystemInfo()
    {
        try
        {
            processBios = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "SerialNumber" });
            processCpu = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
            processManuf = Runtime.getRuntime().exec(new String[] { "wmic", "computersystem", "get", "manufacturer" });
            processModel = Runtime.getRuntime().exec(new String[] { "wmic", "computersystem", "get", "model" });

            processBios.getOutputStream().close();
            processCpu.getOutputStream().close();
            processManuf.getOutputStream().close();
            processModel.getOutputStream().close();

            sc = new Scanner(processBios.getInputStream());
            sc.next();
            bios=sc.next();

            sc = new Scanner(processCpu.getInputStream());
            sc.next();
            cpu=sc.next();

            sc = new Scanner(processManuf.getInputStream());
            sc.next();
            manuf=sc.next();

            sc = new Scanner(processModel.getInputStream());
            sc.next();
            model=sc.next();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return bios+cpu+manuf+model;
    }

    public long getDiff(String str)
    {
        try
        {
            expDate = sdf.parse(str);
            expLocalDate = expDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return ChronoUnit.DAYS.between(LocalDate.now(), expLocalDate);
    }

}
