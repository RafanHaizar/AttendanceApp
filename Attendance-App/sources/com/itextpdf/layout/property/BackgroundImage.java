package com.itextpdf.layout.property;

import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.property.BackgroundRepeat;

public class BackgroundImage {
    /* access modifiers changed from: private */
    public static final BlendMode DEFAULT_BLEND_MODE = BlendMode.NORMAL;
    private final BackgroundBox backgroundClip;
    private final BackgroundBox backgroundOrigin;
    private final BackgroundSize backgroundSize;
    private BlendMode blendMode;
    protected PdfXObject image;
    protected AbstractLinearGradientBuilder linearGradientBuilder;
    private final BackgroundPosition position;
    private BackgroundRepeat repeat;
    @Deprecated
    protected boolean repeatX;
    @Deprecated
    protected boolean repeatY;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public BackgroundImage(BackgroundImage backgroundImage) {
        this(backgroundImage.getImage() == null ? backgroundImage.getForm() : backgroundImage.getImage(), backgroundImage.getRepeat(), backgroundImage.getBackgroundPosition(), backgroundImage.getBackgroundSize(), backgroundImage.getLinearGradientBuilder(), backgroundImage.getBlendMode(), backgroundImage.getBackgroundClip(), backgroundImage.getBackgroundOrigin());
        this.repeatX = backgroundImage.isRepeatX();
        this.repeatY = backgroundImage.isRepeatY();
    }

