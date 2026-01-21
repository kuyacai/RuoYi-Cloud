package com.ruoyi.product.service;

public interface IWorkflowEngineService {

    void getNextNode(String currentNodeInstanceId);

    void executeNode(String nodeInstanceId);
}