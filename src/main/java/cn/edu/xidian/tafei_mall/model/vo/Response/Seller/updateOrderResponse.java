package cn.edu.xidian.tafei_mall.model.vo.Response.Seller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class updateOrderResponse {
    private final String status;

    public updateOrderResponse(String status) {
        this.status = status;
    }
}
