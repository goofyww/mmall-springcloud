/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50173
Source Host           : 127.0.0.1:3306
Source Database       : mmall

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2019-07-26 00:39:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mmall_cart
-- ----------------------------
DROP TABLE IF EXISTS `mmall_cart`;
CREATE TABLE `mmall_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_cart
-- ----------------------------
INSERT INTO `mmall_cart` VALUES ('15', '13', '41', '1', '1', '2019-07-25 21:51:43', '2019-07-25 21:51:46');
INSERT INTO `mmall_cart` VALUES ('16', '13', '41', '1', '0', '2019-07-25 21:51:43', '2019-07-25 21:51:46');
INSERT INTO `mmall_cart` VALUES ('17', '13', '41', '1', '0', '2019-07-25 21:51:43', '2019-07-25 21:51:46');
INSERT INTO `mmall_cart` VALUES ('18', '13', '41', '1', '0', '2019-07-25 21:51:43', '2019-07-25 21:51:46');
INSERT INTO `mmall_cart` VALUES ('19', '13', '41', '1', '0', '2019-07-25 21:51:43', '2019-07-25 21:51:46');
INSERT INTO `mmall_cart` VALUES ('20', '13', '41', '1', '0', '2019-07-25 21:51:43', '2019-07-25 21:51:46');
INSERT INTO `mmall_cart` VALUES ('21', '13', '41', '1', '0', '2019-07-25 21:51:43', '2019-07-25 21:51:46');

-- ----------------------------
-- Table structure for mmall_category
-- ----------------------------
DROP TABLE IF EXISTS `mmall_category`;
CREATE TABLE `mmall_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常,0-已废弃',
  `sort_order` int(4) DEFAULT NULL COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100041 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_category
