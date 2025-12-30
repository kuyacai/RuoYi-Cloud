-- 商品库独立数据库
CREATE DATABASE IF NOT EXISTS cloud_products CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cloud_products;
--USE ruoyi_cloud;

-- 1. 云商品根表
-- goods-status: normal  正常在售;blacklist   黑名单;infringement 侵权下架;recall      召回;discontinued 停产
-- 
CREATE TABLE IF NOT EXISTS goods (
    goods_id            CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '商品ID',
    migrate_source      VARCHAR(64) COMMENT '迁移来源',
    source_shop_name    VARCHAR(128) COMMENT '来源店铺名称',
    source_id           VARCHAR(64) COMMENT '来源商品ID',
    source_title        VARCHAR(255) COMMENT '来源商品标题',
    source_url          TEXT COMMENT '来源商品链接',
    source_category     VARCHAR(255) COMMENT '来源商品类目',
    customer_phone      VARCHAR(32) COMMENT '客户手机号',
    source_item_no      VARCHAR(64) COMMENT '来源商品货号',
    brand               VARCHAR(128) COMMENT '品牌',
    shop_product_id     VARCHAR(64) COMMENT '本店商品ID',
    shop_id             VARCHAR(32) COMMENT '店铺ID',
    goods_status        VARCHAR(20) COMMENT '商品状态',
    gmt_create          DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='云商品根表';

-- 2. 商品版本
-- rev_status: frozen 冻结; editing 编辑中; approving 审核中; approved 已审核; discarded 已废弃
-- revision_type: sync 同步; manual 手动
CREATE TABLE IF NOT EXISTS goods_revision (
    revision_id         CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '版本ID',
    goods_id            CHAR(32) NOT NULL COMMENT '商品ID',
    rev_status          VARCHAR(32) COMMENT '版本状态',
    revision_type       VARCHAR(32) COMMENT '版本类型',
    gmt_create          DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='商品版本';

-- 3. SPU 快照（整行覆盖，无字段级补丁）
-- spu_status: active 上架; inactive 下架; deleted 删除
-- copy_status: pending 待复制; in_progress 复制中; done 已复制; failed 复制失败
-- review_status: pending 待审核; approved 审核通过; rejected 审核拒绝
-- 这三个字段均来自同步源平台，仅用于记录。
CREATE TABLE IF NOT EXISTS goods_revision_spu (
    revision_id                 CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '版本ID',
    title                       VARCHAR(255) COMMENT '商品标题',
    guide_short_title           VARCHAR(255) COMMENT '导购短标题',
    new_title                   VARCHAR(255) COMMENT '商品新标题',
    new_guide_short_title       VARCHAR(255) COMMENT '导购新短标题',
    recommendation              TEXT COMMENT '推荐语',
    freight_template            VARCHAR(128) COMMENT '运费模板',
    attributes                  TEXT COMMENT '属性',
    size_chart_template_name    VARCHAR(128) COMMENT '尺码表模板名称',
    size_chart_size_titles      TEXT COMMENT '尺码表尺码标题',
    sales                       BIGINT COMMENT '销量',
    copy_status                 VARCHAR(20) COMMENT '复制状态',
    copy_error_reason           TEXT COMMENT '复制失败原因',
    review_status               VARCHAR(20) COMMENT '审核状态',
    shipping_mode               VARCHAR(200) COMMENT '发货模式',
    search_keywords             TEXT COMMENT '搜索关键词',
    video_script                TEXT COMMENT '视频脚本',
    in_stock_ship_time          VARCHAR(32) COMMENT '现货发货时间',
    presale_ship_time           VARCHAR(32) COMMENT '预售发货时间',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='SPU 快照';

-- 4. goods 图片
-- image_type: main/main34/white/guide/detail/spec
CREATE TABLE IF NOT EXISTS goods_revision_image (
    image_id                    CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '图片ID',
    revision_id                 CHAR(32) NOT NULL COMMENT '版本ID',
    goods_id                    CHAR(32) NOT NULL COMMENT '商品ID',
    goods_sku_id                CHAR(32) COMMENT 'SKU ID',
    image_type                  VARCHAR(32) NOT NULL COMMENT '图片类型',
    source_url                  TEXT NOT NULL COMMENT '来源URL',
    self_url                    TEXT COMMENT '自建URL',
    local_uri                   TEXT COMMENT '本地URI',
    position                    INT NOT NULL COMMENT '排序位置',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='goods 图片';

-- 4. SKU 快照
-- sku_status: nomal 可上架; deleted 已删除; inactive 不可上架
CREATE TABLE IF NOT EXISTS goods_revision_item (
    item_id                     CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'SKU ID',
    revision_id                 CHAR(32) NOT NULL COMMENT '版本ID',
    shop_id                     VARCHAR(64) NOT NULL COMMENT '店铺ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    shop_sku_id                 VARCHAR(64) NOT NULL COMMENT '店铺SKU ID',
    sku_code                    VARCHAR(255) NOT NULL COMMENT 'SKU商家编码',
    seller_sku                  VARCHAR(255) COMMENT '商家SKU',
    spec1                       VARCHAR(255) COMMENT '规格1',
    spec1_note                  VARCHAR(255) COMMENT '规格1备注',
    spec2                       VARCHAR(255) COMMENT '规格2',
    spec2_note                  VARCHAR(255) COMMENT '规格2备注',
    spec3_or_lead_time          VARCHAR(255) COMMENT '规格3或前置时间',
    spec3_note                  VARCHAR(255) COMMENT '规格3备注',
    in_stock_qty                INT COMMENT '现货数量',
    full_prepay_qty             INT COMMENT '全款预付数量',
    ship_3d_qty                 INT COMMENT '3天发货数量',
    ship_4d_qty                 INT COMMENT '4天发货数量',
    ship_5d_qty                 INT COMMENT '5天发货数量',
    ship_7d_qty                 INT COMMENT '7天发货数量',
    ship_10d_qty                INT COMMENT '10天发货数量',
    ship_15d_qty                INT COMMENT '15天发货数量',
    ship_20d_qty                INT COMMENT '20天发货数量',
    ship_25d_qty                INT COMMENT '25天发货数量',
    ship_30d_qty                INT COMMENT '30天发货数量',
    ship_35d_qty                INT COMMENT '35天发货数量',
    ship_45d_qty                INT COMMENT '45天发货数量',
    orignial_price              BIGINT COMMENT '原价（分）',
    market_price                BIGINT COMMENT '市场价（分）',
    lowest_price                BIGINT COMMENT '最低价（分）',
    highest_price               BIGINT COMMENT '最高价（分）',
    sku_status                  VARCHAR(32) COMMENT 'SKU状态',
    barcode                     VARCHAR(64) COMMENT '条形码',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='SKU 快照';

-- 5. 店铺
-- shop_status: normal 正常; suspended 暂停; closed 关闭
CREATE TABLE IF NOT EXISTS shop (
    shop_id                     CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '店铺ID',
    shop_name                   VARCHAR(128) NOT NULL COMMENT '店铺名称',
    platform                    VARCHAR(32) NOT NULL COMMENT '平台',
    owner_id                    VARCHAR(32) NOT NULL COMMENT '所有者ID',
    shop_status                 VARCHAR(20) NOT NULL COMMENT '店铺状态',
    shop_description            TEXT COMMENT '店铺描述',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='店铺表';

-- 6. 店铺 SPU 级上架开关
-- curr_status: active 上架; inactive 下架; draft 草稿；reviewing 审核中；inactive 可上架;deleted 删除
CREATE TABLE IF NOT EXISTS shop_listing_spu (
    listing_spu_id              CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '上架SPU ID',
    revision_id                 CHAR(32) NOT NULL COMMENT '版本ID',
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    category                    VARCHAR(255) COMMENT '类目',
    product_url                 TEXT COMMENT '商品链接',
    curr_status                 VARCHAR(20) NOT NULL COMMENT '当前状态',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='店铺商品同步状态表';

-- 7. 店铺 SKU 级二次定价/库存
-- sku_status: active 上架; inactive 下架; deleted 删除
CREATE TABLE IF NOT EXISTS shop_listing_sku (
    listing_sku_id              CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '上架SKU ID',
    listing_spu_id              CHAR(32) NOT NULL COMMENT '上架SPU ID',
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    shop_sku_id                 VARCHAR(64) NOT NULL COMMENT '店铺SKU ID',
    item_id                     CHAR(32) NOT NULL COMMENT 'SKU ID',
    market_price                BIGINT COMMENT '市场价（分）',
    channel_stock               INT NOT NULL COMMENT '渠道库存',
    sku_status                  VARCHAR(20) NOT NULL COMMENT 'SKU状态',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='店铺商品SKU同步状态表';

-- 9. 任务实例
-- task_code: edit_title/edit_image/edit_attribute/edit_price/edit_stock/sync_listing/sync_inventory
-- task_status: pending/done/
CREATE TABLE IF NOT EXISTS item_task (
    task_id                     CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '任务ID',
    task_code                   VARCHAR(64) NOT NULL COMMENT '任务代码',
    biz_id                      VARCHAR(64) NOT NULL COMMENT '业务ID(revision_id)',
    task_status                 VARCHAR(20) NOT NULL COMMENT '任务状态',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='任务实例表';

-- 任务计数器表（保持与业务解耦）
CREATE TABLE IF NOT EXISTS task_counter (
    biz_id                      VARCHAR(64) PRIMARY KEY COMMENT '业务ID',
    total_tasks                 INT NOT NULL DEFAULT 0 COMMENT '总任务数',
    completed_tasks             INT NOT NULL DEFAULT 0 COMMENT '已完成任务数',
    last_check_time             DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后检查时间',
    notified_status             VARCHAR(20) DEFAULT 'pending' COMMENT '通知状态: pending/notified',
    notified_time               DATETIME(3) NULL COMMENT '通知时间',
    retry_count                 INT DEFAULT 0 COMMENT '重试次数',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
    INDEX idx_check_time (last_check_time)
) ENGINE = InnoDB COMMENT ='任务计数器表';

-- 价格参考表
CREATE TABLE IF NOT EXISTS price_reference (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    original_price              BIGINT COMMENT '原价',
    multiplier                  BIGINT COMMENT '倍数',
    original_marked_price       BIGINT COMMENT '原始标价',
    effective_marked_price      BIGINT COMMENT '生效标价',
    reference_shipping_fee      BIGINT COMMENT '参考运费',
    actual_discount_amount      BIGINT COMMENT '实际立减金额',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='价格参考表';

-- 定价倍数表
CREATE TABLE IF NOT EXISTS pricing_multiplier (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    price_min                   BIGINT COMMENT '最低价格',
    price_max                   BIGINT COMMENT '最高价格',
    multiplier                  BIGINT COMMENT '倍数',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='定价倍数表';

-- 单品直降配置表
CREATE TABLE IF NOT EXISTS direct_discount (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    price_min                   BIGINT COMMENT '最低价格',
    price_max                   BIGINT COMMENT '最高价格',
    fixed_price                 BIGINT COMMENT '一口价',
    deduction_amount            BIGINT COMMENT '抵扣金额',
    discount_rate               BIGINT COMMENT '折扣率',
    actual_discount_amount      BIGINT COMMENT '实际折扣金额',
    config_status               VARCHAR(20) COMMENT '折扣状态',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='单品直降配置表';

-- 单品直降活动表
-- discount_type: fixed_price 一口价; direct_deduction 立减; discount 折扣
-- discount_status: draft 草稿; active 进行中; ended 已结束
CREATE TABLE IF NOT EXISTS direct_discount_activity (
    activity_id                 CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '活动ID',
    activity_name               VARCHAR(200) NOT NULL COMMENT '活动名称',
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    discount_type               VARCHAR(50) NOT NULL COMMENT '折扣类型',  
    start_time                  DATETIME(3) COMMENT '开始时间',
    end_time                    DATETIME(3) COMMENT '结束时间',
    discount_status             VARCHAR(20) DEFAULT 'draft' COMMENT '折扣状态', 
    platform_activity_id        VARCHAR(100) COMMENT '平台活动ID',          
    notes                       TEXT COMMENT '备注',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='单品直降活动表';

-- 单品直降活动商品表
CREATE TABLE IF NOT EXISTS direct_discount_product (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    activity_id                 CHAR(32) NOT NULL COMMENT '活动ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    shop_id                     VARCHAR(64) NOT NULL COMMENT '店铺ID',    
    shop_sku_id                 VARCHAR(64) NOT NULL COMMENT '店铺SKU ID',
    fixed_price                 BIGINT COMMENT '一口价金额（分）',                
    deduction_amount            BIGINT COMMENT '立减金额（分）',              
    discount_rate               BIGINT COMMENT '折扣率（9000表示0.9）',               
    user_limit                  INTEGER DEFAULT 1 COMMENT '限购数量',      
    item_status                 VARCHAR(20) DEFAULT 'active' COMMENT '商品状态(active/removed)',  
    added_time                  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '添加时间',
    removed_time                DATETIME(3) COMMENT '移除时间',
    platform_sync_status        VARCHAR(20) DEFAULT 'pending' COMMENT '平台同步状态(pending/synced/failed)',  
    platform_error_msg          TEXT COMMENT '平台错误信息',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='单品直降活动商品表';

-- 新用户礼包 ------
CREATE TABLE IF NOT EXISTS new_user_gift_config (
    config_id                   CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    config_name                 VARCHAR(64) COMMENT '配置名称',
    price_min                   BIGINT COMMENT '最低价格',
    price_max                   BIGINT COMMENT '最高价格',
    avg_amount                  BIGINT COMMENT '平均金额',
    max_gift_amount             BIGINT COMMENT '最大礼包金额',
    reference_amount_low        BIGINT COMMENT '参考金额下限',
    reference_amount_high       BIGINT COMMENT '参考金额上限',
    discount_rate               BIGINT COMMENT '折扣率',
    config_status               VARCHAR(20) COMMENT '配置状态',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='新用户礼包配置表';

CREATE TABLE IF NOT EXISTS new_user_gift_activity (
    activity_id                 CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '活动ID',
    activity_name               VARCHAR(200) NOT NULL COMMENT '活动名称',
    discount_type               VARCHAR(20) NOT NULL COMMENT '折扣类型(direct_deduction/discount)',   
    deduction_amount            BIGINT COMMENT '立减金额（分）',                
    discount_rate               BIGINT COMMENT '折扣率（9000表示0.9）',                
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    start_time                  DATETIME(3) COMMENT '开始时间',
    end_time                    DATETIME(3) COMMENT '结束时间',
    activity_status             VARCHAR(20) DEFAULT 'draft' COMMENT '活动状态(draft/active/ended)',  
    platform_activity_id        VARCHAR(100) COMMENT '平台活动ID',           
    notes                       TEXT COMMENT '备注',
    min_order_amount            BIGINT COMMENT '最低订单金额限制（分）',                
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='新用户礼包活动表';

CREATE TABLE IF NOT EXISTS new_user_gift_product (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    activity_id                 CHAR(32) NOT NULL COMMENT '活动ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    shop_id                     VARCHAR(64) NOT NULL COMMENT '店铺ID',
    avg_amount                  BIGINT COMMENT '平均金额',
    max_gift_amount             BIGINT COMMENT '最大礼包金额',
    item_status                 VARCHAR(20) DEFAULT 'active' COMMENT '商品状态(active/removed)',  
    added_time                  DATETIME(3) COMMENT '添加时间',
    removed_time                DATETIME(3) COMMENT '移除时间',
    platform_sync_status        VARCHAR(20) DEFAULT 'pending' COMMENT '平台同步状态(pending/synced/failed)',  
    platform_error_msg          TEXT COMMENT '平台错误信息',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='新用户礼包活动商品表';

-- 通用商品优惠券配置 ------
CREATE TABLE IF NOT EXISTS product_discount_config (
    config_id                   CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    config_name                 VARCHAR(100) COMMENT '配置名称',
    price_min                   BIGINT COMMENT '最低价格',
    price_max                   BIGINT COMMENT '最高价格',
    threshold_amount            BIGINT COMMENT '门槛金额',
    discount_amount             BIGINT COMMENT '折扣金额',
    discount_rate               BIGINT COMMENT '折扣率',
    discount_strength           BIGINT COMMENT '折扣强度',
    priority                    INTEGER COMMENT '优先级',
    recommended_quantity        INTEGER COMMENT '推荐数量',
    config_status               VARCHAR(20) COMMENT '折扣状态',
    discount_type               VARCHAR(20) COMMENT '折扣类型',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='商品优惠配置表';

CREATE TABLE IF NOT EXISTS product_discount_activity (
    activity_id                 CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '活动ID',
    activity_name               VARCHAR(200) NOT NULL COMMENT '活动名称',
    discount_type               VARCHAR(20) NOT NULL COMMENT '折扣类型(direct_deduction/discount)',   
    deduction_amount            BIGINT COMMENT '立减金额（分）',                
    discount_rate               BIGINT COMMENT '折扣率（9000表示0.9）',                
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    start_time                  DATETIME(3) COMMENT '开始时间',
    end_time                    DATETIME(3) COMMENT '结束时间',
    activity_status             VARCHAR(20) DEFAULT 'draft' COMMENT '活动状态(draft/active/ended)',  
    platform_activity_id        VARCHAR(100) COMMENT '平台活动ID',           
    user_limit                  INTEGER DEFAULT 1 COMMENT '每人限领次数（通常为1）',      
    notes                       TEXT COMMENT '备注',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='商品优惠活动表';

CREATE TABLE IF NOT EXISTS product_discount_product (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    activity_id                 CHAR(32) NOT NULL COMMENT '活动ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    shop_id                     VARCHAR(64) NOT NULL COMMENT '店铺ID',  
    item_status                 VARCHAR(20) DEFAULT 'active' COMMENT '商品状态(active/removed)',  
    added_time                  DATETIME(3) COMMENT '添加时间',
    removed_time                DATETIME(3) COMMENT '移除时间',
    platform_sync_status        VARCHAR(20) DEFAULT 'pending' COMMENT '平台同步状态(pending/synced/failed)',  
    platform_error_msg          TEXT COMMENT '平台错误信息',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT ='商品优惠活动商品表';

-- 复购券配置
CREATE TABLE IF NOT EXISTS repurchase_coupon_config (
    config_id                   CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    config_name                 VARCHAR(100) COMMENT '配置名称',
    price_min                   BIGINT COMMENT '最低价格',
    price_max                   BIGINT COMMENT '最高价格',
    threshold_amount            BIGINT COMMENT '门槛金额',
    discount_amount             BIGINT COMMENT '折扣金额',
    discount_rate               BIGINT COMMENT '折扣率',
    discount_strength           BIGINT COMMENT '折扣强度',
    priority                    INTEGER COMMENT '优先级',
    config_status               VARCHAR(20) COMMENT '折扣状态',
    discount_type               VARCHAR(20) COMMENT '折扣类型',
    recommended_quantity        INTEGER COMMENT '推荐数量',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT = '复购券配置表';

-- 复购券活动
CREATE TABLE IF NOT EXISTS repurchase_coupon_activity (
    activity_id                 CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '活动ID',
    activity_name               VARCHAR(200) NOT NULL COMMENT '活动名称',
    discount_type               VARCHAR(20) NOT NULL COMMENT '折扣类型(direct_deduction/discount)',   
    deduction_amount            BIGINT COMMENT '立减金额（分）',                
    discount_rate               BIGINT COMMENT '折扣率（9000表示0.9）',                
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    start_time                  DATETIME(3) COMMENT '开始时间',
    end_time                    DATETIME(3) COMMENT '结束时间',
    activity_status             VARCHAR(20) DEFAULT 'draft' COMMENT '活动状态(draft/active/ended)',  
    platform_activity_id        VARCHAR(100) COMMENT '平台活动ID',           
    notes                       TEXT COMMENT '备注',
    min_order_count             INTEGER DEFAULT 1 COMMENT '最低购买次数门槛',      
    user_limit                  INTEGER DEFAULT 1 COMMENT '用户限领次数',      
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT = '复购券活动表';

CREATE TABLE IF NOT EXISTS `repurchase_coupon_product` (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    activity_id                 CHAR(32) NOT NULL COMMENT '活动ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',
    shop_id                     VARCHAR(64) NOT NULL COMMENT '店铺ID',    
    item_status                 VARCHAR(20) DEFAULT 'active' COMMENT '商品状态(active/removed)',  
    added_time                  DATETIME(3) COMMENT '添加时间',
    removed_time                DATETIME(3) COMMENT '移除时间',
    platform_sync_status        VARCHAR(20) DEFAULT 'pending' COMMENT '平台同步状态(pending/synced/failed)',  
    platform_error_msg          TEXT COMMENT '平台错误信息',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT = '复购券活动商品表';

-- 平台促销配置
CREATE TABLE IF NOT EXISTS `platform_promotion_config` (
    config_id                   CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    config_name                 VARCHAR(64) NOT NULL COMMENT '配置名称', 
    price_min                   BIGINT COMMENT '最低价格',
    price_max                   BIGINT COMMENT '最高价格',
    threshold_amount            BIGINT COMMENT '门槛金额',
    discount_amount             BIGINT COMMENT '折扣金额',
    discount_rate               BIGINT COMMENT '折扣率',
    discount_strength           BIGINT COMMENT '折扣强度',
    priority                    INTEGER COMMENT '优先级',
    config_status               VARCHAR(20) COMMENT '折扣状态',
    discount_type               VARCHAR(20) COMMENT '折扣类型',
    recommended_quantity        INTEGER COMMENT '推荐数量',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT = '平台促销配置表';

-- 平台促销活动
CREATE TABLE IF NOT EXISTS `platform_promotion_activity` (
    activity_id                 CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT '活动ID',
    activity_name               VARCHAR(200) NOT NULL COMMENT '活动名称',
    registration_price          BIGINT COMMENT '报名价（分）',                
    discount_type               VARCHAR(20) NOT NULL COMMENT '折扣类型(fixed_price/direct_deduction/discount)',   
    fixed_price                 BIGINT COMMENT '一口价金额（分）',                
    deduction_amount            BIGINT COMMENT '立减金额（分）',                
    discount_rate               BIGINT COMMENT '折扣率（9000表示0.9）',                
    platform_type               VARCHAR(20) DEFAULT 'douyin' COMMENT '平台类型',
    event_code                  VARCHAR(100) COMMENT '平台大促活动编码',           
    shop_id                     CHAR(32) NOT NULL COMMENT '店铺ID',
    start_time                  DATETIME(3) COMMENT '开始时间',
    end_time                    DATETIME(3) COMMENT '结束时间',
    discount_status             VARCHAR(20) DEFAULT 'draft' COMMENT '折扣状态(draft/active/ended)',  
    platform_activity_id        VARCHAR(100) COMMENT '平台活动ID',           
    notes                       TEXT COMMENT '备注',
    apply_deadline              DATETIME(3) COMMENT '报名截止时间',           
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT = '平台促销活动表';

-- 平台促销活动商品
CREATE TABLE IF NOT EXISTS `platform_promotion_product` (
    id                          CHAR(32) PRIMARY KEY DEFAULT (REPLACE(UUID(), '-', '')) COMMENT 'ID',
    activity_id                 CHAR(32) NOT NULL COMMENT '活动ID',
    shop_product_id             VARCHAR(64) NOT NULL COMMENT '店铺商品ID',  
    shop_id                     VARCHAR(64) NOT NULL COMMENT '店铺ID',  
    item_status                 VARCHAR(20) DEFAULT 'active' COMMENT '商品状态(active/removed)',  
    added_time                  DATETIME(3) COMMENT '添加时间',
    removed_time                DATETIME(3) COMMENT '移除时间',
    platform_sync_status        VARCHAR(20) DEFAULT 'pending' COMMENT '平台同步状态(pending/synced/failed)',  
    platform_error_msg          TEXT COMMENT '平台错误信息',
    gmt_create                  DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    gmt_modified                DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间'
) ENGINE = InnoDB COMMENT = '平台促销活动商品表';

CREATE TABLE IF NOT EXISTS `async_task` (
  `task_id`             varchar(32)  NOT NULL PRIMARY KEY COMMENT 'ID',
  `task_code`           varchar(50)  NOT NULL COMMENT '任务编码',
  `task_name`           varchar(100) DEFAULT NULL COMMENT '任务名称',
  `file_name`           varchar(255) DEFAULT NULL COMMENT '文件名',
  `shop_id`             varchar(255) DEFAULT NULL COMMENT '店铺ID',
  `imported_file_url`   TEXT         DEFAULT NULL COMMENT '导入文件链接',
  `total`               int          DEFAULT 0 COMMENT '总数',
  `duplicate`           int          DEFAULT 0 COMMENT '重复数',
  `success`             int          DEFAULT 0 COMMENT '成功数',
  `skip`                int          DEFAULT 0 COMMENT '跳过数',
  `failure`             int          DEFAULT 0 COMMENT '失败数',
  `fail_file_url`       varchar(255) DEFAULT NULL COMMENT '失败文件URL',
  `exported_file_url`   TEXT         DEFAULT NULL COMMENT '导出文件链接',
  `task_status`         varchar(20)  DEFAULT 'init' COMMENT '任务状态',
  `finish_time`         datetime     DEFAULT NULL COMMENT '完成时间',
  `params_map`          TEXT         DEFAULT NULL COMMENT '附加参数',
  `gmt_create`          DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified`        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3), 
  INDEX idx_code_status (task_code, task_status),
  INDEX idx_create_time (gmt_create)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='异步任务表';