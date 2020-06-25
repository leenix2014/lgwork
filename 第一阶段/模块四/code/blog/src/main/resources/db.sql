CREATE DATABASE blog_system;

DROP TABLE IF EXISTS t_article;
CREATE TABLE t_article (
id INT(11) NOT NULL AUTO_INCREMENT,
title VARCHAR(50) NOT NULL COMMENT '文章标题',
content LONGTEXT COMMENT '文章具体内容',
created DATE NOT NULL COMMENT '发表时间',
modified DATE DEFAULT NULL COMMENT '修改时间',
categories VARCHAR(200) DEFAULT '默认分类'COMMENT '文章分类',
tags VARCHAR(200) DEFAULT NULL COMMENT '文章标签',
allow_comment TINYINT (1) NOT NULL DEFAULT '1' COMMENT '是否允许评论',
thumbnail VARCHAR(200) DEFAULT NULL COMMENT '文章缩略图',
PRIMARY KEY (id)
);
-- Records of t_article
INSERT INTO t_article VALUES ('1', '2019新版Java学习路线图','Java学习路线图具体内容
具体内容具体内容具体内容具体内容具体内容具体内容','2019-10-10', NULL,'默认分类',
'‘2019,Java,学习路线图','1', NULL);
INSERT INTO t_article VALUES ('2', '2019 新版 Python 学习线路图','据悉,Python 已经入驻
小学生教材,未来不学Python不仅知识会脱节,可能与小朋友都没有了共同话题~~所以,从今天起不要再
找借口,不要再说想学Python却没有资源,赶快行动起来,Python等你来探索','2019-10-10',
NULL,'默认分类','‘2019,Java,学习路线图','1', NULL);
INSERT INTO t_article VALUES ('3', 'JDK 8 —Lambda 表达式介绍','Lambda 表达式是 JDK
8中一个重要的新特性,它使用一个清晰简洁的表达式来表达一个接口,同时Lambda表达式也简化了对集合
以及数组数据的遍历、过滤和提取等操作。下面,本篇文章就对Lambda表达式进行简要介绍,并进行演示
说明','2019-10-10', NULL,'默认分类','‘2019,Java,学习路线图','1', NULL);
INSERT INTO t_article VALUES ( '4' , '函数式接口 ','虽然Lambda表达式可以实现匿名内部类的
功能,但在使用时却有一个局限,即接口中有且只有一个抽象方法时才能使用Lamdba表达式代替匿名内部
类。这是因为Lamdba表达式是基于函数式接口实现的,所谓函数式接口是指有且仅有一个抽象方法的接
口,Lambda表达式就是Java中函数式编程的体现,只有确保接口中有且仅有一个抽象方法,Lambda表达
式才能顺利地推导出所实现的这个接口中的方法','2019-10-10', NULL,'默认分类',
'‘2019,Java,学习路线图','1', NULL);
INSERT INTO t_article VALUES ( '5' , '虚拟化容器技术一Docker运行机制介绍','Docker是一
个开源的应用容器引擎,它基于go语言开发,并遵从Apache2.0开源协议。使用Docker可以让开发者封装
他们的应用以及依赖包到一个可移植的容器中,然后发布到任意的Linux机器上,也可以实现虚拟化。
Docker容器完全使用沙箱机制,相互之间不会有任何接口,这保证了容器之间的安全性','2019-10-10', NULL,'默认分类','‘2019,Java,学习路线图','1', NULL);

