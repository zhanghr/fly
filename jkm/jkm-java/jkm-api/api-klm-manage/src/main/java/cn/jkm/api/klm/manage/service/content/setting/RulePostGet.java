package cn.jkm.api.klm.manage.service.content.setting;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.service.content.RulePostService;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.setting
 * @description //发帖规则保存
 * @update 2017/7/26 16:47
 */
@Component("rulePostGet1.0")
public class RulePostGet extends AbstractManageService {

    @Reference(version = "1.0")
    RulePostService postService;

    @Override
    public Map service(Map request) {

            Map<String,Object> data = postService.getInfo();
            return ApiResponse.success().body(data);
    }
}
