package com.studentportal.studentPortalService;


import com.studentportal.exceptionHandler.RandomGeneratedException;
import com.studentportal.model.StudentModel;
import com.studentportal.repository.StudentRepository;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class StudentPortalService<T> {

    @Inject
    StudentRepository studentRepository;

    public StudentModel addStudent(StudentModel student) {
        System.out.println(student.getEmail());
        return studentRepository.save(student);
    }

    public @NonNull Optional<StudentModel> findById(int id) {
        return studentRepository.findById(id);
    }

    public List<StudentModel> getAllStudents() {
        return studentRepository.findAll();

    }

    public Optional<StudentModel> changeEmail(int id, String email) {
        System.out.println("Id:" + id);
        Optional<StudentModel> optStudentModel = studentRepository.findById(id);

        optStudentModel.ifPresentOrElse(
                sm -> {
                    System.out.println("Student" + sm);
                    var updatedModel = studentRepository.modifyEmail(id, email);

                    System.out.println("Updated model: " + updatedModel);
                    if (updatedModel == 0) {
                        throw new RandomGeneratedException("Failed to update");
                    }
                },
                () -> {
                    throw new RandomGeneratedException("Cannot find the user");
                }
        );


        return studentRepository.findById(id);


    }

    public Optional<StudentModel> removeStudent(int id) {

        return studentRepository.findById(id);
    }

}


