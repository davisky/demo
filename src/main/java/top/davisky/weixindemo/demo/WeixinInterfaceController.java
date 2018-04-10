package top.davisky.weixindemo.demo;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.davisky.weixindemo.demo.util.OkHttpUtils;

import java.beans.Encoder;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lixiaodong
 * @Title:
 * @Package top.davisky.weixindemo.demo
 * @date 2018/3/11 10:01
 */
@RestController
@RequestMapping("weixin")
public class WeixinInterfaceController {

    private String appid = "wx52ff3b5d4fb148e1";
    private String appsecret="7435bc1b7d0b46630cabd65bce9f9878";
    @RequestMapping("oauth2buildAuthorizationUrl")
    public String oauth2buildAuthorizationUrl(@RequestParam String redirectURI,@RequestParam String scope,@RequestParam String state) throws IOException {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
        Map<String,String> param = new LinkedHashMap<>();
        param.put("appid",appid);
        redirectURI=java.net.URLEncoder.encode(redirectURI,"UTF-8");
        param.put("redirect_uri", redirectURI);
        param.put("response_type","code");
        param.put("scope",scope);
        param.put("state",state+"#wechat_redirect");
        url +=OkHttpUtils.setUrlParams(param);
        return url;
    }

    @RequestMapping("oauth2getAccessToken")
    public String oauth2getAccessToken(@RequestParam String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String,String> param = new LinkedHashMap<>();
        param.put("appid",appid);
        param.put("secret",appsecret);
        param.put("code",code);
        param.put("grant_type","authorization_code");
        return OkHttpUtils.get(url,param);
    }

    @RequestMapping("oauth2getUserinfo")
    public String oauth2getUserinfo(String accessToken,String openId) throws IOException {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,String> param = new LinkedHashMap<>();
        param.put("access_token",accessToken);
        param.put("openid",openId);
        param.put("lang","zh_CN");
        return OkHttpUtils.get(url,param);
    }
}
