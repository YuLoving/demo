package cn.nj.demo2.yezi;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/2/22 14:29
 * @description ：
 */
public class YeZiStoreInfoRespDTO {

    List<YeZiStoreInfo> sosStoreList;

    public static class YeZiStoreInfo {
        /**
         * 门店编码
         */
        private String storeCode;
        /**
         * 存储点
         */
        private String depotId;
        /**
         * 租户code
         */
        private String companyName;

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getDepotId() {
            return depotId;
        }

        public void setDepotId(String depotId) {
            this.depotId = depotId;
        }


    }

    public List<YeZiStoreInfo> getSosStoreList() {
        return sosStoreList;
    }

    public void setSosStoreList(List<YeZiStoreInfo> sosStoreList) {
        this.sosStoreList = sosStoreList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
