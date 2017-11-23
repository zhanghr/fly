package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/25.
 */
public class ContentComplaintLogTest extends AbstractManageTest {
    @Override
    protected String service() {
        return "contentComplaintHistory";
    }

    @Override
    protected Map params() {
//        "id", "type","limit","page"
        return new HashMap(){{
            put("id","5975c2112d2436ba64b7e6a9");
            put("type","POST");
            put("limit",10);
            put("page",1);
        }};
    }
}
