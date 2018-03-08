package com.login.testlogin;

/**
 * Created by L奎 on 2018/3/8.
 */

public class Bean {

    /**
     * resData : {"result":"0","Msg":"密码格式不正确！checkCode=0"}
     */

    private ResDataBean resData;

    public ResDataBean getResData() {
        return resData;
    }

    public void setResData(ResDataBean resData) {
        this.resData = resData;
    }

    public static class ResDataBean {
        /**
         * result : 0
         * Msg : 密码格式不正确！checkCode=0
         */

        private String result;
        private String Msg;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }

        @Override
        public String toString() {
            return "ResDataBean{" +
                    "result='" + result + '\'' +
                    ", Msg='" + Msg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Bean{" +
                "resData=" + resData +
                '}';
    }
}
