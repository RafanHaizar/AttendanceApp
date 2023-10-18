package com.itextpdf.styledxmlparser.css.resolve;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;

public class CssQuotes {
    private static final String EMPTY_QUOTE = "";
    private ArrayList<String> closeQuotes;
    private ArrayList<String> openQuotes;

    private CssQuotes(ArrayList<String> openQuotes2, ArrayList<String> closeQuotes2) {
        this.openQuotes = new ArrayList<>(openQuotes2);
        this.closeQuotes = new ArrayList<>(closeQuotes2);
    }

    public static CssQuotes createQuotes(String quotesString, boolean fallbackToDefault) {
        boolean error = false;
        ArrayList<ArrayList<String>> quotes = new ArrayList<>(2);
        quotes.add(new ArrayList());
        quotes.add(new ArrayList());
        if (quotesString != null) {
            if (quotesString.equals("none")) {
                quotes.get(0).add("");
                quotes.get(1).add("");
                return new CssQuotes(quotes.get(0), quotes.get(1));
            }
            CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(quotesString);
            int i = 0;
            while (true) {
                CssDeclarationValueTokenizer.Token nextValidToken = tokenizer.getNextValidToken();
                CssDeclarationValueTokenizer.Token token = nextValidToken;
                if (nextValidToken != null) {
                    if (!token.isString()) {
                        error = true;
                        break;
                    }
                    quotes.get(i % 2).add(token.getValue());
                    i++;
                } else {
                    break;
                }
            }
            if (quotes.get(0).size() == quotes.get(1).size() && !quotes.get(0).isEmpty() && !error) {
                return new CssQuotes(quotes.get(0), quotes.get(1));
            }
            LoggerFactory.getLogger((Class<?>) CssQuotes.class).error(MessageFormatUtil.format(LogMessageConstant.QUOTES_PROPERTY_INVALID, quotesString));
        }
        if (fallbackToDefault) {
            return createDefaultQuotes();
        }
        return null;
    }

    public static CssQuotes createDefaultQuotes() {
        ArrayList<String> openQuotes2 = new ArrayList<>();
        ArrayList<String> closeQuotes2 = new ArrayList<>();
        openQuotes2.add("«");
        closeQuotes2.add("»");
        return new CssQuotes(openQuotes2, closeQuotes2);
    }

    public String resolveQuote(String value, AbstractCssContext context) {
        int depth = context.getQuotesDepth();
        if (CommonCssConstants.OPEN_QUOTE.equals(value)) {
            increaseDepth(context);
            return getQuote(depth, this.openQuotes);
        } else if (CommonCssConstants.CLOSE_QUOTE.equals(value)) {
            decreaseDepth(context);
            return getQuote(depth - 1, this.closeQuotes);
        } else if (CommonCssConstants.NO_OPEN_QUOTE.equals(value)) {
            increaseDepth(context);
            return "";
        } else if (!CommonCssConstants.NO_CLOSE_QUOTE.equals(value)) {
            return null;
        } else {
            decreaseDepth(context);
            return "";
        }
    }

    private void increaseDepth(AbstractCssContext context) {
        context.setQuotesDepth(context.getQuotesDepth() + 1);
    }

    private void decreaseDepth(AbstractCssContext context) {
        if (context.getQuotesDepth() > 0) {
            context.setQuotesDepth(context.getQuotesDepth() - 1);
        }
    }

    private String getQuote(int depth, ArrayList<String> quotes) {
        if (depth >= quotes.size()) {
            return quotes.get(quotes.size() - 1);
        }
        if (depth < 0) {
            return "";
        }
        return quotes.get(depth);
    }
}
