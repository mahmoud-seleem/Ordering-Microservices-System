package com.example.orders.core.infrastructure.externalApis;


import com.example.inventory.InventoryGrpcServiceGrpc;
import com.example.inventory.ProductExistenceRequest;
import com.example.inventory.ProductExistenceResponse;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GRPCInventoryClient  {
    // Inject the gRPC blocking stub for the InventoryGrpcService
    // The name 'inventory-grpc-service' matches the configuration in application.properties

    // Define the channel (target server and port)
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
            .usePlaintext() // For testing without TLS
            .build();

    // (Optional) Define call options like timeout
//    CallOptions callOptions = CallOptions.DEFAULT.withDeadlineAfter(5, TimeUnit.SECONDS);

    // Create a blocking stub (you can also use async)
//    InventoryGrpcServiceGrpc.InventoryGrpcServiceBlockingStub stub =
//            InventoryGrpcServiceGrpc.newBlockingStub(channel).withWaitForReady();

    @Autowired
    private InventoryGrpcServiceGrpc.InventoryGrpcServiceBlockingStub stub;

    // Make the gRPC call

//    @GrpcClient("inventory-service")
//    private InventoryGrpcServiceGrpc.InventoryGrpcServiceBlockingStub inventoryGrpcServiceBlockingStub;
//

    @PostConstruct // This method runs after dependency injection
    public void init() {
        if (stub != null) {
            System.out.println("GRPCInventoryClient: inventoryGrpcServiceBlockingStub successfully injected!");
        } else {
            System.err.println("GRPCInventoryClient: inventoryGrpcServiceBlockingStub injection failed!");
        }
    }
    public ProductExistenceResponse checkProductExistence(int productId, String productName) {
        // Build the request message using the generated builder

        ProductExistenceRequest request = ProductExistenceRequest.newBuilder()
                .setProductId(productId)
                .setProductName(productName)
                .build();


//        ProductExistenceRequest request = ProductExistenceRequest.newBuilder()
//                .setProductId(productId)
//                .setProductName(productName)
//                .build();
//
//        // Make the gRPC call using the blocking stub
//        // The method name matches the one defined in the .proto file
        return stub.checkProductExistence(request);
    }

}
