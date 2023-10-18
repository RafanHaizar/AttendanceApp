package com.itextpdf.layout.property;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IElement;

public interface IListSymbolFactory {
    IElement createSymbol(int i, IPropertyContainer iPropertyContainer, IPropertyContainer iPropertyContainer2);
}
