package org.supermy.core.web;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * 
 * 返回一个ajax对象，构造Extjs必要的属性
 * 序列化、反序列化 JSON format格式  @ResponseBody.
 */
public class ExtResponse {

    /**
     * Simple Success response which can be used for custom ajax calls
     */
    public final static ExtResponse SUCCESS = new ExtResponse(true, null);

    protected String message = null;

    protected boolean success = false;
    
    protected Map<String,String> errors=new HashMap<String ,String>(0);
    protected String msg = null;

    public ExtResponse() {
        //no-op constructor
    }

    public ExtResponse(boolean success) {
        this(success,null);
    }

    public ExtResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
    
}
