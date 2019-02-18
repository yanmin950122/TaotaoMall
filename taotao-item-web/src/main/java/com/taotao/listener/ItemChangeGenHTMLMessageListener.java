package com.taotao.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
/**
 * 监听器
 * 获取消息
 * 执行生成静态页面的业务逻辑
 * ClassName: ItemChangeGenHTMLMessageListener <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2019年1月6日 下午8:33:08 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
public class ItemChangeGenHTMLMessageListener implements MessageListener {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer config;
	
	@Override
	public void onMessage(Message message) {
		
		if(message instanceof TextMessage) {
			//1.获取消息	商品Id
			TextMessage message2 = (TextMessage)message;
			try {
				Long itemId = Long.valueOf(message2.getText());
				//2.从数据库中获取数据 可以调用manager中的服务	获取到了数据库
					//引入服务
					//注入服务
					//调用
				TbItem tbItem = itemService.getItemById(itemId);
				Item item = new Item(tbItem);//转换成在页面中显示数据时的POJO
				TbItemDesc itemDesc = itemService.getItemDescById(itemId);
				//3.生成静态页面	准备好模板
				genHtmlFreeMarker(item, itemDesc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 生成静态页面
	 * genHtmlFreeMarker:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param item
	 * @param itemDesc 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 * @since JDK 1.8
	 */
	private void genHtmlFreeMarker(Item item, TbItemDesc itemDesc) throws Exception {
		//1.获取configuration对象
		Configuration configuration = config.getConfiguration();
		//2.创建模板	获取模板文件对象
		Template template = configuration.getTemplate("item.ftl");
		//3.创建数据集
		Map model = new HashMap<>();
		model.put("item", item);
		model.put("itemDesc", itemDesc);
		//4.输出
		//G:/server/freemarker/item/1234.html
		Writer writer = new FileWriter(new File("G:/server/freemarker/item/"+item.getId()+".html"));
		template.process(model, writer);
		//5.关闭流
		writer.close();
	}

}
