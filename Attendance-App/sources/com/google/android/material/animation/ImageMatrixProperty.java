package com.google.android.material.animation;

import android.graphics.Matrix;
import android.util.Property;
import android.widget.ImageView;

public class ImageMatrixProperty extends Property<ImageView, Matrix> {
    private final Matrix matrix = new Matrix();

    public ImageMatrixProperty() {
        super(Matrix.class, "imageMatrixProperty");
    }

    public void set(ImageView object, Matrix value) {
        object.setImageMatrix(value);
    }

    public Matrix get(ImageView object) {
        this.matrix.set(object.getImageMatrix());
        return this.matrix;
    }
}
