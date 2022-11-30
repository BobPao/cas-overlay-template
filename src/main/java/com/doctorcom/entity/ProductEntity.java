package com.doctorcom.entity;

import java.io.Serializable;

public class ProductEntity implements Serializable {

    private Integer fid;
    private String name;
    private String title;
    private String detail;
    private String href;
    private String snapshotUrl;
    private String logoUrl;
    private String informationUrl;
    private String target;
    private Integer sortId;
    private String serviceId;
    private boolean ssoEnable;
    private String contextPath;
    private String moduleId;
    private String moduleVersion;
    private boolean authorizeFlag;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getInformationUrl() {
        return informationUrl;
    }

    public void setInformationUrl(String informationUrl) {
        this.informationUrl = informationUrl;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isSsoEnable() {
        return ssoEnable;
    }

    public void setSsoEnable(boolean ssoEnable) {
        this.ssoEnable = ssoEnable;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public boolean isAuthorizeFlag() {
        return authorizeFlag;
    }

    public void setAuthorizeFlag(boolean authorizeFlag) {
        this.authorizeFlag = authorizeFlag;
    }
}