-- ----------------------------
INSERT INTO `mmall_category` VALUES ('100001', '0', '家用电器', '1', null, '2017-03-25 16:46:00', '2017-03-25 16:46:00');
INSERT INTO `mmall_category` VALUES ('100002', '0', '数码3C', '1', null, '2017-03-25 16:46:21', '2017-03-25 16:46:21');
INSERT INTO `mmall_category` VALUES ('100003', '0', '服装箱包', '1', null, '2017-03-25 16:49:53', '2017-03-25 16:49:53');
INSERT INTO `mmall_category` VALUES ('100004', '0', '食品生鲜', '1', null, '2017-03-25 16:50:19', '2017-03-25 16:50:19');
INSERT INTO `mmall_category` VALUES ('100005', '0', '酒水饮料', '1', null, '2017-03-25 16:50:29', '2017-03-25 16:50:29');
INSERT INTO `mmall_category` VALUES ('100006', '100001', '冰箱', '1', null, '2017-03-25 16:52:15', '2017-03-25 16:52:15');
INSERT INTO `mmall_category` VALUES ('100007', '100001', '电视', '1', null, '2017-03-25 16:52:26', '2017-03-25 16:52:26');
INSERT INTO `mmall_category` VALUES ('100008', '100001', '洗衣机', '1', null, '2017-03-25 16:52:39', '2017-03-25 16:52:39');
INSERT INTO `mmall_category` VALUES ('100009', '100001', '空调', '1', null, '2017-03-25 16:52:45', '2017-03-25 16:52:45');
INSERT INTO `mmall_category` VALUES ('100010', '100001', '电热水器', '1', null, '2017-03-25 16:52:54', '2017-03-25 16:52:54');
INSERT INTO `mmall_category` VALUES ('100011', '100002', '电脑', '1', null, '2017-03-25 16:53:18', '2017-03-25 16:53:18');
INSERT INTO `mmall_category` VALUES ('100012', '100002', '手机', '1', null, '2017-03-25 16:53:27', '2017-03-25 16:53:27');
INSERT INTO `mmall_category` VALUES ('100013', '100002', '平板电脑', '1', null, '2017-03-25 16:53:35', '2017-03-25 16:53:35');
INSERT INTO `mmall_category` VALUES ('100014', '100002', '数码相机', '1', null, '2017-03-25 16:53:56', '2017-03-25 16:53:56');
INSERT INTO `mmall_category` VALUES ('100015', '100002', '3C配件', '1', null, '2017-03-25 16:54:07', '2017-03-25 16:54:07');
INSERT INTO `mmall_category` VALUES ('100016', '100003', '女装', '1', null, '2017-03-25 16:54:44', '2017-03-25 16:54:44');
INSERT INTO `mmall_category` VALUES ('100017', '100003', '帽子', '1', null, '2017-03-25 16:54:51', '2017-03-25 16:54:51');
INSERT INTO `mmall_category` VALUES ('100018', '100003', '旅行箱', '1', null, '2017-03-25 16:55:02', '2017-03-25 16:55:02');
INSERT INTO `mmall_category` VALUES ('100019', '100003', '手提包', '1', null, '2017-03-25 16:55:09', '2017-03-25 16:55:09');
INSERT INTO `mmall_category` VALUES ('100020', '100003', '保暖内衣', '1', null, '2017-03-25 16:55:18', '2017-03-25 16:55:18');
INSERT INTO `mmall_category` VALUES ('100021', '100004', '零食', '1', null, '2017-03-25 16:55:30', '2017-03-25 16:55:30');
INSERT INTO `mmall_category` VALUES ('100022', '100004', '生鲜', '1', null, '2017-03-25 16:55:37', '2017-03-25 16:55:37');
INSERT INTO `mmall_category` VALUES ('100023', '100004', '半成品菜', '1', null, '2017-03-25 16:55:47', '2017-03-25 16:55:47');
INSERT INTO `mmall_category` VALUES ('100024', '100004', '速冻食品', '1', null, '2017-03-25 16:55:56', '2017-03-25 16:55:56');
INSERT INTO `mmall_category` VALUES ('100025', '100004', '进口食品', '1', null, '2017-03-25 16:56:06', '2017-03-25 16:56:06');
INSERT INTO `mmall_category` VALUES ('100026', '100005', '白酒', '1', null, '2017-03-25 16:56:22', '2017-03-25 16:56:22');
INSERT INTO `mmall_category` VALUES ('100027', '100005', '红酒', '1', null, '2017-03-25 16:56:30', '2017-03-25 16:56:30');
INSERT INTO `mmall_category` VALUES ('100028', '100005', '饮料', '1', null, '2017-03-25 16:56:37', '2017-03-25 16:56:37');
INSERT INTO `mmall_category` VALUES ('100029', '100005', '调制鸡尾酒', '1', null, '2017-03-25 16:56:45', '2017-03-25 16:56:45');
INSERT INTO `mmall_category` VALUES ('100030', '100005', '进口洋酒', '1', null, '2017-03-25 16:57:05', '2017-03-25 16:57:05');
INSERT INTO `mmall_category` VALUES ('100039', '100006', '新品类3', '0', null, '2018-04-29 18:50:24', '2018-04-29 18:58:07');
INSERT INTO `mmall_category` VALUES ('100040', '100006', '家人平安', '1', null, '2018-04-29 18:56:04', '2018-04-29 18:57:19');

