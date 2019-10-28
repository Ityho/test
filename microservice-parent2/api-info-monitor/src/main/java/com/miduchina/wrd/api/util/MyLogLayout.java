package com.miduchina.wrd.api.util;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.api.scripting.JSObject;
import springfox.documentation.spring.web.json.Json;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shitao on 2019-06-03 17:04.
 *
 * @author shitao
 */
public class MyLogLayout extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent event) {
//        if (event.getMessage().contains("OperateLogObjectDto")) {
////            String message = event.getFormattedMessage();
////            return message;
////        }else {
////            StringBuilder sb = new StringBuilder();
////            sb.append("{");
////            sb.append("\"timestamp\":");
////            sb.append("\"").append(new Timestamp(event.getTimeStamp())).append("\"");
////            sb.append(", \"severity\":");
////            sb.append("\"").append(event.getLevel()).append("\"");
////            sb.append(", \"trace\":");
////            sb.append("\"").append(event.getThreadName()).append("\"");
////            sb.append(", \"CLASS\": ");
////            sb.append("\"").append(event.getLoggerName()).append("\"");
////            sb.append(",\"message\": ");
////            String message = event.getFormattedMessage();
////            sb.append("\"").append(message).append("\"");
////            sb.append("}");
////            sb.append(CoreConstants.LINE_SEPARATOR);
////            return sb.toString();
////        }

        String message = event.getFormattedMessage();
        HashMap<String, Object> hashMap=JSON.parseObject(message, HashMap.class);
        hashMap.put("timestamp",new Timestamp(event.getTimeStamp()));
        hashMap.put("severity",event.getLevel());
        hashMap.put("trace",event.getThreadName());
        hashMap.put("class",event.getLoggerName());
        if (event.getThrowableProxy()!=null){
            ExtendedThrowableProxyConverter throwableConverter = new ExtendedThrowableProxyConverter();
            throwableConverter.start();
            message = event.getFormattedMessage() + "\n" + throwableConverter.convert(event);
            throwableConverter.stop();
        }

        HashMap<String, Object> resultMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            char[] chars = key.toCharArray();
            if (chars[0] >= 'a' && chars[0] <= 'z') {
                chars[0] = (char)(chars[0] - 32);
            }
            key = new String(chars);
            resultMap.put(key,entry.getValue());
        }
        String jsonString = JSONObject.toJSONString(resultMap);
        StringBuilder sb = new StringBuilder();
        sb.append(jsonString);
        sb.append(CoreConstants.LINE_SEPARATOR);
        return sb.toString();

    }
}
