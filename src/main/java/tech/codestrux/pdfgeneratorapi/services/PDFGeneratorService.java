/******************************************************************************
 * Copyright CodeStrux Tech SRL Costa Rica (c) 2025.                          *
 * Creado por Alvaro Araya aao [at] codestrux.tech                            *
 * github:alvaro-araya github:CodeStrux                                       *
 ******************************************************************************/

package tech.codestrux.pdfgeneratorapi.services;

import java.io.ByteArrayOutputStream;

public interface PDFGeneratorService {
  ByteArrayOutputStream generatePDF(String html);
}
