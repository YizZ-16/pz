package pz.config;

public enum ResponseCodeEnum {
    SUCCESS(0),
    REGISTER_ERROR(2001);
    public int  CODE;

    ResponseCodeEnum(Integer code) {
        this.CODE = code;
    }

}
