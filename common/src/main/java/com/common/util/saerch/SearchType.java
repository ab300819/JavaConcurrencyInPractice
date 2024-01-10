package com.common.util.saerch;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 搜索业务类型
 *
 * @author boreas
 */
@AllArgsConstructor
@Getter
public enum SearchType {
    /**
     * 产品相关搜索
     */
    RELATED_PRODUCT("biomart", "biomart_releated_web", "searchMLT"),

    /**
     * 商家后台产品搜索
     */
    AGENCY_MANAGE_PRODUCT("biomart", "biomart_web", "searchManage"),
    /**
     * 生物学霸「找抗体」
     */
    XUEBA_ANTIBODY("antibody", "antibody_web", "searchBioXueBa"),
    /**
     * 资讯文章搜索
     */
    NEWS_ARTICLE("biomart_article", "biomart_information_web", "searchInformation"),
    /**
     * 实验文章搜索
     */
    EXPERIMENT_ARTICLE("biomart_article", "biomart_experiment_web", null),
    /**
     * 实验搜索
     */
    EXPERIMENT("experiment", "experiment_web", null),

    /**
     * 实验相关词搜索
     */
    EXPERIMENT_KEYWORD("biomart_experiment_keyword", "experiment_web", null),

    /**
     * 产品搜索
     */
    PRODUCT("biomart", "biomart_web", null),

    /**
     * ivd产品搜索
     */
    IVD_PRODUCT("biomart", "biomart_ivd_wechat", "searchIVD"),
    /**
     * 产品类目列表
     */
    PRODUCT_CATEGORY_SEARCH("biomart", "biomart", "searchCategory"),
    /**
     * 提供给丁香搜索的产品搜索接口
     */
    PRODUCT_FOR_DXY_SEARCH("biomart", "biomart_product_web", "searchProduct"),
    /**
     * 搜索词联想
     */
    KEYWORD("biomart_his", "biomart_his_web", null),
    /**
     * BBS 广告搜索
     */
    BBS("biomart_jute", "biomart_jute_web", null),
    /**
     * 丁香通 seo 关键词相关搜索
     */
    PRODUCT_SEO_KEYWORD("jobmd_keywords", "biomart_web", "searchBiomartKeywords"),
    /**
     * 品牌频道页 产品搜索
     */
    BRAND_AGENCY_PRODUCT("biomart", "biomart_web", "searchBrand"),
    /**
     * 品牌搜索，按品牌group 产品
     */
    BRAND_SEARCH("biomart", "biomart_web", "searchBrandGroup"),

    /**
     * 品牌主页搜索产品，按商家group 产品
     */
    BRAND_AGENCY_PRODUCT_GROUP("biomart", "biomart_web", "searchBrandAgency"),

    /**
     * 丁香通 SEO 商家频道相关搜索
     */
    AGENCY_SEO_KEYWORD("biomart", "biomart_web", "searchAgency"),

    /**
     * 发布求购页联想词
     */
    DEMAND_ASSOCIATE_WORD("biomart_demand", "biomart_demand_web", "autoComplete"),

    /**
     * 求购详情页相关求购
     */
    DEMAND_DETAIL_ASSOCIATE("biomart_demand", "biomart_demand_web", "searchDefault"),

    /**
     * 商家店铺产品搜索
     */
    AGENCY_MALL_PRODUCT("biomart", "biomart_web", "searchSingleAgency"),

    /**
     * 产品推荐搜索
     */
    RECOMMEND_PRODUCT("biomart", "biomart_web", "searchPurchase"),

    /**
     * 实验专区 产品推荐搜索
     */
    EXPERIMENT_RECOMMEND_PRODUCT("biomart", "biomart_web", "search4Experiment"),
    /**
     * ivd 求购搜索
     */
    DEMAND_FOR_IVD("biomart_ivd", "biomart_ivd_wechat", null),


    /**
     * 产品关键字搜索v2
     */
    PRODUCT_SEARCH_V2("biomart", "biomart_web", "searchDefaultV2"),


    /**
     * 产品分类搜索v2
     */
    PRODUCT_CATEGORY_SEARCH_V2("biomart", "biomart_web", "searchCategoryV2"),


    /**
     * 小程序首页 分类搜索 （每日top1000 （商家分+产品分倒序，付费优先，按商家折叠）
     */
    PRODUCT_CATEGORY_SEARCH_SIMPLE("biomart_category", "biomart_miniapp", null),

    /**
     * 在线客服faq搜索
     */
    AGENCY_FAQ("biomart_faq", "biomart_faq_web", "searchDefault"),

    /**
     * 研讨会搜索
     */
    SEMINAR("biomart_seminar", "biomart_seminar_web", "searchDefault");
    String core;
    String from;
    String requestHandler;

}
