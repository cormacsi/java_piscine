package edu.school21.printer;

import edu.school21.renderer.Renderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class PrinterWithDateTimeImpl implements Printer {

    private final Renderer renderer;

    private LocalDateTime date = LocalDateTime.now();

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public void print(String text) {
        renderer.render(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + text);
    }
}
