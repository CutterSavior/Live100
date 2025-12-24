package com.lanjii.common.util; // 假设放在common包下的util子包中

import com.lanjii.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * IP 工具类
 *
 * @author lanjii
 */
@Slf4j
public final class IpUtils {

    private static Searcher searcher;

    static {
        initSearcher();
    }

    /**
     * 初始化 IP2Region 搜索引擎
     */
    private static void initSearcher() {
        try (InputStream inputStream = IpUtils.class.getClassLoader().getResourceAsStream("ip2region.xdb")) {
            if (inputStream == null) {
                throw new IllegalStateException("未找到IP2Region数据库文件 (ip2region.xdb)。请确保文件已放置在classpath根目录下。");
            }
            byte[] dbBinStr = inputStream.readAllBytes();
            searcher = Searcher.newWithBuffer(dbBinStr);
            log.info("IP2Region数据库加载成功");
        } catch (Exception e) {
            log.error("IP2Region数据库加载失败: {}", e.getMessage());
            throw new BizException("IP定位工具初始化失败", e);
        }
    }

    /**
     * 解析IP地址，返回完整地域信息数组
     *
     * @param ip IP地址字符串，如 "220.181.38.148"
     * @return 地域信息数组，格式为 [国家, 区域, 省份, 城市, ISP]
     */
    public static String[] parseIp(String ip) {
        if (searcher == null) {
            log.error("IP定位工具未正确初始化。");
            return new String[]{"", "", "", "", ""};
        }
        try {
            String region = searcher.search(ip);
            return region.split("\\|");
        } catch (Exception e) {
            log.error("IP解析失败: ip={}", ip, e);
            return new String[]{"", "", "", "", ""};
        }
    }

    /**
     * 解析IP地址，返回格式化字符串
     *
     * @param ip IP地址字符串
     * @return 格式化地址，如 "中国 北京市 北京市 联通"
     */
    public static String getFormattedAddress(String ip) {
        String[] info = parseIp(ip);
        // 过滤无意义的"0"和内网IP标识
        List<String> list = Arrays.stream(info)
                .filter(s -> !s.equals("0") && !s.equals("内网IP"))
                .toList();
        return String.join(" ", list);
    }

    /**
     * 解析IP地址，仅返回省、市信息
     *
     * @param ip IP地址字符串
     * @return 省市信息数组，如 ["河北省", "石家庄市"]
     */
    public static String[] getProvinceAndCity(String ip) {
        String[] info = parseIp(ip);
        if (info.length >= 5) {
            return new String[]{info[2], info[3]}; // 省份、城市
        }
        return new String[]{"", ""};
    }
}