package com.coderman.bizedu.sync.listener;

import com.coderman.bizedu.constant.SyncConstant;
import com.coderman.bizedu.sync.context.SyncContext;
import com.coderman.bizedu.sync.exception.SyncException;
import com.coderman.bizedu.sync.service.result.ResultService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class ActiveMqListener implements MessageListener {

    @Resource
    private ResultService resultService;

    @SneakyThrows
    @Override
    public void onMessage(Message mqMessage) {

        if (mqMessage instanceof TextMessage) {

            try {

                TextMessage message = (TextMessage) mqMessage;
                int retryTimeLimit = 8;
                int deliveryCount = message.getIntProperty("JMSXDeliveryCount");
                String messageId = message.getJMSMessageID();

                if (resultService.successMsgExistRedis(messageId)) {
                    log.error("consumeMessage-重复消息,标记成功:" + messageId);
                    message.acknowledge();
                    return;
                }

                if (deliveryCount > retryTimeLimit) {
                    log.error("投送次数超过:" + retryTimeLimit + "次,不处理当前消息,当前" + deliveryCount + "次,msgID:" + messageId);
                    message.acknowledge();
                    return;
                }

                // 获取消息
                String msg = new String(message.getText().getBytes(), StandardCharsets.UTF_8);

                String result = SyncContext.getContext().syncData(msg, messageId, SyncConstant.MSG_ROCKET_MQ, deliveryCount);
                if (!SyncConstant.SYNC_END.equalsIgnoreCase(result)) {

                    throw new RuntimeException("RocketMqListener同步结果:" + result);
                }

                // 如果没有异常都任务消费成功
                this.resultService.successMsgSave2Redis(messageId, 60);

            } catch (JMSException e) {

                log.error("there is JMS exception: " + e.getMessage());
                throw JmsUtils.convertJmsAccessException(e);
            }
        }
    }


    public static class LogErrorHandle implements ErrorHandler {

        @Override
        public void handleError(Throwable throwable) {

            log.error("消息者监听器消费失败：{}", throwable.getMessage());
        }
    }
}
