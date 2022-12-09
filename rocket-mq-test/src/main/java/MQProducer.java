import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class MQProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name"); //（1）
        producer.setNamesrvAddr(Constant.nameSrvAddr);  //（2）
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message(Constant.topic /* Topic */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            SendResult sendResult = producer.send(msg);   //（4）
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
