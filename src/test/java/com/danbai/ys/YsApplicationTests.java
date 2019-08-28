package com.danbai.ys;

import com.danbai.ys.entity.Ysb;
import com.danbai.ys.mapper.YsbMapper;
import com.danbai.ys.utils.SendEmail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YsApplication.class)
public class YsApplicationTests {

    @Autowired
	private YsbMapper ysbMapper;
	@Test
	public void contextLoads() {
    }
	@Test
	public void testSelect() {
		List<Ysb> tbUsers = ysbMapper.selectAll();
		for (Ysb tbUser : tbUsers) {
			System.out.println(tbUser.getPm());
		}
	}
	@Test
	public void page(){
		// PageHelper 使用非常简单，只需要设置页码和每页显示笔数即可
		PageHelper.offsetPage(1, 10);
		// 设置分页查询条件
		Example example = new Example(Ysb.class);
		PageInfo<Ysb> pageInfo = new PageInfo<>(ysbMapper.selectByExample(example));
		List<Ysb> list1 = pageInfo.getList();
		System.out.println(list1.get(0).getPm());
	}
	@Test
	public void selectYsByPm() {
		String pm="爱情";
		Example example =new Example(Ysb.class);
		example.createCriteria().andLike("pm","%爱情%");
		List<Ysb> ysbs = ysbMapper.selectByExample(example);
		System.out.println(ysbs.size());
	}
	@Test
	public void md5(){
		String encodeStr= DigestUtils.md5DigestAsHex("hjj20010906".getBytes());
		System.out.println("加密后的字符串:"+encodeStr);

	}
	@Test
	public void senEmail(){
		SendEmail.send("2671641895@qq.com","66666666666");
	}
}
