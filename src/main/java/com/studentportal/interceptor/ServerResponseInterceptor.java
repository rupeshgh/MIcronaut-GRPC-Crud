package com.studentportal.interceptor;


import io.grpc.*;
import jakarta.inject.Singleton;

@Singleton
public class ServerResponseInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {




        return next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                    @Override
                    public void sendMessage(RespT message) {
                        System.out.println("Intercepting a response");
                        Metadata.Key<String> testHeader=Metadata.Key.of("testingHeader",Metadata.ASCII_STRING_MARSHALLER);
                        headers.put(testHeader,"Hello response header");
//                        super.sendHeaders(headers);
                        super.sendMessage(message);
                    }
                },headers
        );
    }
}
