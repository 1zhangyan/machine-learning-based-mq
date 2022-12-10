import com.google.common.util.concurrent.RateLimiter;
import io.prometheus.client.Counter;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Arrays;
import java.util.List;

public class MQProducer {

    static final Counter produceCount = Counter.build()
            .name("produce_count").help("produce_count").labelNames("producer", "topic").register();

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr(Constant.nameSrvAddr);
        producer.start();

        PrometheusHttpServer.startServer();

        Integer rate = 1;
        Long count = 0L;
        RateLimiter rateLimiter = RateLimiter.create(rate);

        while (count <= Long.MAX_VALUE) {
            rateLimiter.acquire(1);
            Message msg = new Message(Constant.topic ,
                    ("Hello RocketMQ " + ++count).getBytes(RemotingHelper.DEFAULT_CHARSET));
            produceCount.labels(producer.getProducerGroup(), Constant.topic).inc();
            if (count % 50 == 0 && rate <= 100) {
                rate  = rate * 2;
                rateLimiter.setRate(rate);
            }
            if (count % 1000 == 0 && rate > 100) {
                rate  = rate - 20;
                rateLimiter.setRate(rate);
            }


            System.out.println("Send :" + count);
//            SendResult sendResult = producer.send(msg);
//            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
        PrometheusHttpServer.closeServer();
    }
}
