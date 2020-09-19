package org.spring.explore.bean.scope;

import org.junit.jupiter.api.Test;
import org.spring.explore.common.util.LogUtil;

public class TestLog {

    @Test
    public void logTest(){
        for (int i = 0; i < 6; i++) {
            LogUtil.testLog();
        }
    }

}
