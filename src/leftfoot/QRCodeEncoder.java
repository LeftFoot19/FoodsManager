package leftfoot;

import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeEncoder {

	public static BufferedImage create(String source, int matrixSize) {

		//QRコード設定
		ConcurrentHashMap hints = new ConcurrentHashMap();

		//エラー訂正レベル
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//エンコード設定
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		//マージン(余白)
		hints.put(EncodeHintType.MARGIN, 0);

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix;
		try {
			bitMatrix = writer.encode(source, BarcodeFormat.QR_CODE, matrixSize, matrixSize, hints);
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
