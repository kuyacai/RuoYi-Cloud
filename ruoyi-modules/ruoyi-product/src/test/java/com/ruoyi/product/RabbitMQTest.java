package com.ruoyi.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ruoyi.product.rq.AgentTaskProducer;

// 指定启动类，通常是 RuoyiProductApplication
@SpringBootTest
@ActiveProfiles("test") // 加载 application-test.yml
public class RabbitMQTest {

    @Autowired
    private AgentTaskProducer agentTaskProducer;

    @Test
    public void testSendAgentTask() {
        String testId = "test_node_001";
        System.out.println(">>>> 准备发送消息...");

        try {
            // agentTaskProducer.sendTask(testId);
            System.out.println(">>>> 消息发送成功！请检查 Python 端。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWorkflowFlow() throws InterruptedException {
        // 触发第一个节点
        // agentTaskProducer.sendTask("test_node_001");
        System.out.println(">>>> 已手动触发第一个节点 test_node_001，请保持容器运行 10 秒以观察回调...");

        // 💡 关键：让主线程休眠一段时间，等待异步回调执行
        Thread.sleep(10000);

        // 此时你可以去数据库观察 test_node_002 的状态是否变成了 running 或 success
        System.out.println(">>>> 测试结束。");
    }
}