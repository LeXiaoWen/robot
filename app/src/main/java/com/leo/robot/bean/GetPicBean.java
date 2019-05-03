package com.leo.robot.bean;

/**
 * created by Leo on 2019/4/27 21 : 15
 */


public class GetPicBean {

    /**
     * Version : 1.0
     * msgType : CmdUploadPic1
     * params : {"height":"640","width":"480","coordinates":"100.0,210.0"}
     */

    private String Version;
    private String msgType;
    private ParamsBean params;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * height : 640
         * width : 480
         * coordinates : 100.0,210.0
         */

        private String height;
        private String width;
        private String coordinates;

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(String coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public String toString() {
            return "ParamsBean{" +
                    "height='" + height + '\'' +
                    ", width='" + width + '\'' +
                    ", coordinates='" + coordinates + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetPicBean{" +
                "Version='" + Version + '\'' +
                ", msgType='" + msgType + '\'' +
                ", params=" + params +
                '}';
    }
}
