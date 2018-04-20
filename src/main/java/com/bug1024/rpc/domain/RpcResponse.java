package com.bug1024.rpc.domain;

/**
 * @author wangyu
 * @date 2018-04-13
 */
public class RpcResponse {
    private String requestId;
    private Object result;
    private Exception exception;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
