package edu.school21.classes;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        Set<? extends Element> htmlFormElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element htmlFormElement : htmlFormElements) {
            StringBuilder form = new StringBuilder();
            HtmlForm htmlForm = htmlFormElement.getAnnotation(HtmlForm.class);
            form.append(String.format("<form action = \"%s\" method = \"%s\">\n", htmlForm.action(), htmlForm.method()));
            Set<? extends Element> htmlInputElements = roundEnv.getElementsAnnotatedWith(HtmlInput.class);
            for (Element htmlInputElement : htmlInputElements) {
                HtmlInput htmlInput = htmlInputElement.getAnnotation(HtmlInput.class);
                form.append(String.format("\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">\n",
                        htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
            }
            if (!htmlInputElements.isEmpty()) {
                form.append("\t<input type = \"submit\" value = \"Send\">\n");
            }
            form.append("</form>\n");

            File file = new File("target/classes/" + htmlForm.fileName());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
                fileOutputStream.write(form.toString().getBytes());
            } catch (IOException e) {
                System.err.println("IOException in writing to file!");
            }
        }
        return true;
    }
}
