package com.studentportal.grpcservice;


import com.studentportal.exceptionHandler.RandomGeneratedException;
import com.studentportal.grpc.*;


import com.studentportal.model.StudentModel;
import com.studentportal.studentPortalService.StudentPortalService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import io.micronaut.grpc.annotation.GrpcService;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@GrpcService
class StudentService extends StudentServiceGrpc.StudentServiceImplBase {
    @Inject
    StudentPortalService studentPortalService;

    @Override
    public void addStudent(Student request, StreamObserver<CreatedResponse> responseObserver) {
        StudentModel sm = StudentModel.builder().email(request.getEmail()).name(request.getName()).standard(request.getStandard()).build();

        StudentModel savedStudent = studentPortalService.addStudent(sm);

        CreatedResponse cr = CreatedResponse.newBuilder().setMessage("Created student with id==" + savedStudent.getId()).build();
        responseObserver.onNext(cr);

        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<Student> studentClientStream(StreamObserver<Empty> responseObserver) {

        return new StreamObserver<Student>() {
            private StudentModel studentModel;

            @Override
            public void onNext(Student value) {
                studentModel = StudentModel.builder().build();
                System.out.println(value);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("THis is in on completed, "+ studentModel.toString());
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void getStudent(Id request, StreamObserver<Student> responseObserver) {
        Optional<StudentModel> studentModel = studentPortalService.findById(request.getId());
        studentModel.ifPresentOrElse(
                sm -> {
                    Student student = Student.newBuilder()
                            .setEmail(sm.getEmail())
                            .setName(sm.getName())
                            .setStandard(sm.getStandard())
                            .build();
                    responseObserver.onNext(student);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("No user with such id").asRuntimeException()));
    }

    @Override
    public void getAllStudents(EmptyRequest request, StreamObserver<StudentsList> responseObserver) {

        List<StudentModel> students = studentPortalService.getAllStudents();

        List<Student> ls = new ArrayList<>();

        students.forEach(currStudent ->
                ls.add(Student.newBuilder().setStandard(currStudent.getStandard()).setName(currStudent.getName()).setEmail(currStudent.getEmail()).build()));

        StudentsList slist = StudentsList.newBuilder().addAllStudents(ls).build();
        responseObserver.onNext(slist);

        responseObserver.onCompleted();

    }

    @Override
    public void changeEmail(EmailChange request, StreamObserver<Student> responseObserver) {

                System.out.println("Request id"+ request.getID().getId());
        try {
           Optional< StudentModel> modifiedStudent = studentPortalService.changeEmail(request.getID().getId(), request.getEmail());

           modifiedStudent.ifPresentOrElse(sm->{
                       responseObserver.onNext(Student.newBuilder()
                               .setEmail(sm.getEmail())
                               .setName(sm.getName())
                               .setStandard(sm.getStandard())
                               .build());

                       responseObserver.onCompleted();

                   },
                   ()->{}


                   );



        }
        catch (Exception e){
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());

        }
    }

    @Override
    public void deleteStudent(Id request, StreamObserver<Student> responseObserver) {
       Optional<StudentModel> deletedStudent= studentPortalService.removeStudent(request.getId());


        responseObserver.onError(Status.NOT_FOUND.withCause(new RandomGeneratedException("No user found")).asRuntimeException());
    }
}
