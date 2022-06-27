package org.netty.rpc.core.server;

import java.io.DataInput;
import java.util.List;

import org.netty.rpc.interfaces.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/25 01:51
 */
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Override
    public String sendData(String body) {
        return null;
    }

    @Override
    public List<String> getList() {
        return null;
    }
}
