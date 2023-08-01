package com.abc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import com.abc.pay.client.CertHelper;
import com.abc.pay.client.MerchantPara;
import com.abc.pay.client.MerchantParaFactory;
import com.abc.pay.client.MerchantParaWeb;
import com.abc.pay.client.TrxException;

public class MerchantParaFromDB extends MerchantParaFactory {

    private static MerchantPara paraWeb = null;

    private static boolean iIsInitialedWeb = false;

    public void refreshConfig() throws TrxException {
        iIsInitialedWeb = false;
    }

    public void init(MerchantPara para) {

        try {

            /**
             * ע�⣺
             * ����������Ҫ�̻����и����̻�������������
             * */

            /*����֧��ƽ̨֤��*/
            para.setTrustPayCertFileName("D:/Code/EBUS/EBusClient/out/artifacts/WebRoot/cert/prod/TrustPay.cer");
            /*ũ�и�֤���ļ�*/
            para.setTrustStoreFileName("D:/Code/EBUS/EBusClient/out/artifacts/WebRoot/cert/prod/abc.truststore");

            /*�̻��б�*/
            ArrayList<String> merchantIDList = new ArrayList<String>();
            merchantIDList.add("103881909990705");
            para.setMerchantIDList(merchantIDList);

            /*�̻�˽Կ֤���б�*/
            ArrayList<byte[]> iMerchantCertList = new ArrayList<byte[]>();
            iMerchantCertList.add(readFile("D:/Code/EBUS/EBusClientLoadFromDBDemo/src/main/resources/cert/prod/103881909990705.pfx"));
            para.setMerchantCertFileList(iMerchantCertList);

            /*�̻�˽Կ֤���Ӧ�����б�*/
            ArrayList<String> iMerchantPasswordList = new ArrayList<String>();
            iMerchantPasswordList.add("Ssliang870915");
            para.setMerchantCertPasswordList(iMerchantPasswordList);

            /*�ӿڰ���־·��*/
            para.setLogPath("D:/Code/EBUS/EBusClient/out/artifacts/WebRoot/log");

            /*����ʱʱ��*/
            para.setTrustPayServerTimeout("");

            /**
             * ע�⣺
             * ���������벻Ҫ�ı�
             * */

            para.setTrustPayConnectMethod("https");

            para.setTrustPayServerName("pay.abchina.com");

            para.setTrustPayServerPort("443");

            para.setTrustPayTrxURL("/ebus/ReceiveMerchantTrxReqServlet");

            para.setTrustPayTrxIEURL("https://pay.abchina.com/ebus/ReceiveMerchantIERequestServlet");

            para.setMerchantErrorURL("http://127.0.0.1:8080/Merchant.html");

            /*ũ�и�֤���ļ�����*/
            para.setTrustStorePassword("changeit");

            para.setProxyIP("");

            para.setProxyPort("");

            para.setMerchantKeyStoreType(MerchantPara.KEY_STORE_TYPE_FILE);

            CertHelper.bindMerchantCertificate(para, iMerchantCertList, iMerchantPasswordList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("[Trustpay�̻���API] - ��ʼ - �̻����ò�����ʼ�����====================");
    }

    public MerchantPara getMerchantPara() throws TrxException {
        if (!iIsInitialedWeb) {
            try {
                paraWeb = MerchantParaWeb.getUniqueInstance();
            } catch (TrxException e) {
                e.printStackTrace();
            }
            init(paraWeb);
            iIsInitialedWeb = true;
        }
        return paraWeb;
    }

    public byte[] readFile(String filePath) {
        File file = new File(filePath);
        byte fileContent[] = null;
        try {
            FileInputStream fin = new FileInputStream(file);
            fileContent = new byte[(int) file.length()];
            fin.read(fileContent);

        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }
        return fileContent;
    }

    public byte[] readPrivateKeyFromDatabase() {
        Connection con;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/ebusclient";
        String user = "root";
        String password = "113413";
        byte[] privateKeyDB = new byte[0];
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            Statement statement = con.createStatement();
            String getSql = "select * from T_EBusCertificate where MerchantNo = '103881909990705'";
            ResultSet rs = statement.executeQuery(getSql);
            while (rs.next()) {
                privateKeyDB = rs.getBytes("PrivateKey");
            }
            rs.close();
            con.close();
            return privateKeyDB;
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
            return privateKeyDB;
        } catch (SQLException e) {
            e.printStackTrace();
            return privateKeyDB;
        } catch (Exception e) {
            e.printStackTrace();
            return privateKeyDB;
        }
    }
}