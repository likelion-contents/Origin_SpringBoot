package dev.aquashdw.consumer.service;

import com.google.gson.Gson;
import dev.aquashdw.consumer.model.JobMessage;
import dev.aquashdw.consumer.model.JobProcess;
import dev.aquashdw.consumer.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "boot.amqp.worker-queue")
public class ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final RedisRepository redisRepository;
    private final Gson gson;

    public ConsumerService(
            @Autowired RedisRepository redisRepository,
            @Autowired Gson gson
    ) {
        this.redisRepository = redisRepository;
        this.gson = gson;
    }

    @RabbitHandler
    public void receive(String message) throws InterruptedException {
        String jobId = "";
        try {
            JobMessage newJob = gson.fromJson(message, JobMessage.class);
            jobId = newJob.getJobId();
            logger.info("Received Job: {}", jobId);
            JobProcess jobProcess = new JobProcess();
            jobProcess.setId(newJob.getJobId());
            jobProcess.setMessage("Job being processed");
            jobProcess.setStatus(1);
            jobProcess.setResult("");
            redisRepository.save(jobProcess);
        } catch (RuntimeException e){
            throw new AmqpRejectAndDontRequeueException(e);
        }
        Thread.sleep(5000);
        JobProcess jobProcess = redisRepository.findById(jobId).get();
        jobProcess.setId(jobId);
        jobProcess.setMessage("Finished");
        jobProcess.setStatus(0);
        jobProcess.setResult("Success");
        redisRepository.save(jobProcess);
        logger.info("Finished Job: {}", jobId);
    }
}
