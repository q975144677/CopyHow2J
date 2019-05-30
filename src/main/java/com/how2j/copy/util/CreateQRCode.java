package com.how2j.copy.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
@Component
public class CreateQRCode {
    private static String path ;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static void main(String[] args ) {
        createQrcode("13811111","D:/img.png");
    }
    public static void createQrcoe(String content){
        createQrcode(content,path);
    }

    public static void createQrcode(String content,String path){
        //生成图片的一些基本参数
        int width = 300;
        int height = 300;
        String format = "png";
        //这里是内容


        //定义二维码参数
        Map<EncodeHintType, Object> params = new HashMap<EncodeHintType, Object>();
        params.put(EncodeHintType.CHARACTER_SET, "utf-8");
        params.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        params.put(EncodeHintType.MARGIN, 2);

        //生成二维码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, params);

            Path file = new File(path).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}