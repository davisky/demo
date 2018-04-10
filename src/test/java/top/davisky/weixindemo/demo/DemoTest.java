package top.davisky.weixindemo.demo;

import org.junit.Test;
import top.davisky.weixindemo.demo.util.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DemoTest {

    @Test
    public void login() throws IOException {
        String url = "http://cms.changan.com.cn/caecmg/server/businesslogin";
        Map<String,String> param = new HashMap<>();
        param.put("appid","7");
        param.put("verify","0cx2VZo8DzjLnGp9cSeBXzAQFOLRHfil8/G0KmkWbKFy4VcFLQVyN0hj89WMh7DxWF09GBpcvqQcT04+gEr1mopgB0GchYHtaAHvDh2fJhjcpBZKhkz398fBEN6cgEoaoMTQ7Nxx0Mul87fB55AipAZuxEuhFymQ5m9k/f1E8xU=");
        String result =OkHttpUtils.postForm(url,param);
        System.out.println(result);
    }


    @Test
    public void templatelist() throws IOException {
        String url = "http://cms.changan.com.cn/caecmg/server/weixin/weixintemplatemsg/getBusinessTemplateMsg";
        Map<String,String> param = new HashMap<>();
        param.put("token","a0d909d0-cbd8-4ac1-ade0-b765760981b5");
        String result =OkHttpUtils.postForm(url,param);
        System.out.println(result);
    }

    @Test
    public void templatesendmsg() throws IOException {
        String url = "http://cms.changan.com.cn/caecmg/server/weixin/weixintemplatemsg/sendtemplatemsg";
        Map<String,String> param = new HashMap<>();
        param.put("token","a0d909d0-cbd8-4ac1-ade0-b765760981b5");
        param.put("templateId","bGEofRBiaoqK8J0Xc8MQ9sABsjYblnJlJax5gG6wq4s");
        param.put("openid","oDz9f0SZHfxoCw-a9gxLrS6ets3A");
        param.put("templateMessageJson","[{\"name\":\"first\",\"value\":\"测试\"},{\"name\":\"account\",\"value\":\"aa\"},{\"name\":\"time\",\"value\":\"8:50\"},{\"name\":\"city\",\"value\":\"重庆\"},{\"name\":\"remark\",\"value\":\"\"}]");
        String result =OkHttpUtils.postForm(url,param);
        System.out.println(result);
    }

    @Test
    public void getByMsgId() throws IOException {
        String url = "http://cms.changan.com.cn/caecmg/server/weixin/weixintemplatemsg/getByMsgId";
        Map<String,String> param = new HashMap<>();
        param.put("token","a0d909d0-cbd8-4ac1-ade0-b765760981b5");
        param.put("msgId","199008094112464897");
        String result =OkHttpUtils.postForm(url,param);
        System.out.println(result);
    }
}
