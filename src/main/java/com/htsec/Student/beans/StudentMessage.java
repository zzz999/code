package com.htsec.Student.beans;

/**
 * Created by bernard on 2017/9/26.
 */
public class StudentMessage {
    //发送的code
    private String code;
    //发往的code
    private String sendCode;
    //1 文本消息、2 文件消息、3 金融债消息、4 同业拆借消息
    private String type;
    private String message;
    private String fileName;
    private String ref;

    public StudentMessage(){

    }
    public StudentMessage(String type,String content){
        this.type=type;
        if(type.equals("2")){
            this.fileName=content;
        }else{
            this.message=content;
        }
    }
    public StudentMessage(String code,String sendCode,String type,String content,String ref){
        this.code=code;
        this.sendCode=sendCode;
        this.type=type;
        this.ref=ref;
        if(type.equals("2")){
            this.fileName=content;
        }else{
            this.message=content;
        }
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
