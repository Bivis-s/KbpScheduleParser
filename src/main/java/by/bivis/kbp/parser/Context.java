package by.bivis.kbp.parser;

import by.bivis.kbp.parser.parsers.values.pages.KbpPages;
import by.bivis.kbp.parser.parsers.values.pages.Pages;
import lombok.Getter;
import lombok.Setter;

public class Context {
    @Getter
    @Setter
    private static Pages pages = new KbpPages();

    private Context() {
    }
}
