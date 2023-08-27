package org.netty.rpc.core.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.netty.rpc.core.Selector;
import org.netty.rpc.core.common.ChannelFutureWrapper;
import org.netty.rpc.core.common.cache.CommonClientCache;
import org.netty.rpc.core.registy.Url;

/**
 * <p>随机路由</p>
 *
 * @author mason
 * @date 2022/9/4 23:19
 */
public class RandomRouterImpl implements IRouter {

    @Override
    public void refreshRouterArray(Selector selector) {
        List<ChannelFutureWrapper> channelFutureWrapperList = CommonClientCache.CONNECT_MAP.get(selector.getProviderServiceName());
        ChannelFutureWrapper[] channelFutureWrappersArray = new ChannelFutureWrapper[channelFutureWrapperList.size()];
        int[] result = createRandomIndex(channelFutureWrappersArray.length);
        for (int i = 0; i < result.length; i++) {
            channelFutureWrappersArray[i] = channelFutureWrapperList.get(result[i]);
        }

        CommonClientCache.SERVICE_ROUTER_MAP.put(selector.getProviderServiceName(), channelFutureWrappersArray);
    }

    @Override
    public ChannelFutureWrapper select(Selector selector) {
        return CommonClientCache.CHANNEL_FUTURE_POLLING_REF.getChannelFutureWrapper(selector.getProviderServiceName());
    }

    @Override
    public void updateWeight(Url url) {
        List<ChannelFutureWrapper> channelFutureWrapperList = CommonClientCache.CONNECT_MAP.get(url.getServiceName());
        Integer[] weightArray = createWeightArray(channelFutureWrapperList);
        Integer[] finalArray = createRandomArray(weightArray);
        ChannelFutureWrapper[] finalChannelFutureWrappers = new ChannelFutureWrapper[finalArray.length];
        for (int i = 0; i < finalArray.length; i++) {
            finalChannelFutureWrappers[i] = channelFutureWrapperList.get(i);
        }

        CommonClientCache.SERVICE_ROUTER_MAP.put(url.getServiceName(), finalChannelFutureWrappers);
    }

    private Integer[] createWeightArray(List<ChannelFutureWrapper> channelFutureWrapperList) {
        List<Integer> weightArray = new ArrayList<>();
        for (int i = 0; i < channelFutureWrapperList.size(); i++) {
            Integer weight = channelFutureWrapperList.get(i).getWeight();
            int c = weight / 100;
            for (int j = 0; j < c; j++) {
                weightArray.add(i);
            }
        }
        Integer[] arr = new Integer[weightArray.size()];
        return weightArray.toArray(arr);
    }

    private Integer[] createRandomArray(Integer[] arr) {
        int total = arr.length;
        Random random = new Random();
        for (int i = 0; i < total; i++) {
            int j = random.nextInt(total);
            if (i == j) {
                continue;
            }
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    private int[] createRandomIndex(int len) {
        int[] arrInt = new int[len];
        Random random = new Random();
        for (int i = 0; i < arrInt.length; i++) {
            arrInt[i] = -1;
        }

        int index = 0;
        while (index < arrInt.length) {
            int num = random.nextInt(len);
            if (!contains(arrInt, num)) {
                arrInt[index++] = num;
            }
        }
        return arrInt;
    }

    private boolean contains(int[] arr, int key) {
        for (int cell : arr) {
            if (cell == key) {
                return true;
            }
        }

        return false;
    }
}
