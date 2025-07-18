/******************************************************************************
 * Copyright CodeStrux Tech SRL Costa Rica (c) 2025.                          *
 * Creado por Alvaro Araya aao [at] codestrux.tech                            *
 * github:alvaro-araya github:CodeStrux                                       *
 ******************************************************************************/

package tech.codestrux.pdfgeneratorapi.controllers;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.codestrux.pdfgeneratorapi.services.PDFGeneratorService;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {
  private final PDFGeneratorService generatorService;

  public PDFController(PDFGeneratorService generatorService) {
    this.generatorService = generatorService;
  }

  @PostMapping("/generate")
  public ResponseEntity<byte[]> generatePDF(@RequestBody String htmlContent) {
    ByteArrayOutputStream pdfStream = generatorService.generatePDF(htmlContent);
    if (pdfStream == null) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", "generated-pdf.pdf");
    return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
  }

  @PostMapping("/generate-with-filename")
  public ResponseEntity<byte[]> generatePDFWithFilename(@RequestBody PDFRequest request) {
    ByteArrayOutputStream pdfStream = generatorService.generatePDF(request.getHtmlContent());
    if (pdfStream == null) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDispositionFormData("attachment", request.getFilename() + ".pdf");
    return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
  }

  @Data
  public static class PDFRequest {
    private String htmlContent;
    private String filename;
  }
}
