package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.Product;
import cn.edu.xidian.tafei_mall.mapper.ProductMapper;
import cn.edu.xidian.tafei_mall.model.vo.ProductVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Seller.getProductResponse;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 *
 * @since 2025-03-17
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Map<String, Object> searchProducts(String keyword, int page, int limit) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        // 如果 keyword 不是空，则执行模糊查询
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.like( Product::getName, keyword);
        }

        // 分页查询
        Page<Product> productPage = new Page<>(page, limit);
        Page<Product> resultPage = productMapper.selectPage(productPage, queryWrapper);

        // 构造返回数据
        List<Product> products = resultPage.getRecords();
        long total = resultPage.getTotal();

        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("results", products);
        return response;
    }

    /**
     * 根据 ID 获取商品详情
     * @param productId 商品ID
     * @return 商品信息
     */
    @Override
    public Optional<Product> getProductById(String productId) {
        return Optional.ofNullable(productMapper.selectById(productId));
    }


    /**
     * 添加商品
     * @param productVO 商品信息
     * @return 商品ID
     */
    @Override
    public String addProduct(ProductVO productVO, String userId) {
        UUID uuid = UUID.randomUUID();
        Product product = BeanUtil.toBean(productVO, Product.class);
        product.setProductId(uuid.toString());
        product.setSellerId(userId);
        productMapper.insert(product);
//        save(product);
        return uuid.toString();
    }

    /**
     * 更新商品
     *
     * @param productVO 商品信息
     * @param productId 商品ID
     * @param userId    用户ID
     * @return 是否更新成功
     */
    @Override
    public Product updateProduct(ProductVO productVO, String productId, String userId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return null;
        }
        BeanUtil.copyProperties(productVO, product);
        productMapper.updateById(product);
        return product;
    }

    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @param userId    用户ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteProduct(String productId, String userId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return false;
        }
        productMapper.deleteById(productId);
        return true;
    }

    /**
     * 获取商品列表
     *
     * @param userId 用户ID
     * @return 商品列表
     */
    @Override
    public getProductResponse getProduct(String userId) {
        List<Product> products = productMapper.selectList(new LambdaQueryWrapper<Product>().eq(Product::getSellerId, userId));
        return new getProductResponse(products);
    }
}
