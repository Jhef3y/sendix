// src/main/java/br/com/sendix/api/domain/service/QrCodeGeneratorService.java

package br.com.sendix.api.application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeGeneratorService {

    public String generateQrCodeBase64(String uri) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix matrix = new MultiFormatWriter().encode(uri, BarcodeFormat.QR_CODE, 200, 200, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

            byte[] qrCodeBytes = outputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(qrCodeBytes);

            return "data:image/png;base64," + base64Image;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao gerar o QR Code", e);
        }
    }
}