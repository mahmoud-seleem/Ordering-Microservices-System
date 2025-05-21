package com.example.inventory.grpc; // Create a suitable package (e.g., grpc)

import com.example.inventory.InventoryGrpcServiceGrpc;
import com.example.inventory.ProductExistenceRequest;
import com.example.inventory.ProductExistenceResponse;
import com.example.inventory.service.InventoryService; // Your existing business logic service
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implements the gRPC service definition from inventory_service.proto.
 * Handles incoming gRPC requests for inventory operations.
 */
@GrpcService // Marks this as a gRPC service bean managed by Spring
// Use @RequiredArgsConstructor if using final fields for injection
public class InventoryGrpcServiceImpl extends InventoryGrpcServiceGrpc.InventoryGrpcServiceImplBase {

    private final InventoryService inventoryService; // Inject your existing service

    @Autowired // Use constructor injection (recommended)
    public InventoryGrpcServiceImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Overrides the CheckProductExistence RPC method defined in the .proto file.
     *
     * @param request          The incoming request message containing product ID and name.
     * @param responseObserver Used to send the response back to the client.
     */
    @Override
    public void checkProductExistence(ProductExistenceRequest request,
                                      StreamObserver<ProductExistenceResponse> responseObserver) {

        System.out.println("gRPC: Received CheckProductExistence request for ID: "
                + request.getProductId() + ", Name: " + request.getProductName());

        // --- Delegate to existing business logic ---
        // Call the method in your InventoryService
        Integer quantityResult = inventoryService.isProductExist(
                request.getProductId(), // Assuming ID is Integer in your service
                request.getProductName()
        );

        // --- Build the gRPC response ---
        ProductExistenceResponse.Builder responseBuilder = ProductExistenceResponse.newBuilder();

        if (quantityResult != null && quantityResult != -1) {
            // Product exists, set exists to true and include the quantity
            responseBuilder.setExists(true);
            responseBuilder.setQuantity(quantityResult);
            System.out.println("gRPC: Product exists. Quantity: " + quantityResult);
        } else {
            // Product does not exist (service returned -1 or null)
            responseBuilder.setExists(false);
            responseBuilder.setQuantity(0); // Or keep default 0
            System.out.println("gRPC: Product does not exist.");
        }

        ProductExistenceResponse response = responseBuilder.build();

        // --- Send the response back to the client ---
        responseObserver.onNext(response);

        // --- Complete the RPC call ---
        responseObserver.onCompleted();
    }

    // Implement other RPC methods defined in your .proto file here...
    // e.g., override reserveProduct if you define that in the proto service
}