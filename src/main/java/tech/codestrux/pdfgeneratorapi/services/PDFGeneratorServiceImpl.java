/******************************************************************************
 * Copyright CodeStrux Tech SRL Costa Rica (c) 2025.                          *
 * Creado por Alvaro Araya aao [at] codestrux.tech                            *
 * github:alvaro-araya github:CodeStrux                                       *
 ******************************************************************************/

package tech.codestrux.pdfgeneratorapi.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {
  @Override
  public ByteArrayOutputStream generatePDF(String html) {
    Document document = null;
    try {
      document = Jsoup.parse(html, "UTF-8");
      document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    } catch (Exception ex) {
      System.out.println("GENERATE ERROR: " + ex.getMessage());
    }
    if (document != null) {
      try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);
        renderer.setDocumentFromString(document.html());
        renderer.getFontResolver().addFont(ResourceUtils.getFile("atkinson-hyperlegible/otf/atkinson-hyperlegible-regular-102.otf").getPath(), true);
        renderer.getFontResolver().addFont(ResourceUtils.getFile("atkinson-hyperlegible/otf/atkinson-hyperlegible-bold-102.otf").getPath(), true);
        renderer.getFontResolver().addFont(ResourceUtils.getFile("atkinson-hyperlegible/otf/atkinson-hyperlegible-italic-102.otf").getPath(), true);
        renderer.getFontResolver().addFont(ResourceUtils.getFile("atkinson-hyperlegible/otf/atkinson-hyperlegible-boldItalic-102.otf").getPath(), true);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream;
      } catch (Exception ex) {
        System.out.println("GENERATE ERROR: " + ex.getMessage());
      }
    }
    return null;
  }
}
