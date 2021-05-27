package cn.nj.demo2.rocketMq;

/**
 * @author ZTY
 * @classname DelayEnum
 * @description RocketMQ 暂时不支持任意的时间精度的延迟，而是固化了 18 个延迟级别
 * @date 2020/12/816:13
 */
public enum DelayEnum {
    /**
     * 延迟级别 1-----时间 1s
     */
    ONE(1,"1s"),
    TWO(2,"5s"),
    THREE(3,"10s"),
    FOUR(4,"30s"),
    FIVE(5,"1m"),
    SIX(6,"2m"),
    SEVEN(7,"3m"),
    EIGHT(8,"4m"),
    NINE(9,"5m"),
    TEN(10,"6m"),
    ELEVEN(11,"7m"),
    TWELVE(12,"8m"),
    THIRTEEN(13,"9m"),
    FOURTEEN(14,"10m"),
    FIFTEEN(15,"20m"),
    SIXTEEN(16,"30m"),
    SEVENTEEN(17,"1h"),
    EIGHTEEN(18,"2h");

    /**
     *延迟级别
     */
    private Integer level;
    /**
     *时间
     */
    private String time;

    DelayEnum(Integer level, String time) {
        this.level = level;
        this.time = time;
    }

    public Integer getLevel() {
        return level;
    }

    public String getTime() {
        return time;
    }
}
