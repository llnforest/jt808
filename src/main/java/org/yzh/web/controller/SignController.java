package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yzh.web.Application;
import org.yzh.web.sign.ISign;
import org.yzh.web.sign.IVerify;
import org.yzh.web.sign.Sign;
import org.yzh.web.sign.Verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

@Api(description = "签名")
@Controller
@RequestMapping
public class SignController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final String password = "1";
    @ApiOperation(value = "签名")
    @RequestMapping(value = "api/sign", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String sign(@RequestParam(value = "data") String data,@RequestParam(value = "timestamp") long timestamp,@RequestParam(value = "version",defaultValue = "e2") String version) throws Exception{

        String password = "1";
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String path = SignController.class.getClassLoader().getResource("cert/dev1.pfx").getPath();
        System.out.println(path);
        try (InputStream input = new FileInputStream(path)) {
            keyStore.load(input, password.toCharArray());
        }
        Enumeration<String> aliases = keyStore.aliases();
        if (!aliases.hasMoreElements()) {
            throw new RuntimeException("no alias found");
        }
        String alias = aliases.nextElement();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());

        ISign sign = new Sign();
        String sign_hex = sign.sign(data, timestamp, privateKey,version);

        return "{\"status\":200,\"data\":\""+sign_hex+"\"}";
    }

    @ApiOperation(value = "签名文件")
    @RequestMapping(value = "api/signFile", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String signFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "timestamp") long timestamp, @RequestParam(value = "version",defaultValue = "e2") String version) throws Exception{
        String password = "1";
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int ch = inputStream.read();
        while (ch != -1){
            output.write(ch);
            ch=inputStream.read();
        }
        byte[] bytes = output.toByteArray();


        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String path = SignController.class.getClassLoader().getResource("cert/dev1.pfx").getPath();
        try (InputStream input = new FileInputStream(path)) {
            keyStore.load(input, password.toCharArray());
        }
        Enumeration<String> aliases = keyStore.aliases();
        if (!aliases.hasMoreElements()) {
            throw new RuntimeException("no alias found");
        }
        String alias = aliases.nextElement();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());

        ISign sign = new Sign();
        String sign_hex = sign.sign(bytes, timestamp, privateKey,version);
        return "{\"status\":200,\"data\":\""+sign_hex+"\"}";
    }

    @ApiOperation(value = "验签")
    @RequestMapping(value = "api/verify", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String verify(@RequestParam(value = "data") String data,@RequestParam(value = "sign") String sign,@RequestParam(value = "timestamp") long timestamp,@RequestParam(value = "version",defaultValue = "e2") String version) throws Exception{
        String password = "1";
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String path = SignController.class.getClassLoader().getResource("cert/dev1.pfx").getPath();
        try (InputStream input = new FileInputStream(path)) {
            keyStore.load(input, password.toCharArray());
        }
        Enumeration<String> aliases = keyStore.aliases();
        if (!aliases.hasMoreElements()) {
            throw new RuntimeException("no alias found");
        }
        String alias = aliases.nextElement();
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
        IVerify verify = new Verify();
        boolean ok = verify.verify(data, timestamp, sign, cert,version);

        return "{\"status\":200,\"data\":"+ok+"}";
    }

    @ApiOperation(value = "验签文件")
    @RequestMapping(value = "api/verifyFile", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String verifyFile(HttpServletRequest request, @RequestParam(value = "sign") String sign,@RequestParam(value = "timestamp") long timestamp,@RequestParam(value = "version",defaultValue = "e2") String version) throws Exception{
        String password = "1";
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int ch = inputStream.read();
        while (ch != -1){
            output.write(ch);
            ch=inputStream.read();
        }
        byte[] bytes = output.toByteArray();

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String path = SignController.class.getClassLoader().getResource("cert/dev1.pfx").getPath();
        try (InputStream input = new FileInputStream(path)) {
            keyStore.load(input, password.toCharArray());
        }
        Enumeration<String> aliases = keyStore.aliases();
        if (!aliases.hasMoreElements()) {
            throw new RuntimeException("no alias found");
        }
        String alias = aliases.nextElement();
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
        IVerify verify = new Verify();
        boolean ok = verify.verify(bytes, timestamp, sign, cert,version);

        return "{\"status\":200,\"data\":"+ok+"}";
    }
}