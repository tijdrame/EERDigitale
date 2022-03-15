package com.boa.aerd.web.rest;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boa.aerd.domain.Client;
import com.boa.aerd.domain.DtoTest;
import com.boa.aerd.response.GenericResponse;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

@RestController
@RequestMapping("/test")
@ApiIgnore
class TestEndPoint {

    @PostMapping("/clients")
    public ResponseEntity<GenericResponse> createClient(@RequestBody Client client, HttpServletRequest request)
            throws URISyntaxException, IOException {
        System.out.println("dans test end point" + client);
        GenericResponse genericResponse = new GenericResponse();
        URL url = new URL("http://192.168.1.116:8280/client");
        // String encodeData = URLEncoder
        Map<Object, Object> postData = new HashMap<>();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        // conn.setRequestProperty("Content-Length",
        // String.valueOf(request.getInputStream()));
        String jsonString ="";
        try {
            jsonString = new JSONObject().put("id", client.getId().toString())
            //.put("JSON2", "Hello my World!")
                    //.put("JSON3", new JSONObject().put("key1", "value1"))
                    .toString();
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



            DtoTest dtoTest = new DtoTest();
            dtoTest.setId(client.getId());
            postData.put("id", dtoTest.getId().toString());
            String psdat = "{id:"+dtoTest.getId().toString()+"}";
    
            OutputStream os = conn.getOutputStream();
            //InputStream inputStream = request.getInputStream(); 
            byte[] postDataBytes =jsonString.getBytes();
            System.out.println("json="+jsonString+" "+postDataBytes);
            
            try {
                os.write(postDataBytes);
                os.flush();
                System.out.println("conn resp=======|||"+conn.getResponseCode()+" "+conn.getResponseMessage());
                BufferedReader br = null;

                if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //System.out.println(" corps msg===|"+br.readLine());
                    String result = br.readLine();
                    System.out.println("result===="+result);
                    JSONObject obj = new JSONObject(result);
                    //obj.
                    System.out.println("client ="+obj.getJSONObject("clients").getJSONObject("client").getString("nom"));

                    genericResponse.setCode(200);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription("OK!"+obj.getJSONObject("clients").getJSONObject("client").getString("nom"));
                } else {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    genericResponse.setCode(400);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription("KO!");
                }
                conn.disconnect();
            } catch (IOException e) {
                //TODO: handle exception
            } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    		
           
        return ResponseEntity.status(genericResponse.getCode())//(new URI("/api/clients/" /*+ result.getId()*/))
            //.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, resp))
            //.header("Authorization", request.getHeader("Authorization"))
            .body(genericResponse);
    }

}