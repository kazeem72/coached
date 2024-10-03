package ng.com.justjava.coached.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

@Service
public class PdfGenerator {

    private String parseThymeleafTemplate(String htmlFileName, Context context) {
        //htmlFileName="/"+htmlFileName;
        System.out.println(" Loading the htmlFileName==="+htmlFileName);

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        System.out.println(" The Prefix Here==="+templateResolver.getPrefix());
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();

        templateEngine.setTemplateResolver(templateResolver);

        System.out.println(" Resolving the template ");
        return templateEngine.process(htmlFileName, context);
    }
    public void generatePdfFromHtml(String htmlFileName,Context context,
                                    HttpServletResponse response) throws IOException {

        System.out.println(" Downloading the file===="+htmlFileName);
        String html = parseThymeleafTemplate(htmlFileName,context);

        System.out.println(" I have just parsed the html successfully.....");
        String outputFolder = System.getProperty("user.dir") +
                File.separator+ "coached"+
                File.separator+"src"+
                File.separator+"main"+
                File.separator+"resources"+
                File.separator+ "static"+
                File.separator+"pdf"+
                File.separator+htmlFileName+".pdf";


        System.out.println(" The location of this file is==="+outputFolder);

        String cwd = Path.of("").toAbsolutePath().toString();


        System.out.println(" The better path finder===="+cwd);

        OutputStream outputStream = response.getOutputStream();//new FileOutputStream(outputFolder);
        response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
        response.setHeader("Content-Disposition","attachment; filename="+outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

    }
}
