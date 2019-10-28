package com.miduchina.wrd.eventanalysis.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * 生成微信支付二维码
 *
 * @author liym
 */
public class WeixinQRCodeServlet extends HttpServlet {
	private static final long serialVersionUID = -4929612022034613719L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String innerTradeNo = req.getParameter("innerTradeNo");

			// 打包参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("appid", WeiXinPayConfig.APP_ID);
			params.put("mch_id", WeiXinPayConfig.MCH_ID);
			String time_stamp = String.valueOf(System.currentTimeMillis() / 1000);
			params.put("time_stamp", time_stamp);
			String nonce_str = PayUtil.getRandomStringByLength(32);
			params.put("nonce_str", nonce_str);
			params.put("product_id", innerTradeNo);

			// 生成签名结果
			String sign = PayUtil.generateSign(params, WeiXinPayConfig.KEY);

			String content = WeiXinPayConfig.QR_CODE_URL + "sign=" + sign + "&appid=" + WeiXinPayConfig.APP_ID + "&mch_id=" + WeiXinPayConfig.MCH_ID + "&product_id=" + innerTradeNo + "&time_stamp=" + time_stamp + "&nonce_str=" + nonce_str;

			// 生成二维码
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 285, 285, hints);
			BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);

			resp.setContentType("image/png");
			resp.setHeader("Cache-Control", "No-cache");
			ImageIO.write(bi, "PNG", resp.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
