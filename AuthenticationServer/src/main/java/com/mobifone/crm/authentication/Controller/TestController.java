package com.mobifone.crm.authentication.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping(value = "/home")
public class TestController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    @PreAuthorize("{@appAuthorizer.authorize(authentication,'API_GET_TEST1')}")
    public ResponseEntity<?> getLogger() {
        Map s= new HashMap();
        s.put("name","Đạt");
        s.put("old","25");
        return new ResponseEntity<Map>(s, HttpStatus.OK);
    }


    @RequestMapping(value = {"/test2"}, method = RequestMethod.GET)
    @PreAuthorize("{@appAuthorizer.authorize(authentication,'API_GET_TEST2')}")
    public ResponseEntity<?> getTest2() {
        Map s= new HashMap();
        s.put("name","Quỳnh");
        s.put("old","25");
        return new ResponseEntity<Map>(s, HttpStatus.OK);
    }




    public static void main(String []args){
        String x="{";
//{/home/test2,/home/test}
        for(int i=0;i<10000;i++){

            x+="/home/test"+String.valueOf(i)+",";
            /*x+= " @RequestMapping(value = {\"/test"+String.valueOf(i)+"\"}, method = RequestMethod.GET)\n" +
                    "    public ResponseEntity<?> getTest"+String.valueOf(i)+"() {\n" +
                    "        Map s= new HashMap();\n" +
                    "        s.put(\"name\",\"Quỳnh\");\n" +
                    "        s.put(\"old\",\"25\");\n" +
                    "        s.put(\"api_num\",\""+String.valueOf(i)+"\");\n" +
                    "        return new ResponseEntity<Map>(s, HttpStatus.OK);\n" +
                    "    } \n\n";*/

            }
        System.out.println(x+"}");
    }



}
