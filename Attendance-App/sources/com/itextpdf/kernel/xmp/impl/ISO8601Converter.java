package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPDateTime;
import com.itextpdf.kernel.xmp.XMPException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.SimpleTimeZone;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

public final class ISO8601Converter {
    private ISO8601Converter() {
    }

    public static XMPDateTime parse(String iso8601String) throws XMPException {
        return parse(iso8601String, new XMPDateTimeImpl());
    }

    public static XMPDateTime parse(String iso8601String, XMPDateTime binValue) throws XMPException {
        if (iso8601String == null) {
            throw new XMPException("Parameter must not be null", 4);
        } else if (iso8601String.length() == 0) {
            return binValue;
        } else {
            ParseState input = new ParseState(iso8601String);
            if (input.mo29238ch(0) == '-') {
                input.skip();
            }
            int value = input.gatherInt("Invalid year in date string", 9999);
            if (!input.hasNext() || input.mo29237ch() == '-') {
                if (input.mo29238ch(0) == '-') {
                    value = -value;
                }
                binValue.setYear(value);
                if (!input.hasNext()) {
                    return binValue;
                }
                input.skip();
                int value2 = input.gatherInt("Invalid month in date string", 12);
                if (input.hasNext() == 0 || input.mo29237ch() == '-') {
                    binValue.setMonth(value2);
                    if (!input.hasNext()) {
                        return binValue;
                    }
                    input.skip();
                    int value3 = input.gatherInt("Invalid day in date string", 31);
                    if (!input.hasNext() || input.mo29237ch() == 'T') {
                        binValue.setDay(value3);
                        if (!input.hasNext()) {
                            return binValue;
                        }
                        input.skip();
                        binValue.setHour(input.gatherInt("Invalid hour in date string", 23));
                        if (!input.hasNext()) {
                            return binValue;
                        }
                        if (input.mo29237ch() == ':') {
                            input.skip();
                            int value4 = input.gatherInt("Invalid minute in date string", 59);
                            if (!input.hasNext() || input.mo29237ch() == ':' || input.mo29237ch() == 'Z' || input.mo29237ch() == '+' || input.mo29237ch() == '-') {
                                binValue.setMinute(value4);
                            } else {
                                throw new XMPException("Invalid date string, after minute", 5);
                            }
                        }
                        if (!input.hasNext()) {
                            return binValue;
                        }
                        if (input.hasNext() && input.mo29237ch() == ':') {
                            input.skip();
                            int value5 = input.gatherInt("Invalid whole seconds in date string", 59);
                            if (!input.hasNext() || input.mo29237ch() == '.' || input.mo29237ch() == 'Z' || input.mo29237ch() == '+' || input.mo29237ch() == '-') {
                                binValue.setSecond(value5);
                                if (input.mo29237ch() == '.') {
                                    input.skip();
                                    int digits = input.pos();
                                    int value6 = input.gatherInt("Invalid fractional seconds in date string", 999999999);
                                    if (!input.hasNext() || input.mo29237ch() == 'Z' || input.mo29237ch() == '+' || input.mo29237ch() == '-') {
                                        int digits2 = input.pos() - digits;
                                        while (digits2 > 9) {
                                            value6 /= 10;
                                            digits2--;
                                        }
                                        while (digits2 < 9) {
                                            value6 *= 10;
                                            digits2++;
                                        }
                                        binValue.setNanoSecond(value6);
                                    } else {
                                        throw new XMPException("Invalid date string, after fractional second", 5);
                                    }
                                }
                            } else {
                                throw new XMPException("Invalid date string, after whole seconds", 5);
                            }
                        } else if (!(input.mo29237ch() == 'Z' || input.mo29237ch() == '+' || input.mo29237ch() == '-')) {
                            throw new XMPException("Invalid date string, after time", 5);
                        }
                        int tzSign = 0;
                        int tzHour = 0;
                        int tzMinute = 0;
                        if (!input.hasNext()) {
                            return binValue;
                        }
                        if (input.mo29237ch() == 'Z') {
                            input.skip();
                        } else if (input.hasNext()) {
                            if (input.mo29237ch() == '+') {
                                tzSign = 1;
                            } else if (input.mo29237ch() == '-') {
                                tzSign = -1;
                            } else {
                                throw new XMPException("Time zone must begin with 'Z', '+', or '-'", 5);
                            }
                            input.skip();
                            tzHour = input.gatherInt("Invalid time zone hour in date string", 23);
                            if (input.hasNext()) {
                                if (input.mo29237ch() == ':') {
                                    input.skip();
                                    tzMinute = input.gatherInt("Invalid time zone minute in date string", 59);
                                } else {
                                    throw new XMPException("Invalid date string, after time zone hour", 5);
                                }
                            }
                        }
                        binValue.setTimeZone(new SimpleTimeZone(((tzHour * 3600 * 1000) + (tzMinute * 60 * 1000)) * tzSign, ""));
                        if (!input.hasNext()) {
                            return binValue;
                        }
                        throw new XMPException("Invalid date string, extra chars at end", 5);
                    }
                    throw new XMPException("Invalid date string, after day", 5);
                }
                throw new XMPException("Invalid date string, after month", 5);
            }
            throw new XMPException("Invalid date string, after year", 5);
        }
    }

    public static String render(XMPDateTime dateTime) {
        StringBuffer buffer = new StringBuffer();
        if (dateTime.hasDate()) {
            DecimalFormat df = new DecimalFormat("0000", new DecimalFormatSymbols(Locale.ENGLISH));
            buffer.append(df.format((long) dateTime.getYear()));
            if (dateTime.getMonth() == 0) {
                return buffer.toString();
            }
            df.applyPattern("'-'00");
            buffer.append(df.format((long) dateTime.getMonth()));
            if (dateTime.getDay() == 0) {
                return buffer.toString();
            }
            buffer.append(df.format((long) dateTime.getDay()));
            if (dateTime.hasTime()) {
                buffer.append('T');
                df.applyPattern("00");
                buffer.append(df.format((long) dateTime.getHour()));
                buffer.append(':');
                buffer.append(df.format((long) dateTime.getMinute()));
                if (!(dateTime.getSecond() == 0 && dateTime.getNanoSecond() == 0)) {
                    double second = (double) dateTime.getSecond();
                    double nanoSecond = (double) dateTime.getNanoSecond();
                    Double.isNaN(nanoSecond);
                    Double.isNaN(second);
                    df.applyPattern(":00.#########");
                    buffer.append(df.format(second + (nanoSecond / 1.0E9d)));
                }
                if (dateTime.hasTimeZone()) {
                    int offset = dateTime.getTimeZone().getOffset(dateTime.getCalendar().getTimeInMillis());
                    if (offset == 0) {
                        buffer.append(Matrix.MATRIX_TYPE_ZERO);
                    } else {
                        int tminutes = Math.abs((offset % 3600000) / 60000);
                        df.applyPattern("+00;-00");
                        buffer.append(df.format((long) (offset / 3600000)));
                        df.applyPattern(":00");
                        buffer.append(df.format((long) tminutes));
                    }
                }
            }
        }
        return buffer.toString();
    }
}
