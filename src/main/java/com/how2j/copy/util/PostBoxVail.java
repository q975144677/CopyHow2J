package com.how2j.copy.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
@Component
public class PostBoxVail {
    @Value("${username}")
    String postUsername;
    @Value("${password}")
    String postPassword;
    @Value("${host}")
    String postHost;
        private static final Logger logger = Logger
                .getLogger(PostBoxVail.class);
        /**
         * @param email 待校验的邮箱地址
         * @return
         *//*
        public static boolean isEmailValid(String email) {
            if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
                logger.error("邮箱（"+email+"）校验未通过，格式不对!");
                return false;
            }
            String host = "";
            String hostName = email.split("@")[1];
            //Record: A generic DNS resource record. The specific record types
            //extend this class. A record contains a name, type, class, ttl, and rdata.
            Record[] result = null;
            SMTPClient client = new SMTPClient();
            try {
                // 查找DNS缓存服务器上为MX类型的缓存域名信息
                Lookup lookup = new Lookup(hostName, Type.MX);
                lookup.run();
                if (lookup.getResult() != Lookup.SUCCESSFUL) {//查找失败
                    logger.error("邮箱（"+email+"）校验未通过，未找到对应的MX记录!");
                    return false;
                } else {//查找成功
                    result = lookup.getAnswers();
                }
                //尝试和SMTP邮箱服务器建立Socket连接
                for (int i = 0; i < result.length; i++) {
                    host = result[i].getAdditionalName().toString();
                    logger.info("SMTPClient try connect to host:"+host);

                    //此connect()方法来自SMTPClient的父类:org.apache.commons.net.SocketClient
                    //继承关系结构：org.apache.commons.net.smtp.SMTPClient-->org.apache.commons.net.smtp.SMTP-->org.apache.commons.net.SocketClient
                    //Opens a Socket connected to a remote host at the current default port and
                    //originating from the current host at a system assigned port. Before returning,
                    //_connectAction_() is called to perform connection initialization actions.
                    //尝试Socket连接到SMTP服务器
                    client.connect(host);
                    //Determine if a reply code is a positive completion response（查看响应码是否正常）.
                    //All codes beginning with a 2 are positive completion responses（所有以2开头的响应码都是正常的响应）.
                    //The SMTP server will send a positive completion response on the final successful completion of a command.
                    if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                        //断开socket连接
                        client.disconnect();
                        continue;
                    } else {
                        logger.info("找到MX记录:"+hostName);
                        logger.info("建立链接成功："+hostName);
                        break;
                    }
                }
                logger.info("SMTPClient ReplyString:"+client.getReplyString());
                String emailSuffix="163.com";
                String emailPrefix="ranguisheng";
                String fromEmail = emailPrefix+"@"+emailSuffix;
                //Login to the SMTP server by sending the HELO command with the given hostname as an argument.
                //Before performing any mail commands, you must first login.
                //尝试和SMTP服务器建立连接,发送一条消息给SMTP服务器
                client.login(emailPrefix);
                logger.info("SMTPClient login:"+emailPrefix+"...");
                logger.info("SMTPClient ReplyString:"+client.getReplyString());

                //Set the sender of a message using the SMTP MAIL command,
                //specifying a reverse relay path.
                //The sender must be set first before any recipients may be specified,
                //otherwise the mail server will reject your commands.
                //设置发送者，在设置接受者之前必须要先设置发送者
                client.setSender(fromEmail);
                logger.info("设置发送者 :"+fromEmail);
                logger.info("SMTPClient ReplyString:"+client.getReplyString());

                //Add a recipient for a message using the SMTP RCPT command,
                //specifying a forward relay path. The sender must be set first before any recipients may be specified,
                //otherwise the mail server will reject your commands.
                //设置接收者,在设置接受者必须先设置发送者，否则SMTP服务器会拒绝你的命令
                client.addRecipient(email);
                logger.info("设置接收者:"+email);
                logger.info("SMTPClient ReplyString:"+client.getReplyString());
                logger.info("SMTPClient ReplyCode："+client.getReplyCode()+"(250表示正常)");
                if (250 == client.getReplyCode()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    client.disconnect();
                } catch (IOException e) {
                }
            }
            return false;
        }
*/
    private static Pattern emailPattern = Pattern
            .compile("^(\\w)+([\\.\\-]?[\\w+]?)*@(\\w)+([\\.-]?\\w+)*((\\.\\w{2,4})+)$");

    /**
     * 验证邮箱是否存在，由于要读取IO，会造成线程阻塞
     *
     * @param email
     *            要验证的邮箱
     * @return 邮箱是否可达
     */
    public static JSONObject valid(String email) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        result.put("code", 400);
        result.put("msg", "邮箱不存在");

        // 发出验证请求的域名(是当前站点的域名，可以任意指定)
        String domain = "webmail120117.21gmail.com";

        if (email == null || email.trim().isEmpty()) {
            result.put("success", false);
            result.put("code", 100);
            result.put("msg", "邮箱不能为空");
            return result;
        }

