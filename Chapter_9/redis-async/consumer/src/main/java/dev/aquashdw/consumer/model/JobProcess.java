package dev.aquashdw.consumer.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Job")
public class JobProcess implements Serializable {
    private String id;
    private int status;
    private String message;
    private String result;

    public JobProcess() {
    }

    public JobProcess(String id, int status, String message, String result) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JobProcess{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
