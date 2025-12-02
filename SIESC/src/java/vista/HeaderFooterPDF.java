/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cris_
 */
public class HeaderFooterPDF extends PdfPageEventHelper {

    private Image headerImage;
    private Image footerImage;

    public HeaderFooterPDF(Image headerImage, Image footerImage) {
        this.headerImage = headerImage;
        this.footerImage = footerImage;
    }

    @Override
public void onEndPage(PdfWriter writer, Document document) {

    PdfContentByte cb = writer.getDirectContent();
    float pageWidth = document.getPageSize().getWidth();

    // ===== HEADER =====
    if (headerImage != null) {
        // Escala proporcionalmente para que no tape contenido
        float scalePercent = 62f; // 95% del tamaño original
        headerImage.scalePercent(scalePercent);
        // Centramos horizontalmente
        float x = (pageWidth - headerImage.getScaledWidth()) / 2;
        float y = document.getPageSize().getTop() - headerImage.getScaledHeight();
        headerImage.setAbsolutePosition(x, y);
        try {
            cb.addImage(headerImage);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    // ===== FOOTER =====
    if (footerImage != null) {
        float scalePercent = 62f; // 95% del tamaño original
        footerImage.scalePercent(scalePercent);
        float x = (pageWidth - footerImage.getScaledWidth()) / 2;
        float y = 0; // pegado al borde inferior
        footerImage.setAbsolutePosition(x, y);
        try {
            cb.addImage(footerImage);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }
}

}