-- ----------------------------
-- Table structure for mmall_order
-- ----------------------------
DROP TABLE IF EXISTS `mmall_order`;
CREATE TABLE `mmall_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `shipping_id` int(11) DEFAULT NULL,
  `payment` decimal(8,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `postage` int(10) DEFAULT NULL COMMENT '运费,单位是元',
  `status` int(10) DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_order
-- ----------------------------
INSERT INTO `mmall_order` VALUES ('103', '1491753014256', '1', '25', '13998.00', '1', '0', '0', null, null, null, null, '2017-04-09 23:50:14', '2017-04-09 23:50:14');
INSERT INTO `mmall_order` VALUES ('104', '1491830695216', '1', '26', '13998.00', '1', '0', '0', null, null, null, null, '2017-04-10 21:24:55', '2017-04-10 21:24:55');
INSERT INTO `mmall_order` VALUES ('105', '1492089528889', '1', '29', '3299.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:18:48', '2017-04-13 21:18:48');
INSERT INTO `mmall_order` VALUES ('106', '1492090946105', '1', '29', '1.00', '1', '0', '40', '2017-04-13 21:42:40', '2019-06-28 22:43:10', null, null, '2017-04-13 21:42:26', '2019-06-28 22:43:10');
INSERT INTO `mmall_order` VALUES ('107', '1492091003128', '1', '29', '8597.00', '1', '0', '40', '2017-04-13 21:43:38', '2018-05-11 23:25:36', null, null, '2017-04-13 21:43:23', '2018-05-11 23:25:36');
INSERT INTO `mmall_order` VALUES ('108', '1492091051313', '13', '29', '1999.00', '1', '0', '20', '2017-04-13 21:43:38', '2019-06-30 17:20:02', null, null, '2017-04-13 21:44:11', '2019-06-30 17:20:02');
INSERT INTO `mmall_order` VALUES ('109', '1492091061513', '13', '29', '6598.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:44:21', '2017-04-13 21:44:21');
INSERT INTO `mmall_order` VALUES ('110', '1492091069563', '1', '29', '3299.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:44:29', '2017-04-13 21:44:29');
INSERT INTO `mmall_order` VALUES ('111', '1492091076073', '1', '29', '4299.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:44:36', '2017-04-13 21:44:36');
INSERT INTO `mmall_order` VALUES ('112', '1492091083720', '1', '29', '3299.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:44:43', '2017-04-13 21:44:43');
INSERT INTO `mmall_order` VALUES ('113', '1492091089794', '1', '29', '6999.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:44:49', '2017-04-13 21:44:49');
INSERT INTO `mmall_order` VALUES ('114', '1492091096400', '1', '29', '1.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:44:56', '2017-04-13 21:44:56');
INSERT INTO `mmall_order` VALUES ('115', '1492091102371', '1', '29', '1.00', '1', '0', '0', null, null, null, null, '2017-04-13 21:45:02', '2017-04-13 21:45:02');
INSERT INTO `mmall_order` VALUES ('116', '1492091110004', '1', '29', '8598.00', '1', '0', '40', '2017-04-13 21:55:16', '2017-04-13 21:55:31', null, null, '2017-04-13 21:45:09', '2017-04-13 21:55:31');
INSERT INTO `mmall_order` VALUES ('117', '1492091141273', '1', '29', '3299.00', '1', '0', '40', '2018-05-07 20:58:20', '2018-05-11 23:23:01', null, null, '2017-04-13 21:45:41', '2018-05-11 23:23:01');
INSERT INTO `mmall_order` VALUES ('118', '1526047788706', '1', '29', '121.00', '1', '0', '0', null, null, null, null, '2018-05-11 22:09:48', '2018-05-11 22:09:48');
INSERT INTO `mmall_order` VALUES ('119', '1526048831302', '1', '29', '121.00', '1', '0', '0', null, null, null, null, '2018-05-11 22:27:11', '2018-05-11 22:27:11');
INSERT INTO `mmall_order` VALUES ('126', '1561911478063', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-01 00:17:58', '2019-07-01 00:17:58');
INSERT INTO `mmall_order` VALUES ('127', '1562480830335', '13', '7', '121.00', '1', '0', '40', null, '2019-07-07 14:50:02', null, null, '2019-07-07 14:27:10', '2019-07-07 14:50:02');
INSERT INTO `mmall_order` VALUES ('128', '1562483460895', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-07 15:11:01', '2019-07-07 15:11:01');
INSERT INTO `mmall_order` VALUES ('129', '1562483971785', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-07 15:19:31', '2019-07-07 15:19:31');
INSERT INTO `mmall_order` VALUES ('130', '1564027182056', '13', '7', '121.00', '1', '0', '0', null, null, null, null, '2019-07-25 11:59:42', '2019-07-25 11:59:42');
INSERT INTO `mmall_order` VALUES ('131', '1564027800149', '13', '7', '242.00', '1', '0', '0', null, null, null, null, '2019-07-25 12:10:00', '2019-07-25 12:10:00');
INSERT INTO `mmall_order` VALUES ('132', '1564040468619', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 15:41:08', '2019-07-25 15:41:08');
INSERT INTO `mmall_order` VALUES ('133', '1564041786841', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 16:03:06', '2019-07-25 16:03:06');
INSERT INTO `mmall_order` VALUES ('134', '1564042299577', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 16:11:39', '2019-07-25 16:11:39');
INSERT INTO `mmall_order` VALUES ('135', '1564042657841', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 16:17:38', '2019-07-25 16:17:38');
INSERT INTO `mmall_order` VALUES ('136', '1564042879513', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 16:21:19', '2019-07-25 16:21:19');
INSERT INTO `mmall_order` VALUES ('137', '1564043832548', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 16:37:12', '2019-07-25 16:37:12');
INSERT INTO `mmall_order` VALUES ('138', '1564045471775', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 17:04:31', '2019-07-25 17:04:31');
INSERT INTO `mmall_order` VALUES ('139', '1564045953119', '13', '7', '60.50', '1', '0', '0', null, null, null, null, '2019-07-25 17:12:33', '2019-07-25 17:12:33');

-- ----------------------------
-- Table structure for mmall_order_item
-- ----------------------------
DROP TABLE IF EXISTS `mmall_order_item`;
CREATE TABLE `mmall_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_order_item
-- ----------------------------
INSERT INTO `mmall_order_item` VALUES ('113', '1', '1491753014256', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '6999.00', '2', '13998.00', '2017-04-09 23:50:14', '2017-04-09 23:50:14');
INSERT INTO `mmall_order_item` VALUES ('114', '1', '1491830695216', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '6999.00', '2', '13998.00', '2017-04-10 21:24:55', '2017-04-10 21:24:55');
INSERT INTO `mmall_order_item` VALUES ('115', '1', '1492089528889', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '1', '3299.00', '2017-04-13 21:18:48', '2017-04-13 21:18:48');
INSERT INTO `mmall_order_item` VALUES ('116', '1', '1492090946105', '29', 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', '4299.00', '2', '8598.00', '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `mmall_order_item` VALUES ('117', '1', '1492090946105', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '1999.00', '1', '1999.00', '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `mmall_order_item` VALUES ('118', '1', '1492090946105', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '1', '3299.00', '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `mmall_order_item` VALUES ('119', '1', '1492090946105', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '6999.00', '2', '13998.00', '2017-04-13 21:42:26', '2017-04-13 21:42:26');
INSERT INTO `mmall_order_item` VALUES ('120', '1', '1492091003128', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '2', '6598.00', '2017-04-13 21:43:23', '2017-04-13 21:43:23');
INSERT INTO `mmall_order_item` VALUES ('121', '1', '1492091003128', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '1999.00', '1', '1999.00', '2017-04-13 21:43:23', '2017-04-13 21:43:23');
INSERT INTO `mmall_order_item` VALUES ('122', '1', '1492091051313', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '1999.00', '1', '1999.00', '2017-04-13 21:44:11', '2017-04-13 21:44:11');
INSERT INTO `mmall_order_item` VALUES ('123', '1', '1492091061513', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '2', '6598.00', '2017-04-13 21:44:21', '2017-04-13 21:44:21');
INSERT INTO `mmall_order_item` VALUES ('124', '1', '1492091069563', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '1', '3299.00', '2017-04-13 21:44:29', '2017-04-13 21:44:29');
INSERT INTO `mmall_order_item` VALUES ('125', '1', '1492091076073', '29', 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', '4299.00', '1', '4299.00', '2017-04-13 21:44:36', '2017-04-13 21:44:36');
INSERT INTO `mmall_order_item` VALUES ('126', '1', '1492091083720', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '1', '3299.00', '2017-04-13 21:44:43', '2017-04-13 21:44:43');
INSERT INTO `mmall_order_item` VALUES ('127', '1', '1492091089794', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '6999.00', '1', '6999.00', '2017-04-13 21:44:49', '2017-04-13 21:44:49');
INSERT INTO `mmall_order_item` VALUES ('128', '1', '1492091096400', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '2', '6598.00', '2017-04-13 21:44:56', '2017-04-13 21:44:56');
INSERT INTO `mmall_order_item` VALUES ('129', '1', '1492091102371', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '1', '3299.00', '2017-04-13 21:45:02', '2017-04-13 21:45:02');
INSERT INTO `mmall_order_item` VALUES ('130', '1', '1492091110004', '29', 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', '4299.00', '2', '8598.00', '2017-04-13 21:45:09', '2017-04-13 21:45:09');
INSERT INTO `mmall_order_item` VALUES ('132', '1', '1492091141273', '27', 'Midea/美的 BCD-535WKZM(E)冰箱双开门对开门风冷无霜智能电家用', 'ac3e571d-13ce-4fad-89e8-c92c2eccf536.jpeg', '3299.00', '1', '3299.00', '2017-04-13 21:45:41', '2017-04-13 21:45:41');
INSERT INTO `mmall_order_item` VALUES ('133', '1', '1526047788706', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2018-05-11 22:09:48', '2018-05-11 22:09:48');
INSERT INTO `mmall_order_item` VALUES ('134', '1', '1526047788706', '37', '白龙马', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2018-05-11 22:09:48', '2018-05-11 22:09:48');
INSERT INTO `mmall_order_item` VALUES ('135', '1', '1526048831302', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2018-05-11 22:27:11', '2018-05-11 22:27:11');
INSERT INTO `mmall_order_item` VALUES ('136', '1', '1526048831302', '37', '白龙马', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2018-05-11 22:27:11', '2018-05-11 22:27:11');
INSERT INTO `mmall_order_item` VALUES ('142', '13', '1561911478063', '37', '白龙马', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-01 00:17:58', '2019-07-01 00:17:58');
INSERT INTO `mmall_order_item` VALUES ('143', '13', '1562480830335', '37', '白龙马', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '2', '121.00', '2019-07-07 14:27:10', '2019-07-07 14:27:10');
INSERT INTO `mmall_order_item` VALUES ('144', '13', '1562483460895', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '60.50', '1', '60.50', '2019-07-07 15:11:04', '2019-07-07 15:11:04');
INSERT INTO `mmall_order_item` VALUES ('145', '13', '1562483971785', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '60.50', '1', '60.50', '2019-07-07 15:19:40', '2019-07-07 15:19:40');
INSERT INTO `mmall_order_item` VALUES ('146', '13', '1564027182056', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '60.50', '2', '121.00', '2019-07-25 12:00:09', '2019-07-25 12:00:09');
INSERT INTO `mmall_order_item` VALUES ('147', '13', '1564027800149', '27', '小米/小米 nova 小米除尘器A9/P10plus', '2bae4f79bf02.jpeg', '60.50', '4', '242.00', '2019-07-25 12:11:19', '2019-07-25 12:11:19');
INSERT INTO `mmall_order_item` VALUES ('148', '13', '1564040468619', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '2', '60.50', '2019-07-25 15:41:08', '2019-07-25 15:41:08');
INSERT INTO `mmall_order_item` VALUES ('149', '13', '1564041786841', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 16:03:06', '2019-07-25 16:03:06');
INSERT INTO `mmall_order_item` VALUES ('150', '13', '1564042299577', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 16:11:39', '2019-07-25 16:11:39');
INSERT INTO `mmall_order_item` VALUES ('151', '13', '1564042657841', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 16:17:38', '2019-07-25 16:17:38');
INSERT INTO `mmall_order_item` VALUES ('152', '13', '1564042879513', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 16:21:19', '2019-07-25 16:21:19');
INSERT INTO `mmall_order_item` VALUES ('153', '13', '1564043832548', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 16:37:12', '2019-07-25 16:37:12');
INSERT INTO `mmall_order_item` VALUES ('154', '13', '1564045471775', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 17:04:31', '2019-07-25 17:04:31');
INSERT INTO `mmall_order_item` VALUES ('155', '13', '1564045953119', '41', '魅族手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '60.50', '1', '60.50', '2019-07-25 17:12:33', '2019-07-25 17:12:33');

-- ----------------------------
-- Table structure for mmall_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `mmall_pay_info`;
CREATE TABLE `mmall_pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_pay_info
-- ----------------------------
INSERT INTO `mmall_pay_info` VALUES ('53', '1', '1492090946105', '1', '2017041321001004300200116250', 'WAIT_BUYER_PAY', '2017-04-13 21:42:33', '2017-04-13 21:42:33');
INSERT INTO `mmall_pay_info` VALUES ('54', '1', '1492090946105', '1', '2017041321001004300200116250', 'TRADE_SUCCESS', '2017-04-13 21:42:41', '2017-04-13 21:42:41');
INSERT INTO `mmall_pay_info` VALUES ('55', '1', '1492091003128', '1', '2017041321001004300200116251', 'WAIT_BUYER_PAY', '2017-04-13 21:43:31', '2017-04-13 21:43:31');
INSERT INTO `mmall_pay_info` VALUES ('56', '1', '1492091003128', '1', '2017041321001004300200116251', 'TRADE_SUCCESS', '2017-04-13 21:43:38', '2017-04-13 21:43:38');
INSERT INTO `mmall_pay_info` VALUES ('57', '1', '1492091141269', '1', '2017041321001004300200116252', 'WAIT_BUYER_PAY', '2017-04-13 21:45:59', '2017-04-13 21:45:59');
INSERT INTO `mmall_pay_info` VALUES ('58', '1', '1492091141269', '1', '2017041321001004300200116252', 'TRADE_SUCCESS', '2017-04-13 21:46:07', '2017-04-13 21:46:07');
INSERT INTO `mmall_pay_info` VALUES ('59', '1', '1492091110004', '1', '2017041321001004300200116396', 'WAIT_BUYER_PAY', '2017-04-13 21:55:08', '2017-04-13 21:55:08');
INSERT INTO `mmall_pay_info` VALUES ('60', '1', '1492091110004', '1', '2017041321001004300200116396', 'TRADE_SUCCESS', '2017-04-13 21:55:17', '2017-04-13 21:55:17');
INSERT INTO `mmall_pay_info` VALUES ('61', '1', '1492091141273', '1', '2018050721001004260200404481', 'TRADE_SUCCESS', '2018-05-07 20:58:22', '2018-05-07 20:58:22');

-- ----------------------------
-- Table structure for mmall_product
-- ----------------------------
DROP TABLE IF EXISTS `mmall_product`;
CREATE TABLE `mmall_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '分类id,对应mmall_category表的主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text COMMENT '图片地址,json格式,扩展用',
  `detail` text COMMENT '商品详情',
  `price` decimal(20,2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_product
-- ----------------------------
INSERT INTO `mmall_product` VALUES ('26', '100002', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', 'iPhone 7，现更以红色呈现。', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '<p><img alt=\"10000.jpg\" src=\"http://img.happymmall.com/00bce8d4-e9af-4c8d-b205-e6c75c7e252b.jpg\" width=\"790\" height=\"553\"><br></p><p><img alt=\"20000.jpg\" src=\"http://img.happymmall.com/4a70b4b4-01ee-46af-9468-31e67d0995b8.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"30000.jpg\" src=\"http://img.happymmall.com/0570e033-12d7-49b2-88f3-7a5d84157223.jpg\" width=\"790\" height=\"365\"><br></p><p><img alt=\"40000.jpg\" src=\"http://img.happymmall.com/50515c02-3255-44b9-a829-9e141a28c08a.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"50000.jpg\" src=\"http://img.happymmall.com/c138fc56-5843-4287-a029-91cf3732d034.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"60000.jpg\" src=\"http://img.happymmall.com/c92d1f8a-9827-453f-9d37-b10a3287e894.jpg\" width=\"790\" height=\"525\"><br></p><p><br></p><p><img alt=\"TB24p51hgFkpuFjSspnXXb4qFXa-1776456424.jpg\" src=\"http://img.happymmall.com/bb1511fc-3483-471f-80e5-c7c81fa5e1dd.jpg\" width=\"790\" height=\"375\"><br></p><p><br></p><p><img alt=\"shouhou.jpg\" src=\"http://img.happymmall.com/698e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9990', '1', null, '2019-06-09 13:06:31');
INSERT INTO `mmall_product` VALUES ('27', '100006', '小米/小米 nova 小米除尘器A9/P10plus', '小米除尘器A9/P10plus 加长款4999元', '2bae4f79bf02.jpeg', '2bae4f79bf02.jpeg', '<p><img alt=\"11TB2fKK3cl0kpuFjSsziXXa.oVXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/5c2d1c6d-9e09-48ce-bbdb-e833b42ff664.jpg\" width=\"790\" height=\"966\"><img alt=\"22TB2YP3AkEhnpuFjSZFpXXcpuXXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/9a10b877-818f-4a27-b6f7-62887f3fb39d.jpg\" width=\"790\" height=\"1344\"><img alt=\"33TB2Yyshk.hnpuFjSZFpXXcpuXXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/7d7fbd69-a3cb-4efe-8765-423bf8276e3e.jpg\" width=\"790\" height=\"700\"><img alt=\"TB2diyziB8kpuFjSspeXXc7IpXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/1d7160d2-9dba-422f-b2a0-e92847ba6ce9.jpg\" width=\"790\" height=\"393\"><br></p>', '60.50', '8077', '2', '2017-04-13 18:51:54', '2019-07-25 14:10:00');
INSERT INTO `mmall_product` VALUES ('28', '100012', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', 'NOVA青春版1999元', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg,13da2172-4445-4eb5-a13f-c5d4ede8458c.jpeg,58d5d4b7-58d4-4948-81b6-2bae4f79bf02.jpeg', '<p><img alt=\"11TB2fKK3cl0kpuFjSsziXXa.oVXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/5c2d1c6d-9e09-48ce-bbdb-e833b42ff664.jpg\" width=\"790\" height=\"966\"><img alt=\"22TB2YP3AkEhnpuFjSZFpXXcpuXXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/9a10b877-818f-4a27-b6f7-62887f3fb39d.jpg\" width=\"790\" height=\"1344\"><img alt=\"33TB2Yyshk.hnpuFjSZFpXXcpuXXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/7d7fbd69-a3cb-4efe-8765-423bf8276e3e.jpg\" width=\"790\" height=\"700\"><img alt=\"TB2diyziB8kpuFjSspeXXc7IpXa_!!1777180618.jpg\" src=\"http://img.happymmall.com/1d7160d2-9dba-422f-b2a0-e92847ba6ce9.jpg\" width=\"790\" height=\"393\"><br></p>', '60.50', '9992', '2', '2017-04-13 18:57:18', '2019-07-25 14:00:00');
INSERT INTO `mmall_product` VALUES ('36', '100006', '孙悟空', '白龙马蹄朝西', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '8e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9968', '1', '2018-04-30 01:22:39', '2018-05-01 23:14:56');
INSERT INTO `mmall_product` VALUES ('37', '100006', '白龙马', '白龙马蹄朝西', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '8e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9959', '1', '2018-04-30 23:59:52', '2019-07-07 14:27:11');
INSERT INTO `mmall_product` VALUES ('38', '100006', '苹果手机', '乔布斯', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '8e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9968', '1', '2018-05-01 00:00:17', '2018-05-01 23:15:02');
INSERT INTO `mmall_product` VALUES ('39', '100006', '小米手机', '乔布斯', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '8e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9968', '1', '2018-05-01 00:00:25', '2018-05-01 00:00:25');
INSERT INTO `mmall_product` VALUES ('40', '100006', '摩托罗拉手机', '少林英雄', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '8e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9968', '1', '2018-05-01 00:00:52', '2018-05-01 00:00:52');
INSERT INTO `mmall_product` VALUES ('41', '100006', '魅族手机', '雷军', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '8e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '60.50', '9971', '1', '2018-05-01 00:01:08', '2019-07-25 19:49:55');
INSERT INTO `mmall_product` VALUES ('42', '100006', '可口可乐汽水饮料', '可口可乐爽歪歪饮用天然水', '241997c4-9e62-4824-b7f0-7425c3c28918.jpeg', 'cc4a98c529bf.jpeg', 'height=\"351\"><br></p>', '60.50', '8000', '1', '2019-06-28 16:54:21', '2019-07-07 13:46:38');

-- ----------------------------
-- Table structure for mmall_shipping
-- ----------------------------
DROP TABLE IF EXISTS `mmall_shipping`;
CREATE TABLE `mmall_shipping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_shipping
-- ----------------------------
INSERT INTO `mmall_shipping` VALUES ('4', '13', 'geely', '13800138003', '13800138002', '河北', '张家口市', '宣化区', '中关村街道', '075543', '2017-01-22 14:26:25', '2019-07-07 12:09:38');
INSERT INTO `mmall_shipping` VALUES ('7', '17', 'Rosen', '13800138000', '13800138000', '北京', '北京', null, '中关村', '100000', '2017-03-29 12:11:01', '2017-03-29 12:11:01');
INSERT INTO `mmall_shipping` VALUES ('31', '13', 'goofy', '18600227708', '18600227708', '河北', '张家口市', '赤城县', '田家窑镇西水泉村', '075543', '2019-07-07 11:34:17', '2019-07-07 11:34:17');
INSERT INTO `mmall_shipping` VALUES ('32', '13', 'goofy', '18600227708', '18600227708', '河北', '张家口市', '赤城县', '田家窑镇西水泉村', '075543', '2019-07-07 12:55:18', '2019-07-07 12:55:18');

-- ----------------------------
-- Table structure for mmall_user
-- ----------------------------
DROP TABLE IF EXISTS `mmall_user`;
CREATE TABLE `mmall_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmall_user
-- ----------------------------
INSERT INTO `mmall_user` VALUES ('1', 'admin', 'A8A12A766726D60B0E748372A25A9EDF', 'admin@happymmall.com', '13800138000', '问题', '答案', '1', '2016-11-06 16:56:45', '2019-04-26 04:52:23');
INSERT INTO `mmall_user` VALUES ('13', 'geely', 'A8A12A766726D60B0E748372A25A9EDF', 'akindofattitude@qq.com', '18600227708', '问题', '答案', '0', '2016-11-19 22:19:25', '2016-11-19 22:19:25');
INSERT INTO `mmall_user` VALUES ('17', 'rosen', '095AC193FE2212EEC7A93E8FEFF11902', 'rosen1@happymmall.com', '13800138000', '问题', '答案', '0', '2017-03-17 10:51:33', '2017-04-09 23:13:26');
INSERT INTO `mmall_user` VALUES ('21', 'soonerbetter', 'DE6D76FE7C40D5A1A8F04213F2BEFBEE', 'test06@happymmall.com', '13800138000', '105204', '105204', '0', '2017-04-13 21:26:22', '2017-04-13 21:26:22');
INSERT INTO `mmall_user` VALUES ('28', 'Goofy', 'CA6851C8882F1E435FE7B724E14626BD', 'Goofy@162.com', '13800138000', '这是一个新的问题', '这是一个新的答案', '0', '2018-04-28 22:46:13', '2019-07-07 10:35:53');
INSERT INTO `mmall_user` VALUES ('29', '小王子', 'A8A12A766726D60B0E748372A25A9EDF', 'adkinof@163.com', '1233567', '文艺', '答案', '0', '2019-06-09 01:29:58', '2019-06-09 01:29:58');
INSERT INTO `mmall_user` VALUES ('30', '一坨屎', 'A8A12A766726D60B0E748372A25A9EDF', 'adkinofd@163.com', '1233567', '文艺', '答案', '0', '2019-06-28 16:41:04', '2019-06-30 14:31:44');
INSERT INTO `mmall_user` VALUES ('31', '凉拖是', 'A8A12A766726D60B0E748372A25A9EDF', 'adkinofda@163.com', '1233567', '文艺', '答案', '0', '2019-06-30 14:16:31', '2019-06-30 14:16:31');
INSERT INTO `mmall_user` VALUES ('32', '凉凉两', 'F1F895D4F5219BF99C3CE1BFF78C7778', 'adkinofdaa@163.com', '1233567', '文艺', '答案', '0', '2019-06-30 16:12:14', '2019-06-30 16:12:14');
INSERT INTO `mmall_user` VALUES ('33', '凉凉', 'F1F895D4F5219BF99C3CE1BFF78C7778', 'adkinofdaaa@163.com', '1233567', '文艺', '答案', '0', '2019-07-07 10:23:47', '2019-07-07 10:23:47');
