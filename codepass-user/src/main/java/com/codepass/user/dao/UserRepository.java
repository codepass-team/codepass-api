package com.codepass.user.dao;

import com.codepass.user.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// https://stackoverflow.com/questions/14014086/what-is-difference-between-crudrepository-and-jparepository-interfaces-in-spring
// JpaRepository extends PagingAndSortingRepository which in turn extends CrudRepository.
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    // 这里没有任何类SQL语句就完成了条件查询方法。这就是Spring-data-jpa的一大特性：通过解析方法名创建查询。
    // http://blog.didispace.com/springbootdata2/

    UserEntity findByEmail(String email);
}
