-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2019-11-23 12:37:09
-- 服务器版本： 5.6.46-log
-- PHP Version: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ys`
--

-- --------------------------------------------------------

--
-- 表的结构 `config`
--

CREATE TABLE IF NOT EXISTS `config` (
  `Item` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设置项名字',
  `value` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `tvb`
--

CREATE TABLE IF NOT EXISTS `tvb` (
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

CREATE TABLE IF NOT EXISTS `user` (
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

CREATE TABLE IF NOT EXISTS `video_time` (
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

CREATE TABLE IF NOT EXISTS `ysb` (
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
  `gkdz` text,
  `xzdz` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `config`
--
ALTER TABLE `config`
  ADD UNIQUE KEY `Item_2` (`Item`),
  ADD KEY `Item` (`Item`);

--
-- Indexes for table `tvb`
--
ALTER TABLE `tvb`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_2` (`id`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_2` (`id`);

--
-- Indexes for table `ysb`
--
ALTER TABLE `ysb`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_2` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tvb`
--
ALTER TABLE `tvb`
  MODIFY `id` int(16) NOT NULL AUTO_INCREMENT COMMENT 'id';
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
