package com.jingtang.constant;

/**
 * ClassName: GameConstants <br/>
 * Description: 工具支持的转换类型 <br/>
 * Author Gyang <br/>
 * Date: 2017-8-1 上午11:30:11 <br/>
 * 
 * @since JDK 1.7
 */
public enum ConvertMethod {

    AMF_2_JSON(1, "amf转json");

    private int type;
    private String desc;

    private ConvertMethod(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

}
