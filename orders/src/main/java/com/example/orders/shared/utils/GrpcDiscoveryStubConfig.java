package com.example.orders.shared.utils;

import com.example.inventory.InventoryGrpcServiceGrpc;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcDiscoveryStubConfig {
    @GrpcClient("inventory-service")
    private Channel inventoryChannel;

    @Bean
    public InventoryGrpcServiceGrpc.InventoryGrpcServiceBlockingStub inventoryStub() {
        return InventoryGrpcServiceGrpc.newBlockingStub(inventoryChannel);
    }
}