    @Deprecated
    public BackgroundImage(PdfImageXObject image2, BackgroundRepeat repeat2, BlendMode blendMode2) {
        this(image2, repeat2, new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, blendMode2, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    @Deprecated
    public BackgroundImage(PdfFormXObject image2, BackgroundRepeat repeat2, BlendMode blendMode2) {
        this(image2, repeat2, new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, blendMode2, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    @Deprecated
    public BackgroundImage(PdfImageXObject image2, BackgroundRepeat repeat2) {
        this(image2, repeat2, new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    @Deprecated
    public BackgroundImage(PdfFormXObject image2, BackgroundRepeat repeat2) {
        this(image2, repeat2, new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    @Deprecated
    public BackgroundImage(PdfImageXObject image2) {
        this(image2, new BackgroundRepeat(), new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    @Deprecated
    public BackgroundImage(PdfFormXObject image2) {
        this(image2, new BackgroundRepeat(), new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public BackgroundImage(PdfImageXObject image2, boolean repeatX2, boolean repeatY2) {
        this(image2, new BackgroundRepeat(repeatX2 ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT, repeatY2 ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT), new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public BackgroundImage(PdfFormXObject image2, boolean repeatX2, boolean repeatY2) {
        this(image2, new BackgroundRepeat(repeatX2 ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT, repeatY2 ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT), new BackgroundPosition(), new BackgroundSize(), (AbstractLinearGradientBuilder) null, DEFAULT_BLEND_MODE, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    @Deprecated
    public BackgroundImage(AbstractLinearGradientBuilder linearGradientBuilder2) {
        this(linearGradientBuilder2, DEFAULT_BLEND_MODE);
    }

    @Deprecated
    public BackgroundImage(AbstractLinearGradientBuilder linearGradientBuilder2, BlendMode blendMode2) {
        this((PdfXObject) null, new BackgroundRepeat(BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT), new BackgroundPosition(), new BackgroundSize(), linearGradientBuilder2, blendMode2, BackgroundBox.BORDER_BOX, BackgroundBox.PADDING_BOX);
    }

    public PdfImageXObject getImage() {
        PdfXObject pdfXObject = this.image;
        if (pdfXObject instanceof PdfImageXObject) {
            return (PdfImageXObject) pdfXObject;
        }
        return null;
    }

    public PdfFormXObject getForm() {
        PdfXObject pdfXObject = this.image;
        if (pdfXObject instanceof PdfFormXObject) {
            return (PdfFormXObject) pdfXObject;
        }
        return null;
    }

    private BackgroundImage(PdfXObject image2, BackgroundRepeat repeat2, BackgroundPosition position2, BackgroundSize backgroundSize2, AbstractLinearGradientBuilder linearGradientBuilder2, BlendMode blendMode2, BackgroundBox clip, BackgroundBox origin) {
        this.blendMode = DEFAULT_BLEND_MODE;
        this.image = image2;
        if (repeat2 != null) {
            this.repeatX = !repeat2.isNoRepeatOnXAxis();
            this.repeatY = !repeat2.isNoRepeatOnYAxis();
        }
        this.repeat = repeat2;
        this.position = position2;
        this.backgroundSize = backgroundSize2;
        this.linearGradientBuilder = linearGradientBuilder2;
        if (blendMode2 != null) {
            this.blendMode = blendMode2;
        }
        this.backgroundClip = clip;
        this.backgroundOrigin = origin;
    }

    public BackgroundPosition getBackgroundPosition() {
        return this.position;
    }

    public AbstractLinearGradientBuilder getLinearGradientBuilder() {
        return this.linearGradientBuilder;
    }

    public boolean isBackgroundSpecified() {
        PdfXObject pdfXObject = this.image;
        return (pdfXObject instanceof PdfFormXObject) || (pdfXObject instanceof PdfImageXObject) || this.linearGradientBuilder != null;
    }

    @Deprecated
    public boolean isRepeatX() {
        return this.repeatX;
    }

    @Deprecated
    public boolean isRepeatY() {
        return this.repeatY;
    }

    public BackgroundSize getBackgroundSize() {
        return this.backgroundSize;
    }

    public float getImageWidth() {
        return this.image.getWidth();
    }

    public float getImageHeight() {
        return this.image.getHeight();
    }

    @Deprecated
    public float getWidth() {
        return this.image.getWidth();
    }

    @Deprecated
    public float getHeight() {
        return this.image.getHeight();
    }

    public BackgroundRepeat getRepeat() {
        if (this.repeatX == this.repeat.isNoRepeatOnXAxis()) {
            this.repeat = new BackgroundRepeat(this.repeatX ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT, this.repeat.getYAxisRepeat());
        }
        if (this.repeatY == this.repeat.isNoRepeatOnYAxis()) {
            this.repeat = new BackgroundRepeat(this.repeat.getXAxisRepeat(), this.repeatY ? BackgroundRepeat.BackgroundRepeatValue.REPEAT : BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT);
        }
        return this.repeat;
    }

    public BlendMode getBlendMode() {
        return this.blendMode;
    }

    public BackgroundBox getBackgroundClip() {
        return this.backgroundClip;
    }

    public BackgroundBox getBackgroundOrigin() {
        return this.backgroundOrigin;
    }

    public static class Builder {
        private BackgroundSize backgroundSize = new BackgroundSize();
        private BlendMode blendMode = BackgroundImage.DEFAULT_BLEND_MODE;
        private BackgroundBox clip = BackgroundBox.BORDER_BOX;
        private PdfXObject image;
        private AbstractLinearGradientBuilder linearGradientBuilder;
        private BackgroundBox origin = BackgroundBox.PADDING_BOX;
        private BackgroundPosition position = new BackgroundPosition();
        private BackgroundRepeat repeat = new BackgroundRepeat();

        public Builder setImage(PdfXObject image2) {
            this.image = image2;
            this.linearGradientBuilder = null;
            return this;
        }

        public Builder setLinearGradientBuilder(AbstractLinearGradientBuilder linearGradientBuilder2) {
            this.linearGradientBuilder = linearGradientBuilder2;
            this.repeat = new BackgroundRepeat(BackgroundRepeat.BackgroundRepeatValue.NO_REPEAT);
            this.image = null;
            return this;
        }

        public Builder setBackgroundRepeat(BackgroundRepeat repeat2) {
            this.repeat = repeat2;
            return this;
        }

        public Builder setBackgroundPosition(BackgroundPosition position2) {
            this.position = position2;
            return this;
        }

        public Builder setBackgroundBlendMode(BlendMode blendMode2) {
            if (blendMode2 != null) {
                this.blendMode = blendMode2;
            }
            return this;
        }

        public Builder setBackgroundSize(BackgroundSize backgroundSize2) {
            if (backgroundSize2 != null) {
                this.backgroundSize = backgroundSize2;
            }
            return this;
        }

        public Builder setBackgroundClip(BackgroundBox clip2) {
            this.clip = clip2;
            return this;
        }

        public Builder setBackgroundOrigin(BackgroundBox origin2) {
            this.origin = origin2;
            return this;
        }

        public BackgroundImage build() {
            return new BackgroundImage(this.image, this.repeat, this.position, this.backgroundSize, this.linearGradientBuilder, this.blendMode, this.clip, this.origin);
        }
    }
}
