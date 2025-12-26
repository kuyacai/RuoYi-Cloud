-- 云商品库ID (MySQL变量赋值语法)

-- 1. 设置父菜单ID（假设@cloud_goods这个变量已被正确赋值）
SET @cloud_goods = 2000;

-- 2. 插入主菜单（关键：component 必须正确！）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('云商品列表', @cloud_goods, '1', 'goods', 'product/goods/index', 1, 0, 'C', '0', '0', 'product:goods:list', '#', 'admin', sysdate(), '', null, '云商品列表菜单');

-- 3. 获取上一条插入的自增ID，用于后续按钮菜单
SELECT @parentId := LAST_INSERT_ID();

-- 4. 插入按钮菜单（perms 前缀也应同步修改为 'product:goods:*'）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) values
('云商品查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'product:goods:query', '#', 'admin', sysdate(), '', null, ''),
('云商品新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'product:goods:add', '#', 'admin', sysdate(), '', null, ''),
('云商品修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'product:goods:edit', '#', 'admin', sysdate(), '', null, ''),
('云商品删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'product:goods:remove', '#', 'admin', sysdate(), '', null, ''),
('云商品导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'product:goods:export', '#', 'admin', sysdate(), '', null, '');

-- SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('导入导出数据', @cloud_goods, '1', 'import', 'product/import/index', 1, 0, 'C', '0', '0', 'product:import:list', '#', 'admin', sysdate(), '', null, '导入导出数据菜单');




-- 插入店铺数据
INSERT INTO shop (shop_id, shop_name, platform, owner_id, shop_status, shop_description, gmt_create, gmt_modified)
VALUES 
('001', '大吴百货优品小店', '抖店', 'wurenping', 'normal', NULL, NOW(3), NOW(3)),
('002', '三台王哥好物公社', '抖店', 'wurenping', 'normal', NULL, NOW(3), NOW(3)),
('003', '沃零听精品市集', '抖店', 'wurenping', 'normal', NULL, NOW(3), NOW(3));

-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('异步任务', @cloud_goods, '1', 'asynctask', 'product/asynctask/index', 1, 0, 'C', '0', '0', 'product:asynctask:list', '#', 'admin', sysdate(), '', null, '异步任务菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('异步任务查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:asynctask:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('异步任务新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:asynctask:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('异步任务修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:asynctask:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('异步任务删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:asynctask:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('异步任务导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:asynctask:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
SET @cloud_goods = 2000;
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动', @cloud_goods, '1', 'singleDiscountActivity', 'product/singleDiscountActivity/index', 1, 0, 'C', '0', '0', 'product:singleDiscountActivity:list', '#', 'admin', sysdate(), '', null, '单品直降活动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountActivity:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountActivity:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountActivity:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountActivity:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountActivity:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降商品', @cloud_goods, '1', 'singleDiscountProduct', 'product/singleDiscountProduct/index', 1, 0, 'C', '0', '0', 'product:product:list', '#', 'admin', sysdate(), '', null, '单品直降活动商品菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动商品查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountProduct:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动商品新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountProduct:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动商品修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountProduct:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动商品删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountProduct:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('单品直降活动商品导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:singleDiscountProduct:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('店铺', @cloud_goods, '1', 'shop', 'product/shop/index', 1, 0, 'C', '0', '0', 'product:shop:list', '#', 'admin', sysdate(), '', null, '店铺菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('店铺查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:shop:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('店铺新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:shop:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('店铺修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:shop:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('店铺删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:shop:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('店铺导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:shop:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动', @cloud_goods, '1', 'repurchase', 'product/repurchaseActivity/index', 1, 0, 'C', '0', '0', 'product:repurchaseActivity:list', '#', 'admin', sysdate(), '', null, '复购券活动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseActivity:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseActivity:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseActivity:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseActivity:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseActivity:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券商品', @cloud_goods, '1', 'repurchaseProduct', 'product/repurchaseProduct/index', 1, 0, 'C', '0', '0', 'product:repurchaseProduct:list', '#', 'admin', sysdate(), '', null, '复购券活动商品菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动商品查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseProduct:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动商品新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseProduct:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动商品修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseProduct:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动商品删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseProduct:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('复购券活动商品导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:repurchaseProduct:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动', @cloud_goods, '1', 'productDiscountActivity', 'product/productDiscountActivity/index', 1, 0, 'C', '0', '0', 'product:productDiscountActivity:list', '#', 'admin', sysdate(), '', null, '商品优惠活动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountActivity:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountActivity:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountActivity:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountActivity:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountActivity:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('优惠券商品', @cloud_goods, '1', 'productDiscountProduct', 'product/productDiscountProduct/index', 1, 0, 'C', '0', '0', 'product:productDiscountProduct:list', '#', 'admin', sysdate(), '', null, '商品优惠活动商品菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动商品查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountProduct:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动商品新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountProduct:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动商品修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountProduct:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动商品删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountProduct:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品优惠活动商品导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:productDiscountProduct:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台大促活动', @cloud_goods, '1', 'platformPromotionActivity', 'product/platformPromotionActivity/index', 1, 0, 'C', '0', '0', 'product:platformPromotionActivity:list', '#', 'admin', sysdate(), '', null, '平台促销活动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionActivity:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionActivity:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionActivity:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionActivity:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionActivity:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台大促商品', @cloud_goods, '1', 'platformPromotionProduct', 'product/platformPromotionProduct/index', 1, 0, 'C', '0', '0', 'product:platformPromotionProduct:list', '#', 'admin', sysdate(), '', null, '平台促销活动商品菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动商品查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionProduct:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动商品新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionProduct:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动商品修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionProduct:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动商品删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionProduct:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('平台促销活动商品导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:platformPromotionProduct:export',       '#', 'admin', sysdate(), '', null, '');



-- 菜单 SQL
SET @cloud_goods = 2000;
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动', @cloud_goods, '1', 'newUserGiftActivity', 'product/newUserGiftActivity/index', 1, 0, 'C', '0', '0', 'product:newUserGiftActivity:list', '#', 'admin', sysdate(), '', null, '新用户礼包活动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftActivity:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftActivity:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftActivity:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftActivity:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftActivity:export',       '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包商品', @cloud_goods, '1', 'newUserGiftProduct', 'product/newUserGiftProduct/index', 1, 0, 'C', '0', '0', 'product:newUserGiftProduct:list', '#', 'admin', sysdate(), '', null, '新用户礼包活动商品菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动商品查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftProduct:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动商品新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftProduct:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动商品修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftProduct:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动商品删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftProduct:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('新用户礼包活动商品导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:newUserGiftProduct:export',       '#', 'admin', sysdate(), '', null, '');