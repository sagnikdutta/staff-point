package ru.point.utils.russian;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author: Mikhail Sedov [27.03.2009]
 */
public class DeclensionFreeMarkerDirective implements TemplateDirectiveModel {

    private Declension dec;

    public DeclensionFreeMarkerDirective(int decIdx) {
        dec = Declension.byIdx(decIdx);
    }

    public void execute(Environment env, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody body) throws TemplateException, IOException {

        boolean isFemale = ((TemplateBooleanModel) params.get("sex")).getAsBoolean();

        if (body != null) {
            body.render(new DeclensionWriter(isFemale, env.getOut()));
        } 
    }

    private class DeclensionWriter extends Writer {

        private boolean isFemale;
        private Writer out;

        private DeclensionWriter(boolean isFemale, Writer out) {
            this.isFemale = isFemale;
            this.out = out;
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            String[] items = new String(cbuf, off, len).split(" ");
            for (String item : items) {
                if (isFemale) {
                    out.write(Declension.inflectFemale(dec, item) + " ");
                } else {
                    out.write(Declension.inflectMale(dec, item) + " ");
                }
            }
        }

        public void flush() throws IOException {
            out.flush();
        }

        public void close() throws IOException {
            out.close();
        }
    }
}
