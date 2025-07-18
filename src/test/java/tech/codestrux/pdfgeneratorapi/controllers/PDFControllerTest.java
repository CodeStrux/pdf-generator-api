package tech.codestrux.pdfgeneratorapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import tech.codestrux.pdfgeneratorapi.services.PDFGeneratorService;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PDFControllerTest {

    @Mock
    private PDFGeneratorService pdfGeneratorService;

    @InjectMocks
    private PDFController pdfController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generatePDF_Success() {
        // Prepare test data
        String htmlContent = "<html><body><h1>Test</h1></body></html>";
        ByteArrayOutputStream mockPdfStream = new ByteArrayOutputStream();
        mockPdfStream.write("PDF content".getBytes(), 0, "PDF content".length());

        // Mock service response
        when(pdfGeneratorService.generatePDF(anyString())).thenReturn(mockPdfStream);

        // Call the controller method
        ResponseEntity<byte[]> response = pdfController.generatePDF(htmlContent);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getHeaders().getContentType().includes(MediaType.APPLICATION_PDF));
        assertNotNull(response.getHeaders().getContentDisposition());
    }

    @Test
    void generatePDF_Error() {
        // Prepare test data
        String htmlContent = "<html><body><h1>Test</h1></body></html>";

        // Mock service response for error case
        when(pdfGeneratorService.generatePDF(anyString())).thenReturn(null);

        // Call the controller method
        ResponseEntity<byte[]> response = pdfController.generatePDF(htmlContent);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}