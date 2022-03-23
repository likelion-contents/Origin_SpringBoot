package dev.aquashdw.consumer.model;

import java.io.Serializable;

public class JobMessage implements Serializable {
    private String jobId;

    public JobMessage() {
    }

    public JobMessage(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "JobMessage{" +
                "jobId='" + jobId + '\'' +
                '}';
    }
}
