package com.itextpdf.layout.tagging;

interface ITaggingRule {
    boolean onTagFinish(LayoutTaggingHelper layoutTaggingHelper, TaggingHintKey taggingHintKey);
}
