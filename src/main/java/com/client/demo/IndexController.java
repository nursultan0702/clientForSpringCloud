package com.client.demo;

import ch.qos.logback.core.net.server.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping("/")
    public ModelAndView Index(){
        Map<String, String> model = new HashMap<>();
        model.put("visited",conector("create"));
        return new ModelAndView("Index",model);
    }
    @RequestMapping("/getvisites")
    public ModelAndView getVisites(){
        String result = "nodata";
        Map<String,String> model = new HashMap<>();
        result = conector("getvisits");
        model.put("visites",result);
        return new ModelAndView("visites",model);
    }
    public static String conector(String strUrl){
        String value = "nodata";
        try {

            URL url = new URL("http://localhost:8761/"+strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                value=output;
                System.out.println(output);
            }


            conn.disconnect();
            return value;

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return value;
    }
}
