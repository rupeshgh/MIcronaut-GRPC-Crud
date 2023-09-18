package com.studentportal.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import jakarta.inject.Singleton;

@Singleton
public class ServerRequestInterceptor implements ServerInterceptor{
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("Interception a request");
        String auth=headers.get(Metadata.Key.of("authheader",Metadata.ASCII_STRING_MARSHALLER));

        System.out.println("Header at auth:::"+auth);
        return  next.startCall(call,headers);
    }
}
