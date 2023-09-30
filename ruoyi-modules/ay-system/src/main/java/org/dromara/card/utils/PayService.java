package org.dromara.card.utils;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.card.domain.vo.PayStatusVo;
import org.dromara.card.domain.vo.PayVo;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.ServletUtils;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

/**
 * 支付服务
 * @author Ay
 * 创建时间 2023-07-29
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class PayService {
    private static final Integer PID = 3145;


    public R<PayStatusVo> toPay(String url, PayVo payVo){
        PayStatusVo payStatusVo = new PayStatusVo();
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("pid",PID);
        map.put("type",payVo.getType());
        map.put("out_trade_no", IdUtil.fastSimpleUUID());
//        https://83831.cn/payok.html
        map.put("notify_url","http://localhost:8080/pay/checkpaystatus");
        map.put("return_url","http://localhost/payok");
        map.put("name",payVo.getName());
        map.put("money",payVo.getMoney());
        map.put("clientip", ServletUtils.getClientIP());
        map.put("param",payVo.getParam());
        String sign = Sign.getSign(map);
        map.put("sign", sign);
        map.put("sign_type","MD5");
//        log.info(JSONUtil.toJsonStr(map));
        HttpResponse httpResponse = HttpUtils.sendPost(url, map);
        JSONObject entries = JSONUtil.parseObj(httpResponse.body());
        Integer code = Convert.toInt(entries.get("code"));
        String tradeNo = Convert.toStr(entries.get("trade_no"));
        String payurl = Convert.toStr(entries.get("payurl"));
        payStatusVo.setCode(code);
        payStatusVo.setTrade_no(tradeNo);
        payStatusVo.setPayurl(payurl);
        if (code == 1){
            return R.ok(payStatusVo);
        }else {
            return R.fail("支付失败");
        }
    }
}
