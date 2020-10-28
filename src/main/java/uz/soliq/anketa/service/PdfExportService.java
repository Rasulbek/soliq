package uz.soliq.anketa.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import uz.soliq.anketa.service.dto.AcademicDegreeDTO;
import uz.soliq.anketa.service.dto.EmployeeDTO;
import uz.soliq.anketa.service.dto.JobHistoryDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class PdfExportService {

    private Document document;

    private EmployeeDTO employeeDTO;

    private Font font;

    public ByteArrayInputStream createCV(EmployeeDTO employeeDTO) {
        document = new Document();
        this.employeeDTO = employeeDTO;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
            BaseFont baseFont = BaseFont.createFont("/content/pt-sans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            font = new Font(baseFont, 16);
            document.open();
            addHeaderText();
            addMainTable();

            addUniversityHeaderText();
            addUniversityTable();

            addJobHistoryHeaderText();
            addJobHistoryTable();

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }



    private void addHeaderText() throws DocumentException {
        font.setStyle(Font.BOLD);
        Paragraph paragraph = new Paragraph("М A Ъ Л У М О T H О M А", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);
    }

    private void addMainTable() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 340, 100 });
        table.setLockedWidth(true);

        table.addCell(getMainDataCell("Ф.И.Ш.", employeeDTO.getFullName()));
        table.addCell(makeImageCell());

        table.addCell(getMainDataCell("Миллати", employeeDTO.getNation()));
        table.addCell(getMainDataCell("Туғилган сана",
            employeeDTO.getBirthday()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        );

        employeeDTO.getUniversities().stream().max(Comparator.comparingInt(AcademicDegreeDTO::getEndYear)).ifPresent(degree -> {
            table.addCell(getMainDataCell("Маълумоти", degree.getObtainedDegree().name()));
        });

        table.addCell(getMainDataCell("Телефон", employeeDTO.getPhoneNumber()));
        table.addCell(getMainDataCell("Email", employeeDTO.getEmail()));

        document.add(table);
    }

    private void addUniversityHeaderText() throws DocumentException {
        document.add(Chunk.NEWLINE);
        font.setSize(14);
        font.setStyle(Font.BOLD);
        Paragraph paragraph = new Paragraph("Маълумотлари", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph(" "));
    }

    private void addUniversityTable() throws DocumentException, IOException {
        font.setSize(12);
        font.setStyle(Font.NORMAL);
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 120, 320 });
        table.setLockedWidth(true);

        PdfPCell headerCell = new PdfPCell(new Phrase("Йиллар", font));
        headerCell.setBorder(Rectangle.BOTTOM);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);
        headerCell = new PdfPCell(new Phrase("Ўқув юрти ва мутахассисилиги", font));
        headerCell.setBorder(Rectangle.BOTTOM);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);

        employeeDTO.getUniversities().stream()
            .sorted(Comparator.comparingInt(AcademicDegreeDTO::getStartYear))
            .peek(academicDegreeDTO -> {
                PdfPCell cell = getUniversityCell(String.format("%d - %d й.й.", academicDegreeDTO.getStartYear(), academicDegreeDTO.getEndYear()));
                table.addCell(cell);
                cell = getUniversityCell(String.format("%s\n%s", academicDegreeDTO.getInstitutionName(), academicDegreeDTO.getDiscipline()));
                table.addCell(cell);
            })
            .collect(Collectors.toList());

        document.add(table);
    }

    private void addJobHistoryHeaderText() throws DocumentException {
        document.add(Chunk.NEWLINE);
        font.setSize(14);
        font.setStyle(Font.BOLD);
        Paragraph paragraph = new Paragraph("Иш жойлари", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph(" "));
    }

    private void addJobHistoryTable() throws DocumentException, IOException {
        font.setSize(12);
        font.setStyle(Font.NORMAL);
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 120, 320 });
        table.setLockedWidth(true);

        PdfPCell headerCell = new PdfPCell(new Phrase("Йиллар", font));
        headerCell.setBorder(Rectangle.BOTTOM);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);
        headerCell = new PdfPCell(new Phrase("Иш жойи ва лавозими", font));
        headerCell.setBorder(Rectangle.BOTTOM);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);

        employeeDTO.getEmployers().stream()
            .sorted(Comparator.comparingInt(JobHistoryDTO::getEndYear).reversed())
            .peek(jobHistoryDTO -> {
                PdfPCell cell = getUniversityCell(String.format("%d - %d й.й.", jobHistoryDTO.getStartYear(), jobHistoryDTO.getEndYear()));
                table.addCell(cell);
                cell = getUniversityCell(String.format("%s\n%s", jobHistoryDTO.getEmployer(), jobHistoryDTO.getJobTitle()));
                table.addCell(cell);
            })
            .collect(Collectors.toList());

        document.add(table);
    }

    private PdfPCell getMainDataCell(String label, String value) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(0);
        cell.addElement(new Paragraph(label, getLabelFont()));
        cell.addElement(new Paragraph(value, getValueFont()));
        return cell;
    }

    private PdfPCell makeImageCell() throws IOException, DocumentException {
        Image image = Image.getInstance(employeeDTO.getPhoto());
        PdfPCell imageCell = new PdfPCell(image, true);
        imageCell.setBorder(0);
        imageCell.setFixedHeight(200);
        imageCell.setRowspan(6);
        return imageCell;
    }

    private Font getLabelFont() {
        Font font = this.font;
        font.setStyle(Font.NORMAL);
        font.setSize(10);
        return font;
    }

    private Font getValueFont() {
        Font font = this.font;
        font.setStyle(Font.BOLD);
        font.setSize(12);
        return font;
    }

    private PdfPCell getUniversityCell(String string) {
        PdfPCell cell = new PdfPCell(new Phrase(string, font));
        cell.setBorder(Rectangle.BOTTOM);
        return cell;
    }
}
