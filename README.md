部标808协议快速开发包
====================
# 项目介绍
* 基于Netty，实现JT/T 808部标协议的消息分发，与编码解码；
* 与Spring解耦合，协议编码解码和Netty服务均可独立运行（Android客户端同样适用）；
* SpringBoot 仅负责将协议暴露至Web接口，目的是方便测试，且为二次开发提供样例；
* 最简洁、清爽、易用的部标开发框架。

问题交流群：[906230542]

# 主要特性
* 代码足够精简，便于二次开发；
* 致敬Spring、Hibernate设计理念，熟悉Web开发的同学上手极快；
* 使用注解描述协议，告别繁琐的封包、解包；
* 支持2013、2019部标协议版本，支持分包请求；
* 支持异步批量处理，显著提升Netty和MySQL入库性能；
* 提供报文解释器（解析过程分析工具），编码解码不再抓瞎；
* 全覆盖的测试用例，稳定发版。

# 代码仓库
 * Gitee仓库地址：[https://gitee.com/yezhihao/jt808-server/tree/master](https://gitee.com/yezhihao/jt808-server/tree/master)
 * Github仓库地址：[https://github.com/yezhihao/jt808-server/tree/master](https://github.com/yezhihao/jt808-server/tree/master)

# 下载方式
 * Gitee下载命令：`git clone https://gitee.com/yezhihao/jt808-server -b master`
 * Github下载命令：`git clone https://github.com/yezhihao/jt808-server -b master`

# 使用说明

## 项目分为四部分：

## 1.framework，核心模块，不推荐修改，有BUG或扩展的需求，建议提交issues或联系作者
```sh
└── framework
    ├── codec 编码解码
    ├── mvc 消息分发、处理
    ├── orm 消息元数据的描述
    ├── session 消息和会话的管理
    └── netty 网络通信 
 ```
注解：

* @Endpoint，服务接入点，等价SpringMVC的 @Controller；
* @Mapping，定义消息ID，等价SpringMVC中 @RequestMapping；
* @AsyncBatch, 异步批量消息，对于并发较高的消息，如0x0200(位置信息汇报)，使用该注解，显著提升Netty和MySQL入库性能。


* @Message，协议类型，等价Hibernate的 @Table；
* @Field，属性定义，等价Hibernate的 @Column；
* @Fs，多版本协议支持

## 2.protocol 部标协议定义，不推荐做大量修改
```sh
└── protocol
    ├── basics 部标协议通用消息头，以及公共的消息定义
    ├── codec 部标编码解码工具
    ├── commons 部标协议ID，工具类等
    ├── t808 JT/T808协议定义（已完成）
    └── t1078 JT/T1078协议（待补充） 
 ```
 消息定义样例：
 ```java
@Message(JT808.定位数据批量上传)
public class T0704 extends AbstractMessage<Header> {

    private Integer total;
    private Integer type;
    private List<Item> items;

    @Field(index = 0, type = DataType.WORD, desc = "数据项个数")
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    @Field(index = 2, type = DataType.BYTE, desc = "位置数据类型 0：正常位置批量汇报，1：盲区补报")
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    @Field(index = 3, type = DataType.LIST, desc = "位置汇报数据项")
    public List<Item> getItems() { return items; }    
    public void setItems(List<Item> items) { this.items = items; this.total = items.size(); }
}
```

## 3.web 开箱即用的Demo，业务需求在这个包下开发，可随意修改
```sh
└── web
    ├── config spring 相关配置
    ├── component.mybatis 附赠极简的mybatis分页插件:D
    ├── endpoint 808消息入口，所有netty进入的请求都会根据@Mapping转发到此
    └── controller service mapper ... 不再赘述
 ```
##### 消息接入：
```java
@Endpoint
public class JT808Endpoint {

    @Autowired
    private LocationService locationService;
    
    @Autowired
    private DeviceService deviceService;

    //异步批量处理 队列大小20000 最大累积200处理一次 最大等待时间5秒
    @AsyncBatch(capacity = 20000, maxElements = 200, maxWait = 5000)
    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public void 位置信息汇报(List<T0200> list) {
        locationService.batchInsert(list);
    }

    @Async
    @Mapping(types = 终端注册, desc = "终端注册")
    public T8100 register(T0100 message, Session session) {
        Header header = message.getHeader();

        T8100 result = new T8100(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(header.getSerialNo());

        String token = deviceService.register(message);
        if (token != null) {
            session.register(header);

            result.setResultCode(T8100.Success);
            result.setToken(token);
        } else {

            result.setResultCode(T8100.NotFoundTerminal);
        }
        return result;
    }
}
```

##### 消息下发：
```java
@Controller
@RestController("terminal")
public class TerminalController {

    private MessageManager messageManager = MessageManager.getInstance();

    @ApiOperation("设置终端参数")
    @PostMapping("{terminalId}/parameters")
    public T0001 updateParameters(@PathVariable("terminalId") String terminalId, @RequestBody List<TerminalParameter> parameters) {
        T8103 request = new T8103(terminalId);
        request.setItems(parameters);
        T0001 response = messageManager.request(request, T0001.class);
        return response;
    }
}
```
##### 已集成Swagger文档，启动后可访问如下地址 
 
* Swagger UI：[http://127.0.0.1:8000/swagger-ui.html](http://127.0.0.1:8000/swagger-ui.html)
* Bootstrap UI：[http://127.0.0.1:8000/doc.html](http://127.0.0.1:8000/doc.html)
![Bootstrap UI](https://images.gitee.com/uploads/images/2020/0731/135035_43dfca8e_670717.png "doc2.png")

## 4.test 808协议全覆盖的测试用例，以及报文解释器

* QuickStart 不依赖Spring的启动，可用于Android客户端
* Beans 测试数据
* TestBeans 消息对象的封包解包
* TestHex 原始报文测试

* Elucidator 报文解释器 - 解码
* DarkRepulsor 报文解释器 - 编码

分析报文内每个属性所处的位置以及转换后的值，以便查询报文解析出错的原因

Elucidator 运行效果如下：
```
020000610123456789017fff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c05033737371105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e3101286b

0	0200	消息ID	512
2	0061	消息体属性	97
4	012345678901	终端手机号	12345678901
10	7fff	流水号	32767
12	0000	消息包总数	0
14	0400	包序号	1024
0	00000400	报警标志	1024
4	00000800	状态	2048
8	06eeb6ad	纬度	116307629
12	02633df7	经度	40058359
16	0138	海拔	312
18	0003	速度	3
20	0063	方向	99
22	200707192359	时间	200707192359
0	01	附加信息ID	1
1	04	附加信息长度	4
2	0000000b	参数值	[B@2a798d51
0	02	附加信息ID	2
1	02	附加信息长度	2
2	0016	参数值	[B@6d763516
0	03	附加信息ID	3
1	02	附加信息长度	2
2	0021	参数值	[B@52bf72b5
0	04	附加信息ID	4
1	02	附加信息长度	2
2	002c	参数值	[B@37afeb11
0	05	附加信息ID	5
1	03	附加信息长度	3
2	373737	参数值	[B@515aebb0
0	11	附加信息ID	17
1	05	附加信息长度	5
2	4200000042	参数值	[B@dd8ba08
0	12	附加信息ID	18
1	06	附加信息长度	6
2	4d0000004d4d	参数值	[B@245b4bdc
0	13	附加信息ID	19
1	07	附加信息长度	7
2	00000058005858	参数值	[B@6c64cb25
0	25	附加信息ID	37
1	04	附加信息长度	4
2	00000063	参数值	[B@6ae5aa72
0	2a	附加信息ID	42
1	02	附加信息长度	2
2	000a	参数值	[B@222545dc
0	2b	附加信息ID	43
1	04	附加信息长度	4
2	00000014	参数值	[B@5c5eefef
0	30	附加信息ID	48
1	01	附加信息长度	1
2	1e	参数值	[B@16293aa2
0	31	附加信息ID	49
1	01	附加信息长度	1
2	28	参数值	[B@5158b42f
28	01040000000b02020016030200210402002c05033737371105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e310128	位置附加信息	[BytesAttribute[id=1,length=4,value={0,0,0,11}], BytesAttribute[id=2,length=2,value={0,22}], BytesAttribute[id=3,length=2,value={0,33}], BytesAttribute[id=4,length=2,value={0,44}], BytesAttribute[id=5,length=3,value={55,55,55}], BytesAttribute[id=17,length=5,value={66,0,0,0,66}], BytesAttribute[id=18,length=6,value={77,0,0,0,77,77}], BytesAttribute[id=19,length=7,value={0,0,0,88,0,88,88}], BytesAttribute[id=37,length=4,value={0,0,0,99}], BytesAttribute[id=42,length=2,value={0,10}], BytesAttribute[id=43,length=4,value={0,0,0,20}], BytesAttribute[id=48,length=1,value={30}], BytesAttribute[id=49,length=1,value={40}]]
```

使用发包工具模拟请求
```
7e020000610123456789017fff000004000000080006eeb6ad02633df701380003006320070719235901040000000b02020016030200210402002c05033737371105420000004212064d0000004d4d1307000000580058582504000000632a02000a2b040000001430011e3101286b7e
```
![使用发包工具模拟请求](https://images.gitee.com/uploads/images/2019/0705/162745_9becaf08_670717.png)

项目会不定期进行更新，建议star和watch一份，您的支持是我最大的动力。

如有任何疑问或者BUG，请联系我，非常感谢。

技术交流QQ群：[906230542]
