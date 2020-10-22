package com.danieldisu.hnnotify.framework.repositories.user

import com.danieldisu.hnnotify.framework.repositories.data.UserDBO
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserDBO, String> {
}
