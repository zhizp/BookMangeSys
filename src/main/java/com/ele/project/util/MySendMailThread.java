package com.ele.project.util;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ele.project.sysmanager.user.pojo.UserDTO;
import com.sun.mail.util.MailSSLSocketFactory;


public class MySendMailThread extends Thread {

    private UserDTO user = null;

    public MySendMailThread(UserDTO user) {
        this.user = user;
    }

    @Override
    public void run() {

        // 跟smtp服务器建立一个连接
        Properties p = new Properties();
        // 设置邮件服务器主机名
        p.setProperty("mail.host", "smtp.163.com");// 指定邮件服务器，默认端口 25
        // 发送服务器需要身份验证
        p.setProperty("mail.smtp.auth", "true");// 要采用指定用户名密码的方式去认证
        // 发送邮件协议名称
        p.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        p.put("mail.smtp.ssl.enable", "true");
        p.put("mail.smtp.ssl.socketFactory", sf);

        // 开启debug调试，以便在控制台查看
        // session.setDebug(true);也可以这样设置
        // p.setProperty("mail.debug", "true");

        // 创建session
        Session session = Session.getDefaultInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名可以用QQ账号也可以用邮箱的别名
                PasswordAuthentication pa = new PasswordAuthentication(
                        "1234567@163.com", "111111");
                // 后面的字符是授权码，用qq密码不行！！
                return pa;
            }
        });

        session.setDebug(true);// 设置打开调试状态

        try {
            // 声明一个Message对象(代表一封邮件),从session中创建
            MimeMessage msg = new MimeMessage(session);
            // 邮件信息封装
            // 1发件人
            msg.setFrom(new InternetAddress("1234567@163.com"));
            // 2收件人
            msg.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
            // 3邮件内容:主题、内容
            msg.setSubject(user.getUsername() + ",Welcome to Register Account,Please click on the link to activate the account");

            // StringBuilder是线程不安全的,但是速度快，这里因为只会有这个线程来访问，所以可以用这个
            StringBuilder sbd = new StringBuilder();
            sbd.append(user.getUsername() + "<br/>Welcome！Please confirm this email address to activate your account.<br/>");
            sbd.append("<font color='red'><a href='http://127.0.0.1:8080/MailAndDownloadFile/ActiveController/toActivation?code="
                    + user.getCode() + "&userid="+user.getId()+"' target='_blank'");
            sbd.append(">Immediate activation</a></font><br/>");
            sbd.append("Or click on the link below:<br/>");
            sbd.append("http://127.0.0.1:8080/MailAndDownloadFile/ActiveController/toActivation?code="
                    + user.getCode() + "&userid="+user.getId()+ "<br/>");
            sbd.append("This is an automatic email.If you do not request but receive this letter，You don't need to do anything.");

            msg.setContent(sbd.toString(), "text/html;charset=utf-8");// 发html格式的文本

            // 发送动作
            Transport.send(msg);

            System.out.println("Send "+user.getEmail()+" an email successfully.");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}