        if (!emailPattern.matcher(email).matches()) {
            result.put("success", false);
            result.put("code", 100);
            result.put("msg", "邮箱格式不正确");
            return result;
        }
        String host = email.split("@")[1];
        if (domain.equals(host)) {
            result.put("success", false);
            result.put("code", 100);
            result.put("msg", "主机域名不能和发出验证请求的域名[" + domain + "]一样");
            return result;
        }
        Socket socket = new Socket();

        InputStream is = null;
        BufferedInputStream bis = null;
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;

        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bufferedWriter = null;

        try {
            // 查找mx记录，查找DNS缓存服务器上为MX类型的缓存域名信息
            Record[] mxRecords = new Lookup(host, Type.MX).run();
            if (mxRecords == null || mxRecords.length == 0) {
                result.put("success", false);
                result.put("code", 400);
                result.put("msg", "查找不到MX记录");
                return result;
            }

            // 邮件服务器地址
            String mxHost = ((MXRecord) mxRecords[0]).getTarget().toString();

            if (mxRecords.length > 1) { // 优先级排序
                List<Record> arrRecords = new ArrayList<Record>();
                Collections.addAll(arrRecords, mxRecords);
                Collections.sort(arrRecords, new Comparator<Record>() {

                    public int compare(Record record, Record record2) {
                        return new CompareToBuilder().append(
                                ((MXRecord) record).getPriority(),
                                ((MXRecord) record2).getPriority())
                                .toComparison();
                    }

                });
                mxHost = ((MXRecord) arrRecords.get(0)).getTarget().toString();
            }
            // 开始smtp
            socket.connect(new InetSocketAddress(mxHost, 25));

            // 输入流
            is = socket.getInputStream();
            bis = new BufferedInputStream(is);
            isr = new InputStreamReader(bis);

            bufferedReader = new BufferedReader(isr);

            // 输出流
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);

            bufferedWriter = new BufferedWriter(osw);

            // 超时时间(毫秒)
            long timeout = 6000;
            // 睡眠时间片段(毫秒)
            int sleepSect = 50;

            // 相应码
            int responseCode = 0;

            responseCode = getResponseCode(timeout, sleepSect, bufferedReader);

            // 连接(服务器是否就绪)
            if (responseCode != 220) {
                result.put("success", false);
                result.put("code", 400);
                result.put("msg", "连接邮箱服务器失败，服务器未就绪完毕");
                return result;
            }

            // 握手
            bufferedWriter.write("HELO " + domain + "\r\n");
            bufferedWriter.flush();

            responseCode = getResponseCode(timeout, sleepSect, bufferedReader);

            if (responseCode != 250) {
                result.put("success", false);
                result.put("code", 400);
                result.put("msg", "与邮箱服务器握手失败");
                return result;
            }
            // 身份
            bufferedWriter.write("MAIL FROM: <check@" + domain + ">\r\n");
            bufferedWriter.flush();

            responseCode = getResponseCode(timeout, sleepSect, bufferedReader);

            if (responseCode != 250) {
                result.put("success", false);
                result.put("code", 400);
                result.put("msg", "与邮箱服务器握手失败");
                return result;
            }
            // 验证
            bufferedWriter.write("RCPT TO: <" + email + ">\r\n");
            bufferedWriter.flush();

            responseCode = getResponseCode(timeout, sleepSect, bufferedReader);

            if (responseCode != 250) {
                result.put("success", false);
                result.put("code", 400);
                result.put("msg", "邮箱与邮箱服务器不可通信，邮箱不存在");
                return result;
            }
            // 断开
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();

            result.put("success", true);
            result.put("code", 200);
            result.put("msg", "邮箱存在");
        } catch (ConnectException e) {
            result.put("success", false);
            result.put("code", 100);
            result.put("msg", "请求超时，请稍后重新尝试");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("code", 500);
            result.put(
                    "msg",
                    "请求异常，异常信息：" + e.getClass().getName() + "->"
                            + e.getMessage());
        } finally {
            // 关闭资源
            closeAll(result, socket, is, bis, isr, bufferedReader, os, osw,
                    bufferedWriter);
        }

        return result;
    }

    /** 读取响应码 */
    private static int getResponseCode(long timeout, int sleepSect,
                                       BufferedReader bufferedReader) throws InterruptedException,
            NumberFormatException, IOException {
        int code = 0;
        for (long i = sleepSect; i < timeout; i += sleepSect) {
            Thread.sleep(sleepSect);
            if (bufferedReader.ready()) {
                String outline = bufferedReader.readLine();

                while (bufferedReader.ready()) {
                    bufferedReader.readLine();
                }
                //System.out.println(outline);
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        return code;
    }

    /** 关闭资源 */
    private static void closeAll(JSONObject result, Socket socket,
                                 InputStream is, BufferedInputStream bis, InputStreamReader isr,
                                 BufferedReader bufferedReader, OutputStream os,
                                 OutputStreamWriter osw, BufferedWriter bufferedWriter) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (bis != null) {
            try {
                bis.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (isr != null) {
            try {
                isr.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (osw != null) {
            try {
                osw.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                result.put("success", false);
                result.put("code", 500);
                result.put("msg", "请求异常，异常信息：" + e.getClass().getName() + "->"
                        + e.getMessage());
            }
        }

    }
    @Autowired
    SpringTemplateEngine springTemplateEngine ;
public static void main(String[] args){


    System.out.println(valid("w`q"));
}
}
