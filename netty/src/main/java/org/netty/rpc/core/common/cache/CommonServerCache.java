package org.netty.rpc.core.common.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.netty.rpc.core.registy.Url;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/22 22:40
 */
public class CommonServerCache {

    public static final Map<String, Object> PROVIDER_CLASS_MAP = new HashMap<>();

    public static final Set<Url> PROVIDER_URL_SET = new HashSet<>();
}
