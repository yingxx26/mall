package com.macro.mall;


import cn.hutool.json.JSONUtil;
import com.macro.mall.dao.PmsMemberPriceDao;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsMemberPrice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmsDaoTests {
    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsProductDao productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsDaoTests.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @Transactional
    @Rollback
    public void testInsertBatch() {
        List<PmsMemberPrice> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PmsMemberPrice memberPrice = new PmsMemberPrice();
            memberPrice.setProductId(1L);
            memberPrice.setMemberLevelId((long) (i + 1));
            memberPrice.setMemberPrice(new BigDecimal("22"));
            list.add(memberPrice);
        }
        int count = memberPriceDao.insertList(list);
        Assert.assertEquals(5, count);
    }

    @Test
    public void testGetProductUpdateInfo() {
        PmsProductResult productResult = productDao.getUpdateInfo(7L);
        String json = JSONUtil.parse(productResult).toString();
        LOGGER.info(json);
    }

    @Test
    public void testredisHash() {
        //redisTemplate.boundHashOps("testhash");
        //redisTemplate.opsForHash().put("testhash", "1", 1);
        //redisTemplate.opsForHash().put("testhash", "2", 2);
        Object testhash = redisTemplate.opsForHash().get("testhash", "1");
        Set<Object> testhash1 = redisTemplate.opsForHash().keys("testhash");
        List<Object> testhash2 = redisTemplate.opsForHash().values("testhash");
        Map<Object, Object> testhash3 = redisTemplate.boundHashOps("testhash").entries();
        Set<Object> testhash4 = redisTemplate.boundHashOps("testhash").keys();
        List<Object> testhash5 = redisTemplate.boundHashOps("testhash").values();
        redisTemplate.boundHashOps("testhash").get("2");
        LOGGER.info("");
    }


    @Test
    public void testredisList() {

        redisTemplate.opsForList().leftPush("testlist", 1);
        redisTemplate.opsForList().leftPush("testlist", 2);
        redisTemplate.opsForList().leftPush("testlist", 3);
        redisTemplate.opsForList().leftPush("testlist", 4);
        //Object rightPop = redisTemplate.opsForList().rightPop("testlist");
        //Object rightPop1 = redisTemplate.boundListOps("testlist").rightPop();
        LOGGER.info("");
    }

}
