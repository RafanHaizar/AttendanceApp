package com.itextpdf.styledxmlparser.jsoup;

import com.itextpdf.styledxmlparser.jsoup.helper.DataUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
import com.itextpdf.styledxmlparser.jsoup.safety.Cleaner;
import com.itextpdf.styledxmlparser.jsoup.safety.Whitelist;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Jsoup {
    private Jsoup() {
    }

    public static Document parse(String html, String baseUri) {
        return Parser.parse(html, baseUri);
    }

    public static Document parse(String html, String baseUri, Parser parser) {
        return parser.parseInput(html, baseUri);
    }

    public static Document parse(String html) {
        return Parser.parse(html, "");
    }

    public static Document parseXML(String xml, String baseUri) {
        return Parser.parseXml(xml, baseUri);
    }

    public static Document parseXML(String xml) {
        return Parser.parseXml(xml, "");
    }

    public static Document parseXML(InputStream in, String charsetName, String baseUri) throws IOException {
        return parse(in, charsetName, baseUri, Parser.xmlParser());
    }

    public static Document parseXML(InputStream in, String charsetName) throws IOException {
        return parseXML(in, charsetName, "");
    }

    public static Document parse(File in, String charsetName, String baseUri) throws IOException {
        return DataUtil.load(in, charsetName, baseUri);
    }

    public static Document parse(File in, String charsetName) throws IOException {
        return DataUtil.load(in, charsetName, in.getAbsolutePath());
    }

    public static Document parse(InputStream in, String charsetName, String baseUri) throws IOException {
        return DataUtil.load(in, charsetName, baseUri);
    }

    public static Document parse(InputStream in, String charsetName, String baseUri, Parser parser) throws IOException {
        return DataUtil.load(in, charsetName, baseUri, parser);
    }

    public static Document parseBodyFragment(String bodyHtml, String baseUri) {
        return Parser.parseBodyFragment(bodyHtml, baseUri);
    }

    public static Document parseBodyFragment(String bodyHtml) {
        return Parser.parseBodyFragment(bodyHtml, "");
    }

    public static String clean(String bodyHtml, String baseUri, Whitelist whitelist) {
        return new Cleaner(whitelist).clean(parseBodyFragment(bodyHtml, baseUri)).body().html();
    }

    public static String clean(String bodyHtml, Whitelist whitelist) {
        return clean(bodyHtml, "", whitelist);
    }

    public static String clean(String bodyHtml, String baseUri, Whitelist whitelist, Document.OutputSettings outputSettings) {
        Document clean = new Cleaner(whitelist).clean(parseBodyFragment(bodyHtml, baseUri));
        clean.outputSettings(outputSettings);
        return clean.body().html();
    }

    public static boolean isValid(String bodyHtml, Whitelist whitelist) {
        return new Cleaner(whitelist).isValid(parseBodyFragment(bodyHtml, ""));
    }
}
