package cn.nj.demo2.yezi;

import lombok.Data;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/2/22 15:06
 * @description ：
 */

public class DepotStore {

    private List<String> codeList;
    private List<String> typeList;
    private List<String> depotIdList;
    private List<String> depotStoreIdList;
    private List<String> distributorGroupIdList;
    private List<String> distributorIdList;
    private List<String> nameList;
    private List<String> officeIdList;
    private Integer pageNo;
    private Integer pageSize;
    private List<String> statusList;
    private List<String> externalCodeList;
    private Integer storeType;
    private Integer storeAffiliation;
    private List<String> tenantCodeList;

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<String> getDepotIdList() {
        return depotIdList;
    }

    public void setDepotIdList(List<String> depotIdList) {
        this.depotIdList = depotIdList;
    }

    public List<String> getDepotStoreIdList() {
        return depotStoreIdList;
    }

    public void setDepotStoreIdList(List<String> depotStoreIdList) {
        this.depotStoreIdList = depotStoreIdList;
    }

    public List<String> getDistributorGroupIdList() {
        return distributorGroupIdList;
    }

    public void setDistributorGroupIdList(List<String> distributorGroupIdList) {
        this.distributorGroupIdList = distributorGroupIdList;
    }

    public List<String> getDistributorIdList() {
        return distributorIdList;
    }

    public void setDistributorIdList(List<String> distributorIdList) {
        this.distributorIdList = distributorIdList;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getOfficeIdList() {
        return officeIdList;
    }

    public void setOfficeIdList(List<String> officeIdList) {
        this.officeIdList = officeIdList;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public List<String> getExternalCodeList() {
        return externalCodeList;
    }

    public void setExternalCodeList(List<String> externalCodeList) {
        this.externalCodeList = externalCodeList;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Integer getStoreAffiliation() {
        return storeAffiliation;
    }

    public void setStoreAffiliation(Integer storeAffiliation) {
        this.storeAffiliation = storeAffiliation;
    }

    public List<String> getTenantCodeList() {
        return tenantCodeList;
    }

    public void setTenantCodeList(List<String> tenantCodeList) {
        this.tenantCodeList = tenantCodeList;
    }

    @Override
    public String toString() {
        return "DepotStore{}";
    }
}
