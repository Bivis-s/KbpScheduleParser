package parser;

import lombok.Getter;
import lombok.Setter;
import parser.values.pages.KbpPages;
import parser.values.pages.Pages;

public class Context {
    @Getter
    @Setter
    private static Pages pages = new KbpPages();

    private Context() {
    }
}
