package com.frame.huxh.mvpdemo.bean;

import java.util.List;

/**
 * Created by huxh on 2017/2/18.
 */

public class AllBankListBean {
    /**
     * returnCode : 0000
     * returnList : [{"bankUrl":"http://www.icbc.com.cn/icbc/","inMax":"50000","inMin":"50","bankType":"ICBC","bankName":"中国工商银行"},{"bankUrl":"http://www.abchina.com/cn/","inMax":"50000","inMin":"40","bankType":"ABC","bankName":"中国农业银行"},{"bankUrl":"http://www.ccb.com/cn/home/index.html","inMax":"50000","inMin":"60","bankType":"CCB","bankName":"中国建设银行"},{"bankUrl":"http://www.psbc.com/portal/zh_CN/index.html","inMax":"50000","inMin":"50","bankType":"PSBC","bankName":"中国邮政储蓄银行"},{"bankUrl":"http://www.cib.com.cn/cn/index.html","inMax":"50000","inMin":"50","bankType":"CIB","bankName":"兴业银行"},{"bankUrl":"http://www.cmbc.com.cn/","inMax":"50000","inMin":"50","bankType":"CMBC","bankName":"中国民生银行"},{"bankUrl":"http://www.pbc.gov.cn/","inMax":"50000","inMin":"50","bankType":"PBC","bankName":"中国人民银行"},{"bankUrl":"http://www.hsbc.com.cn/1/2/","inMax":"50000","inMin":"50","bankType":"HSBC","bankName":"汇丰银行"},{"bankUrl":"http://www.boc.cn/","inMax":"50000","inMin":"50","bankType":"BC","bankName":"中国银行"},{"bankUrl":"http://bank.ecitic.com","inMax":"50000","inMin":"50","bankType":"CITIC","bankName":"中信银行"},{"bankUrl":"http://www.bankcomm.com/BankCommSite/default.shtml","inMax":"50000","inMin":"50","bankType":"BOC","bankName":"交通银行"},{"bankUrl":"http://www.cmbchina.com/","inMax":"50000","inMin":"50","bankType":"CMB","bankName":"招商银行"},{"bankUrl":"http://www.spdb.com.cn/","inMax":"50000","inMin":"50","bankType":"SPDB","bankName":"上海浦东发展银行"},{"bankUrl":"http://www.cebbank.com/","inMax":"50000","inMin":"50","bankType":"CEB","bankName":"中国光大银行"},{"bankUrl":"http://www.hxb.com.cn/home/cn/","inMax":"50000","inMin":"50","bankType":"HB","bankName":"华夏银行"},{"bankUrl":"http://www.cgbchina.com.cn/","inMax":"50000","inMin":"50","bankType":"GDB","bankName":"广东发展银行"},{"bankUrl":"http://","inMax":"50000","inMin":"50","bankType":"QTBANK","bankName":"其他银行"}]
     * returnMap : null
     * returnMessage :
     */

    private String returnCode;
    private Object returnMap;
    private String returnMessage;
    private List<ReturnListBean> returnList;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Object getReturnMap() {
        return returnMap;
    }

    public void setReturnMap(Object returnMap) {
        this.returnMap = returnMap;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * bankUrl : http://www.icbc.com.cn/icbc/
         * inMax : 50000
         * inMin : 50
         * bankType : ICBC
         * bankName : 中国工商银行
         */

        private String bankUrl;
        private String inMax;
        private String inMin;
        private String bankType;
        private String bankName;

        public String getBankUrl() {
            return bankUrl;
        }

        public void setBankUrl(String bankUrl) {
            this.bankUrl = bankUrl;
        }

        public String getInMax() {
            return inMax;
        }

        public void setInMax(String inMax) {
            this.inMax = inMax;
        }

        public String getInMin() {
            return inMin;
        }

        public void setInMin(String inMin) {
            this.inMin = inMin;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }
}
