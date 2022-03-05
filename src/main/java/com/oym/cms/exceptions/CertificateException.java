package com.oym.cms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/5 21:11
 */
public class CertificateException extends RuntimeException {
    private static final long serialVersionUID = 1058940050993043833L;

    public CertificateException(String message) {
        super(message);
    }
}
