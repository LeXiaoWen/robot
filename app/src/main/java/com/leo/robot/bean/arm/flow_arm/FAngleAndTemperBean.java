package com.leo.robot.bean.arm.flow_arm;

/**
 * 从臂 关节角度、关节温度
 * created by Leo on 2019/5/22 21 : 44
 */


public class FAngleAndTemperBean {

    /**
     * msgId : MA_Angle、MA_Temper
     * params : {"A":"0.00","B":"0.00","C":"0.00","D":"0.00","E":"0.00","F":"0.00"}
     */

    private String msgId;
    private ParamsBean params;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * A : 0.00
         * B : 0.00
         * C : 0.00
         * D : 0.00
         * E : 0.00
         * F : 0.00
         */

        private String A;
        private String B;
        private String C;
        private String D;
        private String E;
        private String F;

        public String getA() {
            return A;
        }

        public void setA(String A) {
            this.A = A;
        }

        public String getB() {
            return B;
        }

        public void setB(String B) {
            this.B = B;
        }

        public String getC() {
            return C;
        }

        public void setC(String C) {
            this.C = C;
        }

        public String getD() {
            return D;
        }

        public void setD(String D) {
            this.D = D;
        }

        public String getE() {
            return E;
        }

        public void setE(String E) {
            this.E = E;
        }

        public String getF() {
            return F;
        }

        public void setF(String F) {
            this.F = F;
        }
    }
}
