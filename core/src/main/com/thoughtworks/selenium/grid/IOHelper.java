package com.thoughtworks.selenium.grid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

/**
 * Band-aid for painful Java IO API.
 */
public class IOHelper {

    private static final Log LOGGER = LogFactory.getLog(IOHelper.class);

    /**
     * Copy remaining stream content to another stream.
     *
     * @param in             Input stream to copy (remaining) content from. Cannot be null.
     * @param out            Output stream to copy content to. Cannot be null.
     * @param copyBufferSize Size of the maximum chunk of data that will be copied in one step. A buffer a this
     *                       size will be allocated internally so beware of the usual speed vs. memory tradeoff.
     *                       Must be strictly positive.
     * @throws java.io.IOException on IO error.
     */
    public static void copyStream(InputStream in, Writer out, int copyBufferSize) throws IOException {
        InputStreamReader reader = null;
        final char[] buffer;
        int bytesRead;

        buffer = new char[copyBufferSize];
        try {
            reader = new InputStreamReader(in);
            while (true) {
                bytesRead = reader.read(buffer);
                if (bytesRead < 0) {    /* End of stream */
                    break;
                }
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            close(reader);
        }
        out.flush();
    }

    /**
     * Safely close an input stream  without bothering about null or IOExceptions.
     *
     * @param is  InputStream to close. Can be null.
     */
    public static void close(InputStream is) {
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing input stream '" + is + "'", e);
            }
        }
    }

    /**
     * Safely close a Reader without bothering about null or IOExceptions.
     *
     * @param reader Reader to close. Can be null.
     */
    public static void close(Reader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing reader '" + reader + "'", e);
            }
        }
    }


    /**
     * Safely close a Writer without bothering about null or IOExceptions.
     *
     * @param writer Writer to close. Can be null.
     */
    public static void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                LOGGER.info("Ignoring exception while closing writer stream '" + writer + "'", e);
            }
        }
    }

}