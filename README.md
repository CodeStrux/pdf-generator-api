# HTML to PDF Endpoint Documentation

## Overview

This document describes the new endpoint that converts HTML content to PDF format using the PDFGenerator service.

## Endpoint Details

### URL

```
POST /api/pdf/generate
```

### Request

- **Method**: POST
- **Content-Type**: text/html or application/json
- **Body**: Raw HTML content as a string

#### Example Request

```bash
curl -X POST \
  http://localhost:8080/api/pdf/generate \
  -H 'Content-Type: text/html' \
  -d '<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1" name="viewport">
  <title>Document</title>
</head>
<body>
  <h1>Hello, World!</h1>
  <p>This is a sample HTML that will be converted to PDF.</p>
</body>
</html>'
```

### Response

- **Content-Type**: application/pdf
- **Content-Disposition**: attachment; filename="generated-pdf.pdf"
- **Body**: Binary PDF content

### Status Codes

- **200 OK**: The HTML was successfully converted to PDF
- **500 Internal Server Error**: An error occurred during PDF generation

## Implementation Details

The endpoint uses the PDFGeneratorService to convert the provided HTML content to a PDF document. The service:

1. Parses the HTML using Jsoup
2. Renders the HTML to PDF using ITextRenderer
3. Applies custom fonts (Atkinson Hyperlegible) to ensure proper rendering
4. Returns the PDF as a byte stream

## Notes

- The HTML content should be well-formed and valid
- Custom fonts are automatically applied to the generated PDF
- The PDF is returned as an attachment with the filename "generated-pdf.pdf"