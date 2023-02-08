package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.baseclasses.AppInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
@RequiredArgsConstructor
@RestController
public class UtilController {

    private final ObjectMapper objectMapper;


    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> getQRCodeImage() throws Exception {
        byte[] image = getQRCodeImage("{$MY_PROJECT_NAME}", 200, 200);
        return ResponseEntity.ok().header("Content-Type", "image/png").body(image);
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInfo() throws Exception {
        String info = objectMapper.writeValueAsString(new AppInfo("MY-AWESOME-APP","1.0"));
        System.out.println(info);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

}
