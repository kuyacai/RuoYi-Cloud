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