-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2020-07-02 10:38:58
-- 服务器版本： 5.5.62-log
-- PHP 版本： 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `ys`
--

-- --------------------------------------------------------

--
-- 表的结构 `config`
--

CREATE TABLE `config` (
  `Item` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设置项名字',
  `value` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `config`
--

INSERT INTO `config` (`Item`, `value`) VALUES
('ad', ''),
('appGG', '欢迎使用淡白影视.'),
('dmcache', 'no'),
('footer', '<script>\r\n(function(){\r\n    var bp = document.createElement(\'script\');\r\n    var curProtocol = window.location.protocol.split(\':\')[0];\r\n    if (curProtocol === \'https\') {\r\n        bp.src = \'https://zz.bdstatic.com/linksubmit/push.js\';\r\n    }\r\n    else {\r\n        bp.src = \'http://push.zhanzhang.baidu.com/push.js\';\r\n    }\r\n    var s = document.getElementsByTagName(\"script\")[0];\r\n    s.parentNode.insertBefore(bp, s);\r\n})();\r\n</script>\r\n'),
('gg', '欢迎使用淡白影视!<a href=\"http://cdn.p00q.cn/ys/app/%E6%B7%A1%E7%99%BD%E5%BD%B1%E8%A7%86Flutter.apk\">app下载</a>\r\n<a href=\"https://github.com/Programming-With-Love/dbys\">本网站开源地址</a> '),
('head', ''),
('ylink', '[\r\n{name:\"淡白的博客\",url:\"https://p00q.cn\"},{name:\"MVCAT\",url:\"http://www.mvcat.com\"},\r\n{name:\"站点地图\",url:\"https://dbys.vip/sitemap.xml\"},{name:\"临时云\",url:\"http://tempyun.com\"},{name:\"V聚焦\",url:\"http://www.vjujiao.com\"},{name:\"LOL坑逼网\",url:\"http://lolkb.top\"},{name:\"樱花动漫\",url:\"https://www.52xnet.cn\"},{name:\"舔狗日记\",url:\"http://tiangou.p00q.cn/\"},{name:\"在线H5壁纸\",url:\"http://wallpaper.p00q.cn\"}\r\n]');

-- --------------------------------------------------------

--
-- 表的结构 `feedback`
--

CREATE TABLE `feedback` (
  `id` int(128) NOT NULL,
  `type` int(128) NOT NULL,
  `content` text NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dispose` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='反馈表';

-- --------------------------------------------------------

--
-- 表的结构 `tvb`
--

CREATE TABLE `tvb` (
  `id` int(16) NOT NULL COMMENT 'id',
  `name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '频道名',
  `url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '源路径',
  `tpurl` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'http://img.p00q.cn:222/2019/11/04/4bf3fa0e9ae80.png' COMMENT '图片',
  `dmid` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '弹幕id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播源表';

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `user_type` int(11) NOT NULL DEFAULT '1' COMMENT '用户类型 0 禁用 1普通 2 管理员',
  `password` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `email` varchar(128) COLLATE utf8_bin NOT NULL,
  `headurl` text COLLATE utf8_bin NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `video_time`
--

CREATE TABLE `video_time` (
  `id` bigint(20) NOT NULL,
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `ysid` int(11) NOT NULL,
  `ysjiname` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '影视id',
  `time` float NOT NULL COMMENT '观看时间',
  `gktime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `ysb`
--

CREATE TABLE `ysb` (
  `id` int(11) NOT NULL DEFAULT '0',
  `pm` text NOT NULL,
  `tp` text,
  `zt` text,
  `pf` float DEFAULT '0',
  `bm` text,
  `dy` text,
  `zy` text,
  `lx` text,
  `dq` text,
  `yy` text,
  `sytime` text,
  `pctime` text,
  `gxtime` text,
  `js` text,
  `gkdz` longblob,
  `xzdz` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转储表的索引
--

--
-- 表的索引 `config`
--
ALTER TABLE `config`
  ADD UNIQUE KEY `Item_2` (`Item`),
  ADD KEY `Item` (`Item`);

--
-- 表的索引 `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `tvb`
--
ALTER TABLE `tvb`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_2` (`id`),
  ADD KEY `id` (`id`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 表的索引 `video_time`
--
ALTER TABLE `video_time`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`);

--
-- 表的索引 `ysb`
--
ALTER TABLE `ysb`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(128) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `tvb`
--
ALTER TABLE `tvb`
  MODIFY `id` int(16) NOT NULL AUTO_INCREMENT COMMENT 'id';

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id';

--
-- 使用表AUTO_INCREMENT `video_time`
--
ALTER TABLE `video_time`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
