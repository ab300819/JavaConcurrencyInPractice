package org.netty.rpc.core.common.event;

import org.junit.jupiter.api.Test;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/6 18:27
 */
class MRpcListenerLoaderTest {


    @Test
    public void sayHello() {
        CustomerListener customerListener = new CustomerListener();
        Class<?> interfaceT = MRpcListenerLoader.getInterfaceT(customerListener);
        System.out.println(interfaceT);
    }

    public static class CustomerListener implements MRpcListener<String> {

        @Override
        public void callback(Object t) {
            System.out.println("hello test!");
        }
    }
}
