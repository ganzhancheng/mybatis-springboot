package com.winterchen;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String GET = "GET"; // GET
    private static final String POST = "POST";// POST
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;

    /**
     * 初始化http请求参数
     *
     * @param url
     * @param method
     * @param headers
     * @return
     * @throws Exception
     */
    private static HttpURLConnection initHttp(String url, String method,
                                              Map<String, String> headers) throws Exception {
        URL urlStr = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlStr.openConnection();
        // 连接超时
        http.setConnectTimeout(DEF_CONN_TIMEOUT);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(DEF_READ_TIMEOUT);
        http.setUseCaches(false);
        http.setRequestMethod(method);
        http.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        http.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (null != headers && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                http.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        return http;
    }





    /**
     * 功能描述: 构造请求参数
     *
     * @return 返回类型:
     * @throws Exception
     */
    public static String initParams(String url, Map<String, String> params)
            throws Exception {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        }
        sb.append(map2Url(params));
        return sb.toString();
    }

    /**
     * map构造url
     *
     * @return 返回类型:
     * @throws Exception
     */
    public static String map2Url(Map<String, String> paramToMap)
            throws Exception {
        if (null == paramToMap || paramToMap.isEmpty()) {
            return null;
        }
        StringBuffer url = new StringBuffer();
        boolean isfist = true;
        for (Entry<String, String> entry : paramToMap.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                url.append("&");
            }
            url.append(entry.getKey()).append("=");
            String value = entry.getValue();
            if (!StringUtils.isEmpty(value)) {
                url.append(URLEncoder.encode(value, DEFAULT_CHARSET));
            }
        }
        return url.toString();
    }

    /**
     * 检测是否https
     *
     * @param url
     */
    private static boolean isHttps(String url) {
        return url.startsWith("https");
    }

    /**
     * https 域名校验
     *
     * @return
     */
    public class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;// 直接返回true
        }
    }

    public static String getSha1(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 签名算法
     */
    public static String signature(String token, String timestamp, String nonce) {
        String[] tmpArr = {token, timestamp, nonce};
        Arrays.sort(tmpArr);
        String tmpStr = "";
        for (String s : tmpArr) {
            tmpStr += s;
        }
        //System.out.println("排序拼装后的字符串："+tmpStr);
        return sha1(tmpStr);
    }

    /**
     * SHA1加密
     */
    public static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return hexString(md.digest(str.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return str;
        }
    }

    /**
     * byte数组转字符串
     */
    public static String hexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        formatter.close();

        return sb.toString();
    }


    /**
     * 获取当前时间戳
     * @return
     */
    public String getTimeStamp() {
        return (new Date()).getTime() + "";
    }


    public static String postJson(String url,String json,Map<String,String> header){

        String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            if (header != null) {
                Set<Entry<String, String>> entries = header.entrySet();
                for (Entry<String, String> entry : entries) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            httpPost.setEntity(new StringEntity(json));

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");

            log.info(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;

    }

    /**
     * 发送post请求，参数用map接收
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
    public static String post(String url,Map<String,String> map) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Entry<String,String> entry : map.entrySet())
        {
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /*public static void main(String[] args) {
	*//*String signatures = signature("SJZXPRO2017","1539155021188","551204");
	System.out.println(signatures);
	String url = "https://bgy.secure.force.com/services/apexrest/api/";
	String signature = signature("SJZXPRO2017","1462724913","144117");
	Map<String, Map<String,String>> maps = new HashMap<>();
	Map<String,String> header = new HashMap<>();
	header.put("appid", "301");
	header.put("nonce", "144117");
	header.put("sid", "10016050900283305244");
	header.put("signature", signature);
	header.put("timestamp","1462724913");
	maps.put("header", header);
	Map<String,String> cmd = new HashMap<>();
	cmd.put("name", "t_GetRepairReport");
	maps.put("cmd", cmd);
	Map<String,String> data = new HashMap<>();
	data.put("StartTime","2018-10-21 00:00:00");
	data.put("EndTime","2018-10-21 59:59:59");
	data.put("Areaid", "0101");
	//data.put("ComplainType", "投诉工单");
	//data.put("Status", "已处理");
	//data.put("Size", "999999");
	//data.put("Index", "1");
	//data.put("Status", "2");
	//data.put("Projectid", "185439");
	//data.put("ComplainType", "1");
	//data.put("Status", "1");
	maps.put("data", data);
	String mapToJson = mapToJson(maps);
	System.out.println(mapToJson);
	//String str = "{\"data\":{\"EndTime\":\"2018-01-01 59:59:59\",\"StartTime\":\"2018-01-01 00:00:00\",\"Areaid\":\"01BA\"},\"header\":{\"signature\":\"b7841b3e8b0480c815ba7c92c5e6b87118158138\",\"appid\":\"301\",\"nonce\":\"551204\",\"sid\":\"12318101015034100001\",\"timestamp\":\"1539155021188\"},\"cmd\":{\"name\":\"t_GetRepairReport\"}}";
	//System.out.println(mapToJson);
	JSONObject httpsRequest = httpsRequest(url, "POST", mapToJson,1);
	System.out.println(httpsRequest.toJSONString());
	HttpApiClient_cockpitService.
	Demo_cockpitService.getBusinessFeesseriaHttpTest();*//*


    }*/



    public static String  httpGet(String url,Map<String,String> header){
        String responseContent = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/json");
            if (header != null) {
                Set<Entry<String, String>> entries = header.entrySet();
                for (Entry<String, String> entry : entries) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            responseContent = EntityUtils.toString(entity, "UTF-8");
            log.info(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static String getJSESSIONID(String url) {
        CloseableHttpClient httpClient = null;
        HttpGet httpPost = null;
        String result = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpGet(url);
            List<NameValuePair> list = new ArrayList<>();
            if (list.size() > 0) {
               // UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
               // httpPost.setEntity(entity);
            }
            httpClient.execute(httpPost);
            String JSESSIONID = null;
            String cookie_user = null;
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("JSESSIONID")) {
                    JSESSIONID = cookies.get(i).getValue();
                    return JSESSIONID;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args){
        /*String jsessionid = getJSESSIONID("http://211.162.75.141:52467/iVehicleGPS/loginBsSdk.hd?username=ndyanshi&password=123456");
        System.out.println(jsessionid);

        HashMap<String, String> header = new HashMap<>();
        header.put("cookie", "JSESSIONID=" + jsessionid);
        String s = httpGet("http://211.162.75.141:52467/iVehicleGPS/vehicle/findVehiclePage.hd", header);
        System.out.println(s);*/

    }
}


