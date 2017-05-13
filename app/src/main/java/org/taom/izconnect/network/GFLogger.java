package org.taom.izconnect.network;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GFLogger {
    private static final String TAG_SEPARATOR = ":";
    private static final String MESSAGE_SEPARATOR = " ";
    private static Logger LOGGER = getLogger();

    public static Logger getLogger() {
        return Logger.getGlobal();
    }

    public static void log(Level level, String prefix, String... message) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(prefix)
                .append(TAG_SEPARATOR);
        for (String msg : message) {
            stringBuilder
                    .append(MESSAGE_SEPARATOR)
                    .append(msg);
        }
        LOGGER.log(level, stringBuilder.toString());
    }
}