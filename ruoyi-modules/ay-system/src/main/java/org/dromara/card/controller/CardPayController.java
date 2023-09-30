package org.dromara.card.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.card.domain.bo.CardCarmiBo;
import org.dromara.card.domain.vo.CardCarmiVo;
import org.dromara.card.domain.vo.PayStatusVo;
import org.dromara.card.domain.vo.PayVo;
import org.dromara.card.service.ICardCarmiService;
import org.dromara.card.utils.PayService;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.ServletUtils;
import org.dromara.common.core.utils.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 对接易支付接口
 * @author Ay
 * 创建时间 2023-07-29
 */
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pay")
public class CardPayController {

    private static final String URL = "https://83831.cn/mapi.php";


    private final PayService payService;

    private final ICardCarmiService cardCarmiService;

    @PostMapping("/topay")
    @SaIgnore
    public R<PayStatusVo> PayCarmi(@RequestBody PayVo payVo) {
        R<PayStatusVo> PayStatusVo = payService.toPay(URL, payVo);
        String tradeNo = PayStatusVo.getData().getTrade_no();
        CardCarmiVo oneCarmi = cardCarmiService.getOneCarmi(payVo.getParam());
        oneCarmi.setOutTradeNo(tradeNo);
        CardCarmiBo cardCarmiBo = new CardCarmiBo();
        BeanUtil.copyProperties(oneCarmi,cardCarmiBo);
        cardCarmiService.updateByBo(cardCarmiBo);
        return PayStatusVo;
    }

    @GetMapping("/checkpaystatus")
    @SaIgnore
    public R<CardCarmiVo> returnSuccess(){
        String tradeStatus = ServletUtils.getParameter("trade_status");
        String tradeNo = ServletUtils.getParameter("trade_no");
//        log.info(tradeStatus);
//        log.info(name);
        if (StringUtils.isNotEmpty(tradeStatus) && tradeStatus.equals("TRADE_SUCCESS")){
            CardCarmiVo cardCarmiVo = cardCarmiService.getCarmiByTradeNo(tradeNo);
            if (cardCarmiVo != null){
                return R.ok(cardCarmiVo);
            }else {
                return R.fail("校验订单失败，请输入正确的订单号！");
            }
        }else {
            throw new ServiceException("校验订单失败，请输入正确的订单号！");
        }
    }
}
