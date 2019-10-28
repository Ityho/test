package com.miduchina.wrd.dto.hotsportview;

import com.miduchina.wrd.po.hotspot.Message;
import lombok.Data;

import java.util.List;

/**
 *
 * 【消息返回列表】
 *
 * @version 1.0
 * @since 2017年2月28日 下午5:51:32
 * @author virgo
 */
@Data
public class MessageRes {
    private String code;
    private String message;
    private List<Message> messages;

}
