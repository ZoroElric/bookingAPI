// 预定返回响应对象
public class BookingReponse {
    // 返回结果编码
    Integer code;
    // 返回的结果信息
    String msg;

    public BookingReponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // 方便HTML展示，重写toString方法
    @Override
    public String toString() {
        return "BookingReponse{" +
                "</br>code=" + code +
                "</br>msg='" + msg + '\'' +
                "</br>}";
    }
}
