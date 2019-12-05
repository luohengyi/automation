package sample.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import sample.bean.Firewall;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HillStoneRestfulClient {

    /**
     * 防火墙接口url前缀	http://ip:port
     */
    private String urlPrefix;

    /**
     * 登录地址
     */
    private static final String LoginUrl = "rest/doc/login";

    /**
     * 检测登录状态地址
     */
    private static final String CheckLoginUrl = "rest/doc/devicetime";
    private static Firewall firewall=null;

    private CloseableHttpClient httpClient;

    private String userName;

    private String password;

    private String token = "WmBev9M3bO5feg13fmTNkJDKJjeL14sM5S5P660";

    private String vsysId;

    private String role;

    private String fromrootvsys;



    public HillStoneRestfulClient(Firewall firewall) {
        this(firewall.getIp(), firewall.getPort(), firewall.getUsername(), firewall.getPassword(), true);
        HillStoneRestfulClient.firewall =firewall;
    }

    public HillStoneRestfulClient(String ip, int port, String userName, String password, boolean ssl) {
        if (ssl) {
//			this.urlPrefix = "https://" + ip + ":" + port + "/";
            this.urlPrefix = "https://" + ip + "/";
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{tm}, null);
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                this.httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//			this.urlPrefix = "http://" + ip + ":" + port + "/";
            this.urlPrefix = "http://" + ip + "/";
            httpClient = HttpClients.custom().build();
        }
        this.userName = userName;
        this.password = Base64.encodeBase64String(password.getBytes());
    }

    public List<Map<String, Object>> getAddrBook(String addrBookName) throws Exception {
        String queryUrl = this.urlPrefix + "rest/doc/addrbook?query=" + URLEncoder.encode("{\"conditions\":[{\"field\":\"name\",\"operator\":0,\"value\":\"" + addrBookName + "\"}]}", "utf-8");
        //需要第二次访问
        //this.get(queryUrl).get("result");

        List<Map<String, Object>> addrBookData = (List<Map<String, Object>>) this.get(queryUrl).get("result");
        return addrBookData;
    }

    public boolean addAddrBook(String addrBookName) {
        String updateUrl = "rest/addrbook_address?isTransaction=1";
        // [{"is_ipv6":"0","type":"0","name":"test_1","description":"test"}]
        List<Map<String, Object>> addrBookData = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("is_ipv6", 0);
        data.put("type", 0);
        data.put("name", addrBookName);
        data.put("description", addrBookName);
        addrBookData.add(data);
        boolean res = true;
        try {
            Map<String, Object> post = this.post(updateUrl, new ObjectMapper().writeValueAsString(addrBookData));
            System.out.println(post);
            res = (boolean) post.get("success");
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    public void updateAddrBook(String addrBookName, String ipAddr, boolean isAdd) throws Exception {
        String queryUrl = this.urlPrefix + "rest/doc/addrbook?query=" + URLEncoder.encode("{\"conditions\":[{\"field\":\"name\",\"operator\":0,\"value\":\"" + addrBookName + "\"}]}", "utf-8");

        List<Map<String, Object>> addrBookData = (List<Map<String, Object>>) this.get(queryUrl).get("result");
        if (addrBookData.size() == 0)
            throw new RuntimeException("没有找到名称为" + addrBookName + "的地址簿");
        Map<String, Object> addrBookMap = addrBookData.get(0);
        List<Map<String, Object>> ipAddrList = (List<Map<String, Object>>) addrBookMap.get("ip");
        if (isAdd) {
            Map<String, Object> ipAddrToAdd = new HashMap<String, Object>();
            ipAddrToAdd.put("ip_addr", ipAddr);
            ipAddrToAdd.put("netmask", "32");
            ipAddrToAdd.put("flag", "0");
            ipAddrList.add(ipAddrToAdd);
        } else {
            for (Iterator<Map<String, Object>> iterator = ipAddrList.iterator(); iterator.hasNext(); ) {
                Map<String, Object> ipAddrToDel = iterator.next();
                if (ipAddr.equals(ipAddrToDel.get("ip_addr"))) {
                    iterator.remove();
                    break;
                }
            }
        }
        String updateUrl = "rest/doc/addrbook";
        this.put(updateUrl, new ObjectMapper().writeValueAsString(addrBookData));
    }

    public Map<String, Object> get(String url) throws Exception {
        if (url != null && !url.startsWith("http"))
            url = urlPrefix + url;
        return this.execute(new HttpGet(url));
    }

    public Map<String, Object> post(String url, String param) throws Exception {
        if (url != null && !url.startsWith("http"))
            url = urlPrefix + url;
        HttpPost postRequest = new HttpPost(url);
        if (param != null && !param.trim().equals(""))
            postRequest.setEntity(new StringEntity(param, "utf-8"));
        return this.execute(postRequest);
    }

    public Map<String, Object> put(String url, String param) throws Exception {
        if (url != null && !url.startsWith("http"))
            url = urlPrefix + url;
        HttpPut putRequest = new HttpPut(url);
        if (param != null && !param.trim().equals(""))
            putRequest.setEntity(new StringEntity(param, "utf-8"));
        return this.execute(putRequest);
    }

    public Map<String, Object> delete(String url, String param) throws Exception {
        if (url != null && !url.startsWith("http"))
            url = urlPrefix + url;
        HttpDeleteWithBody deleteRequest = new HttpDeleteWithBody(url);
        if (param != null && !param.trim().equals(""))
            deleteRequest.setEntity(new StringEntity(param, "utf-8"));
        return this.execute(deleteRequest);
    }

    private Map<String, Object> execute(HttpUriRequest httpUriRequest) {
        return this.execute(httpUriRequest, false);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> execute(HttpUriRequest httpUriRequest, boolean login) {
        Map<String, Object> result = new HashMap<String, Object>();
        CloseableHttpResponse response = null;
        try {
            httpUriRequest.setHeader("Cookie", "username=" + this.userName + ";token=" + this.token + ";vsysId=" + this.vsysId + ";role=" + this.role + ";fromrootvsys=" + this.fromrootvsys);
            System.out.println(httpUriRequest.getURI());
            response = httpClient.execute(httpUriRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                String responseStr = EntityUtils.toString(response.getEntity());
                System.out.println(responseStr);
                result = new ObjectMapper().readValue(responseStr, Map.class);
                if (!(Boolean) result.get("success")) {
                    String message = "未知错误";
                    Map<String, Object> errorMap = (Map<String, Object>) result.get("exception");
                    if (errorMap.get("message") != null && !errorMap.get("message").toString().trim().equals("")) {
                        message = errorMap.get("message").toString();
                    } else {
                        message = (String) errorMap.get("code");
                    }
                    throw new RuntimeException(message);
                }
            } else if (response.getStatusLine().getStatusCode() == 502 && !login) {
                this.login();
                result = this.execute(httpUriRequest, true);
            } else {
                throw new RuntimeException("未知错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String message = "访问防火墙失败，";
            System.out.println(message);
            if (NoHttpResponseException.class.isInstance(e) && !login) {
                try {
                    this.login();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    throw new RuntimeException(e1.getMessage());
                }
                result = this.execute(httpUriRequest, true);
            } else {
                if (HttpHostConnectException.class.isInstance(e)) {
                    message += "网络无法连通";
                } else if (RuntimeException.class.isInstance(e)) {
                    message += e.getMessage();
                } else {
                    message += "未知错误";
                }
                throw new RuntimeException(message);
            }
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public void login() throws Exception {
        String loginUrl = urlPrefix + LoginUrl;
        Map<String, String> param = new HashMap<String, String>();
        param.put("lang", "zh_CN");
        param.put("userName", this.userName);
        param.put("password", this.password);
        System.out.println(loginUrl);
        Map<String, Object> result = this.post(loginUrl, new ObjectMapper().writeValueAsString(param));
        if ((boolean) result.get("success")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("result");
            for (Map<String, Object> map : data) {
                this.token = (String) map.get("token");
                this.vsysId = (String) map.get("vsysId");
                this.role = (String) map.get("role");
                this.fromrootvsys = (String) map.get("fromrootvsys");
            }
        } else {
            Map<String, Object> exceptionData = (Map<String, Object>) result.get("exception");
            String message = (String) exceptionData.get("message");
            if (message == null || message.trim().equals("")) {
                message = (String) exceptionData.get("code");
            }
            throw new RuntimeException("登录失败" + (exceptionData == null ? "" : "，" + unicodeToString(message)));
        }
    }

    class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

        public HttpDeleteWithBody() {
            super();
        }

        public HttpDeleteWithBody(String uri) {
            super();
            setURI(URI.create(uri));
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }


    }

    private static X509TrustManager tm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private String stringToUnicode(String str) {
        char[] utfBytes = str.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * unicode转字符串
     *
     * @param unicode
     * @return
     */
    private String unicodeToString(String unicode) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicode);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicode = unicode.replace(matcher.group(1), ch + "");
        }
        return unicode;
    }
}
