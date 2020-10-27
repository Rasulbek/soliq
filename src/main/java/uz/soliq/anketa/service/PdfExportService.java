package uz.soliq.anketa.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import uz.soliq.anketa.service.dto.EmployeeDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfExportService {

    public ByteArrayInputStream createCV(EmployeeDTO employeeDTO) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("ANKETA", font);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);

            document.close();
        } catch (DocumentException e) {

        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
