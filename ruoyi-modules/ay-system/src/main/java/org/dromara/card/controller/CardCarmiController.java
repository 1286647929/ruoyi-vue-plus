package org.dromara.card.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.card.domain.bo.CardCarmiBo;
import org.dromara.card.domain.vo.CardCarmiVo;
import org.dromara.card.service.ICardCarmiService;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 卡密
 *
 * @author ay
 * @date 2023-07-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/card/carmi")
public class CardCarmiController extends BaseController {

    private final ICardCarmiService cardCarmiService;

    /**
     * 查询卡密列表
     */
    @SaCheckPermission("card:carmi:list")
    @GetMapping("/list")
    public TableDataInfo<CardCarmiVo> list(CardCarmiBo bo, PageQuery pageQuery) {
        return cardCarmiService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出卡密列表
     */
    @SaCheckPermission("card:carmi:export")
    @Log(title = "卡密", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CardCarmiBo bo, HttpServletResponse response) {
        List<CardCarmiVo> list = cardCarmiService.queryList(bo);
        ExcelUtil.exportExcel(list, "卡密", CardCarmiVo.class, response);
    }

    /**
     * 获取卡密详细信息
     *
     * @param cardId 主键
     */
    @SaCheckPermission("card:carmi:query")
    @GetMapping("/{cardId}")
    public R<CardCarmiVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long cardId) {
        return R.ok(cardCarmiService.queryById(cardId));
    }

    /**
     * 新增卡密
     */
    @SaCheckPermission("card:carmi:add")
    @Log(title = "卡密", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CardCarmiBo bo) {
        return toAjax(cardCarmiService.insertBatch(bo));
    }

    /**
     * 修改卡密
     */
    @SaCheckPermission("card:carmi:edit")
    @Log(title = "卡密", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CardCarmiBo bo) {
        return toAjax(cardCarmiService.updateByBo(bo));
    }

    /**
     * 删除卡密
     *
     * @param cardIds 主键串
     */
    @SaCheckPermission("card:carmi:remove")
    @Log(title = "卡密", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cardIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] cardIds) {
        return toAjax(cardCarmiService.deleteWithValidByIds(List.of(cardIds), true));
    }
}
