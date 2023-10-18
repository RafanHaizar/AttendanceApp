package com.itextpdf.kernel.xmp.impl;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class FixASCIIControlsReader extends PushbackReader {
    private static final int BUFFER_SIZE = 8;
    private static final int STATE_AMP = 1;
    private static final int STATE_DIG1 = 4;
    private static final int STATE_ERROR = 5;
    private static final int STATE_HASH = 2;
    private static final int STATE_HEX = 3;
    private static final int STATE_START = 0;
    private int control = 0;
    private int digits = 0;
    private int state = 0;

    public FixASCIIControlsReader(Reader input) {
        super(input, 8);
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        int readAhead = 0;
        int read = 0;
        int pos = off;
        char[] readAheadBuffer = new char[8];
        boolean available = true;
        while (available && read < len) {
            boolean z = true;
            if (super.read(readAheadBuffer, readAhead, 1) != 1) {
                z = false;
            }
            available = z;
            if (available) {
                char c = processChar(readAheadBuffer[readAhead]);
                int i = this.state;
                if (i == 0) {
                    if (Utils.isControlChar(c)) {
                        c = ' ';
                    }
                    cbuf[pos] = c;
                    readAhead = 0;
                    read++;
                    pos++;
                } else if (i == 5) {
                    unread(readAheadBuffer, 0, readAhead + 1);
                    readAhead = 0;
                } else {
                    readAhead++;
                }
            } else if (readAhead > 0) {
                unread(readAheadBuffer, 0, readAhead);
                this.state = 5;
                readAhead = 0;
                available = true;
            }
        }
        if (read > 0 || available) {
            return read;
        }
        return -1;
    }

    private char processChar(char ch) {
        switch (this.state) {
            case 0:
                if (ch == '&') {
                    this.state = 1;
                }
                return ch;
            case 1:
                if (ch == '#') {
                    this.state = 2;
                } else {
                    this.state = 5;
                }
                return ch;
            case 2:
                if (ch == 'x') {
                    this.control = 0;
                    this.digits = 0;
                    this.state = 3;
                } else if ('0' > ch || ch > '9') {
                    this.state = 5;
                } else {
                    this.control = Character.digit(ch, 10);
                    this.digits = 1;
                    this.state = 4;
                }
                return ch;
            case 3:
                if (('0' <= ch && ch <= '9') || (('a' <= ch && ch <= 'f') || ('A' <= ch && ch <= 'F'))) {
                    this.control = (this.control * 16) + Character.digit(ch, 16);
                    int i = this.digits + 1;
                    this.digits = i;
                    if (i <= 4) {
                        this.state = 3;
                    } else {
                        this.state = 5;
                    }
                } else if (ch != ';' || !Utils.isControlChar((char) this.control)) {
                    this.state = 5;
                } else {
                    this.state = 0;
                    return (char) this.control;
                }
                return ch;
            case 4:
                if ('0' <= ch && ch <= '9') {
                    this.control = (this.control * 10) + Character.digit(ch, 10);
                    int i2 = this.digits + 1;
                    this.digits = i2;
                    if (i2 <= 5) {
                        this.state = 4;
                    } else {
                        this.state = 5;
                    }
                } else if (ch != ';' || !Utils.isControlChar((char) this.control)) {
                    this.state = 5;
                } else {
                    this.state = 0;
                    return (char) this.control;
                }
                return ch;
            case 5:
                this.state = 0;
                return ch;
            default:
                return ch;
        }
    }
}
