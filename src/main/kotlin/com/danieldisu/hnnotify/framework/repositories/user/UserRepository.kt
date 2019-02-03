package com.danieldisu.hnnotify.framework.repositories.user

import com.danieldisu.hnnotify.framework.repositories.interest.data.UserDBO
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserDBO, String> {
}