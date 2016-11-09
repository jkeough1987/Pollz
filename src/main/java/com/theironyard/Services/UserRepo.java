package com.theironyard.Services;


import com.theironyard.Entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface UserRepo extends PagingAndSortingRepository<User, Integer> {
    User findFirstByName(String name);
    User findById(int id);

    Page<User> findAll(Pageable pageable);
}
