package top.davisky.weixindemo.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * json处理工具类
 * @author zhangle
 */
@Component
public class JsonUtil {

	private static final String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	private static final ObjectMapper mapper;

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		mapper = new ObjectMapper();
		mapper.setDateFormat(dateFormat);
		//允许对象忽略json中不存在的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//允许出现特殊字符和转义符
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
		//允许生成空对象
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		 //允许出现单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true) ;
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	}
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("转换json字符失败!",e);
		}
	}

	
	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("将json字符转换为对象时失败!");
		}
	}
	public static <T> T toObject(String json, TypeReference<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("将json字符转换为对象时失败!");

		}
	}

}
