package org.dromara.card.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.secure.BCrypt;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.card.domain.bo.CardUserBo;
import org.dromara.card.domain.bo.UserCarmiBo;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.card.service.ICardUserService;
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
 * 用户信息
 *
 * @author ay
 * @date 2023-07-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/card/user")
public class CardUserController extends BaseController {

    private final ICardUserService cardUserService;

    /**
     * 查询用户信息列表
     */
    @SaCheckPermission("card:user:list")
    @GetMapping("/list")
    public TableDataInfo<CardUserVo> list(CardUserBo bo, PageQuery pageQuery) {
        return cardUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户信息列表
     */
    @SaCheckPermission("card:user:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CardUserBo bo, HttpServletResponse response) {
        List<CardUserVo> list = cardUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户信息", CardUserVo.class, response);
    }

    /**
     * 获取用户信息详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("card:user:query")
    @GetMapping("/{userId}")
    public R<CardUserVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(cardUserService.queryById(userId));
    }

    /**
     * 新增用户信息
     */
    @SaCheckPermission("card:user:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CardUserBo bo) {
        return toAjax(cardUserService.insertByBo(bo));
    }

    /**
     * 修改用户信息
     */
    @SaCheckPermission("card:user:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CardUserBo bo) {
        return toAjax(cardUserService.updateByBo(bo));
    }

    /**
     * 修改用户状态
     * @param card
     * @return
     */
    @SaCheckPermission("card:user:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody CardUserBo card){
        return toAjax(cardUserService.updateUserStatus(card.getUserId(),card.getStatus()));
    }

    /**
     * 用户续费
     * @return
     */
//    @SaCheckPermission("card:user:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @SaIgnore
    @PutMapping("/expire")
    public R<CardUserVo> usereExpire(@Validated(EditGroup.class) @RequestBody UserCarmiBo userCarmiBo){
        return R.ok(cardUserService.expireByUserName(userCarmiBo.getUserName(), userCarmiBo.getCardKey()));
    }

    /**
     * 用户密码重置
     * @param user
     * @return
     */
    @SaCheckPermission("card:user:resetPwd")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public R<Void> resetPwd(@RequestBody CardUserBo user){
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return toAjax(cardUserService.resetUserPwd(user.getUserId(),user.getPassword()));
    }

    /**
     * 删除用户信息
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("card:user:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(cardUserService.deleteWithValidByIds(List.of(userIds), true));
    }
}
