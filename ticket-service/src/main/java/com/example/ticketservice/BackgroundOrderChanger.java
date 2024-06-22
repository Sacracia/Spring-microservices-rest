package com.example.ticketservice;

import com.example.ticketservice.model.Order;
import com.example.ticketservice.repository.OrderRepo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
class BackgroundOrderChanger implements DisposableBean, Runnable {
    @Autowired
    OrderRepo orderRepo;
    private Thread thread;
    private volatile boolean enabled;

    BackgroundOrderChanger(){
        enabled = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run(){
        try {
            while (enabled) {
                Thread.sleep(5000);
                List<Order> orderList = orderRepo.findAllByStatus(1);
                if (orderList.isEmpty())
                    continue;
                Random rand = new Random();
                Order randomOrder = orderList.get(rand.nextInt(orderList.size()));
                randomOrder.setStatus(rand.nextInt(2, 4));
                orderRepo.save(randomOrder);
            }
        }
        catch (Exception ex) {
            destroy();
        }
    }

    @Override
    public void destroy(){
        enabled = false;
    }
}
