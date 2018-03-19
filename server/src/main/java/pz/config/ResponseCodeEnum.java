package pz.config;

public enum ResponseCodeEnum {
    SUCCESS(0),
    REGISTER_ERROR(2001),
    EDIT_ERROR(3001),
    ADD_ERROR(3002),
    DELETE_ERROR(3003),
    LOGOUT_ERROR(1001);
    public int  CODE;

    ResponseCodeEnum(Integer code) {
        this.CODE = code;
    }

}
