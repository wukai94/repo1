package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public String findCategory() {
        Jedis jedis = JedisUtil.getJedis();
        String json_category = jedis.get("category");
        if(json_category==null || json_category.length()==0){
            try {
                List<Category> list=categoryDao.findCategory();
                ObjectMapper mapper=new ObjectMapper();
                json_category = mapper.writeValueAsString(list);
                jedis.set("category",json_category);
            } catch (JsonProcessingException e) {
            }
        }
        return json_category;
    }
}
