package com.oym.cms.dto;

import com.oym.cms.entity.Certificate;

import java.util.List;

/**
 * 证书数据传输对象
 * @Author: Mr_OO
 * @Date: 2022/3/5 20:46
 */
public class CertificateDTO {
    private Certificate certificate;
    private List<Certificate> certificateList;
    private Integer msg;
    private Integer count;

    public CertificateDTO() {}

    public CertificateDTO(Integer msg) {
        this.msg = msg;
    }

    public CertificateDTO(Certificate certificate, Integer msg) {
        this.certificate = certificate;
        this.msg = msg;
    }

    public CertificateDTO(Integer msg, List<Certificate> certificateList) {
        this.certificateList = certificateList;
        this.msg = msg;
    }

    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Integer getMsg() {
        return msg;
    }

    public void setMsg(Integer msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "certificate=" + certificate +
                ", msg=" + msg +
                '}';
    }
}
