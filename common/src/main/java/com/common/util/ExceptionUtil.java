package com.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ExceptionUtil {

    private ExceptionUtil() {
        throw new RuntimeException();
    }

    public static void catchException(String source, RunTarget runTarget) {
        log.info("source from {}", source);

        try {
            runTarget.run();
        } catch (Exception e) {
            log.error("source from {} have error {}", source, String.valueOf(e));
        }

    }
}
