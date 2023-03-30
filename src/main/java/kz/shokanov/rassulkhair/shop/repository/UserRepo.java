package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
