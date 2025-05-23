package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import cn.edu.xidian.tafei_mall.model.vo.CartItemUpdateVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物车项表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface CartItemService extends IService<CartItem> {
    void updateCartItem(String itemId, CartItemUpdateVO cartItemUpdateVO);
    void deleteCartItem(String itemId);
}
