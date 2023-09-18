package com.studentportal.repository;

import com.studentportal.model.StudentModel;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;
import org.hibernate.annotations.Parameter;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Integer> {

    @Query("update StudentModel s set s.email=:email where s.id=:id")
    int modifyEmail(int id, String email);
}
