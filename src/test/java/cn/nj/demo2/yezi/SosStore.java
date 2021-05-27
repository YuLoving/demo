package cn.nj.demo2.yezi;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/2/5 10:15
 * @description ：
 */
public class SosStore {

    private List<String> storeCodeList;

    private List<String> depotIdList;

    private String tenantCode;


    public List<String> getStoreCodeList() {
        return storeCodeList;
    }

    public void setStoreCodeList(List<String> storeCodeList) {
        this.storeCodeList = storeCodeList;
    }

    public List<String> getDepotIdList() {
        return depotIdList;
    }

    public void setDepotIdList(List<String> depotIdList) {
        this.depotIdList = depotIdList;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
