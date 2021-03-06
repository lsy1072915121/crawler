package com.liushiyao.reptile.analoglogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class BaiDu {
    public static void main(String[] args) {
        new BaiDu().vmain();
    }

    HttpClient client;

    //模拟登录参数
    Map<String, String> context = new HashMap<String, String>() {
        public String put(String key, String value) {
            System.out.println(key + ":" + value);
            return super.put(key, value);
        };
    };

    CookieStore cookieStore;
    HttpGet get;
    HttpPost post;

    HttpResponse res;

    public BaiDu() {
        try {
            cookieStore = new BasicCookieStore();
            List<Header> headers = new ArrayList<Header>();
            headers.add(new BasicHeader("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/35.0"));
            client = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setDefaultHeaders(headers).build();
            get = new HttpGet();
            res = null;
            get.setURI(new URI("https://www.baidu.com/"));
            client.execute(get);
            getToken();
            context.put("username", "1072915121@qq.com");
            context.put("pass", "dirk454587963");
            Encrypt();
            login();
        } catch (Exception e) {
            System.out.println("init fail!");
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    public void Encrypt() throws Exception {
        get = new HttpGet();
        get.setURI(new URI("https://passport.baidu.com/v2/getpublickey?token="
                + context.get("token") + "&tpl=mn&apiver=v3&tt="
                + System.currentTimeMillis()));
        res = client.execute(get);
        String string = EntityUtils.toString(res.getEntity());
        Matcher matcher = Pattern.compile(
                "-----BEGIN PUBLIC KEY-----(.*)-----END PUBLIC KEY-----")
                .matcher(string);
        if (matcher.find()) {
            String s = matcher.group(1);
            context.put("pass", RSAUtil.encrypt(
                    s.replace("\\n", "").replace("\\/", "/"),
                    context.get("pass")));
        }
        Matcher matcher2 = Pattern.compile("\"key\":'(.+)'}$").matcher(string);
        if (matcher2.find()) {
            context.put("rsakey", matcher2.group(1));
        }
    }

    private void errorhandle() throws Exception {
        if (context.get("error") == null) {
            System.out.println("登陆成功");
            return;
        }
        switch (Integer.valueOf(context.get("error"))) {
        case 257:
            get.setURI(new URI("https://passport.baidu.com/cgi-bin/genimage?"
                    + context.get("codeString")));
            res = client.execute(get);
            File file = new File("/test/" + context.get("username") + ".gif");
            FileUtils.touch(file);
            OutputStream os = new FileOutputStream(file);
            res.getEntity().writeTo(os);
            os.close();
            volidate();
            break;
        case 0:
            System.out.println("登陆成功");
            break;
        default:
            break;
        }
    }

    public String getToken() throws Exception {
        get.setURI(new URI(
                "https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true"));
        res = client.execute(get);
        BufferedReader br = new BufferedReader(new InputStreamReader(res
                .getEntity().getContent()));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.contains("login_token")) {
                String token = line.substring(line.indexOf("'") + 1,
                        line.lastIndexOf("'"));
                context.put("token", token);
                return context.get("token");
            }
        }
        return null;
    }

    public void login() throws Exception {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>() {
            @Override
            public boolean add(BasicNameValuePair e) {
                if (e.getValue() == null) {
                    e = new BasicNameValuePair(e.getName(), "");
                }
                return super.add(e);
            }
        };
        params.add(new BasicNameValuePair("staticpage",
                "http://www.baidu.com/cache/user/html/jump.html"));
        params.add(new BasicNameValuePair("charset", "utf-8"));
        params.add(new BasicNameValuePair("apiver", "v3"));
        params.add(new BasicNameValuePair("token", context.get("token")));
        params.add(new BasicNameValuePair("tpl", "mn"));
        params.add(new BasicNameValuePair("tt", System.currentTimeMillis() + ""));
        params.add(new BasicNameValuePair("safeflg", String.valueOf(0)));
        params.add(new BasicNameValuePair("u", "http://www.baidu.com/"));
        params.add(new BasicNameValuePair("detect", "1"));
        params.add(new BasicNameValuePair("gid", ""));
        params.add(new BasicNameValuePair("quick_user", "0"));
        params.add(new BasicNameValuePair("logintype", "basicLogin"));
        params.add(new BasicNameValuePair("logLoginType", "pc_loginBasic"));
        params.add(new BasicNameValuePair("idc", ""));
        params.add(new BasicNameValuePair("loginmerge", "true"));
        params.add(new BasicNameValuePair("rsakey", context.get("rsakey")));
        params.add(new BasicNameValuePair("username", context.get("username")));
        params.add(new BasicNameValuePair("password", context.get("pass")));
        params.add(new BasicNameValuePair("isPhone", "false"));
        params.add(new BasicNameValuePair("loginType", "1"));
        params.add(new BasicNameValuePair("index", "0"));
        params.add(new BasicNameValuePair("verifycode", context
                .get("verifycode")));
        params.add(new BasicNameValuePair("codestring", context
                .get("codestring")));
        params.add(new BasicNameValuePair("mem_pass", "on"));
        params.add(new BasicNameValuePair("callback",
                "parent.bdPass.api.login._postCallback"));
        params.add(new BasicNameValuePair("crypttype", "12"));
        post = new HttpPost();
        post.setURI(new URI("https://passport.baidu.com/v2/api/?login"));
        post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        res = client.execute(post);
        String string = EntityUtils.toString(res.getEntity());
        Matcher matcher = Pattern.compile("err_no=(\\d+)&").matcher(string);
        if (matcher.find()) {
            context.put("error", matcher.group(1));
        } else {
            matcher = Pattern.compile("error=(\\d+)'").matcher(string);
            if (matcher.find()) {
                context.put("error", matcher.group(1));
            }
        }
        Matcher codem = Pattern.compile("code[sS]tring=(\\w+)&")
                .matcher(string);
        if (codem.find()) {
            context.put("codeString", codem.group(1));
        }
        System.out.println(string);
        errorhandle();
    }

    public void vmain() {
    }

    private void volidate() throws Exception {
        System.out.println("请输入验证码:");
        context.put("verifycode", new Scanner(System.in).next());
        get.setURI(new URI("https://passport.baidu.com/v2/?checkvcode&token="
                + context.get("token") + "&tpl=mn&apiver=v3&tt="
                + System.currentTimeMillis() + "&verifycode="
                + context.get("verifycode") + "&codestring="
                + context.get("codeString")));
        res = client.execute(get);
        Matcher m = Pattern.compile("no\": \"(\\d+)\"").matcher(
                EntityUtils.toString(res.getEntity()));
        if (m.find()) {
            switch (Integer.valueOf(m.group(1))) {
            case 500002:
                System.out.println("验证码错误");
                break;
            case 0:
                System.out.println("验证成功");
                login();
                break;
            default:
                break;
            }
        }
    }
}