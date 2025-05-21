package com.example.orders.shared.utils;

import com.example.inventory.InventoryGrpcServiceGrpc;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

@Configuration
public class GrpcDiscoveryConfiguration {

    @Bean
    public NameResolverProvider eurekaNameResolverProvider(EurekaClient eurekaClient) {
        return new NameResolverProvider() {
            @Override
            public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
                if ("discovery".equals(targetUri.getScheme())) {
                    String serviceName = targetUri.getAuthority();
                    return new NameResolver() {
                        @Override
                        public String getServiceAuthority() {
                            return serviceName;
                        }

                        @Override
                        public void start(Listener2 listener) {
                            List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress(serviceName, false);
                            List<EquivalentAddressGroup> addressGroups = instances.stream()
                                    .map(instance -> {
                                        String grpcPortStr = instance.getMetadata().get("grpc.port");
                                        int grpcPort = grpcPortStr != null ? Integer.parseInt(grpcPortStr) : instance.getPort();
                                        InetSocketAddress socketAddress = new InetSocketAddress(instance.getHostName(), grpcPort);
                                        return new EquivalentAddressGroup(socketAddress);
                                    })
                                    .toList();
                            listener.onAddresses(addressGroups, Attributes.EMPTY);
                        }

                        @Override
                        public void shutdown() {}
                    };
                }
                return null;
            }

            @Override
            public String getDefaultScheme() {
                return "discovery";
            }

            @Override
            public boolean isAvailable() {
                return true;
            }

            @Override
            public int priority() {
                return 5;
            }
        };
    }
}
