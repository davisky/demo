package top.davisky.weixindemo.demo.util;

import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/12/6.
 */
public class OkHttpUtils {
    private static MediaType JSON_CONTENT_TYPE = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static Log log = LogFactory.getLog(OkHttpUtils.class);
    private static OkHttpClient okHttpClient;

    static {
        okhttp3.OkHttpClient.Builder ClientBuilder = new okhttp3.OkHttpClient.Builder();
        ClientBuilder.readTimeout(30, TimeUnit.SECONDS);//读取超时
        ClientBuilder.connectTimeout(10, TimeUnit.SECONDS);//连接超时
        ClientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时
        okHttpClient = ClientBuilder.build();
    }


    public static String get(String url, Map<String, String> params) throws IOException {
        Request request = new Request.Builder().url(url+"?" + setUrlParams(params)).build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();

    }

    public static String postBody(String url, String postBody, MediaType contentType) throws IOException {
        if (contentType == null) {
            contentType = JSON_CONTENT_TYPE;
        }
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(contentType, postBody))
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }


    public static String postForm(String url, Map<String, String> params) throws IOException {
       /* FormBody.Builder builder = new FormBody.Builder(Charset.forName("UTF-8"));
        if (!CollectionUtils.isEmpty(params)) {
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = params.get(name);
                builder.addEncoded(name, value);
            }

        }
        RequestBody formBody = builder.build();
*/

        RequestBody formBody = RequestBody.create(FORM_CONTENT_TYPE,setUrlParams(params));
        Request request = new Request.Builder().url(url).post(formBody).build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    public static String setUrlParams(Map<String, String> mapParams) {
        String strParams = "";
        if (mapParams != null) {
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next();
                strParams += "&" + key + "=" + mapParams.get(key);
            }
            strParams= strParams.replaceFirst("&", "");
        }
        return strParams;
    }

   /* public static void main(String[] args) throws IOException {
        String url ="https://api.mch.weixin.qq.com/pay/unifiedorder";
        String body = "<xml>\n" +
                "   <appid>wx2421b1c4370ec43b</appid>\n" +
                "   <attach>支付测试</attach>\n" +
                "   <body>JSAPI支付测试</body>\n" +
                "   <mch_id>10000100</mch_id>\n" +
                "   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" } ] }]]></detail>\n" +
                "   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n" +
                "   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n" +
                "   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>\n" +
                "   <out_trade_no>1415659990</out_trade_no>\n" +
                "   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n" +
                "   <total_fee>1</total_fee>\n" +
                "   <trade_type>JSAPI</trade_type>\n" +
                "   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n" +
                "</xml>";
        String result =postBody(url,body,MediaType.parse("application/xml;charset=utf-8"));
        System.out.println(result);
    }*/
}